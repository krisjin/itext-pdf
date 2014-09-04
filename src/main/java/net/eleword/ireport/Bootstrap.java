package net.eleword.ireport;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.eleword.ireport.parse.BriefReportParse;
import net.eleword.ireport.parse.TemplateParse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

/**
 * @author krisjin
 */
public class Bootstrap {

	public void parseBriefReport(List<BriefingDto> modules, String title, String subTitle) {
		BriefReport br = null;
		try {
			br = BriefReportParse.parse(modules, title, subTitle);
			String html = new TemplateParse().parse("", br);
			br.setOutputPath("C:/Users/kris/Desktop/"+br.getTitle()+".pdf");
			br.setTemplate(html);
			
			try {
				startup(br);
			} catch (DocumentException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void startup(BriefReport br) throws DocumentException, IOException {

		Rectangle rect = new Rectangle(PageSize.A4);
		if (br.getBackground() != null) {
			rect.setBackgroundColor(BaseColorExt.newInstance(br.getBackground()));
		}
		Document doc = new Document(rect);

		doc.setMargins(54, 54, 68, 68);

		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(br.getOutputPath()));

		writer.setPageEvent(new HeaderFooter());

		doc.open();

		FontFactory.registerDirectories();

		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
		registerFont(fontProvider);

		CSSResolver cssResolver = new StyleAttrCSSResolver();
		CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream(br.getCssStyle().getBytes()));
		cssResolver.addCss(cssFile);

		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

		PdfWriterPipeline pdf = new PdfWriterPipeline(doc, writer);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

		XMLWorker worker = new XMLWorker(css, true);
		XMLParser p = new XMLParser(worker);
		p.parse(new ByteArrayInputStream(br.getTemplate().getBytes()));
		doc.close();

	}

	public static void registerFont(XMLWorkerFontProvider fontProvider) {
		Map fontMap = FontConfig.getInstance().getFontMap();
		Iterator iter = fontMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) iter.next();
			fontProvider.register(entry.getValue(), entry.getKey());
		}
	}

	public static void crateTable(Document doc) {
		// BaseFont.createFont(name, encoding, embedded)
		PdfPTable table = new PdfPTable(4);

		try {
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 4, 5, 2, 2 });
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

}

package net.eleword.ireport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
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
	public static final String HTML = "E:/rep/itext-pdf/src/main/resources/template/rbjt.html";
	public static final String CSS = "E:/rep/itext-pdf/src/main/resources/template/rbjt.css";
	
	
	public static void startup(BriefReport br) throws DocumentException, IOException {
		

		Rectangle rect = new Rectangle(PageSize.A4);
		if (br.getBackgroundColor() != null) {
			rect.setBackgroundColor(BaseColorExt.newInstance(br.getBackgroundColor()));
		}
		Document doc = new Document(rect);

		doc.setMargins(br.getMarginLeft(), br.getMarginRight(), br.getMarginTop(), br.getMarginBottom());

		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(br.getOuputPath()));

		writer.setPageEvent(new HeaderFooter());

		doc.open();
		
		FontFactory.registerDirectories();

		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
		registerFont(fontProvider);
		
		
		CSSResolver cssResolver = new StyleAttrCSSResolver();
		CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream(CSS));
		cssResolver.addCss(cssFile);
		
		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

		PdfWriterPipeline pdf = new PdfWriterPipeline(doc, writer);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

		XMLWorker worker = new XMLWorker(css, true);
		XMLParser p = new XMLParser(worker);
		p.parse(new FileInputStream(HTML),Charset.forName("UTF-8"));
		doc.close();

	}
	
	
	public static void registerFont(XMLWorkerFontProvider fontProvider){
		Map fontMap = FontConfig.getInstance().getFontMap();
		Iterator iter = fontMap.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String,String> entry =(Entry<String,String>)iter.next();
			fontProvider.register(entry.getValue(), entry.getKey());
		}
	}

}

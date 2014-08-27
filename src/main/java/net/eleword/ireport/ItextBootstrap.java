package net.eleword.ireport;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

import net.eleword.ireport.model.Summary;
import net.eleword.ireport.util.ReportHelper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * @author krisjin
 */
public class ItextBootstrap {

	public void generateBriefReport(BriefReport br) throws DocumentException, IOException {

		Rectangle rect = new Rectangle(PageSize.A4);
		if (br.getBackgroundColor() != null) {
			rect.setBackgroundColor(BaseColorExt.newInstance(br.getBackgroundColor()));
		}
		Document doc = new Document(rect);

		doc.setMargins(br.getMarginLeft(), br.getMarginRight(), br.getMarginTop(), br.getMarginBottom());

		PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(br.getOuputPath()));

		pdfWriter.setPageEvent(new HeaderFooter());

		doc.open();
		FontFactory.registerDirectories();

		generateParagraphTitle(br, doc);

		doc.add(generateDateAndAuthor(br));

		LineSeparator ls = new LineSeparator(1.5f, 100, BaseColor.RED, Element.ALIGN_CENTER, -2);
		Paragraph divideLine = new Paragraph();
		divideLine.add(ls);
		divideLine.setSpacingAfter(20);
		doc.add(divideLine);

		Summary summary = br.getSummary();
		Paragraph summaryTitle = new Paragraph(summary.getTitle(), new Font(getCustomFont(summary.getTitleFamily()), summary.getTitleFontSize(),
				Font.ITALIC));
		summaryTitle.setSpacingBefore(16);
		summaryTitle.setSpacingAfter(8);
		doc.add(summaryTitle);

		Map<String, String> ms = summary.getModuleSummary();

		for (Entry<String, String> e : ms.entrySet()) {
			Chunk c = new Chunk(String.valueOf((char) Symbol.square), new Font(ReportHelper.getChineseBaseFont(), 9));
			Chunk c2 = new Chunk(" " + e.getKey(), new Font(getCustomFont("E:/gitrep/itext-pdf/src/main/resources/font/KaiTi_GB2312.ttf"), 14,
					Font.BOLD));
			Paragraph p = new Paragraph();
			p.add(c);
			p.add(c2);
			doc.add(p);

			Paragraph content = new Paragraph(e.getValue(), new Font(getCustomFont("E:/gitrep/itext-pdf/src/main/resources/font/KaiTi_GB2312.ttf"),
					14));
			content.setSpacingBefore(4);
			content.setSpacingAfter(4);
			content.setIndentationLeft(16);
			doc.add(content);
		}

		XMLWorkerHelper xwh = XMLWorkerHelper.getInstance();

		xwh.parseXHtml(pdfWriter, doc, new FileInputStream(br.getTemplatePath()), null, Charset.forName("UTF-8"));

		doc.close();
	}

	public void generateParagraphTitle(BriefReport br, Document doc) {
		try {
			BaseFont titleFont = BaseFont.createFont(br.getTitleFontPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			if (br.getTitle().indexOf("|") != -1) {
				String[] titles = br.getTitle().split("\\|");
				Paragraph p1 = new Paragraph(titles[0], new Font(titleFont, 34, Font.NORMAL, BaseColorExt.newInstance(br.getTitleColor())));
				Paragraph p2 = new Paragraph(titles[1], new Font(titleFont, 34, Font.NORMAL, BaseColorExt.newInstance(br.getTitleColor())));
				p1.setAlignment(Element.ALIGN_CENTER);
				p2.setAlignment(Element.ALIGN_CENTER);
				p2.setSpacingBefore(-15.0f);
				doc.add(p1);
				doc.add(p2);

			} else {
				Paragraph p = new Paragraph(br.getTitle(), new Font(titleFont, 34, Font.NORMAL, BaseColorExt.newInstance(br.getTitleColor())));
				doc.add(p);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param br
	 * @return
	 */
	public Paragraph generateDateAndAuthor(BriefReport br) {
		BaseFont font = getCustomFont("E:/gitrep/itext-pdf/src/main/resources/font/MicrosoftYaHei.ttf");

		StringBuilder result = new StringBuilder();
		if (br.getReportDate() != null || !br.getReportDate().equals("")) {
			result.append(br.getReportDate());
		}
		if (br.getAuthor() != null || !br.getAuthor().equals("")) {
			result.append("  ").append(br.getAuthor()).append("  ").append("编");
		}

		Paragraph p = new Paragraph(result.toString(), new Font(font, 10, Font.NORMAL));
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(12);
		return p;
	}

	/**
	 * @param fontPath
	 * @return
	 */
	public BaseFont getCustomFont(String fontPath) {
		BaseFont font = null;
		try {
			font = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return font;
	}
}

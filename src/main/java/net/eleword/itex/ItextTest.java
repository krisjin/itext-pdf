package net.eleword.itex;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ItextTest {

	public static void main(String[] args) throws Exception {

		BriefReport pdf = new BriefReport();
		pdf.setTemplatePath("E:/pitaya/src/main/webapp/rbjt.html");
		pdf.setTitle("中国人民保险集团|舆情周报");
		pdf.setOuputPath("C:/Users/kris/Desktop/ouput.pdf");
		pdf.setTitleFontPath("E://gitrep//itext-pdf//src//main//resources//font//FZXiaoBiaoSong-B05S.ttf");

		generateBriefReport(pdf);
	}

	public static void generateBriefReport(BriefReport pdf) throws DocumentException, IOException {

		BaseFont titleFont = BaseFont.createFont(pdf.getTitleFontPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		Rectangle rect = new Rectangle(PageSize.A4);

		if (pdf.getBackgroundColor() != null) {
			rect.setBackgroundColor(BaseColorExt.newInstance(pdf.getBackgroundColor()));
		}
		Document doc = new Document(rect);

		doc.setMargins(54, 54, 68, 68);

		PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(pdf.getOuputPath()));

		pdfWriter.setPageEvent(new HeaderFooter());

		doc.open();

		if (pdf.getTitle().indexOf("|") != -1) {
			String[] titles = pdf.getTitle().split("\\|");
			Paragraph p1 = new Paragraph(titles[0], new Font(titleFont, 34, Font.NORMAL,
					BaseColorExt.newInstance(pdf.getTitleColor())));
			Paragraph p2 = new Paragraph(titles[1], new Font(titleFont, 34, Font.NORMAL,
					BaseColorExt.newInstance(pdf.getTitleColor())));
			p1.setAlignment(Element.ALIGN_CENTER);
			p2.setAlignment(Element.ALIGN_CENTER);
			p2.setSpacingBefore(-15.0f);
			doc.add(p1);
			doc.add(p2);
		} else {
			doc.add(new Paragraph(pdf.getTitle(), new Font(titleFont, 34, Font.NORMAL, BaseColorExt.newInstance(pdf
					.getTitleColor()))));
		}

		LineSeparator ls = new LineSeparator(1.5f, 100, BaseColor.RED, Element.ALIGN_CENTER, -2);
		Paragraph paraLine = new Paragraph();
		paraLine.add(ls);
		doc.add(paraLine);

		XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, doc, new FileInputStream(pdf.getTemplatePath()), null,
				new ChineseFontProvider());

		doc.close();
	}

}

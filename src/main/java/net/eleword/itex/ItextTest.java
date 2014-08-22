package net.eleword.itex;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
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

public class ItextTest {

	public static void main(String[] args) throws Exception {

		BriefReport br = new BriefReport();
		br.setTemplatePath("E:/pitaya/src/main/webapp/rbjt.html");
		br.setTitle("中国人民保险集团|舆情周报");
		br.setOuputPath("C:/Users/kris/Desktop/ouput.pdf");
		br.setTitleFontPath("E://gitrep//itext-pdf//src//main//resources//font//FZXiaoBiaoSong-B05S.ttf");
		br.setReportDate("2014-06-30至2014-07-06");
		br.setAuthor("和讯通编辑部");

		ItextTest it = new ItextTest();

		it.generateBriefReport(br);
	}

	public void generateBriefReport(BriefReport pdf) throws DocumentException, IOException {

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
		FontFactory.registerDirectories();

		if (pdf.getTitle().indexOf("|") != -1) {
			String[] titles = pdf.getTitle().split("\\|");
			Paragraph p1 = new Paragraph(titles[0], new Font(titleFont, 34, Font.NORMAL, BaseColorExt.newInstance(pdf.getTitleColor())));
			Paragraph p2 = new Paragraph(titles[1], new Font(titleFont, 34, Font.NORMAL, BaseColorExt.newInstance(pdf.getTitleColor())));
			p1.setAlignment(Element.ALIGN_CENTER);
			p2.setAlignment(Element.ALIGN_CENTER);
			p2.setSpacingBefore(-15.0f);
			doc.add(p1);
			doc.add(p2);
		} else {
			doc.add(new Paragraph(pdf.getTitle(), new Font(titleFont, 34, Font.NORMAL, BaseColorExt.newInstance(pdf.getTitleColor()))));
		}

		doc.add(generateDateAndAuthor(pdf));

		LineSeparator ls = new LineSeparator(1.5f, 100, BaseColor.RED, Element.ALIGN_CENTER, -2);
		Paragraph divideLine = new Paragraph();
		divideLine.add(ls);
		divideLine.setSpacingAfter(20);
		doc.add(divideLine);

		XMLWorkerHelper xwh = XMLWorkerHelper.getInstance();

		xwh.parseXHtml(pdfWriter, doc, new FileInputStream(pdf.getTemplatePath()), null, new ChineseFontProvider());

		doc.close();
	}

	public List<Paragraph> generateParagraphTitle(BriefReport br) {
		List<Paragraph> pList = new ArrayList<Paragraph>();
		try {
			BaseFont titleFont = BaseFont.createFont(br.getTitleFontPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			if (br.getTitle().indexOf("|") != -1) {
				String[] titles = br.getTitle().split("\\|");
				Paragraph p1 = new Paragraph(titles[0], new Font(titleFont, 34, Font.NORMAL, BaseColorExt.newInstance(br.getTitleColor())));
				Paragraph p2 = new Paragraph(titles[1], new Font(titleFont, 34, Font.NORMAL, BaseColorExt.newInstance(br.getTitleColor())));
				p1.setAlignment(Element.ALIGN_CENTER);
				p2.setAlignment(Element.ALIGN_CENTER);
				p2.setSpacingBefore(-15.0f);
				pList.add(p1);
				pList.add(p2);

			} else {
				Paragraph p = new Paragraph(br.getTitle(), new Font(titleFont, 34, Font.NORMAL,
						BaseColorExt.newInstance(br.getTitleColor())));
				pList.add(p);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pList;
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

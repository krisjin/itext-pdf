package net.eleword.itex;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ItextTest {

	public static void main(String[] args) throws Exception {
		String pdfPath = "e://xxx.pdf";
		createPdf(pdfPath);
	}

	public static void createPdf(String filename) throws DocumentException, IOException {
		Rectangle rect = new Rectangle(PageSize.A4);
		rect.setBackgroundColor(BaseColor.ORANGE);
		Document doc = new Document(rect);

		// 页边空白
		doc.setMargins(40, 40, 20, 40);
		PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(filename));

		pdfWriter.setPageEvent(new HeaderFooter());
//		PDFUtil.encrypt(pdfWriter, "name", "123");
		doc.open();

		XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, doc, new FileInputStream("e:/index.html"), null,
				new ChineseFontProvider());
		
		PDFHelper.generateCatalog(doc, pdfWriter);

		// document.addTitle("测试问题");
		// document.add(new Paragraph("Hello xxx范德薩發沙發",font));
		doc.close();
	}

}

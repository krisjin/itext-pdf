package net.eleword.itex;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ItextTest {

	public static void main(String[] args) throws Exception {
		String pdfPath = "C:/Users/kris/Desktop/ouput.pdf";
		createPdf(pdfPath);
	}

	public static void createPdf(String filename) throws DocumentException, IOException {
		
//		BaseFont baseFont = BaseFont.createFont("d://font//经典粗宋简.TTF", BaseFont.IDENTITY_H,  BaseFont.EMBEDDED);
		
		
		Rectangle rect = new Rectangle(PageSize.A4);
		//rect.setBackgroundColor(BaseColor.ORANGE);
		Document doc = new Document(rect);

		doc.setMargins(40, 40, 20, 40);
		PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(filename));

		pdfWriter.setPageEvent(new HeaderFooter());
		
		doc.open();
		
		FontFactory.registerDirectory("d:/font/");
		Set<String> sets= FontFactory.getRegisteredFonts();
		Iterator iter = sets.iterator();
		while(iter.hasNext()){
			String ft=(String)iter.next();
			System.out.println(ft);
			doc.add(new Paragraph(ft+"-Hello人保集团",FontFactory.getFont(ft,22)));
		}
		
		
		
//		 doc.add(new Paragraph("人保集团周报",new Font(baseFont,28)));
		
		
//		Font fontbold = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
//		
//		doc.add(new Paragraph("Times-Roman, Boldniha", fontbold));

		XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, doc, new FileInputStream("e:/rbjt.html"), null, new ChineseFontProvider());
		PDFHelper.generateCatalog(doc, pdfWriter);

		// document.addTitle("测试问题");
		// document.add(new Paragraph("Hello xxx范德薩發沙發",font));
		doc.close();
	}

}

package net.eleword.itex;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ItextTest {

	public static final Font FONT = new Font(FontFamily.HELVETICA, 10);
	public static final String[] FIELDS = { "name", "abbr", "capital", "city", "population", "surface", "timezone1",
			"timezone2", "dst" };
	protected static Map<String, Rectangle> positions;

	public static void main(String[] args) throws Exception {
		String pdfPath = "e://你好嗎.pdf";
		createPdf(pdfPath);
	}

	public static void createPdf(String filename) throws DocumentException, IOException {
		// BaseFont baseFont = BaseFont.createFont("STSong-Light",
		// "UniGB-UCS2-H", BaseFont.EMBEDDED);
		// Font font =new Font(baseFont);
		PdfReader reader = new PdfReader(filename);
		AcroFields form = reader.getAcroFields();

		positions = new HashMap<String, Rectangle>();
		Rectangle rectangle;
		Map<String, AcroFields.Item> fields = form.getFields();
		for (String name : fields.keySet()) {
			rectangle = form.getFieldPositions(name).get(0).position;
			positions.put(name, rectangle);
		}

		Document doc = new Document(PageSize.A4);
		doc.setMargins(80, 80, 30, 30);
		doc.setPageCount(11);
		doc.addAuthor("krisibm@163.com");

		PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream(filename));
		pdf.setPageEvent(new Background(pdf.getImportedPage(reader, 1)));
		doc.open();

		PdfContentByte cb = pdf.getDirectContent();
		StringTokenizer tokenizer;
		BufferedReader br = new BufferedReader(new FileReader(DATA));
		String line = br.readLine();
		while ((line = br.readLine()) != null) {
			int i = 0;
			tokenizer = new StringTokenizer(line, ";");
			while (tokenizer.hasMoreTokens()) {
				process(cb, FIELDS[i++], tokenizer.nextToken());
			}
			doc.newPage();
		}
		br.close();

		XMLWorkerHelper.getInstance().parseXHtml(pdf, doc, new FileInputStream("e:/index.html"), null,
				new ChineseFontProvider());

		// document.addTitle("测试问题");
		// document.add(new Paragraph("Hello xxx范德薩發沙發",font));
		doc.close();
	}

	protected static void process(PdfContentByte cb, String name, String value) throws DocumentException {
		Rectangle rect = positions.get(name);
		Phrase p = new Phrase(value, FONT);
		ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, p, rect.getLeft() + 2, rect.getBottom() + 2, 0);
	}

	public static class Background extends PdfPageEventHelper {

		PdfImportedPage background;

		public Background(PdfImportedPage background) {
			this.background = background;
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContentUnder();
			cb.addTemplate(background, 0, 0);
			ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase("page " + writer.getPageNumber()), 550, 800,
					0);
		}

	}
}

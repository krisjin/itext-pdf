package net.eleword.itex;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
	private int pageCount;
	private String pageNumberPosition;
	private Font font = null;

	public HeaderFooter() {
		font = new Font();
		font.setColor(BaseColor.BLACK);
		font.setSize(10);
	}

	public void onOpenDocument(PdfWriter writer, Document document) {
		pageCount = 0;
	}

	public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title) {
	}

	public void onStartPage(PdfWriter writer, Document document) {
		pageCount++;
	}

	// public void onEndPage(PdfWriter writer, Document document) {
	// if (pageCount == 1) {
	// this.pageNumberPosition = "left";
	// }
	// writer.getDefaultColorspace();
	// int offsetX = Integer.valueOf(300);
	// int offsetY = Integer.valueOf(10);
	// if ("left".equalsIgnoreCase(this.pageNumberPosition)) {
	// ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
	// new Phrase(Integer.toString(pageCount), font), 600 - offsetX, offsetY,
	// 0);
	// pageNumberPosition = "right";
	// } else if ("right".equalsIgnoreCase(this.pageNumberPosition)) {
	// ColumnText.showTextAligned(writer.getDirectContent(),
	// Element.ALIGN_RIGHT,
	// new Phrase(Integer.toString(pageCount), font), offsetX, offsetY, 0);
	// pageNumberPosition = "left";
	// }
	// }

	public void onEndPage(PdfWriter writer, Document document) {

		PdfContentByte cb = writer.getDirectContent();
		cb.saveState();

		cb.beginText();
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cb.setFontAndSize(bf, 10);

		// Header
		// float x = document.top(-20);
		// 左
		// cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "H-Left",
		// document.left(), x, 0);
		// 中
		// cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
		// writer.getPageNumber() + " page",(document.right() + document.left())
		// / 2, x, 0);
		// 右
		// cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "H-Right",
		// document.right(), x, 0);

		// Footer
		float y = document.bottom(-20);

		// 左
		// cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "F-Left",
		// document.left(), y, 0);
		// 中
		cb.showTextAligned(PdfContentByte.ALIGN_CENTER, writer.getPageNumber() + "页",
				(document.right() + document.left()) / 2, y, 0);
		// 右
		// cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "F-Right",
		// document.right(), y, 0);

		cb.endText();

		cb.restoreState();
	}
}

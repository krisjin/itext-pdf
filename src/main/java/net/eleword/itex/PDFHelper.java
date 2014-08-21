package net.eleword.itex;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFHelper {

	public static void encrypt(PdfWriter pdfWriter, String userName, String password) {
		try {
			pdfWriter.setEncryption("Hello".getBytes(), "World".getBytes(), PdfWriter.ALLOW_SCREENREADERS,
					PdfWriter.STANDARD_ENCRYPTION_128);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public static InputStream getPdfHtmlTemplateWithString(String str) {
		InputStream fis = new ByteArrayInputStream(str.getBytes());
		return fis;
	}

	public static InputStream getPdfHtmlTemplateWithFile(String path) {
		if (path == null) {
			throw new IllegalArgumentException("pdf template path is null");
		}
		if (!(new File(path).exists())) {
			throw new IllegalArgumentException("pdf template path is not exist");
		}
		InputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fis;
	}

	public static void generateCatalog(Document document, PdfWriter writer) throws DocumentException {

		// Code 1
		document.add(new Chunk("Chapter 1").setLocalDestination("1"));

		document.newPage();
		document.add(new Chunk("Chapter 2").setLocalDestination("2"));
		document.add(new Paragraph(new Chunk("Sub 2.1").setLocalDestination("2.1")));
		document.add(new Paragraph(new Chunk("Sub 2.2").setLocalDestination("2.2")));

		document.newPage();
		document.add(new Chunk("Chapter 3").setLocalDestination("3"));

		// Code 2
		PdfContentByte cb = writer.getDirectContent();
		PdfOutline root = cb.getRootOutline();

		// Code 3
		@SuppressWarnings("unused")
		PdfOutline oline1 = new PdfOutline(root, PdfAction.gotoLocalPage("1", false), "Chapter 1");

		PdfOutline oline2 = new PdfOutline(root, PdfAction.gotoLocalPage("2", false), "Chapter 2");
		oline2.setOpen(false);

		@SuppressWarnings("unused")
		PdfOutline oline2_1 = new PdfOutline(oline2, PdfAction.gotoLocalPage("2.1", false), "Sub 2.1");
		@SuppressWarnings("unused")
		PdfOutline oline2_2 = new PdfOutline(oline2, PdfAction.gotoLocalPage("2.2", false), "Sub 2.2");

		@SuppressWarnings("unused")
		PdfOutline oline3 = new PdfOutline(root, PdfAction.gotoLocalPage("3", false), "Chapter 3");
	}

	public static void registerFont(String fontPath){
		 FontFactory.registerDirectory(fontPath);
	}
}

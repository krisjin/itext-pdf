package net.eleword.itex;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

/**
 * 
 * @author kris
 */
public class ChineseFontProvider extends XMLWorkerFontProvider {

	public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color) {

		BaseFont bf = null;
		try {
			bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Font font = new Font(bf, size, style, color);
		font.setColor(color);
		return font;
	}

}

package net.eleword.itex;

import com.itextpdf.text.BaseColor;

public class BaseColorExt extends BaseColor {

	public BaseColorExt(int argb) {
		super(argb);
	}

	public static BaseColor newInstance(String rgb) {
		if (rgb.equals("")) {
			return null;
		}
		String[] c = rgb.split(",");
		BaseColor color = new BaseColor(Integer.valueOf(c[0]), Integer.valueOf(c[1]), Integer.valueOf(c[2]));
		return color;
	}

}

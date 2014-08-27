package net.eleword.ireport;

import java.io.IOException;

import net.eleword.ireport.util.PathUtil;

public class Font {

	public static void main(String[] args) {
		String path = "";
		try {
			path = PathUtil.getFullPathRelateClass("../../../font",Font.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(path);
	}
}

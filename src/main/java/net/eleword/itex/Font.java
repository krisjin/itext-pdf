package net.eleword.itex;

import java.util.Iterator;
import java.util.Set;

import com.itextpdf.text.FontFactory;

public class Font {

	public static void main(String[] args) {
//		Set<String> set = FontFactory.getRegisteredFamilies();
//		FontFactory.registerDirectories();
		Set<String> set = FontFactory.getRegisteredFonts();
		
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
			
		}
	}
}

package net.eleword.ireport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import net.eleword.ireport.util.PathUtil;

/**
 * @author krisjin
 */
public class FontConfig {

	private Map<String, String> fontMap = new HashMap<String, String>();

	private FontConfig() {
		String fontPath = "";
		String fontConfigFile = "";
		Properties prop = new Properties();
		try {
			fontPath = PathUtil.getFullPathRelateClass("../../../font", FontConfig.class);
			fontConfigFile = PathUtil.getFullPathRelateClass("../../../fonts.properties", FontConfig.class);

			prop.load(new FileInputStream(fontConfigFile));
			
			Iterator<Entry<Object,Object>> iter = prop.entrySet().iterator();
			while(iter.hasNext()){
				Entry<Object,Object> entry = iter.next();
				entry.getKey();
				fontMap.put((String)entry.getKey(), fontPath+File.separator+entry.getValue());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static class FontConfigHolder {
		private static FontConfig instance = new FontConfig();
	}

	public static FontConfig getInstance() {
		return FontConfigHolder.instance;
	}

	public Map<String, String> getFontMap() {
		return fontMap;
	}

	
}

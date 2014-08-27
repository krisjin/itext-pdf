package net.eleword.ireport.model;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author kris
 */
public class Summary {
	
	private String title;
	
	private String titleFamily;
	
	private boolean isTitleFontBold;
	
	private boolean isTitleFontItalics;
	
	private int titleFontSize;

	private String symbol;
	
	private boolean isSymbol;
	
	private String content;
	
	
	
	private Map<String, String> moduleSummary = new LinkedHashMap<String, String>();

	public void addModuleSummary(String name, String conent) {
		moduleSummary.put(name, conent);
	}

	public void clean() {
		moduleSummary.clear();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitleFamily() {
		return titleFamily;
	}

	public void setTitleFamily(String titleFamily) {
		this.titleFamily = titleFamily;
	}

	public boolean isTitleFontBold() {
		return isTitleFontBold;
	}

	public void setTitleFontBold(boolean isTitleFontBold) {
		this.isTitleFontBold = isTitleFontBold;
	}

	public boolean isTitleFontItalics() {
		return isTitleFontItalics;
	}

	public void setTitleFontItalics(boolean isTitleFontItalics) {
		this.isTitleFontItalics = isTitleFontItalics;
	}

	public int getTitleFontSize() {
		return titleFontSize;
	}

	public void setTitleFontSize(int titleFontSize) {
		this.titleFontSize = titleFontSize;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public boolean isSymbol() {
		return isSymbol;
	}

	public void setSymbol(boolean isSymbol) {
		this.isSymbol = isSymbol;
	}

	public Map<String, String> getModuleSummary() {
		return moduleSummary;
	}

	public void setModuleSummary(Map<String, String> moduleSummary) {
		this.moduleSummary = moduleSummary;
	}
	
	
	
}

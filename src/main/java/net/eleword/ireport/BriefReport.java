package net.eleword.ireport;

import net.eleword.ireport.model.Summary;

/**
 * @author krisjin
 */
public class BriefReport {

	private int marginLeft;

	private int marginRight;

	private int marginTop;

	private int marginBottom;

	private boolean isGenerateDirectory;
	/**
	 * HTML string template
	 */
	private String template;

	/**
	 * template path
	 */
	private String templatePath;

	/**
	 * Output generate brief report path
	 */
	private String ouputPath;

	/**
	 * brief report background color
	 */
	private String backgroundColor;

	private String title;

	/**
	 * initialize brief report title color is red
	 */
	private String titleColor = "255,0,0";

	private String titleFontPath;
	
	private String titleSize;

	private String reportFileName;

	private String reportDate;
	
	private String author;
	
	private Summary summary;
	
	private String css;
	
	private String cssPath;
	
	
	
	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getCssPath() {
		return cssPath;
	}

	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	public String getTitleSize() {
		return titleSize;
	}

	public void setTitleSize(String titleSize) {
		this.titleSize = titleSize;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public int getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}

	public int getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(int marginRight) {
		this.marginRight = marginRight;
	}

	public int getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}

	public int getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(int marginBottom) {
		this.marginBottom = marginBottom;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getOuputPath() {
		return ouputPath;
	}

	public void setOuputPath(String ouputPath) {
		this.ouputPath = ouputPath;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	public String getTitleFontPath() {
		return titleFontPath;
	}

	public void setTitleFontPath(String titleFontPath) {
		this.titleFontPath = titleFontPath;
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public boolean isGenerateDirectory() {
		return isGenerateDirectory;
	}

	public void setGenerateDirectory(boolean isGenerateDirectory) {
		this.isGenerateDirectory = isGenerateDirectory;
	}
	
	
}

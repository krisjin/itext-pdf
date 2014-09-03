package net.eleword.ireport.model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author shijingui
 * @date 2014-9-2上午09:22:46
 */
public class BriefReport {

	private String title;

	private String subTitle;

	private String logo;

	private LinkedHashMap<String, List<News>> moduleNews;

	private LinkedHashMap<String, String> moduleDescription;

	private LinkedHashMap<String, String> publicOpinionOverview;

	private String publicOpinionSummary;

	public LinkedHashMap<String, String> getModuleDescription() {
		return moduleDescription;
	}

	public void setModuleDescription(LinkedHashMap<String, String> moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	public LinkedHashMap<String, String> getPublicOpinionOverview() {
		return publicOpinionOverview;
	}

	public void setPublicOpinionOverview(LinkedHashMap<String, String> publicOpinionOverview) {
		this.publicOpinionOverview = publicOpinionOverview;
	}

	public String getPublicOpinionSummary() {
		return publicOpinionSummary;
	}

	public void setPublicOpinionSummary(String publicOpinionSummary) {
		this.publicOpinionSummary = publicOpinionSummary;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public LinkedHashMap<String, List<News>> getModuleNews() {
		return moduleNews;
	}

	public void setModuleNews(LinkedHashMap<String, List<News>> moduleNews) {
		this.moduleNews = moduleNews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

}

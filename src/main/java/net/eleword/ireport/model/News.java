package net.eleword.ireport.model;


import java.util.List;

/**
 * @author shijingui
 * @date 2014-9-2上午09:42:41
 */
public class News {

	private String newsMedia;
	private String newsAuthor;
	private String newsPostTime;
	private String newsContent;
	private String newsDescription;
	private String newsTitle;
	private int sameNewsCount;
	private List<String> forwardMedia;
	private String newsLink;

	public String getNewsMedia() {
		return newsMedia;
	}

	public void setNewsMedia(String newsMedia) {
		this.newsMedia = newsMedia;
	}

	public String getNewsAuthor() {
		return newsAuthor;
	}

	public void setNewsAuthor(String newsAuthor) {
		this.newsAuthor = newsAuthor;
	}

	public String getNewsPostTime() {
		return newsPostTime;
	}

	public void setNewsPostTime(String newsPostTime) {
		this.newsPostTime = newsPostTime;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getNewsDescription() {
		return newsDescription;
	}

	public void setNewsDescription(String newsDescription) {
		this.newsDescription = newsDescription;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public int getSameNewsCount() {
		return sameNewsCount;
	}

	public void setSameNewsCount(int sameNewsCount) {
		this.sameNewsCount = sameNewsCount;
	}

	public List<String> getForwardMedia() {
		return forwardMedia;
	}

	public void setForwardMedia(List<String> forwardMedia) {
		this.forwardMedia = forwardMedia;
	}

	public String getNewsLink() {
		return newsLink;
	}

	public void setNewsLink(String newsLink) {
		this.newsLink = newsLink;
	}

}

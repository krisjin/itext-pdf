package net.eleword.ireport.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.eleword.ireport.model.HtmlTagName;
import net.eleword.ireport.model.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author shijingui
 * @date 2014-9-2上午09:19:52
 */
public class BriefReportParse {

	public static BriefReport parse(List<BriefingDto> modules, String title, String subTitle) throws IOException {
		LinkedHashMap<String, List<News>> moduleNews = new LinkedHashMap<String, List<News>>();
		BriefReport briefReport = new BriefReport();

		for (BriefingDto br : modules) {
			if (br.getModuleName().equals("舆情概要")) {
				briefReport.setPublicOpinionSummary(br.getModuleContent());
			} else {
				String content = br.getModuleContent();
				moduleNews.put(br.getModuleName(), parseNews(content));
			}
		}
		briefReport.setTitle(title);
		briefReport.setSubTitle(subTitle);
		briefReport.setModuleNews(moduleNews);
		return briefReport;
	}

	private static List<News> parseNews(String content) throws IOException {
		List<News> newsList = new ArrayList<News>();
		Document doc = Jsoup.parse(content);// (new File("d:/a.txt"), "UTF-8");

		Elements mediaEle = doc.getElementsByAttributeValue(HtmlTagName.TAG_ATTRIBUTE_NAME, HtmlTagName.MEDIA);
		Elements postTimeEle = doc.getElementsByAttributeValue(HtmlTagName.TAG_ATTRIBUTE_NAME, HtmlTagName.POST_TIME);
		Elements authorEle = doc.getElementsByAttributeValue(HtmlTagName.TAG_ATTRIBUTE_NAME, HtmlTagName.AUTHOR);
		Elements forwardCountEle = doc.getElementsByAttributeValue(HtmlTagName.TAG_ATTRIBUTE_NAME, HtmlTagName.SAME_NEWS_COUNT);
		Elements forwardMediaEle = doc.getElementsByAttributeValue(HtmlTagName.TAG_ATTRIBUTE_NAME, HtmlTagName.FORWARD_MEDIA);
		Elements contentEle = doc.getElementsByAttributeValue(HtmlTagName.TAG_ATTRIBUTE_NAME, HtmlTagName.NEWS_CONTENT);
		Elements titleEle = doc.select(HtmlTagName.NEWS_TITLE);

		if (titleEle.size() > 0) {
			for (int i = 0; titleEle.size() > i; i++) {
				News news = new News();

				Element aEle = titleEle.get(i).child(0);
				String newsLink = aEle.attr("href");
				String newsTitle = aEle.html();
				String media = mediaEle.get(i).html();
				String postTime = postTimeEle.get(i).html();
				String newsAuthor = authorEle.get(i).html();
				String forwardCount = forwardCountEle.get(i).html();
				String forwardMedia = forwardMediaEle.get(i).html();
				String newsContent = contentEle.get(i).html();

				news.setNewsLink(newsLink);
				news.setNewsTitle(newsTitle);
				news.setNewsAuthor(newsAuthor);
				news.setNewsMedia(media);
				news.setNewsPostTime(postTime);
				news.setForwardCount(forwardCount);
				news.setForwardMedia(null);
				news.setNewsContent(newsContent);

				newsList.add(news);
			}
		}
		return newsList;
	}

	// public static void main(String[] args) throws IOException {
	//
	// parseNews("");
	//
	// }

}

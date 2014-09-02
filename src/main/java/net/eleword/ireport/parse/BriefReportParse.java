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


	private static List<News> parseNews(String content) throws IOException {
		Document doc = Jsoup.parse(new File("d:/a.txt"), "UTF-8");

		Elements medias = doc.getElementsByAttributeValue("name", HtmlTagName.MEDIA);
		Elements postTime = doc.getElementsByAttributeValue("name", HtmlTagName.POST_TIME);
		Elements author = doc.getElementsByAttributeValue("name", HtmlTagName.AUTHOR);

		Elements newsTitle = doc.select("h4");
		for (int i = 0; newsTitle.size() > 0; i++) {
			Element h4 = newsTitle.get(i).nextElementSibling();

			String newsLink = h4.attr("href");
			String nt = h4.html();
			String media = medias.get(i).html();
			String pt = postTime.get(i).html();
			String newsAuthor = author.get(i).html();
		}

		List<News> newsList = new ArrayList<News>();
		News news = new News();

		return newsList;
	}

	public static void main(String[] args) throws IOException {

		parseNews("");

	}

}

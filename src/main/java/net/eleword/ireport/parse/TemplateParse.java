package net.eleword.ireport.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import net.eleword.ireport.ClientTest;
import net.eleword.ireport.model.BriefReport;
import net.eleword.ireport.model.News;
import net.eleword.ireport.util.PathUtil;
import net.eleword.ireport.util.ReflectUtils;
import net.eleword.ireport.util.TplConst;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TemplateParse {

	public  String parse(String template, BriefReport br) throws IOException {
		String tpl = PathUtil.getFullPathRelateClass("../../.../../../../classes/template", ClientTest.class);
		Document doc = Jsoup.parse(new File(tpl + "/rbjt.html"), "UTF-8");
		StringBuilder sb = new StringBuilder(doc.toString());
		sb.replace(sb.indexOf(TplConst.PROP_REPORT_TITLE), sb.indexOf(TplConst.PROP_REPORT_TITLE) + TplConst.PROP_REPORT_TITLE.length(), br.getTitle());
		sb.replace(sb.indexOf(TplConst.PROP_SUB_TITLE), sb.indexOf(TplConst.PROP_SUB_TITLE) + TplConst.PROP_SUB_TITLE.length(), br.getSubTitle());

		doc = Jsoup.parse(sb.toString());
		// ----------------
		Element overViewEle = doc.getElementById(TplConst.ID_OVERVIEW);
		Elements overViewModuleEle = doc.getElementsByTag(TplConst.TAG_OVERVIEW_MODULE_LOOP);
		StringBuilder newNode = new StringBuilder();
		StringBuilder tplPart = new StringBuilder(overViewModuleEle.html());

		if (br.getPublicOpinionOverview() != null && br.getPublicOpinionOverview().size() > 0) {
			LinkedHashMap<String, String> overView = br.getPublicOpinionOverview();
			Set<Entry<String, String>> sets = overView.entrySet();
			Iterator<Entry<String, String>> iter = sets.iterator();
			while (iter.hasNext()) {
				Entry<String, String> e = iter.next();
				tplPart.replace(tplPart.indexOf(TplConst.PROP_OVERVIEW_NAME), tplPart.indexOf(TplConst.PROP_OVERVIEW_NAME) + TplConst.PROP_OVERVIEW_NAME.length(), e.getKey());
				tplPart.replace(tplPart.indexOf(TplConst.PROP_OVERVIEW_CONTENT), tplPart.indexOf(TplConst.PROP_OVERVIEW_CONTENT) + TplConst.PROP_OVERVIEW_CONTENT.length(), e
						.getValue());
				newNode.append(tplPart.toString());
			}
			overViewEle.append(newNode.toString());
			overViewModuleEle.remove();
		}

		// -------------------
		Element catalogEle = doc.getElementById(TplConst.ID_CATALOG);
		Elements catalogLoopEle = doc.getElementsByTag(TplConst.TAG_CATALOG_LOOP);
		Element catalog = catalogLoopEle.get(0);

		StringBuilder catalogNewNode = new StringBuilder();

		if (br.getModuleNews() != null && br.getModuleNews().size() > 0) {
			LinkedHashMap<String, List<News>> moduleNews = br.getModuleNews();
			Iterator<Entry<String, List<News>>> iter = moduleNews.entrySet().iterator();
			while (iter.hasNext()) {
				StringBuilder part = new StringBuilder(catalog.html());

				Entry<String, List<News>> e = iter.next();
				List<News> newsList = e.getValue();
				part.replace(part.indexOf(TplConst.PROP_MODULE_NAME), part.indexOf(TplConst.PROP_MODULE_NAME) + TplConst.PROP_MODULE_NAME.length(), e.getKey());
				StringBuilder li = new StringBuilder();

				if (newsList != null && newsList.size() > 0) {
					for (News news : newsList) {
						li.append("<li>").append(news.getNewsTitle()).append("-").append(news.getNewsMedia()).append("</li>");
					}
				}
				part.replace(part.indexOf(TplConst.PROP_NEWS_TITLE), part.indexOf(TplConst.PROP_NEWS_TITLE) + TplConst.PROP_NEWS_MEDIA.length() + TplConst.PROP_NEWS_TITLE.length()
						+ 1, li.toString());
				catalogNewNode.append(part);
			}

			catalogEle.append(catalogNewNode.toString());
			catalogLoopEle.remove();

		}

		// ----------------------------------
		Element contentEle = doc.getElementById(TplConst.ID_CONTENT);
		Elements contentLoopEle = doc.getElementsByTag(TplConst.TAG_CONTENT_LOOP);
		Element content = contentLoopEle.get(0);
		StringBuilder contentNewNode = new StringBuilder();

		if (br.getModuleNews() != null && br.getModuleNews().size() > 0) {
			
			LinkedHashMap<String, List<News>> moduleNews = br.getModuleNews();
			Iterator<Entry<String, List<News>>> iter = moduleNews.entrySet().iterator();
			while (iter.hasNext()) {
				StringBuilder part = new StringBuilder();
				part.append("<div class=\"c-module\">");

				String nameDiv = content.getElementsByClass("c-module-name").get(0).outerHtml();
				String titleDiv = content.getElementsByClass("c-module-title").get(0).outerHtml();
				String contentDiv = content.getElementsByClass("c-module-content").get(0).outerHtml();

				part.append(nameDiv);
				Entry<String, List<News>> e = iter.next();
				List<News> newsList = e.getValue();
				part.replace(part.indexOf(TplConst.PROP_MODULE_NAME), part.indexOf(TplConst.PROP_MODULE_NAME) + TplConst.PROP_MODULE_NAME.length(), e.getKey());

				for (News news : newsList) {
					part.append(titleDiv);
					part.replace(part.indexOf(TplConst.PROP_NEWS_TITLE), part.indexOf(TplConst.PROP_NEWS_TITLE) + TplConst.PROP_NEWS_TITLE.length(), news.getNewsTitle());
					part.append(contentDiv);
					part.replace(part.indexOf(TplConst.PROP_NEWS_CONTENT), part.indexOf(TplConst.PROP_NEWS_CONTENT) + TplConst.PROP_NEWS_CONTENT.length(), news.getNewsContent());
				}
				part.append("</div>");
				contentNewNode.append(part);
			}
			contentEle.append(contentNewNode.toString());
			contentLoopEle.remove();
		}

		// ------------------------------

		Elements attach = doc.getElementsByTag(TplConst.TAG_ATTACH);
		if (attach != null) {
			String[] prop = attach.get(0).html().trim().split("\\|");
			StringBuilder trs = new StringBuilder();
			Element aele = doc.getElementById("attach");

			LinkedHashMap<String, List<News>> moduleNews = br.getModuleNews();
			List<News> collNews = new ArrayList<News>();
			
			Iterator iter = moduleNews.keySet().iterator();
			while(iter.hasNext()){
				String key =(String)iter.next();
				List<News> news =moduleNews.get(key);
				for(News n :news){
					collNews.add(n);
				}
			}
			for (News n : collNews) {
				trs.append("<tr>");
				for (String s : prop) {
					String val = (String) ReflectUtils.getFieldValue(n, s);
					trs.append("<td>").append(val).append("</td>");
				}
				trs.append("</tr>");
			}
			aele.append(trs.toString());
			attach.remove();
		}

		br.setCssStyle(fetchCssStyle(doc));
		fetchBriefReportConfig(doc);
		String dd=doc.html();
		return doc.toString();
	}

	public static void main(String[] args) throws IOException {
		TemplateParse tp = new TemplateParse();
		// tp.parse("");

		// StringUtils.replace(inString, oldPattern, newPattern)

	}

	private String fetchCssStyle(Document doc) {
		String css = "";
		Elements styleEle = doc.getElementsByTag("style");
		css = styleEle.get(0).html();
		styleEle.remove();
		return css;
	}

	private String fetchBriefReportConfig(Document doc) {
		Element ele = doc.getElementById(TplConst.PROP_BRIEF_REPORT_CONFIG);
		String ret=ele.html();
		ele.remove();
		return ret;
	}

}

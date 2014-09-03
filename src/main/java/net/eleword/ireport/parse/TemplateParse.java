package net.eleword.ireport.parse;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import net.eleword.ireport.ClientTest;
import net.eleword.ireport.model.BriefReport;
import net.eleword.ireport.util.PathUtil;
import net.eleword.ireport.util.TplConst;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TemplateParse {

	public void parse(String template, BriefReport br) throws IOException {
		String tpl = PathUtil.getFullPathRelateClass("../../../../template", ClientTest.class);
		Document doc = Jsoup.parse(new File(tpl + "/rbjt.html"), "UTF-8");
		StringBuilder sb = new StringBuilder(doc.toString());
		sb.replace(sb.indexOf(TplConst.PROP_REPORT_TITLE), sb.indexOf(TplConst.PROP_REPORT_TITLE) + TplConst.PROP_REPORT_TITLE.length(),
				br.getTitle());
		sb.replace(sb.indexOf(TplConst.PROP_SUB_TITLE), sb.indexOf(TplConst.PROP_SUB_TITLE) + TplConst.PROP_SUB_TITLE.length(),
				br.getSubTitle());

		doc = Jsoup.parse(sb.toString());

		Element overViewEle = doc.getElementById(TplConst.ID_OVERVIEW);
		Elements overViewModuleEle = doc.getElementsByTag(TplConst.TAG_OVERVIEW_MODULE_LOOP);
		StringBuilder newNode = new StringBuilder();
		StringBuilder tplPart = new StringBuilder(overViewModuleEle.html());

		if (br.getPublicOpinionOverview().size() > 0) {
			LinkedHashMap<String, String> overView = br.getPublicOpinionOverview();
			Set<Entry<String, String>> sets = overView.entrySet();
			Iterator<Entry<String, String>> iter = sets.iterator();
			while (iter.hasNext()) {
				Entry<String, String> e = iter.next();
				tplPart.replace(tplPart.indexOf(TplConst.PROP_OVERVIEW_NAME), tplPart.indexOf(TplConst.PROP_OVERVIEW_NAME)
						+ TplConst.PROP_OVERVIEW_NAME.length(), e.getKey());
				tplPart.replace(tplPart.indexOf(TplConst.PROP_OVERVIEW_CONTENT), tplPart.indexOf(TplConst.PROP_OVERVIEW_CONTENT)
						+ TplConst.PROP_OVERVIEW_CONTENT.length(), e.getValue());
				newNode.append(tplPart.toString());
			}
			overViewEle.append(newNode.toString());
			overViewModuleEle.remove();
		}

		Element catalogEle = doc.getElementById(TplConst.ID_CATALOG);
		Elements catalogLoopEle = doc.getElementsByTag(TplConst.TAG_CATALOG_LOOP);

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
		return ele.html();
	}

}

package net.eleword.ireport.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	
	public static void main(String[] args) throws IOException {
		InputStream is = new FileInputStream("E:/MyEclipse-Workspace/hxt_briefing/resource/template/rbjt.html");
		StringBuilder sb =  new StringBuilder();
		byte[] buffer =new byte[512];
		int i=0;
		while((i=is.read(buffer))!=-1){
			sb.append(new String(buffer,0,i));
		}
		String tpl=sb.toString();
		
		Document doc = Jsoup.parse(tpl);
		Element overView = doc.getElementById("OverView");
		
		
		Elements overviewModuleEle =overView.getElementsByTag("OverviewModule");
		Element ome =overviewModuleEle.get(0);
		StringBuilder sbt =new StringBuilder(ome.html());
		
		sbt.replace(sbt.indexOf("#overviewName"),sbt.indexOf("#overviewName")+13 , "ddddddddddddddddd");
		
		
		overView.append(sbt.toString());
		overviewModuleEle.remove();
		
		System.out.println(doc.toString());
//		System.out.println("abc".replace("c", "ffffffffff"));
	}

}

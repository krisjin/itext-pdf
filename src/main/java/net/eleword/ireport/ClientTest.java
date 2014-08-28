package net.eleword.ireport;

import net.eleword.ireport.util.PathUtil;


public class ClientTest {
	public static void main(String[] args) throws Exception {

		String template = PathUtil.getFullPathRelateClass("../../../../template",ClientTest.class);
		System.out.println(template);
		BriefReport br = new BriefReport();
		br.setTemplatePath(template+"/rbjt.html");
		br.setTitle("中国人民保险集团|舆情周报");
		br.setOuputPath("C:/Users/kris/Desktop/ouput.pdf");
		br.setCssPath(template+"/rbjt.css");
		
		
		
		br.setMarginLeft(54);
		br.setMarginRight(54);
		br.setMarginTop(68);
		br.setMarginBottom(68);

		Bootstrap.startup(br);
	}

}

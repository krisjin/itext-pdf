package net.eleword.ireport;


public class ClientTest {
	public static void main(String[] args) throws Exception {

		BriefReport br = new BriefReport();
		br.setTemplatePath("E:/rep/itext-pdf/src/main/resources/template/rbjt.html");
		br.setTitle("中国人民保险集团|舆情周报");
		br.setOuputPath("C:/Documents and Settings/Administrator/桌面/ouput.pdf");
//		br.setTitleFontPath("E:/rep/itext-pdf/src/main/resources/font/FZXiaoBiaoSong-B05S.ttf");
		br.setReportDate("2014-06-30至2014-07-06");
		br.setAuthor("编辑部");
		br.setMarginLeft(54);
		br.setMarginRight(54);
		br.setMarginTop(68);
		br.setMarginBottom(68);

		Bootstrap.startup(br);
	}

}

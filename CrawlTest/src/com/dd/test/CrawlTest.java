package com.dd.test;

import main.java.us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.Spider;

public class CrawlTest {
	public static void main(String[] args) {
		TaobaoPageProcessor pageProcessor = new TaobaoPageProcessor();
		Spider s = Spider.create(pageProcessor);
//				.addUrl("http://s.taobao.com/search?q=dell%202312")
//				.addUrl("http://search.jd.com/Search?keyword=dell%202312")
				//s.addUrl("http://s.taobao.com/search?q=dell%202414")
				s.addUrl("http://stock.finance.sina.com.cn/usstock/quotes/AAC.html")
				.setDownloader(new SeleniumDownloader(System.getProperty("user.dir")+"/driver/chromedriver.exe"));
				s.run();
		System.out.println("....................."+pageProcessor.getHtml());
		
	}
}

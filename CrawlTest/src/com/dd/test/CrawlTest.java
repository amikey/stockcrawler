package com.dd.test;

import main.java.us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.Spider;

import com.dd.htmlunit.driver.downloader.htmlunitDriverDownLoader;

public class CrawlTest {

	public static void main(String[] args) {
		TaobaoPageProcessor pageProcessor = new TaobaoPageProcessor();
		Spider s = Spider.create(pageProcessor);
		// .addUrl("http://s.taobao.com/search?q=dell%202312")
		// .addUrl("http://search.jd.com/Search?keyword=dell%202312")
		// s.addUrl("http://s.taobao.com/search?q=dell%202414")
		s.addUrl("http://stock.finance.sina.com.cn/usstock/quotes/AAC.html");
		// .setDownloader(new
		// SeleniumDownloader(System.getProperty("user.dir")+"/driver/chromedriver.exe"));
		//s.setDownloader(new HtmlUnitDownLoader()).thread(5);
		s.setDownloader(new htmlunitDriverDownLoader()).thread(5);
		
		s.run();
		System.out.println("..............KKKKKK......." + pageProcessor.getHtml());
      /***
		//Spider s = Spider.create(pageProcessor);
		// .addUrl("http://s.taobao.com/search?q=dell%202312")
		// .addUrl("http://search.jd.com/Search?keyword=dell%202312")
		// s.addUrl("http://s.taobao.com/search?q=dell%202414")
		 Spider s2 = Spider.create(pageProcessor);
		  s2.addUrl("http://stock.finance.sina.com.cn/usstock/quotes/AAC.html")
		 .setDownloader(new
		 SeleniumDownloader(System.getProperty("user.dir")+"/driver/chromedriver.exe"));
		//s.setDownloader(new HtmlUnitDownLoader()).thread(5);
		s2.run();
		System.out.println("...........LLLLLL.........." + pageProcessor.getHtml());
   **/
	}
}

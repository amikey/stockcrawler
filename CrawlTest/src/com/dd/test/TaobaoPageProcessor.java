package com.dd.test;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class TaobaoPageProcessor implements PageProcessor{
	
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    String html = null;
	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {
		
		html = page.getHtml().toString();
//		List<String> rows = page.getHtml().xpath("//div[@class='item']").all();
//		System.out.println(rows.size());
//		for (int i=0; i< rows.size(); i++) {
//			Html h = new Html(rows.get(i));
//			String title = h.xpath("//div[@class='col title']/h3/a/text()").toString();
//			String url = h.xpath("//div[@class='col title']/h3/a/@href").toString();
//			String price = h.xpath("//div[@class='col total']/div[@class='price']/strong/text()").toString();
//			String shipping = h.xpath("//div[@class='col total']/div[@class='shipping']/text()").toString();
//			String dealing = h.xpath("//div[@class='col dealing']/div[1]/text()").toString();
//			String comment = h.xpath("//div[@class='col dealing']/div[@class='count']/a/text()").toString();
//			System.out.println(title+"	"+price+"	"+shipping+"	"+dealing+"	"+comment+"	"+url);
//		}
		
		// 部分二：定义如何抽取页面信息，并保存下来
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name") == null) {
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
//
//        // 部分三：从页面发现后续的url地址来抓取
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
	}

	@Override
	public Site getSite() {
		return site;
	}

	public String getHtml() {
		return html;
	}

}

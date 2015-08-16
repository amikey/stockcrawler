/**
 * 
 */
package com.alibaba.stock.crawler;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Selectable;

import com.alibaba.stock.vo.LinkItem;

/**
 * @author meide.zhangmd
 *
 */
public class SinaStockNewsCrawler extends AbstractCrawler {
	private String stockSymbol = "";

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public SinaStockNewsCrawler() {

	}

	public SinaStockNewsCrawler(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		// ³éÈ¡ÔªËØ
		page.putField(
				"name",
				page.getHtml()
						.xpath("/html/body/div[6]/div[2]/div[1]/div[1]/div/div[1]/h1/span/text()"));
		page.putField("price", page.getHtml()
				.xpath("//*[@id='hqPrice']/text()"));
		List<Selectable> newsItems = page.getHtml()
				.xpath("//*[@id='tabContNews']/div[2]/ul").nodes();
		List<LinkItem> newsLinkItems = new ArrayList<LinkItem>();
		for (Selectable newsitem : newsItems) {
			newsLinkItems.add(new LinkItem(newsitem.xpath("text()").get(),
					newsitem.links().get()));
		}
		page.putField("newsLinkItems", newsLinkItems);
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub

		return super
				.getSite()
				.setDomain("finance.sina.com.cn")
				.addHeader("Referer",
						"http://finance.sina.com.cn/stock/usstock/sector.shtml")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.setUserAgent(
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
				.setUseGzip(false);
	}

	public static void main(String[] args) {
		String stockSymbol = "AAC";
		String starturl = "http://stock.finance.sina.com.cn/usstock/quotes/AAC.html";
		Spider spider = Spider.create(new SinaStockNewsCrawler(stockSymbol))
				.addUrl(starturl).thread(5);
		ResultItems resultItems = spider.<ResultItems> get(starturl);
		System.out.println(resultItems);
	}
}

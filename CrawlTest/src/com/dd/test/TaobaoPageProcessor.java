package com.dd.test;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class TaobaoPageProcessor implements PageProcessor{
	
	// ����һ��ץȡ��վ��������ã��������롢ץȡ��������Դ�����
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    String html = null;
	@Override
	// process�Ƕ��������߼��ĺ��Ľӿڣ��������д��ȡ�߼�
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
		
		// ���ֶ���������γ�ȡҳ����Ϣ������������
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name") == null) {
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
//
//        // ����������ҳ�淢�ֺ�����url��ַ��ץȡ
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

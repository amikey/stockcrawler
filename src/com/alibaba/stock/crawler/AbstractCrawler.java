/**
 * 
 */
package com.alibaba.stock.crawler;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author meide.zhangmd
 *
 */
public abstract class AbstractCrawler implements PageProcessor {
    
	private  Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setUseGzip(true);;
	
	public Site getSite() {
		return site;
	}


	public  void setSite(Site site) {
		this.site = site;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	

}

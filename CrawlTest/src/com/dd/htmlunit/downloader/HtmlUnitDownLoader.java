/**
 * 
 */
package com.dd.htmlunit.downloader;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.UrlUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author meide.zhangmd
 *
 */
public class HtmlUnitDownLoader implements Downloader, Closeable {
	private Logger logger = Logger.getLogger(getClass());

    private int sleepTime = 5000;

    private int poolSize = 1;
    
    private volatile HtmlUnitWebClientPool htmlUnitWebClientPool;


    /**
     * set sleep time to wait until load success
     *
     * @param sleepTime
     * @return this
     */
    public HtmlUnitDownLoader setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }
    
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		htmlUnitWebClientPool.closeAll();
	}

	@SuppressWarnings("deprecation")
	
	public Page download11(Request request, Task task) {
		// TODO Auto-generated method stub
		WebClient webClient;	
		try{
			        
	      /***  webClient = htmlUnitWebClientPool.get();	        
	        logger.info("downloading page " + request.getUrl());
	        	      
	        CookieManager manage = new CookieManager();
	        Site site = task.getSite();
	        if (site.getCookies() != null) {
	            for (Map.Entry<String, String> cookieEntry : site.getCookies().entrySet()) {
	            	com.gargoylesoftware.htmlunit.util.Cookie cookie = new com.gargoylesoftware.htmlunit.util.Cookie(site.getDomain(),cookieEntry.getKey(), cookieEntry.getValue());
	                manage.addCookie(cookie);
	            }
	        }
	        webClient.setCookieManager(manage);
	        ****/
		    webClient=new WebClient(BrowserVersion.FIREFOX_24);
		     //模拟浏览器打开一个目标网址,先用一个试试看
			//设置webClient的相关参数
	        webClient.getOptions().setJavaScriptEnabled(true);
	        webClient.getOptions().setCssEnabled(false);
	        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	        //webClient.getOptions().setTimeout(50000);
	        webClient.getOptions().setThrowExceptionOnScriptError(false);
	       
	        
	            HtmlPage rootPage= webClient.getPage(request.getUrl());
	            System.out.println("为了获取js执行的数据 线程开始沉睡等待");
	            Thread.sleep(this.sleepTime);//主要是这个线程的等待 因为js加载也是需要时间的
	            System.out.println("线程结束沉睡");
	            String html = rootPage.asXml();
	            System.out.println(html);
	            Page page = new Page();
	            page.setRawText(html);
	            page.setHtml(new Html(UrlUtils.fixAllRelativeHrefs(html, request.getUrl())));
	            page.setUrl(new PlainText(request.getUrl()));
	            page.setRequest(request);
	            return page;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Page download(Request request, Task task) {
		// TODO Auto-generated method stub
		WebClient webClient;	
		checkInit();
		try{
			        
	        webClient = htmlUnitWebClientPool.get();	        
	        logger.info("downloading page " + request.getUrl());
	        	      
	        CookieManager manage = new CookieManager();
	        Site site = task.getSite();
	        if (site.getCookies() != null) {
	            for (Map.Entry<String, String> cookieEntry : site.getCookies().entrySet()) {
	            	com.gargoylesoftware.htmlunit.util.Cookie cookie = new com.gargoylesoftware.htmlunit.util.Cookie(site.getDomain(),cookieEntry.getKey(), cookieEntry.getValue());
	                manage.addCookie(cookie);
	            }
	        }
	        webClient.setCookieManager(manage);
	        
		   /**** WebClient webClient=new WebClient(BrowserVersion.FIREFOX_24);
		     //模拟浏览器打开一个目标网址,先用一个试试看
			//设置webClient的相关参数
	        webClient.getOptions().setJavaScriptEnabled(true);
	        webClient.getOptions().setCssEnabled(false);
	        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	        //webClient.getOptions().setTimeout(50000);
	        webClient.getOptions().setThrowExceptionOnScriptError(false);
	        ***/
	        
	            HtmlPage rootPage= webClient.getPage(request.getUrl());
	            System.out.println("为了获取js执行的数据 线程开始沉睡等待");
	            Thread.sleep(this.sleepTime);//主要是这个线程的等待 因为js加载也是需要时间的
	            System.out.println("线程结束沉睡");
	            WebResponse response = rootPage.getWebResponse();
	            //WebElement html = rootPage.getByXPath("/html");
	            System.out.println(rootPage);
	            Page page = new Page();
	            page.setRawText(response.getContentAsString());
	            page.setHtml(new Html(UrlUtils.fixAllRelativeHrefs(response.getContentAsString(), request.getUrl())));
	            page.setUrl(new PlainText(request.getUrl()));
	            page.setRequest(request);
	            return page;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public void setThread(int threadNum) {
		// TODO Auto-generated method stub
		this.poolSize = threadNum;
		
	}

	 private void checkInit() {
	        if (htmlUnitWebClientPool == null) {
	            synchronized (this){
	            	htmlUnitWebClientPool = new HtmlUnitWebClientPool(poolSize);
	            }
	        }
	    }
	 
	 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

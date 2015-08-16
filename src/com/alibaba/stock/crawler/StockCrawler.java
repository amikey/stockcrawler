/**
 * 
 */
package com.alibaba.stock.crawler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author meide.zhangmd
 *
 */
public class StockCrawler implements PageProcessor {

	private Site site;
	private List<String> urls=new ArrayList<String>();
	
	
	
	public String[] getUrls() {
		String[] uls={};
		return urls.toArray(uls);
	}





	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

    public StockCrawler(String starturl){
    	urls.add(starturl);
       (this.site=site.me()).setDomain("stock");//.addStartUrl(url);
    }



	@SuppressWarnings("static-access")
	public StockCrawler(){

	Calendar calendar=Calendar.getInstance();
	calendar.getTime();

	
	String url="";
	for(int i=0;i<=10;i++){
	calendar.add(5,-1);//表示天减1
	//yyyy-MM-dd HH:mm:ss.SSS
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	String datestr= df.format(calendar.getTime());	
	
	
	 url="http://data.eastmoney.com/stock/lhb/"+datestr+".html";

	  System.out.println(url); 
	 (this.site=site.me()).setDomain("stock");//.addStartUrl(url);
	 this.urls.add(url);
     
		    		// .addStartUrl("http://data.eastmoney.com/");	
	}

	}
	
	
	
	
    
    @Override
    public void process(Page page){
    	System.out.println("--------------start prepare to fetch  stock info-------------------");
        String stock_symbol="";//后面做成配置，便于查到任何股票的信息；
       List<String>  newsLinks=page.getHtml().xpath("//*[@id='yfi_headlines']/div[2]").links().all();
       
       List<String>  newstites=new ArrayList<String>();
       int i=0;
       for (String newsLink:newsLinks){
    	   i++;
    	   System.out.println("-----------------------");
    	   newstites.add(page.getHtml().xpath("//*[@id='yfi_headlines']/div[2]/ul/li["+i+"]/a/text()").get());
    	   System.out.println(newstites.get(i-1));
    	   System.out.println("************************");
    	   System.out.println(newsLink);
       }
       System.out.println("-------------end  fetch  newslinks--------------");
    }
    
    
    
    
    public void processeastmoney(Page page) {
    	try{
    		
    	System.out.println("---------------------------");
    		List<String> links = page.getHtml().links().regex("http://data\\.eastmoney\\.com/stock/lhb,*\\d+\\.html").all();
    		//List<String> links = page.getHtml().links().regex("http://data\\.eastmoney\\.com/stock/lhb,+\\.html").all();
    		//List<String> links = page.getHtml().links().regex("http://data\\.eastmoney\\.com/stock/lhb,[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30))),\\d+\\.html").all();
    		//List<String> links = page.getHtml().links().regex("http://data\\.eastmoney\\.com/stock/lhb,\\w\\dd\\.html").all();
    		//List<String> links = page.getHtml().links().regex("http://data\\.eastmoney\\.com/stock/lhb.\\d+.\\d+.\\d+.\\d+.\\d+*.html").all();

    		page.addTargetRequests(links);
	        List<String> dateInfo = page.getHtml().xpath("//div[@id='cont1']/div/ul[@id='datelist']/li/span[@class='red']/text()").all();
	        List<String> titleInfo = page.getHtml().xpath("/html/head/title/text()").all();
	        List<String> stockBuyOrgName = page.getHtml().xpath("//div[@id='cont1']/table[@class='tab2']/tbody/tr//td[@class='tdtext']/a/text()").all();
	        List<String> stockBuyMount = page.getHtml().xpath("//div[@id='cont1']/table[@class='tab2']/tbody/tr//td[3]/span[@class='red']/text()").all();
	        List<String> stockBuyPercent = page.getHtml().xpath("//div[@id='cont1']/table[@class='tab2']/tbody/tr//td[4]/span/text()").all();
	       
	        List<String> stockSaleMount = page.getHtml().xpath("//div[@id='cont1']/table[@class='tab2']/tbody/tr//td[5]/span[@class='green']/text()").all();
	        List<String> stockSalePercent = page.getHtml().xpath("//div[@id='cont1']/table[@class='tab2']/tbody/tr//td[6]/span/text()").all();
	       
	        if(!titleInfo.isEmpty() && !dateInfo.isEmpty()){
	        	System.out.println("==================================  begin =====================================");
	        	System.out.println("页面："+page.getUrl().toString());	
	        	System.out.println("日期："+dateInfo.get(0));
		        System.out.println("股票代号："+titleInfo.get(0));
//		        System.out.println("页面："+page.getUrl().toString());	
//		        System.out.println("机构："+stockBuyOrgName.toString());
//		        System.out.println("金额："+stockBuyMount.toString());
//		        System.out.println("百分比："+stockBuyPercent.toString());
//		        
		        if(!stockBuyOrgName.isEmpty()){
		        	for(int i=0; i<stockBuyOrgName.size() ;i++){
		        		if(stockBuyPercent.get(i)!=null && !stockBuyPercent.get(i).equals("-")){
		        			if(i==0){
		        				System.out.println("买入金额最大的前5名");
		        			}
		        			System.out.println(i+1+stockBuyOrgName.get(i) + "    买入金额： " + stockBuyMount.get(i) +"    买入比例："+stockBuyPercent.get(i)+ " 卖出金额" + stockSaleMount.get(i) + " 卖出比例" + stockSalePercent.get(i)   );
		        		}else{
		        			if(i-stockBuyMount.size()==0){
		        				System.out.println("卖出金额最大的前5名");
		        			}
		        			System.out.println(i-stockBuyMount.size()+1 +"，    "+ stockBuyOrgName.get(i)  + "    卖出金额： " + stockSaleMount.get(i-stockBuyMount.size()) +"     卖出比例："+stockSalePercent.get(i));
		        		}
		        	}
		        	System.out.println("==================================  end =====================================");
		        }
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        
        
    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
  
    	//StockInfoGather stockInfoGather =new StockInfoGather();
    	//stockInfoGather.getUrl();
    	
    	//System.out.print(Site.me().);
    	StockCrawler stockCrawler=new StockCrawler("http://finance.yahoo.com/q?s=aac&fr=uh3_finance_web&uhb=uhb2");
        Spider.create(stockCrawler).addUrl(stockCrawler.getUrls()).addPipeline(new ConsolePipeline()).thread(5).run();
    }

}

//
//create table TIGER
//( 
//  orders number(1) not null,
//  stockcode VARCHAR2(20) not null,
//  STOCKname VARCHAR2(20) not null,
//  RQ    VARCHAR2(10) not null,
//  ORG   VARCHAR2(100) not null,
//  buyaccount    NUMBER(15,2),
//  buypercent    NUMBER(4,2),
//  saleaccount    NUMBER(15,2),
//  saleercent    NUMBER(4,2),
//  FLAG  VARCHAR2(4) not null
//)
//
//

 // create table url (url varchar2(400) not null);


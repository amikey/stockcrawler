/**
 * 
 */
package com.alibaba.stock.vo;

/**
 * @author meide.zhangmd
 *
 */
public class LinkItem {
	private String  txt;
	private String  link;
	

	public LinkItem(){
		
	}
	
	/**
	 * @param txt
	 * @param link
	 */
	public LinkItem(String txt,String link){
		this.link=link;
		this.txt=txt;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}


	public String getTxt() {
		return txt;
	}


	public void setTxt(String txt) {
		this.txt = txt;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package com.taotao.portal.pojo;

import java.util.List;

/**
 * @author wilkins.liang
 *
 */
public class SearchResult {
	private Long recordCount;  //总记录数
	private List<Item>itemList;  //商品列表
	private long pageCount;  //分页总数
	private long curPage;  //当前页
	/**
	 * @return the recordCount
	 */
	public Long getRecordCount() {
		return recordCount;
	}
	/**
	 * @param recordCount the recordCount to set
	 */
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	/**
	 * @return the itemList
	 */
	public List<Item> getItemList() {
		return itemList;
	}
	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	/**
	 * @return the pageCount
	 */
	public long getPageCount() {
		return pageCount;
	}
	/**
	 * @param pageCount the pageCount to set
	 */
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * @return the curPage
	 */
	public long getCurPage() {
		return curPage;
	}
	/**
	 * @param curPage the curPage to set
	 */
	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}
	
	
}

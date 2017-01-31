package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

/**
 * @author wilkins.liang
 *
 */
public interface SearchServices {

	/**
	 * @param queryString
	 * @param pages
	 * @param rows
	 * @return
	 */
	public SearchResult search(String queryString, Integer pages, Integer rows);
}

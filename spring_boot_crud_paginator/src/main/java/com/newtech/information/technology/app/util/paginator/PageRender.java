package com.newtech.information.technology.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	
	private String url;
	
	private Page<T> page;
	
	private int totalPages;
	
	private int elementNumberPerPage;
	
	private int currentPage;
	
	private List<PageItem> pages;
	
	public PageRender(String url, Page<T> page){
	
		this.url = url;
		this.page = page;
		this.pages = new ArrayList<PageItem>();
		
		elementNumberPerPage = 3;
		totalPages = page.getTotalPages();
		currentPage = page.getNumber() + 1;
		
		int from, upto;
		
		if(totalPages <= elementNumberPerPage){
			from = 1;
			upto = totalPages;
		}else{
		
			if(currentPage <= elementNumberPerPage/2){
				from = 1;
				upto = elementNumberPerPage;
			
			}else if(currentPage >= totalPages - elementNumberPerPage/2){
			
				from = totalPages - elementNumberPerPage + 1;
				upto = elementNumberPerPage;
			}else {
				from = currentPage - elementNumberPerPage/2;
				upto = elementNumberPerPage;
			} 
		}
		
		for(int i = 0; i < upto; i++){
			
			pages.add(new PageItem(from + i, currentPage == from + i ));
		}
	}

	public String getUrl() {
		return url;
	}

	public Page<T> getPage() {
		return page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getElementNumberPerPage() {
		return elementNumberPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}

	public boolean isLast() {
		return page.isLast();
	}

	public boolean isHasNext() {
		return page.hasNext();
	}

	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
		
}
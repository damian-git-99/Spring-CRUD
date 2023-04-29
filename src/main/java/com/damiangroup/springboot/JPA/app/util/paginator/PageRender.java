package com.damiangroup.springboot.JPA.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * The PageRender class represents a pagination object that is used to render a list of items in a web page.
 * It takes in a Page object containing the list of items and renders it using the specified URL.
 * The class also calculates the page numbers to be displayed in the pagination based on the current page and the total number of pages.
 * The PageItem class represents a single page number in the pagination, along with a boolean flag indicating if it is the current page.
 */
public class PageRender<T> {

    private String url;
    private Page<T> page;
    private int totalPages;
    private int elementsPerPage;
    private int currentPage;
    private List<PageItem> pages;

    public PageRender(String url, Page<T> page) {

        this.url = url;
        this.page = page;
        this.pages = new ArrayList<>();
        this.elementsPerPage = page.getSize();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        int from, pageItem;

        // Calculates where pageItem start displaying the paginator and where pageItem end.
        if (totalPages <= elementsPerPage) {
            // Case 1: There are fewer pages than elements per page.
            // All pages are shown.
            from = 1;
            pageItem = totalPages;
        } else {
            pageItem = elementsPerPage;
            // Case 2: There are more pages than elements per page.
            // A subset of the pages is shown.

            // First, calculate the range of pages pageItem be shown.
            int half = elementsPerPage / 2;
            int remainingPages = totalPages - currentPage;

            if (currentPage <= half) {
                // Case 2.1: The current page is near the beginning.
                // The first 'elementsPerPage' pages are shown.
                from = 1;
            } else if (remainingPages <= half) {
                // Case 2.2: The current page is near the end.
                // The last 'elementsPerPage' pages are shown.
                from = totalPages - elementsPerPage + 1;
            } else {
                // Case 2.3: The current page is in the middle.
                // 'elementsPerPage' pages are shown around the current page.
                from = currentPage - half;
            }
        }

        // Saves the different page numbers that the paginator has, so that they can be printed in the view later.
        for (int i = 0; i < pageItem; i++) {
            pages.add(new PageItem(from + i, currentPage == from + i));
        }

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getElementsPerPage() {
        return elementsPerPage;
    }

    public void setElementsPerPage(int elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<PageItem> getPages() {
        return pages;
    }

    public void setPages(List<PageItem> pages) {
        this.pages = pages;
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

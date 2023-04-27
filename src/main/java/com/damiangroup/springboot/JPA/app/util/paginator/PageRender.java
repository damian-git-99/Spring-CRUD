package com.damiangroup.springboot.JPA.app.util.paginator;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

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

        int desde, hasta;
        /**
         * 
         * Calcula desde donde se va a empezar ha mostrar el paginador y hasta donde
         * terminar
         * 
         */
        if (totalPages <= elementsPerPage) {
            desde = 1;
            hasta = totalPages;
        } else {
            if (currentPage <= elementsPerPage / 2) {
                desde = 1;
                hasta = elementsPerPage;
            } else if (currentPage >= totalPages - elementsPerPage / 2) {
                desde = totalPages - elementsPerPage + 1;
                hasta = elementsPerPage;
            } else {
                desde = currentPage - elementsPerPage / 2;
                hasta = elementsPerPage;
            }
        }

        /**
         * Guarda los diferentes numeros de paginas que tiene el paginador para despues
         * poder imprimirlos la vista
         * 
         */
        for (int i = 0; i < hasta; i++) {
            pages.add(new PageItem(desde + i, currentPage == desde + i));
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

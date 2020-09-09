package com.damiangroup.springboot.JPA.app.controllers.util.paginator;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

public class PageRender<T> {

    private String url;
    private Page<T> page;
    private int totalPaginas;
    private int numeroElementosPorPagina;
    private int paginaActual;
    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {

        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<>();
        this.numeroElementosPorPagina = page.getSize();
        this.totalPaginas = page.getTotalPages();
        this.paginaActual = page.getNumber() + 1;

        int desde, hasta;
        // 5 , 50  = 10
        if (totalPaginas <= numeroElementosPorPagina) {
            desde = 1;
            hasta = totalPaginas;
        } else {
            if (paginaActual <= numeroElementosPorPagina / 2) {
                desde = 1;
                hasta = numeroElementosPorPagina;
            } else if (paginaActual >= numeroElementosPorPagina / 2) {
                desde = totalPaginas - numeroElementosPorPagina + 1;
                hasta = numeroElementosPorPagina;
            } else {
                desde = paginaActual - numeroElementosPorPagina / 2;
                hasta = numeroElementosPorPagina;
            }
        }

        for (int i = 0; i < hasta; i++) {
            paginas.add(new PageItem(desde + i, paginaActual == desde + i));
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

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getNumeroElementosPorPagina() {
        return numeroElementosPorPagina;
    }

    public void setNumeroElementosPorPagina(int numeroElementosPorPagina) {
        this.numeroElementosPorPagina = numeroElementosPorPagina;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<PageItem> paginas) {
        this.paginas = paginas;
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

    public boolean isHasPrevious(){
        return page.hasPrevious();
    }

}

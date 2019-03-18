package lebelleami.com.beam.Model;

import java.util.List;

import lebelleami.com.beam.Model.ReviewData;

public class Review {

    private Integer id;
    private Integer page;
    private List<ReviewData> results;
    private Integer total_pages;
    private Integer total_results;

    public Review(Integer id, Integer page, List<ReviewData> results, Integer total_pages, Integer total_results) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<ReviewData> getResults() {
        return results;
    }

    public void setResults(List<ReviewData> results) {
        this.results = results;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }
}

package lebelleami.com.beam.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tv {

    private Integer page;
    private Integer total_Results;
    private Integer total_Pages;
    private List<TvData> results;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Tv(Integer page, Integer total_Results, Integer total_Pages, List<TvData> results,
                 Map<String, Object> additionalProperties) {
        this.page = page;
        this.total_Results = total_Results;
        this.total_Pages = total_Pages;
        this.results = results;
        this.additionalProperties = additionalProperties;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal_Results() {
        return total_Results;
    }

    public void setTotal_Results(Integer total_Results) {
        this.total_Results = total_Results;
    }

    public Integer getTotal_Pages() {
        return total_Pages;
    }

    public void setTotal_Pages(Integer total_Pages) {
        this.total_Pages = total_Pages;
    }

    public List<TvData> getResults() {
        return results;
    }

    public void setResults(List<TvData> results) {
        this.results = results;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

}

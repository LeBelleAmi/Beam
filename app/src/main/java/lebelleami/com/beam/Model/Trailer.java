package lebelleami.com.beam.Model;

import java.util.List;

public class Trailer {

    private Integer id;
    private List<TrailerData> results;

    public Trailer(Integer id, List<TrailerData> results) {
        this.id = id;
        this.results = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TrailerData> getResults() {
        return results;
    }

    public void setResults(List<TrailerData> results) {
        this.results = results;
    }
}

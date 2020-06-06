package lebelleami.com.beam.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvCredits {
    private List<TvCast> cast;
    private List<TvCrew> crew;
    private Integer id;

    public TvCredits(List<TvCast> cast, List<TvCrew> crew, Integer id) {
        this.cast = cast;
        this.crew = crew;
        this.id = id;
    }

    public List<TvCast> getCast() {
        return cast;
    }

    public void setCast(List<TvCast> cast) {
        this.cast = cast;
    }

    public List<TvCrew> getCrew() {
        return crew;
    }

    public void setCrew(List<TvCrew> crew) {
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

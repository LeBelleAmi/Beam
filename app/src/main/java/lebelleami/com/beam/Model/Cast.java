package lebelleami.com.beam.Model;

import java.util.List;

public class Cast {

    private Integer id;
    private List<CastData> cast;
    private List<CrewData> crew;

    public Cast(Integer id, List<CastData> cast, List<CrewData> crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CastData> getCast() {
        return cast;
    }

    public void setCast(List<CastData> cast) {
        this.cast = cast;
    }

    public List<CrewData> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewData> crew) {
        this.crew = crew;
    }
}

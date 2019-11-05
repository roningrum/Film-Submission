package co.id.roni.filmsubmission.objectdata;

import java.util.List;

import co.id.roni.filmsubmission.model.MovieModel;

public class MovieObjectData {
    private List<MovieModel> results;
    private int page;


    public List<MovieModel> getResults() {
        return results;
    }

    public void setResults(List<MovieModel> results) {
        this.results = results;
    }

}

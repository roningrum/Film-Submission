package co.id.roni.film_submission;

import java.util.List;

import co.id.roni.film_submission.model.Cast;

public class CreditModel {
    private int id;
    private List<Cast> castList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCastList() {
        return castList;
    }

    public void setCastList(List<Cast> castList) {
        this.castList = castList;
    }
}

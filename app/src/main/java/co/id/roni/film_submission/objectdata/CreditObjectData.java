package co.id.roni.film_submission.objectdata;

import java.util.List;

import co.id.roni.film_submission.model.Cast;

public class CreditObjectData {
    private List<Cast> casts;

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }
}

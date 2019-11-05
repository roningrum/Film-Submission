package co.id.roni.filmsubmission.objectdata;

import java.util.List;

import co.id.roni.filmsubmission.model.Cast;

public class CreditObjectData {
    private List<Cast> cast;

    public List<Cast> getCasts() {
        return cast;
    }

    public void setCasts(List<Cast> casts) {
        this.cast = casts;
    }
}

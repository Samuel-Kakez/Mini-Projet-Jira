package model;

import java.time.LocalDate;

// Cette classe sert juste à structurer une ligne du tableau du rapport
public class TacheRapport {
    private String quoi;
    private String qui;
    private LocalDate quand;

    public TacheRapport(String quoi, String qui, LocalDate quand) {
        this.quoi = quoi;
        this.qui = qui;
        this.quand = quand;
    }

    // Getters
    public String getQuoi() {
        return quoi;
    }

    public String getQui() {
        return qui;
    }

    public LocalDate getQuand() {
        return quand;
    }

    @Override
    public String toString() {
        return String.format("%s par %s le %s", quoi, qui, quand);
    }
}

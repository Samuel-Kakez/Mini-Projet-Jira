package model;

import java.time.LocalDate;

public class Besoin {

    // Définition de l'Enum pour les états
    public enum Etat {
        A_ANALYSER,
        ANALYSE,
        ANNULE,
        TERMINE
    }

    // Propriétés de base
    private String description;
    private Etat etatCourant;
    private LocalDate dateCreation;

    // Constructeur : Permet de créer un besoin
    public Besoin(String description) {
        this.description = description;
        this.etatCourant = Etat.A_ANALYSER; // État initial
        this.dateCreation = LocalDate.now(); // Date de création actuelle
    }

    // Getters & Setters
    public String getDescription() {
        return description;
    }

    public Etat getEtatCourant() {
        return etatCourant;
    }

    public void setEtatCourant(Etat etatCourant) {
        this.etatCourant = etatCourant;
    }

    @Override
    public String toString(){
        return "Besoin[" +
                "description='" + description + '\'' +
                ", etatCourant=" + etatCourant +
                ", dateCreation=" + dateCreation +
                ']';
    }
}
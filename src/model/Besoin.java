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
    private double charge;
    private String responsable;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String raisonAnnulation; // Pour l'état ANNULÉ

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

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setCharge(double charge) { this.charge = charge; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public void setRaisonAnnulation(String raisonAnnulation) { this.raisonAnnulation = raisonAnnulation; }

    // Ajout pour le DAO (recharger une ancienne date)
    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
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
package model;

import java.time.LocalDate;

public class Contrainte {
    public enum  EtatContrainte{
        A_PRENDRE_EN_COMPTE,
        A_VERIFIER,
        VERIFIEE,
        ANNULEE
    }

    private String libelle;
    private EtatContrainte etat;

    // Champs optionnels
    private String verificateur; 
    private LocalDate dateVerification;
    private String raisonAnnulation;

    public Contrainte(String libelle) {
        this.libelle = libelle;
        this.etat = EtatContrainte.A_PRENDRE_EN_COMPTE;
    }

    // Getters & Setters
    public String getLibelle() {
        return libelle;
    }
    public EtatContrainte getEtat() {
        return etat;
    }

    public void setEtat(EtatContrainte etat){
        this.etat = etat;
        // On peut auto-fill la date
        if (etat ==EtatContrainte.VERIFIEE){
            this.dateVerification = LocalDate.now();
        }
    }

    public void setVerificateur(String verificateur) {
        this.verificateur = verificateur;
    }

    public void setRaisonAnnulation(String raisonAnnulation) {
        this.raisonAnnulation = raisonAnnulation;
    }

    @Override
    public String toString(){
        return libelle + " (" + etat + ")";
    }

}

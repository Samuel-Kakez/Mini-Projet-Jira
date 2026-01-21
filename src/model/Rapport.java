package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rapport {
    private String auteur;
    private LocalDate dateReunion;

    // On utilise des List pour l'interface
    // et des ArrayList pour l'implémentation
    private List<String> participants = new ArrayList<>();
    private List<TacheRapport> taches = new ArrayList<>();

    public Rapport(String auteur, LocalDate dateReunion) {
        this.auteur = auteur;
        this.dateReunion = dateReunion;
    }

    // Méthodes pour ajouter des éléments dans la liste
    public void ajouterParticipant(String nom) {
        this.participants.add(nom);
    }

    public void ajouterTache(TacheRapport tache){
        this.taches.add(tache);
    }

    // Getters
    public String getAuteur() {
        return auteur;
    }

    public LocalDate getDateReunion() {
        return dateReunion;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public List<TacheRapport> getTaches() {
        return taches;
    }

    

    @Override
    public String toString(){
        return "Rapport de " + auteur + " (" + participants.size() + " participants, " + taches.size() + " tâches)";
    }
}

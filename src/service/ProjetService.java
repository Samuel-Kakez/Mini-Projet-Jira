package service;

import java.util.ArrayList;
import java.util.List;
import model.Besoin;
import model.Contrainte;
import model.Rapport;

public class ProjetService {
    // Les données vivent ici tant que l'appli tourne (RAM)
    private List<Besoin> listeBesoins = new ArrayList<>();
    private List<Contrainte> listeContraintes = new ArrayList<>();
    private List<Rapport> listeRapports = new ArrayList<>();

    // --- Gestion des Besoins ---
    public void creerBesoin(String description) {
        Besoin b = new Besoin(description);
        listeBesoins.add(b);
        // On ajoutera dao.sauvergarder(listeBesoins) plus tard
    }

    public List<Besoin> getTousLesBesoins() {
        return listeBesoins;
    }

    // Récupérer un besoin spécifique par son index (pour le modifier)
    public Besoin getBesoin(int index) {

        if (index >= 0 && index < listeBesoins.size()) {
            return listeBesoins.get(index);
        }
        return null;
    }

    // --- Gestion des Contraintes ---

    public void creerContrainte(String libelle) {
        Contrainte c = new Contrainte(libelle);
        listeContraintes.add(c);
    }

    public List<Contrainte>getToutesLesContraintes(){
        return listeContraintes;
    }

    // --- Gestion des Rapports ---

    // Ici on ajoute un rapport déjà créé 
}

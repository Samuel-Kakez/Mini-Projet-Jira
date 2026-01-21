package service;

import java.util.ArrayList;
import java.util.List;
import model.Besoin;
import model.Contrainte;
import model.Rapport;
// imports des DAO
import dao.BesoinDao;
import dao.RapportDao;
import dao.ContrainteDao;

public class ProjetService {
    
    private BesoinDao besoinDao = new BesoinDao(); // Lien avec le DAO
    private RapportDao rapportDao = new RapportDao();
    private ContrainteDao contrainteDao = new ContrainteDao();

    // Les données vivent ici tant que l'appli tourne (RAM)
    private List<Besoin> listeBesoins;
    private List<Contrainte> listeContraintes = new ArrayList<>();
    private List<Rapport> listeRapports = new ArrayList<>();

    public ProjetService(){
        // Au démarrage, on cherche tout ce qu'il y a dans le fichier CSV
        this.listeBesoins = besoinDao.chargerTout();
        this.listeContraintes = contrainteDao.chargerTout();
    }

    // --- Gestion des Besoins ---
    public void creerBesoin(String description) {
        Besoin b = new Besoin(description);
        listeBesoins.add(b);
        
        // On sauvegarde tout dans le fichier !
        besoinDao.sauvegarderTout(listeBesoins);
    }

    // Pour la suppression
    public void supprimerBesoin(int index) {
        if (index >= 0 && index < listeBesoins.size()) {
            listeBesoins.remove(index);
            besoinDao.sauvegarderTout(listeBesoins);
        }
    }

    // Pour valider une modification faite dans le menu
    public void mettreAJourBesoins() {
        besoinDao.sauvegarderTout(listeBesoins);
    }

    // Il manque une méthode pour sauvegarder après une modification d'état
    // Ex : si on change l'état dans le menu, il faut que le fichier soit mis à jour
    public void sauvegarderBesoins(){
        besoinDao.sauvegarderTout(listeBesoins);
    }

    public void creerRapport(Rapport r){
        listeRapports.add(r);
        // On génère le fichier texte immédiatement
        rapportDao.sauvegarderRapport(r);
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

    // --- Gestion Contraintes ---

    public void creerContrainte(String libelle){
        Contrainte c = new Contrainte(libelle);
        listeContraintes.add(c);
        contrainteDao.sauvegarderTout(listeContraintes);
    }

    public void supprimerContrainte(int index) {
        if (index >= 0 && index < listeContraintes.size()) {
            listeContraintes.remove(index);
            contrainteDao.sauvegarderTout(listeContraintes);
        }
    }
    
    public void mettreAJourContraintes() {
        contrainteDao.sauvegarderTout(listeContraintes);
    }

    // On ajoute cette méthode pour sauvegarder après modif (ex: changement d'état)
    public void sauvegarderContraintes() {
        contrainteDao.sauvegarderTout(listeContraintes);
    }

    public List<Contrainte> getToutesLesContraintes() {
        return listeContraintes;
    }

    // --- Gestion des Rapports ---

    // Ici on ajoute un rapport déjà créé
    public void ajouterRapport(Rapport r) {
        listeRapports.add(r);
    }

    public void supprimerRapport(int index) {
        if (index >= 0 && index < listeRapports.size()) {
            listeRapports.remove(index);
            // Pas de sauvegarde CSV pour les rapports, donc rien d'autre à faire
        }
    }

    public List<Rapport> getTousLesRapports() {
        return listeRapports;
    }
}

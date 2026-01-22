package ui;

import java.util.Scanner;
import service.ProjetService;
import model.Besoin;
import model.Contrainte;
import model.Rapport;
import model.TacheRapport;

public class Menu {

    // --- COULEURS ---
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE_BOLD = "\033[1;37m";

    private ProjetService service;
    private Scanner scanner;

    public Menu() {
        this.service = new ProjetService();
        this.scanner = new Scanner(System.in);
    }

    // =================================================================================
    //                                NAVIGATION PRINCIPALE
    // =================================================================================

    public void demarrer() {
        boolean running = true;
        while (running) {
            // Titre en Blanc comme demandé
            afficherTitrePrincipal("MENU PRINCIPAL");
            
            System.out.println(" 1. Gérer les " + BLUE + "BESOINS" + RESET);
            System.out.println(" 2. Gérer les " + PURPLE + "CONTRAINTES" + RESET);
            System.out.println(" 3. Gérer les " + YELLOW + "RAPPORTS" + RESET);
            System.out.println(" Q. " + RED + "Quitter" + RESET);
            
            String choix = lireTexte("Votre choix");
            switch (choix.toUpperCase()) {
                case "1" -> gererBesoins();
                case "2" -> gererContraintes();
                case "3" -> gererRapports();
                case "Q" -> running = false;
                default  -> afficherErreur("Choix invalide.");
            }
        }
        System.out.println(YELLOW + "Fermeture de l'application. À bientôt !" + RESET);
    }

    // =================================================================================
    //                                SOUS-MENUS
    // =================================================================================

    private void gererBesoins() {
        boolean back = false;
        while (!back) {
            afficherBloc("GESTION DES BESOINS", BLUE);
            System.out.println(" 1. Lister");
            System.out.println(" 2. Créer");
            System.out.println(" 3. Modifier (État/Info)");
            System.out.println(" 4. " + RED + "Supprimer" + RESET);
            System.out.println(" R. Retour");

            switch (lireTexte("Choix").toUpperCase()) {
                case "1" -> { listerBesoins(); pause(); }
                case "2" -> { ajouterBesoin(); pause(); }
                case "3" -> { modifierBesoin(); pause(); }
                case "4" -> { supprimerBesoin(); pause(); }
                case "R" -> back = true;
                default  -> afficherErreur("Choix incorrect.");
            }
        }
    }

    private void gererContraintes() {
        boolean back = false;
        while (!back) {
            afficherBloc("GESTION DES CONTRAINTES", PURPLE);
            System.out.println(" 1. Lister");
            System.out.println(" 2. Créer");
            System.out.println(" 3. Modifier (État/Verif)");
            System.out.println(" 4. " + RED + "Supprimer" + RESET);
            System.out.println(" R. Retour");

            switch (lireTexte("Choix").toUpperCase()) {
                case "1" -> { listerContraintes(); pause(); }
                case "2" -> { ajouterContrainte(); pause(); }
                case "3" -> { modifierContrainte(); pause(); }
                case "4" -> { supprimerContrainte(); pause(); }
                case "R" -> back = true;
                default  -> afficherErreur("Choix incorrect.");
            }
        }
    }

    private void gererRapports() {
        boolean back = false;
        while (!back) {
            // Bloc Jaune pour différencier du Bleu
            afficherBloc("GESTION DES RAPPORTS", YELLOW); 
            System.out.println(" 1. Lister (Session)");
            System.out.println(" 2. Générer nouveau");
            System.out.println(" 3. Compléter rapport");
            System.out.println(" 4. " + RED + "Supprimer" + RESET);
            System.out.println(" R. Retour");

            switch (lireTexte("Choix").toUpperCase()) {
                case "1" -> { listerRapports(); pause(); }
                case "2" -> { creerRapportInteractif(); pause(); }
                case "3" -> { modifierRapport(); pause(); }
                case "4" -> { supprimerRapport(); pause(); }
                case "R" -> back = true;
                default  -> afficherErreur("Choix incorrect.");
            }
        }
    }

    // =================================================================================
    //                            LOGIQUE MÉTIER : BESOINS
    // =================================================================================

    private void listerBesoins() {
        afficherSeparateur("LISTE DES BESOINS");
        var liste = service.getTousLesBesoins();
        if (liste.isEmpty()) {
            System.out.println(YELLOW + "  Aucun besoin enregistré." + RESET);
        } else {
            for (int i = 0; i < liste.size(); i++) {
                Besoin b = liste.get(i);
                // Affichage formaté avec l'état en bleu
                System.out.printf("  [%d] %-40s %s(%s)%s%n", i, b.getDescription(), BLUE, b.getEtatCourant(), RESET);
            }
        }
    }

    private void ajouterBesoin() {
        String desc = lireTexte("Description du besoin");
        if (!desc.isBlank()) {
            service.creerBesoin(desc);
            afficherSucces("Besoin ajouté.");
        }
    }

    private void modifierBesoin() {
        listerBesoins();
        int index = lireEntier("Numéro à modifier");
        Besoin b = service.getBesoin(index);

        if (b != null) {
            System.out.println("  Sélectionné : " + WHITE_BOLD + b.getDescription() + RESET);
            System.out.println("  1. À Analyser");
            System.out.println("  2. Analysé (Nécessite charge & dates)");
            System.out.println("  3. Annulé (Nécessite raison)");
            System.out.println("  4. Terminé");
            
            String choix = lireTexte("Nouvel état");
            switch (choix) {
                case "1" -> {
                    b.setEtatCourant(Besoin.Etat.A_ANALYSER);
                    // Pas de demande de date prévue car champ inexistant dans votre classe
                }
                case "2" -> {
                    b.setEtatCourant(Besoin.Etat.ANALYSE);
                    b.setCharge(lireDouble("Charge (j/h)"));
                    b.setResponsable(lireTexte("Responsable"));
                    try { b.setDateDebut(java.time.LocalDate.parse(lireTexte("Date Début (YYYY-MM-DD)"))); } catch(Exception e){}
                    try { b.setDateFin(java.time.LocalDate.parse(lireTexte("Date Fin (YYYY-MM-DD)"))); } catch(Exception e){}
                }
                case "3" -> {
                    b.setEtatCourant(Besoin.Etat.ANNULE);
                    b.setRaisonAnnulation(lireTexte("Raison annulation"));
                }
                case "4" -> b.setEtatCourant(Besoin.Etat.TERMINE);
                default -> { afficherErreur("Annulé."); return; }
            }
            
            service.mettreAJourBesoins();
            afficherSucces("Mise à jour effectuée.");
        } else {
            afficherErreur("Besoin introuvable.");
        }
    }

    private void supprimerBesoin() {
        listerBesoins();
        int index = lireEntier("Numéro à supprimer");
        if (index >= 0) {
            service.supprimerBesoin(index);
            afficherSucces("Supprimé.");
        }
    }

    // =================================================================================
    //                          LOGIQUE MÉTIER : CONTRAINTES
    // =================================================================================

    private void listerContraintes() {
        afficherSeparateur("LISTE DES CONTRAINTES");
        var liste = service.getToutesLesContraintes();
        if (liste.isEmpty()) System.out.println(YELLOW + "  Aucune contrainte." + RESET);
        
        for (int i = 0; i < liste.size(); i++) {
            System.out.printf("  [%d] %s%n", i, liste.get(i));
        }
    }

    private void ajouterContrainte() {
        service.creerContrainte(lireTexte("Libellé"));
        afficherSucces("Contrainte ajoutée.");
    }

    private void modifierContrainte() {
        listerContraintes();
        int index = lireEntier("Numéro à modifier");
        if (index >= 0 && index < service.getToutesLesContraintes().size()) {
            Contrainte c = service.getToutesLesContraintes().get(index);
            
            System.out.println("  1. À prendre en compte | 2. À vérifier | 3. Vérifiée | 4. Annulée");
            switch (lireTexte("Nouvel état")) {
                case "1" -> c.setEtat(Contrainte.EtatContrainte.A_PRENDRE_EN_COMPTE);
                case "2" -> c.setEtat(Contrainte.EtatContrainte.A_VERIFIER);
                case "3" -> {
                    c.setEtat(Contrainte.EtatContrainte.VERIFIEE);
                    c.setVerificateur(lireTexte("Vérifié par"));
                    c.setDateVerification(java.time.LocalDate.now());
                }
                case "4" -> {
                    c.setEtat(Contrainte.EtatContrainte.ANNULEE);
                    c.setRaisonAnnulation(lireTexte("Raison"));
                }
            }
            service.mettreAJourContraintes();
            afficherSucces("Contrainte mise à jour.");
        } else {
            afficherErreur("Introuvable.");
        }
    }

    private void supprimerContrainte() {
        listerContraintes();
        int index = lireEntier("Numéro à supprimer");
        if (index >= 0) {
            service.supprimerContrainte(index);
            afficherSucces("Supprimé.");
        }
    }

    // =================================================================================
    //                           LOGIQUE MÉTIER : RAPPORTS
    // =================================================================================

    private void listerRapports() {
        afficherSeparateur("RAPPORTS (SESSION)");
        var liste = service.getTousLesRapports();
        if (liste.isEmpty()) System.out.println(YELLOW + "  Aucun rapport." + RESET);
        for (int i = 0; i < liste.size(); i++) {
            Rapport r = liste.get(i);
            System.out.printf("  [%d] %s (par %s)%n", i, r.getDateReunion(), r.getAuteur());
        }
    }

    private void creerRapportInteractif() {
        afficherSeparateur("NOUVEAU RAPPORT");
        Rapport r = new Rapport(lireTexte("Auteur"), java.time.LocalDate.now());

        System.out.println(BLUE + "  Participants (Entrée vide pour finir) :" + RESET);
        while (true) {
            String nom = lireTexte("- Nom");
            if (nom.isBlank()) break;
            r.ajouterParticipant(nom);
        }

        System.out.println(BLUE + "  Tâches (Entrée vide pour finir) :" + RESET);
        while (true) {
            String quoi = lireTexte("- Quoi");
            if (quoi.isBlank()) break;
            
            String qui = lireTexte("  Qui");
            String dateStr = lireTexte("  Date (YYYY-MM-DD)");
            try {
                // Tente de parser la date, sinon met la date du jour en fallback ou ignore
                java.time.LocalDate d = (dateStr.isBlank()) ? java.time.LocalDate.now() : java.time.LocalDate.parse(dateStr);
                r.ajouterTache(new TacheRapport(quoi, qui, d));
            } catch (Exception e) {
                afficherErreur("Date invalide, tâche ignorée.");
            }
        }
        service.creerRapport(r);
        afficherSucces("Fichier généré !");
    }

    private void modifierRapport() {
        listerRapports();
        int index = lireEntier("Numéro à compléter");
        if (index >= 0 && index < service.getTousLesRapports().size()) {
            Rapport r = service.getTousLesRapports().get(index);
            System.out.println("  Ajout tâche pour : " + r.getAuteur());
            
            String quoi = lireTexte("Quoi");
            String qui = lireTexte("Qui");
            r.ajouterTache(new TacheRapport(quoi, qui, java.time.LocalDate.now()));
            
            service.creerRapport(r); // Régénère le fichier
            afficherSucces("Rapport mis à jour et fichier écrasé.");
        }
    }

    private void supprimerRapport() {
        listerRapports();
        int index = lireEntier("Numéro à retirer");
        if (index >= 0) {
            service.supprimerRapport(index);
            afficherSucces("Retiré de la liste.");
        }
    }

    // =================================================================================
    //                                OUTILS UI / SAISIE
    // =================================================================================

    private void afficherTitrePrincipal(String titre) {
        nettoyerEcran();
        // Le titre principal est en Blanc Gras
        System.out.println(WHITE_BOLD + "+------------------------------------------+");
        System.out.println("| " + centrerTexte(titre, 40) + " |");
        System.out.println("+------------------------------------------+" + RESET);
    }

    private void afficherBloc(String titre, String couleur) {
        nettoyerEcran();
        // Le bloc prend la couleur passée en paramètre
        System.out.println(couleur + "+------------------------------------------+");
        System.out.println("| " + centrerTexte(titre, 40) + " |");
        System.out.println("+------------------------------------------+" + RESET);
    }

    private void afficherSeparateur(String titre) {
        System.out.println("\n" + WHITE_BOLD + "--- " + titre + " ---" + RESET);
    }

    private String centrerTexte(String texte, int largeur) {
        if (texte.length() >= largeur) return texte;
        int padding = (largeur - texte.length()) / 2;
        return " ".repeat(padding) + texte + " ".repeat(largeur - texte.length() - padding);
    }

    // Affiche le prompt avec un saut de ligne AVANT pour l'aération
    private String lireTexte(String prompt) {
        System.out.println(); // Saut de ligne pour lisibilité
        System.out.print(RESET + prompt + " > " + GREEN);
        String input = scanner.nextLine();
        System.out.print(RESET);
        return input;
    }

    private int lireEntier(String prompt) {
        try {
            String s = lireTexte(prompt);
            if(s.isBlank()) return -1;
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double lireDouble(String prompt) {
        try {
            String s = lireTexte(prompt);
            if(s.isBlank()) return 0.0;
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void nettoyerEcran() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void afficherSucces(String msg) {
        System.out.println(GREEN + " [OK] " + msg + RESET);
    }

    private void afficherErreur(String msg) {
        System.out.println(RED + " [!] " + msg + RESET);
    }

    private void pause() {
        System.out.println("\n" + WHITE_BOLD + "Appuyez sur [ENTRÉE] pour continuer..." + RESET);
        scanner.nextLine();
    }
}
package ui;

import java.util.Scanner;
import service.ProjetService;
import model.Besoin;
import model.Contrainte;
import model.Rapport;
import model.TacheRapport;

public class Menu {

    // --- Couleurs (Codes ANSI) ---
    // -> Copié-Collé IA Start
    public static final String RESET = "\033[0m"; // Remet la couleur par défaut
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String CYAN = "\033[0;36m";
    public static final String PURPLE = "\033[0;35m";
    // -> Copié-Collé IA End

    private ProjetService service;
    private Scanner scanner;

    public Menu() {
        this.service = new ProjetService(); // Le menu possède son propre cerveau
        this.scanner = new Scanner(System.in);
    }

    // =================================================================================
    //                                MENU PRINCIPAL
    // =================================================================================

    public void demarrer() {
        boolean running = true;
        while (running) {
            afficherEntete("MENU PRINCIPAL");
            System.out.println("1. Gérer les " + BLUE + "BESOINS" + RESET);
            System.out.println("2. Gérer les " + PURPLE + "CONTRAINTES" + RESET);
            System.out.println("3. Gérer les " + CYAN + "RAPPORTS" + RESET);
            System.out.println("Q. " + RED + "Quitter" + RESET);
            System.out.println("----------------------------------------");
            System.out.print("Choix > ");

            String choix = scanner.nextLine();
            switch (choix.toUpperCase()) {
                case "1":
                    gererBesoins();
                    break;
                case "2":
                    gererContraintes();
                    break;
                case "3":
                    gererRapports();
                    break;
                case "Q":
                    running = false;
                    break;
                default:
                    messageErreur("Choix invalide");
            }
        }
        System.out.println(YELLOW + "Au revoir !" + RESET);
    }

    // =================================================================================
    //                                SOUS-MENUS
    // =================================================================================

    private void gererBesoins() {
        boolean back = false;
        while (!back) {
            afficherEntete("GESTION DES BESOINS");
            System.out.println("1. " + BLUE + "Lister" + RESET + " les besoins");
            System.out.println("2. " + GREEN + "Créer" + RESET + " un besoin");
            System.out.println("3. " + YELLOW + "Modifier" + RESET + " (Changer état / Compléter)");
            System.out.println("4. " + RED + "Supprimer" + RESET + " un besoin");
            System.out.println("R. Retour");
            System.out.print("Choix > ");

            String choix = scanner.nextLine();
            switch (choix.toUpperCase()) {
                case "1":
                    listerBesoins();
                    pause();
                    break;
                case "2":
                    ajouterBesoin();
                    pause();
                    break;
                case "3":
                    modifierBesoin();
                    pause();
                    break;
                case "4":
                    supprimerBesoin();
                    pause();
                    break;
                case "R":
                    back = true;
                    break;
                default:
                    messageErreur("Choix incorrect");
            }
        }
    }

    private void gererContraintes() {
        boolean back = false;
        while (!back) {
            afficherEntete("GESTION DES CONTRAINTES");
            System.out.println("1. " + PURPLE + "Lister" + RESET + " les contraintes");
            System.out.println("2. " + GREEN + "Créer" + RESET + " une contrainte");
            System.out.println("3. " + YELLOW + "Modifier" + RESET + " (État / Vérification)");
            System.out.println("4. " + RED + "Supprimer" + RESET + " une contrainte");
            System.out.println("R. Retour");
            System.out.print("Choix > ");

            String choix = scanner.nextLine();
            switch (choix.toUpperCase()) {
                case "1":
                    listerContraintes();
                    pause();
                    break;
                case "2":
                    ajouterContrainte();
                    pause();
                    break;
                case "3":
                    modifierContrainte();
                    pause();
                    break;
                case "4":
                    supprimerContrainte();
                    pause();
                    break;
                case "R":
                    back = true;
                    break;
                default:
                    messageErreur("Choix incorrect");
            }
        }
    }

    private void gererRapports() {
        boolean back = false;
        while (!back) {
            afficherEntete("GESTION DES RAPPORTS");
            System.out.println("1. " + CYAN + "Lister" + RESET + " les rapports (session)");
            System.out.println("2. " + GREEN + "Générer" + RESET + " un nouveau rapport");
            System.out.println("3. " + YELLOW + "Modifier" + RESET + " (Ajout tâche + Régénération)");
            System.out.println("4. " + RED + "Supprimer" + RESET + " (De la liste)");
            System.out.println("R. Retour");
            System.out.print("Choix > ");

            String choix = scanner.nextLine();
            switch (choix.toUpperCase()) {
                case "1":
                    listerRapports();
                    pause();
                    break;
                case "2":
                    creerRapportInteractif();
                    pause();
                    break;
                case "3":
                    modifierRapport();
                    pause();
                    break;
                case "4":
                    supprimerRapport();
                    pause();
                    break;
                case "R":
                    back = true;
                    break;
                default:
                    messageErreur("Choix incorrect");
            }
        }
    }

    // =================================================================================
    //                            LOGIQUE MÉTIER : BESOINS
    // =================================================================================

    private void listerBesoins() {
        System.out.println("\n--- LISTE DES BESOINS ---");
        var liste = service.getTousLesBesoins();
        if (liste.isEmpty()) {
            System.out.println(YELLOW + "Aucun besoin enregistré." + RESET);
        } else {
            for (int i = 0; i < liste.size(); i++) {
                Besoin b = liste.get(i);
                // On affiche un résumé propre
                System.out.printf("[%d] %s -> %s%s%s\n", i, b.getDescription(), BLUE, b.getEtatCourant(), RESET);
            }
        }
    }

    private void ajouterBesoin() {
        System.out.println("\n--- NOUVEAU BESOIN ---");
        System.out.print("Description : ");
        String desc = scanner.nextLine();
        service.creerBesoin(desc);
        messageSucces("Besoin ajouté avec succès !");
    }

    private void modifierBesoin() {
        listerBesoins();
        System.out.print("Numéro du besoin à modifier : ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            Besoin b = service.getBesoin(index);

            if (b != null) {
                System.out.println("Sélection : " + BLUE + b.getDescription() + RESET);
                System.out.println("1. À Analyser");
                System.out.println("2. Analysé (Nécessite charge & dates)");
                System.out.println("3. Annulé (Nécessite raison)");
                System.out.println("4. Terminé");
                System.out.print("Nouvel état > ");

                String choixEtat = scanner.nextLine();

                switch (choixEtat) {
                    case "1":
                        b.setEtatCourant(Besoin.Etat.A_ANALYSER);
                        break;
                    case "2":
                        b.setEtatCourant(Besoin.Etat.ANALYSE);
                        System.out.print("Charge (j/h) : ");
                        b.setCharge(Double.parseDouble(scanner.nextLine()));
                        System.out.print("Responsable : ");
                        b.setResponsable(scanner.nextLine());
                        break;
                    case "3":
                        b.setEtatCourant(Besoin.Etat.ANNULE);
                        System.out.print("Raison de l'annulation : ");
                        b.setRaisonAnnulation(scanner.nextLine());
                        break;
                    case "4":
                        b.setEtatCourant(Besoin.Etat.TERMINE);
                        break;
                    default:
                        messageErreur("État inconnu, modification annulée.");
                        return;
                }
                service.mettreAJourBesoins();
                messageSucces("Besoin modifié.");
            } else {
                messageErreur("Numéro introuvable.");
            }
        } catch (Exception e) {
            messageErreur("Entrée invalide.");
        }
    }

    private void supprimerBesoin() {
        listerBesoins();
        System.out.print("Numéro à supprimer : ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            service.supprimerBesoin(index);
            messageSucces("Besoin supprimé.");
        } catch (Exception e) {
            messageErreur("Erreur lors de la suppression.");
        }
    }

    // =================================================================================
    //                          LOGIQUE MÉTIER : CONTRAINTES
    // =================================================================================

    private void listerContraintes() {
        System.out.println("\n--- LISTE DES CONTRAINTES ---");
        var liste = service.getToutesLesContraintes();
        if (liste.isEmpty()) System.out.println(YELLOW + "Aucune contrainte." + RESET);
        for (int i = 0; i < liste.size(); i++) {
            System.out.println("[" + i + "] " + liste.get(i));
        }
    }

    private void ajouterContrainte() {
        System.out.print("Libellé de la contrainte : ");
        String libelle = scanner.nextLine();
        service.creerContrainte(libelle);
        messageSucces("Contrainte ajoutée.");
    }

    private void modifierContrainte() {
        listerContraintes();
        System.out.print("Numéro à modifier : ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index < 0 || index >= service.getToutesLesContraintes().size()) {
                messageErreur("Numéro invalide.");
                return;
            }

            Contrainte c = service.getToutesLesContraintes().get(index);
            System.out.println("Sélection : " + PURPLE + c.getLibelle() + RESET);

            System.out.println("1. À prendre en compte");
            System.out.println("2. À vérifier");
            System.out.println("3. Vérifiée");
            System.out.println("4. Annulée");
            System.out.print("Nouvel état > ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1": c.setEtat(Contrainte.EtatContrainte.A_PRENDRE_EN_COMPTE); break;
                case "2": c.setEtat(Contrainte.EtatContrainte.A_VERIFIER); break;
                case "3":
                    c.setEtat(Contrainte.EtatContrainte.VERIFIEE);
                    System.out.print("Vérifié par : ");
                    c.setVerificateur(scanner.nextLine());
                    c.setDateVerification(java.time.LocalDate.now());
                    break;
                case "4":
                    c.setEtat(Contrainte.EtatContrainte.ANNULEE);
                    System.out.print("Raison : ");
                    c.setRaisonAnnulation(scanner.nextLine());
                    break;
            }
            service.mettreAJourContraintes();
            messageSucces("Contrainte mise à jour.");
        } catch (Exception e) {
            messageErreur("Erreur saisie.");
        }
    }

    private void supprimerContrainte() {
        listerContraintes();
        System.out.print("Numéro à supprimer : ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            service.supprimerContrainte(index);
            messageSucces("Contrainte supprimée.");
        } catch (Exception e) {
            messageErreur("Erreur.");
        }
    }

    // =================================================================================
    //                           LOGIQUE MÉTIER : RAPPORTS
    // =================================================================================

    private void listerRapports() {
        System.out.println("\n--- RAPPORTS DE LA SESSION ---");
        var liste = service.getTousLesRapports();
        if (liste.isEmpty()) System.out.println(YELLOW + "Aucun rapport." + RESET);
        for (int i = 0; i < liste.size(); i++) {
            System.out.println("[" + i + "] Rapport de " + liste.get(i).getAuteur() + " (" + liste.get(i).getDateReunion() + ")");
        }
    }

    private void creerRapportInteractif() {
        System.out.println("\n--- CRÉATION RAPPORT ---");
        System.out.print("Auteur : ");
        String auteur = scanner.nextLine();
        Rapport rapport = new Rapport(auteur, java.time.LocalDate.now());

        System.out.println("Participants ('fin' pour arrêter) :");
        while (true) {
            System.out.print("- Nom : ");
            String nom = scanner.nextLine();
            if (nom.equalsIgnoreCase("fin") || nom.isEmpty()) break;
            rapport.ajouterParticipant(nom);
        }

        System.out.println("Tâches ('fin' pour arrêter) :");
        while (true) {
            System.out.print("Quoi : ");
            String quoi = scanner.nextLine();
            if (quoi.equalsIgnoreCase("fin") || quoi.isEmpty()) break;
            System.out.print("Qui : ");
            String qui = scanner.nextLine();
            System.out.print("Quand (YYYY-MM-DD) : ");
            try {
                java.time.LocalDate date = java.time.LocalDate.parse(scanner.nextLine());
                rapport.ajouterTache(new TacheRapport(quoi, qui, date));
            } catch (Exception e) {
                System.out.println(RED + "Format date invalide, tâche ignorée." + RESET);
            }
        }
        service.creerRapport(rapport);
        messageSucces("Fichier généré !");
    }

    private void modifierRapport() {
        listerRapports();
        System.out.print("Numéro du rapport à compléter : ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (index < 0 || index >= service.getTousLesRapports().size()) return;

            Rapport r = service.getTousLesRapports().get(index);
            System.out.println("Ajout tâche pour le rapport de " + r.getAuteur());
            
            System.out.print("Quoi : ");
            String quoi = scanner.nextLine();
            System.out.print("Qui : ");
            String qui = scanner.nextLine();
            r.ajouterTache(new TacheRapport(quoi, qui, java.time.LocalDate.now()));

            service.creerRapport(r); // Régénération
            messageSucces("Rapport mis à jour et fichier régénéré.");
        } catch (Exception e) {
            messageErreur("Erreur.");
        }
    }

    private void supprimerRapport() {
        listerRapports();
        System.out.print("Numéro à retirer : ");
        try {
            service.supprimerRapport(Integer.parseInt(scanner.nextLine()));
            messageSucces("Rapport retiré.");
        } catch (Exception e) {
            messageErreur("Erreur.");
        }
    }

    // =================================================================================
    //                                UTILITAIRES UI
    // =================================================================================

    private void afficherEntete(String sousTitre) {
        nettoyerEcran();
        System.out.println(CYAN + "========================================" + RESET);
        System.out.println(CYAN + "      GESTION DE PROJET CONSOLE         " + RESET);
        System.out.println(CYAN + "========================================" + RESET);
        System.out.println("   >>> " + sousTitre + " <<<");
        System.out.println("----------------------------------------");
    }

    private void nettoyerEcran() {
        // Code ANSI pour nettoyer l'écran et remettre le curseur en haut
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void messageSucces(String msg) {
        System.out.println(GREEN + "✔ " + msg + RESET);
    }

    private void messageErreur(String msg) {
        System.out.println(RED + "✘ " + msg + RESET);
    }

    private void pause() {
        System.out.println("\nAppuyez sur [ENTRÉE] pour continuer...");
        scanner.nextLine();
    }
}
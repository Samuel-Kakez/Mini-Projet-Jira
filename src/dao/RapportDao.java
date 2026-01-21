package dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import model.Rapport;
import model.TacheRapport;

public class RapportDao {
    public void sauvegarderRapport(Rapport rapport) {

        // 1. Générer un nom de fichier unique
        // On remplace les espaces par des underscores

        String nomFichier = "Rapport_" + rapport.getDateReunion() + "_"
                + rapport.getAuteur().replace(" ", "_") + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {

            // 2. En-tête du document
            writer.write("==================================================\n");
            writer.write("              COMPTE RENDU DE RÉUNION             \n");
            writer.write("==================================================\n\n");

            writer.write("Auteur : " + rapport.getAuteur() + "\n");
            writer.write("Date : " + rapport.getDateReunion() + "\n");
            writer.write("Participants : " + rapport.getParticipants() + "\n\n");

            // 3. Tableau des actions
            writer.write("ACTIONS DÉCIDÉES :\n");
            writer.write("----------------------------------------------------------------\n");
            // Formatage : %-20s veut dire "colonne de 20 caractères alignée à gauche"
            writer.write(String.format("| %-30s | %-15s | %-10s |\n", "QUOI", "QUI", "QUAND"));
            writer.write("----------------------------------------------------------------\n");

            for (TacheRapport tache : rapport.getTaches()) {
                writer.write(String.format("| %-30s | %-15s | %-10s |\n",
                        tache.getQuoi(),
                        tache.getQui(),
                        tache.getQuand()));
            }
            writer.write("----------------------------------------------------------------\n");
            System.out.println("Rapport généré : " + nomFichier);

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du rapport : " + e.getMessage());
        }
    }
}

package dao;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Contrainte;
import model.Contrainte.EtatContrainte;

public class ContrainteDao {

    private static final String FICHIER_CSV = "contraintes.csv";
    private static final String SEP = ";";

    public void sauvegarderTout(List<Contrainte> liste) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_CSV))) {
            for (Contrainte c : liste) {
                // Astuce : On utilise une chaîne vide "" si la valeur est null pour ne pas écrire "null"
                String verif = (c.getVerificateur() == null) ? "null" : c.getVerificateur();
                String raison = (c.getRaisonAnnulation() == null) ? "null" : c.getRaisonAnnulation();
                String date = (c.getDateVerification() == null) ? "null" : c.getDateVerification().toString();

                String ligne = c.getLibelle().replace(";", ",") + SEP +
                               c.getEtat() + SEP +
                               verif + SEP +
                               date + SEP +
                               raison;
                
                writer.write(ligne);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde contraintes : " + e.getMessage());
        }
    }

    public List<Contrainte> chargerTout() {
        List<Contrainte> liste = new ArrayList<>();
        File fichier = new File(FICHIER_CSV);
        if (!fichier.exists()) return liste;

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] p = ligne.split(SEP);
                if (p.length >= 5) { // On attend 5 colonnes
                    Contrainte c = new Contrainte(p[0]);
                    
                    // Reconstruction de l'état
                    c.setEtat(EtatContrainte.valueOf(p[1]));

                    // Gestion des nulls à la lecture
                    if (!p[2].equals("null")) c.setVerificateur(p[2]);
                    if (!p[3].equals("null")) c.setDateVerification(LocalDate.parse(p[3]));
                    if (!p[4].equals("null")) c.setRaisonAnnulation(p[4]);

                    liste.add(c);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lecture contraintes : " + e.getMessage());
        }
        return liste;
    }
}
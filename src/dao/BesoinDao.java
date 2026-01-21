package dao;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Besoin;
import model.Besoin.Etat; // import de l'Enum

public class BesoinDao {
    private static final String FICHIER_CSV = "besoins.csv";
    private static final String SEPARATOR = ";";

    // --- SAUVEGARDE (écriture) ---

    public void sauvegarderTout(List<Besoin> besoins) {
        // Le try(...) ferme automatiquement le fichier à la fin, même en cas d'erreur
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_CSV))) {

            for (Besoin b : besoins) {
                // 1. On nettoie la description (on vire les ";")
                String descPropre = b.getDescription().replace(";", ";");

                // 2. On construit la ligne : Description;Etat;Date
                String ligne = descPropre + SEPARATOR +
                        b.getEtatCourant() + SEPARATOR +
                        b.getDateCreation();

                writer.write(ligne);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    // --- CHARGEMENT (Lecture) ---
    public List<Besoin> chargerTout(){
        List<Besoin> liste = new ArrayList<>();
        File fichier = new File(FICHIER_CSV);

        // Si le fichier n'existe pas, on retourne une liste vide
        if (!fichier.exists()){
            return liste;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))){
            String ligne;
            // On lit ligne par ligne jusqu'à la fin
            while ((ligne = reader.readLine()) != null){
                String[] parties = ligne.split(SEPARATOR);

                // Vérification pour éviter les erreurs sur une ligne vide
                if(parties.length >= 3){
                    String desc = parties[0];
                    String etatStr = parties[1];
                    String dateStr = parties[2];

                    // Reconstruction de l'objet
                    Besoin b = new Besoin(desc);
                    b.setEtatCourant((Etat.valueOf(etatStr))); // Convertit String -> Enum
                    b.setDateCreation(LocalDate.parse(dateStr)); // Convertit String -> Date

                    liste.add(b);
                }
            }
        }
        catch(IOException e){
            System.err.println("Erreur lecture du fichier : " + e.getMessage());
        }
        catch(Exception e){
            System.err.println("Erreur données corrompues ? : " + e.getMessage());
        }
        return liste;
    }
}

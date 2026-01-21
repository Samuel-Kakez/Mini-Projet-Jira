import java.time.LocalDate;
import model.Besoin;
import model.Contrainte;
import model.Rapport;
import model.TacheRapport;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Démarrage de l'application ---");

        // 1. Test Contrainte
        Contrainte c = new Contrainte("Pas de budget avant 2025");
        c.setEtat(Contrainte.EtatContrainte.ANNULEE);
        c.setRaisonAnnulation("Budget débloqué finalement");
        System.out.println("Contrainte : " + c);

        // 2. Test rapport avec listes
        Rapport r = new Rapport("Alice", LocalDate.now());

        // Ajout participants
        r.ajouterParticipant("bob");
        r.ajouterParticipant("Charlie");

        // Ajout tâche (quoi, qui, quand)
        TacheRapport t1 = new TacheRapport("Relancer le client", "Bob", LocalDate.now().plusDays(2));
        r.ajouterTache(t1);

        System.out.println(r);
    }
}
package com.example.jpa;

import com.example.jpa.entities.Etudiant;
import com.example.jpa.entities.Cours;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            // 1. Création de la EntityManagerFactory
            emf = Persistence.createEntityManagerFactory("gestionPU");

            // 2. Création de l'EntityManager
            em = emf.createEntityManager();

            // 3. Récupération de la transaction
            EntityTransaction tx = em.getTransaction();

            // ==========================================
            // 4. Ajouter un étudiant
            // ==========================================
            try {
                tx.begin();

                Etudiant etudiant = new Etudiant("Youssef", "youssef@gmail.com");
                em.persist(etudiant);

                tx.commit();

                System.out.println("Étudiant ajouté avec succès :");
                System.out.println(etudiant);

            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw e;
            }

            // ==========================================
            // 5. Ajouter un cours
            // ==========================================
            try {
                tx.begin();

                Cours cours = new Cours("Java", 4.0);
                em.persist(cours);

                tx.commit();

                System.out.println("\nCours ajouté avec succès :");
                System.out.println(cours);

            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw e;
            }

            // ==========================================
            // 6. Rechercher un étudiant par son ID
            // ==========================================
            List<Etudiant> etudiants = em.createQuery(
                    "SELECT e FROM Etudiant e",
                    Etudiant.class
            ).getResultList();

            if (!etudiants.isEmpty()) {
                Etudiant found = em.find(
                        Etudiant.class,
                        etudiants.get(0).getId()
                );

                System.out.println("\nÉtudiant recherché :");
                System.out.println(found);

                // ==========================================
                // 7. Modifier un étudiant
                // ==========================================
                try {
                    tx.begin();

                    found.setNom("Youssef Benali");

                    tx.commit();

                    System.out.println("\nÉtudiant modifié avec succès :");
                    System.out.println(found);

                } catch (Exception e) {
                    if (tx.isActive()) {
                        tx.rollback();
                    }
                    throw e;
                }
            }

            // ==========================================
            // 8. Afficher tous les étudiants
            // ==========================================
            List<Etudiant> listeEtudiants = em.createQuery(
                    "SELECT e FROM Etudiant e",
                    Etudiant.class
            ).getResultList();

            System.out.println("\n===== LISTE DES ÉTUDIANTS =====");

            for (Etudiant e : listeEtudiants) {
                System.out.println(e);
            }

            // ==========================================
            // 9. Afficher tous les cours
            // ==========================================
            List<Cours> listeCours = em.createQuery(
                    "SELECT c FROM Cours c",
                    Cours.class
            ).getResultList();

            System.out.println("\n===== LISTE DES COURS =====");

            for (Cours c : listeCours) {
                System.out.println(c);
            }

            // ==========================================
            // 10. Tester le rollback
            // ==========================================
            System.out.println("\n===== TEST DU ROLLBACK =====");

            try {
                tx.begin();

                Etudiant temporaire = new Etudiant(
                        "Etudiant temporaire",
                        "temporaire@gmail.com"
                );

                em.persist(temporaire);

                // Simulation d'une erreur
                throw new RuntimeException(
                        "Erreur simulée pour tester le rollback"
                );

            } catch (RuntimeException e) {
                if (tx.isActive()) {
                    tx.rollback();
                }

                System.out.println(
                        "Transaction annulée : " + e.getMessage()
                );
            }

            // ==========================================
            // 11. Vérifier les étudiants après rollback
            // ==========================================
            List<Etudiant> listeFinale = em.createQuery(
                    "SELECT e FROM Etudiant e",
                    Etudiant.class
            ).getResultList();

            System.out.println(
                    "\n===== ÉTUDIANTS APRÈS ROLLBACK ====="
            );

            for (Etudiant e : listeFinale) {
                System.out.println(e);
            }

        } catch (Exception e) {
            System.err.println(
                    "Une erreur est survenue pendant l'exécution :"
            );
            e.printStackTrace();

        } finally {
            // 12. Fermeture des ressources
            if (em != null && em.isOpen()) {
                em.close();
                System.out.println("\nEntityManager fermé.");
            }

            if (emf != null && emf.isOpen()) {
                emf.close();
                System.out.println("EntityManagerFactory fermée.");
            }
        }
    }
}

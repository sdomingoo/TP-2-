
package com.example.jpa.test;

import com.example.jpa.entities.Etudiant;
import com.example.jpa.entities.Cours;
import com.example.jpa.service.ServiceGestion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServiceGestionTest {

    private ServiceGestion service;

    @Before
    public void setUp() {
        service = new ServiceGestion();
    }

    @After
    public void tearDown() {
        if (service != null) {
            service.fermer();
        }
    }

    // Test 1 : Création et recherche d'un étudiant
    @Test
    public void testCreationEtRechercheEtudiant() {

        String email = "test.etudiant@example.com";

        Etudiant etudiant = new Etudiant("Test Etudiant", email);
        service.ajouterEtudiant(etudiant);

        assertNotNull(etudiant.getId());

        Etudiant resultat =
                service.rechercherEtudiant(etudiant.getId());

        assertNotNull(resultat);
        assertEquals("Test Etudiant", resultat.getNom());
        assertEquals(email, resultat.getEmail());
    }

    // Test 2 : Création et recherche d'un cours
    @Test
    public void testCreationEtRechercheCours() {

        Cours cours = new Cours("Java Test", 4.0);
        service.ajouterCours(cours);

        assertNotNull(cours.getId());

        Cours resultat =
                service.rechercherCours(cours.getId());

        assertNotNull(resultat);
        assertEquals("Java Test", resultat.getNom());
        assertEquals(4.0, resultat.getCoefficient(), 0.001);
    }

    // Test 3 : Affichage de la liste des étudiants
    @Test
    public void testListerEtudiants() {

        Etudiant etudiant = new Etudiant(
                "Etudiant Liste",
                "liste.etudiant@example.com"
        );

        service.ajouterEtudiant(etudiant);

        List<Etudiant> etudiants = service.listerEtudiants();

        assertNotNull(etudiants);
        assertTrue(
                etudiants.stream().anyMatch(
                        e -> "liste.etudiant@example.com"
                                .equals(e.getEmail())
                )
        );
    }

    // Test 4 : Recherche d'étudiants par nom
    @Test
    public void testEtudiantsParNom() {

        Etudiant etudiant = new Etudiant(
                "Alice Test",
                "alice.test@example.com"
        );

        service.ajouterEtudiant(etudiant);

        List<Etudiant> resultats =
                service.etudiantsParNom("Alice Test");

        assertNotNull(resultats);
        assertTrue(
                resultats.stream().anyMatch(
                        e -> "alice.test@example.com"
                                .equals(e.getEmail())
                )
        );
    }

    // Test 5 : Recherche des cours par coefficient minimal
    @Test
    public void testCoursParCoefficientMin() {

        Cours cours = new Cours("Hibernate Test", 5.0);
        service.ajouterCours(cours);

        List<Cours> resultats =
                service.coursParCoefficientMin(4.0);

        assertNotNull(resultats);
        assertTrue(
                resultats.stream().anyMatch(
                        c -> "Hibernate Test".equals(c.getNom())
                )
        );
    }
}

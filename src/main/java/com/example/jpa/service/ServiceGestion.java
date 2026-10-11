package com.example.jpa.service;

import com.example.jpa.DAO.EtudiantDao;
import com.example.jpa.DAO.CoursDao;
import com.example.jpa.entities.Etudiant;
import com.example.jpa.entities.Cours;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ServiceGestion {

    private EntityManagerFactory emf;
    private EntityManager em;

    private EtudiantDao etudiantDao;
    private CoursDao coursDao;

    // Constructeur
    public ServiceGestion() {
        emf = Persistence.createEntityManagerFactory("gestionPU");
        em = emf.createEntityManager();

        etudiantDao = new EtudiantDao(em);
        coursDao = new CoursDao(em);
    }

    // Ajouter un étudiant
    public void ajouterEtudiant(Etudiant etudiant) {
        etudiantDao.create(etudiant);
    }

    // Ajouter un cours
    public void ajouterCours(Cours cours) {
        coursDao.create(cours);
    }

    // Rechercher un étudiant par son ID
    public Etudiant rechercherEtudiant(Long id) {
        return etudiantDao.find(id);
    }

    // Rechercher un cours par son ID
    public Cours rechercherCours(Long id) {
        return coursDao.find(id);
    }

    // Lister tous les étudiants
    public List<Etudiant> listerEtudiants() {
        return etudiantDao.findAll();
    }

    // Lister tous les cours
    public List<Cours> listerCours() {
        return coursDao.findAll();
    }

    // Rechercher les étudiants par nom
    public List<Etudiant> etudiantsParNom(String nom) {
        return etudiantDao.findByNom(nom);
    }

    // Rechercher les cours par coefficient minimal
    public List<Cours> coursParCoefficientMin(double coefficient) {
        return coursDao.findByCoefficientMin(coefficient);
    }

    // Modifier un étudiant
    public Etudiant modifierEtudiant(Etudiant etudiant) {
        return etudiantDao.update(etudiant);
    }

    // Modifier un cours
    public Cours modifierCours(Cours cours) {
        return coursDao.update(cours);
    }

    // Supprimer un étudiant
    public void supprimerEtudiant(Etudiant etudiant) {
        etudiantDao.delete(etudiant);
    }

    // Supprimer un cours
    public void supprimerCours(Cours cours) {
        coursDao.delete(cours);
    }

    // Fermer les ressources
    public void fermer() {
        if (em != null && em.isOpen()) {
            em.close();
        }

        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}

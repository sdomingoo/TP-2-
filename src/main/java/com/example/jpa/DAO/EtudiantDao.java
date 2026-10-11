
package com.example.jpa.DAO;

import com.example.jpa.entities.Etudiant;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EtudiantDao extends GenericDao<Etudiant> {

    public EtudiantDao(EntityManager em) {
        super(em, Etudiant.class);
    }

    // Search students by exact name
    public List<Etudiant> findByNom(String nom) {
        TypedQuery<Etudiant> query = em.createQuery(
                "SELECT e FROM Etudiant e WHERE e.nom = :nom",
                Etudiant.class
        );

        query.setParameter("nom", nom);
        return query.getResultList();
    }

    // Search students whose names contain a string
    public List<Etudiant> findByNomContaining(String texte) {
        TypedQuery<Etudiant> query = em.createQuery(
                "SELECT e FROM Etudiant e WHERE LOWER(e.nom) LIKE LOWER(:pattern)",
                Etudiant.class
        );

        query.setParameter("pattern", "%" + texte + "%");
        return query.getResultList();
    }

    // Search students by email
    public Etudiant findByEmail(String email) {
        TypedQuery<Etudiant> query = em.createQuery(
                "SELECT e FROM Etudiant e WHERE e.email = :email",
                Etudiant.class
        );

        query.setParameter("email", email);
        List<Etudiant> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }
}

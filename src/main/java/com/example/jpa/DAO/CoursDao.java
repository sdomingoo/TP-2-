
package com.example.jpa.DAO;

import com.example.jpa.entities.Cours;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CoursDao extends GenericDao<Cours> {

    public CoursDao(EntityManager em) {
        super(em, Cours.class);
    }

    // Search courses with a minimum coefficient
    public List<Cours> findByCoefficientMin(double coefficientMin) {
        TypedQuery<Cours> query = em.createQuery(
                "SELECT c FROM Cours c WHERE c.coefficient >= :minCoefficient",
                Cours.class
        );

        query.setParameter("minCoefficient", coefficientMin);
        return query.getResultList();
    }

    // Search courses whose names contain a string
    public List<Cours> findByNomContaining(String texte) {
        TypedQuery<Cours> query = em.createQuery(
                "SELECT c FROM Cours c WHERE LOWER(c.nom) LIKE LOWER(:pattern)",
                Cours.class
        );

        query.setParameter("pattern", "%" + texte + "%");
        return query.getResultList();
    }
}

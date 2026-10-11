
package com.example.jpa;

import com.example.jpa.DAO.EtudiantDao;
import com.example.jpa.DAO.CoursDao;
import com.example.jpa.entities.Etudiant;
import com.example.jpa.entities.Cours;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TestRequetesJPQL {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("gestionPU");

        try {
            EntityManager em = emf.createEntityManager();

            try {
                EtudiantDao etudiantDao = new EtudiantDao(em);
                CoursDao coursDao = new CoursDao(em);

                List<Etudiant> etudiants =
                        etudiantDao.findByNomContaining("Jean");

                System.out.println("Étudiants trouvés :");
                etudiants.forEach(System.out::println);

                List<Cours> cours =
                        coursDao.findByCoefficientMin(2.0);

                System.out.println("Cours avec un coefficient >= 2 :");
                cours.forEach(System.out::println);

            } finally {
                em.close();
            }

        } finally {
            emf.close();
        }
    }
}

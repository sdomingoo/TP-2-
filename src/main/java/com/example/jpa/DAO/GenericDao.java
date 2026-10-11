
package com.example.jpa.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class GenericDao<T> {

    protected final EntityManager em;
    private final Class<T> entityClass;

    public GenericDao(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    // Create an entity
    public T create(T entity) {
        executeInTransaction(() -> em.persist(entity));
        return entity;
    }

    // Find an entity by its ID
    public T find(Object id) {
        return em.find(entityClass, id);
    }

    // Update an entity
    public T update(T entity) {
        executeInTransaction(() -> em.merge(entity));
        return entity;
    }

    // Delete an entity
    public void delete(T entity) {
        executeInTransaction(() -> {
            T managedEntity = em.contains(entity)
                    ? entity
                    : em.merge(entity);

            em.remove(managedEntity);
        });
    }

    // Find all entities
    public List<T> findAll() {
        String jpql = "SELECT e FROM "
                + entityClass.getSimpleName() + " e";

        TypedQuery<T> query = em.createQuery(jpql, entityClass);
        return query.getResultList();
    }

    // Execute an operation safely inside a transaction
    protected void executeInTransaction(Runnable operation) {
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            operation.run();
            tx.commit();

        } catch (RuntimeException e) {
            if (tx.isActive()) {
                try {
                    tx.rollback();
                } catch (RuntimeException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
            }

            System.err.println(
                    "Transaction error: " + e.getMessage()
            );

            throw e;
        }
    }
}

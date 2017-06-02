/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.dao;

import com.sun.ejb.containers.TimerState;
import edu.co.sergio.mundo.dao.exceptions.NonexistentEntityException;
import edu.co.sergio.mundo.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author james
 */
public class TimerStateJpaController implements Serializable {

    public TimerStateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TimerState timerState) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(timerState);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTimerState(timerState.getTimerId()) != null) {
                throw new PreexistingEntityException("TimerState " + timerState + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TimerState timerState) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            timerState = em.merge(timerState);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = timerState.getTimerId();
                if (findTimerState(id) == null) {
                    throw new NonexistentEntityException("The timerState with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TimerState timerState;
            try {
                timerState = em.getReference(TimerState.class, id);
                timerState.getTimerId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The timerState with id " + id + " no longer exists.", enfe);
            }
            em.remove(timerState);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TimerState> findTimerStateEntities() {
        return findTimerStateEntities(true, -1, -1);
    }

    public List<TimerState> findTimerStateEntities(int maxResults, int firstResult) {
        return findTimerStateEntities(false, maxResults, firstResult);
    }

    private List<TimerState> findTimerStateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TimerState.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TimerState findTimerState(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TimerState.class, id);
        } finally {
            em.close();
        }
    }

    public int getTimerStateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TimerState> rt = cq.from(TimerState.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

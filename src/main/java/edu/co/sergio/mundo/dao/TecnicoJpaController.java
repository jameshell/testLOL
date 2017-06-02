/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.dao;

import edu.co.sergio.mundo.dao.exceptions.NonexistentEntityException;
import edu.co.sergio.mundo.dao.exceptions.PreexistingEntityException;
import edu.co.sergio.mundo.vo.Tecnico;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author james
 */
public class TecnicoJpaController implements Serializable {

    private EntityManager em=null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tecnico tecnico) throws PreexistingEntityException, Exception {
        startOperation();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tecnico);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTecnico(tecnico.getIdtecnico()) != null) {
                throw new PreexistingEntityException("Tecnico " + tecnico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                emf.close();
            }
        }
    }

    public void edit(Tecnico tecnico) throws NonexistentEntityException, Exception {
        startOperation();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tecnico = em.merge(tecnico);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tecnico.getIdtecnico();
                if (findTecnico(id) == null) {
                    throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                emf.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        startOperation();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tecnico tecnico;
            try {
                tecnico = em.getReference(Tecnico.class, id);
                tecnico.getIdtecnico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.", enfe);
            }
            em.remove(tecnico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
                emf.close();
            }
        }
    }

    public List<Tecnico> findTecnicoEntities() {
        return findTecnicoEntities(true, -1, -1);
    }

    public List<Tecnico> findTecnicoEntities(int maxResults, int firstResult) {
        return findTecnicoEntities(false, maxResults, firstResult);
    }

    private List<Tecnico> findTecnicoEntities(boolean all, int maxResults, int firstResult) {
        startOperation();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tecnico.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
            emf.close();
        }
    }

    public Tecnico findTecnico(Integer id) {
        startOperation();
        try {
            return em.find(Tecnico.class, id);
        } finally {
            em.close();
            emf.close();
        }
    }

    public int getTecnicoCount() {
        startOperation();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tecnico> rt = cq.from(Tecnico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
            emf.close();
        }
    }
     protected void startOperation() { 
        URI dbUri = null;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL")); 
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            Map<String, String> properties = new HashMap<String, String>();
            properties.put("javax.persistence.jdbc.url", dbUrl);
            properties.put("javax.persistence.jdbc.user", username );
            properties.put("javax.persistence.jdbc.password", password );
            properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            this.emf = Persistence.createEntityManagerFactory("catalogPU",properties);
            this.em = emf.createEntityManager();
        } catch (URISyntaxException ex) {
            Logger.getLogger(TransaccionregistroJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
}

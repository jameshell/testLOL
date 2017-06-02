/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.dao;

import edu.co.sergio.mundo.dao.exceptions.IllegalOrphanException;
import edu.co.sergio.mundo.dao.exceptions.NonexistentEntityException;
import edu.co.sergio.mundo.dao.exceptions.PreexistingEntityException;
import edu.co.sergio.mundo.vo.Recolector;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.co.sergio.mundo.vo.Transaccionrecoleccion;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author james
 */
public class RecolectorJpaController implements Serializable {

    private EntityManager em=null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recolector recolector) throws PreexistingEntityException, Exception {
        if (recolector.getTransaccionrecoleccionCollection() == null) {
            recolector.setTransaccionrecoleccionCollection(new ArrayList<Transaccionrecoleccion>());
        }
        startOperation();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Transaccionrecoleccion> attachedTransaccionrecoleccionCollection = new ArrayList<Transaccionrecoleccion>();
            for (Transaccionrecoleccion transaccionrecoleccionCollectionTransaccionrecoleccionToAttach : recolector.getTransaccionrecoleccionCollection()) {
                transaccionrecoleccionCollectionTransaccionrecoleccionToAttach = em.getReference(transaccionrecoleccionCollectionTransaccionrecoleccionToAttach.getClass(), transaccionrecoleccionCollectionTransaccionrecoleccionToAttach.getIdtransaccionrecoleccion());
                attachedTransaccionrecoleccionCollection.add(transaccionrecoleccionCollectionTransaccionrecoleccionToAttach);
            }
            recolector.setTransaccionrecoleccionCollection(attachedTransaccionrecoleccionCollection);
            em.persist(recolector);
            for (Transaccionrecoleccion transaccionrecoleccionCollectionTransaccionrecoleccion : recolector.getTransaccionrecoleccionCollection()) {
                Recolector oldIdrecolectorOfTransaccionrecoleccionCollectionTransaccionrecoleccion = transaccionrecoleccionCollectionTransaccionrecoleccion.getIdrecolector();
                transaccionrecoleccionCollectionTransaccionrecoleccion.setIdrecolector(recolector);
                transaccionrecoleccionCollectionTransaccionrecoleccion = em.merge(transaccionrecoleccionCollectionTransaccionrecoleccion);
                if (oldIdrecolectorOfTransaccionrecoleccionCollectionTransaccionrecoleccion != null) {
                    oldIdrecolectorOfTransaccionrecoleccionCollectionTransaccionrecoleccion.getTransaccionrecoleccionCollection().remove(transaccionrecoleccionCollectionTransaccionrecoleccion);
                    oldIdrecolectorOfTransaccionrecoleccionCollectionTransaccionrecoleccion = em.merge(oldIdrecolectorOfTransaccionrecoleccionCollectionTransaccionrecoleccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecolector(recolector.getIdrecolector()) != null) {
                throw new PreexistingEntityException("Recolector " + recolector + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                emf.close();
            }
        }
    }

    public void edit(Recolector recolector) throws IllegalOrphanException, NonexistentEntityException, Exception {
        startOperation();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recolector persistentRecolector = em.find(Recolector.class, recolector.getIdrecolector());
            Collection<Transaccionrecoleccion> transaccionrecoleccionCollectionOld = persistentRecolector.getTransaccionrecoleccionCollection();
            Collection<Transaccionrecoleccion> transaccionrecoleccionCollectionNew = recolector.getTransaccionrecoleccionCollection();
            List<String> illegalOrphanMessages = null;
            for (Transaccionrecoleccion transaccionrecoleccionCollectionOldTransaccionrecoleccion : transaccionrecoleccionCollectionOld) {
                if (!transaccionrecoleccionCollectionNew.contains(transaccionrecoleccionCollectionOldTransaccionrecoleccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaccionrecoleccion " + transaccionrecoleccionCollectionOldTransaccionrecoleccion + " since its idrecolector field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Transaccionrecoleccion> attachedTransaccionrecoleccionCollectionNew = new ArrayList<Transaccionrecoleccion>();
            for (Transaccionrecoleccion transaccionrecoleccionCollectionNewTransaccionrecoleccionToAttach : transaccionrecoleccionCollectionNew) {
                transaccionrecoleccionCollectionNewTransaccionrecoleccionToAttach = em.getReference(transaccionrecoleccionCollectionNewTransaccionrecoleccionToAttach.getClass(), transaccionrecoleccionCollectionNewTransaccionrecoleccionToAttach.getIdtransaccionrecoleccion());
                attachedTransaccionrecoleccionCollectionNew.add(transaccionrecoleccionCollectionNewTransaccionrecoleccionToAttach);
            }
            transaccionrecoleccionCollectionNew = attachedTransaccionrecoleccionCollectionNew;
            recolector.setTransaccionrecoleccionCollection(transaccionrecoleccionCollectionNew);
            recolector = em.merge(recolector);
            for (Transaccionrecoleccion transaccionrecoleccionCollectionNewTransaccionrecoleccion : transaccionrecoleccionCollectionNew) {
                if (!transaccionrecoleccionCollectionOld.contains(transaccionrecoleccionCollectionNewTransaccionrecoleccion)) {
                    Recolector oldIdrecolectorOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion = transaccionrecoleccionCollectionNewTransaccionrecoleccion.getIdrecolector();
                    transaccionrecoleccionCollectionNewTransaccionrecoleccion.setIdrecolector(recolector);
                    transaccionrecoleccionCollectionNewTransaccionrecoleccion = em.merge(transaccionrecoleccionCollectionNewTransaccionrecoleccion);
                    if (oldIdrecolectorOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion != null && !oldIdrecolectorOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion.equals(recolector)) {
                        oldIdrecolectorOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion.getTransaccionrecoleccionCollection().remove(transaccionrecoleccionCollectionNewTransaccionrecoleccion);
                        oldIdrecolectorOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion = em.merge(oldIdrecolectorOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recolector.getIdrecolector();
                if (findRecolector(id) == null) {
                    throw new NonexistentEntityException("The recolector with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        startOperation();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recolector recolector;
            try {
                recolector = em.getReference(Recolector.class, id);
                recolector.getIdrecolector();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recolector with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Transaccionrecoleccion> transaccionrecoleccionCollectionOrphanCheck = recolector.getTransaccionrecoleccionCollection();
            for (Transaccionrecoleccion transaccionrecoleccionCollectionOrphanCheckTransaccionrecoleccion : transaccionrecoleccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Recolector (" + recolector + ") cannot be destroyed since the Transaccionrecoleccion " + transaccionrecoleccionCollectionOrphanCheckTransaccionrecoleccion + " in its transaccionrecoleccionCollection field has a non-nullable idrecolector field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(recolector);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
                emf.close();
            }
        }
    }

    public List<Recolector> findRecolectorEntities() {
        return findRecolectorEntities(true, -1, -1);
    }

    public List<Recolector> findRecolectorEntities(int maxResults, int firstResult) {
        return findRecolectorEntities(false, maxResults, firstResult);
    }

    private List<Recolector> findRecolectorEntities(boolean all, int maxResults, int firstResult) {
        startOperation();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recolector.class));
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

    public Recolector findRecolector(Integer id) {
         startOperation();
        try {
            return em.find(Recolector.class, id);
        } finally {
            em.close();
            emf.close();
        }
    }

    public int getRecolectorCount() {
        startOperation();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recolector> rt = cq.from(Recolector.class);
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
            Logger.getLogger(RecolectorJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
}

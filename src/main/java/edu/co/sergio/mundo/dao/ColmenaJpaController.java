/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.dao;

import edu.co.sergio.mundo.dao.exceptions.IllegalOrphanException;
import edu.co.sergio.mundo.dao.exceptions.NonexistentEntityException;
import edu.co.sergio.mundo.dao.exceptions.PreexistingEntityException;
import edu.co.sergio.mundo.vo.Colmena;
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
public class ColmenaJpaController implements Serializable {

      private EntityManager em=null;
     private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Colmena colmena) throws PreexistingEntityException, Exception {
        if (colmena.getTransaccionrecoleccionCollection() == null) {
            colmena.setTransaccionrecoleccionCollection(new ArrayList<Transaccionrecoleccion>());
        }
        startOperation();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Transaccionrecoleccion> attachedTransaccionrecoleccionCollection = new ArrayList<Transaccionrecoleccion>();
            for (Transaccionrecoleccion transaccionrecoleccionCollectionTransaccionrecoleccionToAttach : colmena.getTransaccionrecoleccionCollection()) {
                transaccionrecoleccionCollectionTransaccionrecoleccionToAttach = em.getReference(transaccionrecoleccionCollectionTransaccionrecoleccionToAttach.getClass(), transaccionrecoleccionCollectionTransaccionrecoleccionToAttach.getIdtransaccionrecoleccion());
                attachedTransaccionrecoleccionCollection.add(transaccionrecoleccionCollectionTransaccionrecoleccionToAttach);
            }
            colmena.setTransaccionrecoleccionCollection(attachedTransaccionrecoleccionCollection);
            em.persist(colmena);
            for (Transaccionrecoleccion transaccionrecoleccionCollectionTransaccionrecoleccion : colmena.getTransaccionrecoleccionCollection()) {
                Colmena oldIdcolmenaOfTransaccionrecoleccionCollectionTransaccionrecoleccion = transaccionrecoleccionCollectionTransaccionrecoleccion.getIdcolmena();
                transaccionrecoleccionCollectionTransaccionrecoleccion.setIdcolmena(colmena);
                transaccionrecoleccionCollectionTransaccionrecoleccion = em.merge(transaccionrecoleccionCollectionTransaccionrecoleccion);
                if (oldIdcolmenaOfTransaccionrecoleccionCollectionTransaccionrecoleccion != null) {
                    oldIdcolmenaOfTransaccionrecoleccionCollectionTransaccionrecoleccion.getTransaccionrecoleccionCollection().remove(transaccionrecoleccionCollectionTransaccionrecoleccion);
                    oldIdcolmenaOfTransaccionrecoleccionCollectionTransaccionrecoleccion = em.merge(oldIdcolmenaOfTransaccionrecoleccionCollectionTransaccionrecoleccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findColmena(colmena.getIdcolmena()) != null) {
                throw new PreexistingEntityException("Colmena " + colmena + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                emf.close();
            }
        }
    }

    public void edit(Colmena colmena) throws IllegalOrphanException, NonexistentEntityException, Exception {
        startOperation();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Colmena persistentColmena = em.find(Colmena.class, colmena.getIdcolmena());
            Collection<Transaccionrecoleccion> transaccionrecoleccionCollectionOld = persistentColmena.getTransaccionrecoleccionCollection();
            Collection<Transaccionrecoleccion> transaccionrecoleccionCollectionNew = colmena.getTransaccionrecoleccionCollection();
            List<String> illegalOrphanMessages = null;
            for (Transaccionrecoleccion transaccionrecoleccionCollectionOldTransaccionrecoleccion : transaccionrecoleccionCollectionOld) {
                if (!transaccionrecoleccionCollectionNew.contains(transaccionrecoleccionCollectionOldTransaccionrecoleccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaccionrecoleccion " + transaccionrecoleccionCollectionOldTransaccionrecoleccion + " since its idcolmena field is not nullable.");
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
            colmena.setTransaccionrecoleccionCollection(transaccionrecoleccionCollectionNew);
            colmena = em.merge(colmena);
            for (Transaccionrecoleccion transaccionrecoleccionCollectionNewTransaccionrecoleccion : transaccionrecoleccionCollectionNew) {
                if (!transaccionrecoleccionCollectionOld.contains(transaccionrecoleccionCollectionNewTransaccionrecoleccion)) {
                    Colmena oldIdcolmenaOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion = transaccionrecoleccionCollectionNewTransaccionrecoleccion.getIdcolmena();
                    transaccionrecoleccionCollectionNewTransaccionrecoleccion.setIdcolmena(colmena);
                    transaccionrecoleccionCollectionNewTransaccionrecoleccion = em.merge(transaccionrecoleccionCollectionNewTransaccionrecoleccion);
                    if (oldIdcolmenaOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion != null && !oldIdcolmenaOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion.equals(colmena)) {
                        oldIdcolmenaOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion.getTransaccionrecoleccionCollection().remove(transaccionrecoleccionCollectionNewTransaccionrecoleccion);
                        oldIdcolmenaOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion = em.merge(oldIdcolmenaOfTransaccionrecoleccionCollectionNewTransaccionrecoleccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = colmena.getIdcolmena();
                if (findColmena(id) == null) {
                    throw new NonexistentEntityException("The colmena with id " + id + " no longer exists.");
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
            Colmena colmena;
            try {
                colmena = em.getReference(Colmena.class, id);
                colmena.getIdcolmena();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The colmena with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Transaccionrecoleccion> transaccionrecoleccionCollectionOrphanCheck = colmena.getTransaccionrecoleccionCollection();
            for (Transaccionrecoleccion transaccionrecoleccionCollectionOrphanCheckTransaccionrecoleccion : transaccionrecoleccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Colmena (" + colmena + ") cannot be destroyed since the Transaccionrecoleccion " + transaccionrecoleccionCollectionOrphanCheckTransaccionrecoleccion + " in its transaccionrecoleccionCollection field has a non-nullable idcolmena field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(colmena);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
                emf.close();
            }
        }
    }

    public List<Colmena> findColmenaEntities() {
        return findColmenaEntities(true, -1, -1);
    }

    public List<Colmena> findColmenaEntities(int maxResults, int firstResult) {
        return findColmenaEntities(false, maxResults, firstResult);
    }

    private List<Colmena> findColmenaEntities(boolean all, int maxResults, int firstResult) {
        startOperation();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Colmena.class));
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

    public Colmena findColmena(Integer id) {
       startOperation();
        try {
            return em.find(Colmena.class, id);
        } finally {
            em.close();
            emf.close();
        }
    }

    public int getColmenaCount() {
        startOperation();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Colmena> rt = cq.from(Colmena.class);
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

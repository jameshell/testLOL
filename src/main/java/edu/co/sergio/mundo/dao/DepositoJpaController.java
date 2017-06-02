/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.dao;

import edu.co.sergio.mundo.dao.exceptions.NonexistentEntityException;
import edu.co.sergio.mundo.dao.exceptions.PreexistingEntityException;
import edu.co.sergio.mundo.vo.Deposito;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.co.sergio.mundo.vo.Transaccionrecoleccion;
import java.net.URI;
import java.net.URISyntaxException;
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
public class DepositoJpaController implements Serializable {

    private EntityManager em=null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Deposito deposito) throws PreexistingEntityException, Exception {
        startOperation();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccionrecoleccion idtransaccionrecoleccion = deposito.getIdtransaccionrecoleccion();
            if (idtransaccionrecoleccion != null) {
                idtransaccionrecoleccion = em.getReference(idtransaccionrecoleccion.getClass(), idtransaccionrecoleccion.getIdtransaccionrecoleccion());
                deposito.setIdtransaccionrecoleccion(idtransaccionrecoleccion);
            }
            em.persist(deposito);
            if (idtransaccionrecoleccion != null) {
                idtransaccionrecoleccion.getDepositoCollection().add(deposito);
                idtransaccionrecoleccion = em.merge(idtransaccionrecoleccion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDeposito(deposito.getIddeposito()) != null) {
                throw new PreexistingEntityException("Deposito " + deposito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
                emf.close();
            }
        }
    }

    public void edit(Deposito deposito) throws NonexistentEntityException, Exception {
        startOperation();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deposito persistentDeposito = em.find(Deposito.class, deposito.getIddeposito());
            Transaccionrecoleccion idtransaccionrecoleccionOld = persistentDeposito.getIdtransaccionrecoleccion();
            Transaccionrecoleccion idtransaccionrecoleccionNew = deposito.getIdtransaccionrecoleccion();
            if (idtransaccionrecoleccionNew != null) {
                idtransaccionrecoleccionNew = em.getReference(idtransaccionrecoleccionNew.getClass(), idtransaccionrecoleccionNew.getIdtransaccionrecoleccion());
                deposito.setIdtransaccionrecoleccion(idtransaccionrecoleccionNew);
            }
            deposito = em.merge(deposito);
            if (idtransaccionrecoleccionOld != null && !idtransaccionrecoleccionOld.equals(idtransaccionrecoleccionNew)) {
                idtransaccionrecoleccionOld.getDepositoCollection().remove(deposito);
                idtransaccionrecoleccionOld = em.merge(idtransaccionrecoleccionOld);
            }
            if (idtransaccionrecoleccionNew != null && !idtransaccionrecoleccionNew.equals(idtransaccionrecoleccionOld)) {
                idtransaccionrecoleccionNew.getDepositoCollection().add(deposito);
                idtransaccionrecoleccionNew = em.merge(idtransaccionrecoleccionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = deposito.getIddeposito();
                if (findDeposito(id) == null) {
                    throw new NonexistentEntityException("The deposito with id " + id + " no longer exists.");
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
            Deposito deposito;
            try {
                deposito = em.getReference(Deposito.class, id);
                deposito.getIddeposito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deposito with id " + id + " no longer exists.", enfe);
            }
            Transaccionrecoleccion idtransaccionrecoleccion = deposito.getIdtransaccionrecoleccion();
            if (idtransaccionrecoleccion != null) {
                idtransaccionrecoleccion.getDepositoCollection().remove(deposito);
                idtransaccionrecoleccion = em.merge(idtransaccionrecoleccion);
            }
            em.remove(deposito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
                emf.close();
            }
        }
    }

    public List<Deposito> findDepositoEntities() {
        return findDepositoEntities(true, -1, -1);
    }

    public List<Deposito> findDepositoEntities(int maxResults, int firstResult) {
        return findDepositoEntities(false, maxResults, firstResult);
    }

    private List<Deposito> findDepositoEntities(boolean all, int maxResults, int firstResult) {
        startOperation();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Deposito.class));
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

    public Deposito findDeposito(Integer id) {
       startOperation();
        try {
            return em.find(Deposito.class, id);
        } finally {
            em.close();
            emf.close();
        }
    }

    public int getDepositoCount() {
        startOperation();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Deposito> rt = cq.from(Deposito.class);
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

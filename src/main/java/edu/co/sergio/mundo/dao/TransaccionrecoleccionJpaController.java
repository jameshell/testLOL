/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sergio.mundo.dao;

import edu.co.sergio.mundo.dao.exceptions.IllegalOrphanException;
import edu.co.sergio.mundo.dao.exceptions.NonexistentEntityException;
import edu.co.sergio.mundo.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.co.sergio.mundo.vo.Colmena;
import edu.co.sergio.mundo.vo.Recolector;
import edu.co.sergio.mundo.vo.Deposito;
import edu.co.sergio.mundo.vo.Transaccionrecoleccion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author james
 */
public class TransaccionrecoleccionJpaController implements Serializable {

    public TransaccionrecoleccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaccionrecoleccion transaccionrecoleccion) throws PreexistingEntityException, Exception {
        if (transaccionrecoleccion.getDepositoCollection() == null) {
            transaccionrecoleccion.setDepositoCollection(new ArrayList<Deposito>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Colmena idcolmena = transaccionrecoleccion.getIdcolmena();
            if (idcolmena != null) {
                idcolmena = em.getReference(idcolmena.getClass(), idcolmena.getIdcolmena());
                transaccionrecoleccion.setIdcolmena(idcolmena);
            }
            Recolector idrecolector = transaccionrecoleccion.getIdrecolector();
            if (idrecolector != null) {
                idrecolector = em.getReference(idrecolector.getClass(), idrecolector.getIdrecolector());
                transaccionrecoleccion.setIdrecolector(idrecolector);
            }
            Collection<Deposito> attachedDepositoCollection = new ArrayList<Deposito>();
            for (Deposito depositoCollectionDepositoToAttach : transaccionrecoleccion.getDepositoCollection()) {
                depositoCollectionDepositoToAttach = em.getReference(depositoCollectionDepositoToAttach.getClass(), depositoCollectionDepositoToAttach.getIddeposito());
                attachedDepositoCollection.add(depositoCollectionDepositoToAttach);
            }
            transaccionrecoleccion.setDepositoCollection(attachedDepositoCollection);
            em.persist(transaccionrecoleccion);
            if (idcolmena != null) {
                idcolmena.getTransaccionrecoleccionCollection().add(transaccionrecoleccion);
                idcolmena = em.merge(idcolmena);
            }
            if (idrecolector != null) {
                idrecolector.getTransaccionrecoleccionCollection().add(transaccionrecoleccion);
                idrecolector = em.merge(idrecolector);
            }
            for (Deposito depositoCollectionDeposito : transaccionrecoleccion.getDepositoCollection()) {
                Transaccionrecoleccion oldIdtransaccionrecoleccionOfDepositoCollectionDeposito = depositoCollectionDeposito.getIdtransaccionrecoleccion();
                depositoCollectionDeposito.setIdtransaccionrecoleccion(transaccionrecoleccion);
                depositoCollectionDeposito = em.merge(depositoCollectionDeposito);
                if (oldIdtransaccionrecoleccionOfDepositoCollectionDeposito != null) {
                    oldIdtransaccionrecoleccionOfDepositoCollectionDeposito.getDepositoCollection().remove(depositoCollectionDeposito);
                    oldIdtransaccionrecoleccionOfDepositoCollectionDeposito = em.merge(oldIdtransaccionrecoleccionOfDepositoCollectionDeposito);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransaccionrecoleccion(transaccionrecoleccion.getIdtransaccionrecoleccion()) != null) {
                throw new PreexistingEntityException("Transaccionrecoleccion " + transaccionrecoleccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaccionrecoleccion transaccionrecoleccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccionrecoleccion persistentTransaccionrecoleccion = em.find(Transaccionrecoleccion.class, transaccionrecoleccion.getIdtransaccionrecoleccion());
            Colmena idcolmenaOld = persistentTransaccionrecoleccion.getIdcolmena();
            Colmena idcolmenaNew = transaccionrecoleccion.getIdcolmena();
            Recolector idrecolectorOld = persistentTransaccionrecoleccion.getIdrecolector();
            Recolector idrecolectorNew = transaccionrecoleccion.getIdrecolector();
            Collection<Deposito> depositoCollectionOld = persistentTransaccionrecoleccion.getDepositoCollection();
            Collection<Deposito> depositoCollectionNew = transaccionrecoleccion.getDepositoCollection();
            List<String> illegalOrphanMessages = null;
            for (Deposito depositoCollectionOldDeposito : depositoCollectionOld) {
                if (!depositoCollectionNew.contains(depositoCollectionOldDeposito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Deposito " + depositoCollectionOldDeposito + " since its idtransaccionrecoleccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idcolmenaNew != null) {
                idcolmenaNew = em.getReference(idcolmenaNew.getClass(), idcolmenaNew.getIdcolmena());
                transaccionrecoleccion.setIdcolmena(idcolmenaNew);
            }
            if (idrecolectorNew != null) {
                idrecolectorNew = em.getReference(idrecolectorNew.getClass(), idrecolectorNew.getIdrecolector());
                transaccionrecoleccion.setIdrecolector(idrecolectorNew);
            }
            Collection<Deposito> attachedDepositoCollectionNew = new ArrayList<Deposito>();
            for (Deposito depositoCollectionNewDepositoToAttach : depositoCollectionNew) {
                depositoCollectionNewDepositoToAttach = em.getReference(depositoCollectionNewDepositoToAttach.getClass(), depositoCollectionNewDepositoToAttach.getIddeposito());
                attachedDepositoCollectionNew.add(depositoCollectionNewDepositoToAttach);
            }
            depositoCollectionNew = attachedDepositoCollectionNew;
            transaccionrecoleccion.setDepositoCollection(depositoCollectionNew);
            transaccionrecoleccion = em.merge(transaccionrecoleccion);
            if (idcolmenaOld != null && !idcolmenaOld.equals(idcolmenaNew)) {
                idcolmenaOld.getTransaccionrecoleccionCollection().remove(transaccionrecoleccion);
                idcolmenaOld = em.merge(idcolmenaOld);
            }
            if (idcolmenaNew != null && !idcolmenaNew.equals(idcolmenaOld)) {
                idcolmenaNew.getTransaccionrecoleccionCollection().add(transaccionrecoleccion);
                idcolmenaNew = em.merge(idcolmenaNew);
            }
            if (idrecolectorOld != null && !idrecolectorOld.equals(idrecolectorNew)) {
                idrecolectorOld.getTransaccionrecoleccionCollection().remove(transaccionrecoleccion);
                idrecolectorOld = em.merge(idrecolectorOld);
            }
            if (idrecolectorNew != null && !idrecolectorNew.equals(idrecolectorOld)) {
                idrecolectorNew.getTransaccionrecoleccionCollection().add(transaccionrecoleccion);
                idrecolectorNew = em.merge(idrecolectorNew);
            }
            for (Deposito depositoCollectionNewDeposito : depositoCollectionNew) {
                if (!depositoCollectionOld.contains(depositoCollectionNewDeposito)) {
                    Transaccionrecoleccion oldIdtransaccionrecoleccionOfDepositoCollectionNewDeposito = depositoCollectionNewDeposito.getIdtransaccionrecoleccion();
                    depositoCollectionNewDeposito.setIdtransaccionrecoleccion(transaccionrecoleccion);
                    depositoCollectionNewDeposito = em.merge(depositoCollectionNewDeposito);
                    if (oldIdtransaccionrecoleccionOfDepositoCollectionNewDeposito != null && !oldIdtransaccionrecoleccionOfDepositoCollectionNewDeposito.equals(transaccionrecoleccion)) {
                        oldIdtransaccionrecoleccionOfDepositoCollectionNewDeposito.getDepositoCollection().remove(depositoCollectionNewDeposito);
                        oldIdtransaccionrecoleccionOfDepositoCollectionNewDeposito = em.merge(oldIdtransaccionrecoleccionOfDepositoCollectionNewDeposito);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaccionrecoleccion.getIdtransaccionrecoleccion();
                if (findTransaccionrecoleccion(id) == null) {
                    throw new NonexistentEntityException("The transaccionrecoleccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccionrecoleccion transaccionrecoleccion;
            try {
                transaccionrecoleccion = em.getReference(Transaccionrecoleccion.class, id);
                transaccionrecoleccion.getIdtransaccionrecoleccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaccionrecoleccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Deposito> depositoCollectionOrphanCheck = transaccionrecoleccion.getDepositoCollection();
            for (Deposito depositoCollectionOrphanCheckDeposito : depositoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Transaccionrecoleccion (" + transaccionrecoleccion + ") cannot be destroyed since the Deposito " + depositoCollectionOrphanCheckDeposito + " in its depositoCollection field has a non-nullable idtransaccionrecoleccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Colmena idcolmena = transaccionrecoleccion.getIdcolmena();
            if (idcolmena != null) {
                idcolmena.getTransaccionrecoleccionCollection().remove(transaccionrecoleccion);
                idcolmena = em.merge(idcolmena);
            }
            Recolector idrecolector = transaccionrecoleccion.getIdrecolector();
            if (idrecolector != null) {
                idrecolector.getTransaccionrecoleccionCollection().remove(transaccionrecoleccion);
                idrecolector = em.merge(idrecolector);
            }
            em.remove(transaccionrecoleccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaccionrecoleccion> findTransaccionrecoleccionEntities() {
        return findTransaccionrecoleccionEntities(true, -1, -1);
    }

    public List<Transaccionrecoleccion> findTransaccionrecoleccionEntities(int maxResults, int firstResult) {
        return findTransaccionrecoleccionEntities(false, maxResults, firstResult);
    }

    private List<Transaccionrecoleccion> findTransaccionrecoleccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaccionrecoleccion.class));
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

    public Transaccionrecoleccion findTransaccionrecoleccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaccionrecoleccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaccionrecoleccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaccionrecoleccion> rt = cq.from(Transaccionrecoleccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

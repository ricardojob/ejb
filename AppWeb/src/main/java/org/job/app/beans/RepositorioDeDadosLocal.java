package org.job.app.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.job.dac.Pedido;

/**
 *
 * @author Ricardo Job
 */
@Stateless
public class RepositorioDeDadosLocal {

    @PersistenceContext(unitName = "segundoPU")
    private EntityManager em;

    public Object salvar(Object obj) {
        em.persist(obj);
        return em.merge(obj);
    }

    public <T> List<T> listar(Class<T> classe) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(classe);
        Query query = em.createQuery(cq);
        return query.getResultList();
    }

    public void atualizar(Pedido pedido) {
        em.merge(pedido);
    }

    public <T> T refresh(Object obj) {
        return (T) em.merge(obj);
    }
}

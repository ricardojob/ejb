/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.job.app.beans;

import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.job.dac.servicos.RepositorioDeDados;

/**
 *
 * @author Ricardo Job
 */
@Stateless
@Remote(RepositorioDeDados.class)
public class RepositorioDeDadosRemoto implements RepositorioDeDados {

    @PersistenceContext(unitName = "segundoPU")
    private EntityManager em;

    @Override
    public <T> List<T> listar(Class<T> classe) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(classe);
        Query query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public <T> T salvar(Object obj) {
        em.persist(obj);
        return (T) em.merge(obj);
    }
}

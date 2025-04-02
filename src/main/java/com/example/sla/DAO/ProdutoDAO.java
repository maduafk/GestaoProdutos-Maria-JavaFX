package com.example.sla.DAO;

import com.example.sla.Entity.Produto;
import com.example.sla.util.HibernateUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class ProdutoDAO {
    public static void salvar(Produto produto) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public static void atualizar(Produto produto) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(produto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public static void remover(Produto produto) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            produto = em.find(Produto.class, produto.getId());
            em.remove(produto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public static List<Produto> listarTodos() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("FROM Produto", Produto.class).getResultList();
        } finally {
            em.close();
        }
    }

    public static Produto buscarPorId(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }
}

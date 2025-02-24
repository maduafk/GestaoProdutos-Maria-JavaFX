package com.example.sla;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class IMCRegistroDAO {
    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("jpa_ProvaN2");
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar EntityManagerFactory", e);
        }
    }

    public static void salvar(IMCRegistro registro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(registro);
            em.getTransaction().commit();
            System.out.println("Registro salvo com sucesso: " + registro.getNome());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static List<IMCRegistro> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM IMCRegistro r", IMCRegistro.class).getResultList();
        } finally {
            em.close();
        }
    }
}
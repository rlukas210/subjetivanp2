package com.poo.locadora.util;

import com.poo.locadora.entities.Cliente;
import com.poo.locadora.entities.Locacao;
import com.poo.locadora.entities.Veiculo;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Cliente.class)
                    .addAnnotatedClass(Veiculo.class)
                    .addAnnotatedClass(Locacao.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to create sessionFactory object.");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

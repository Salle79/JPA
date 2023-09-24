package com.thorben.janssen.course.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import com.thorben.janssen.course.model.ChessGame;
import com.thorben.janssen.course.model.ChessMove;

public class HibernateSessionHandling {
    
    private static SessionFactory sessionFactory;

    public static void start() {
		ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().build();
		MetadataSources sources = new MetadataSources( standardRegistry ).addAnnotatedClass(ChessGame.class)
                                                                         .addAnnotatedClass(ChessMove.class);
		Metadata metadata = sources.buildMetadata();
		sessionFactory = metadata.buildSessionFactory();
    }

    public static void shutdown() {
        sessionFactory.close();
    }

    public static void openTransaction() {
        getSession().getTransaction().begin();
    }

    public static void commitSession() {
        getSession().getTransaction().commit();
    }

    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}

package site_parser_app.my_session;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class MySession {
    private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
            configure("hibernate.cfg.xml").
            build();
    private static Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
    private static SessionFactory factory = metadata.getSessionFactoryBuilder().build();

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static void closeSession() {
        factory.close();
    }
}

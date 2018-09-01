package com.csvparser.dao;

import com.csvparser.entity.CsvStructure;
import com.csvparser.parser.CsvParser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;

public class Connection {

    public void setUp() throws Exception {

        CsvParser csvParser = new CsvParser();
        List<CsvStructure> data = csvParser.parse();

        SessionFactory sessionFactory;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {

            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw e;
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (CsvStructure objects : data) {
            session.save(objects);
        }

        session.getTransaction().commit();
        session.close();
    }

}

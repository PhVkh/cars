package ru.lanit.phvkh.database;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void addPerson(PersonEntity person) {
        sessionFactory.getCurrentSession().save(person);
    }

    public PersonEntity getPersonById(long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM PersonEntity WHERE id = :id");
        query.setParameter("id", id);
        List<PersonEntity> people = query.list();
        if (people.size() == 0) {
            return null;
        } else {
            return  people.get(0);
        }
    }

    public long countPeople() {
        return (long) sessionFactory.getCurrentSession().createQuery("SELECT COUNT(*) FROM PersonEntity").uniqueResult();
    }

    public void clear() {
        sessionFactory.getCurrentSession().createQuery("DELETE PersonEntity").executeUpdate();
    }
}

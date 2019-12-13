package ru.lanit.phvkh.database;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class CarDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void addCar(CarEntity car) {
        sessionFactory.getCurrentSession().save(car);
    }

    public CarEntity getCarById(long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM CarEntity WHERE id = :id");
        query.setParameter("id", id);
        List<CarEntity> cars = query.list();
        if (cars.size() == 0) {
            return null;
        } else {
            return  cars.get(0);
        }
    }

    public long countCars() {
        return (long) sessionFactory.getCurrentSession().createQuery("SELECT COUNT(*) FROM CarEntity").uniqueResult();
    }

    public long countVendors() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT COUNT (DISTINCT UPPER (vendor)) FROM cars");
        BigInteger count = (BigInteger) query.uniqueResult();
        return count.intValue();
    }

    public void clear() {
        sessionFactory.getCurrentSession().createQuery("DELETE CarEntity").executeUpdate();
    }
}

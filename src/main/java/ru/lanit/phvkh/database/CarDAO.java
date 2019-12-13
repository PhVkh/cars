package ru.lanit.phvkh.database;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        return sessionFactory.getCurrentSession().createQuery("SELECT DISTINCT UPPER(vendor) FROM CarEntity").list().size(); //СПРОСИТЬ КАК СДЕЛАТЬ НОРМАЛЬНЫЙ ПОДСЧЁТ
    }

    public void clear() {
        sessionFactory.getCurrentSession().createQuery("DELETE CarEntity").executeUpdate();
    }
}

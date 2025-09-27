package lk.ijse.elight_driving_school.dao.custom.impl;

import lk.ijse.elight_driving_school.config.FactoryConfig;
import lk.ijse.elight_driving_school.dao.custom.PaymentDAO;
import lk.ijse.elight_driving_school.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class PaymentsDAOImpl implements PaymentDAO {

    private final FactoryConfig factoryConfig = FactoryConfig.getInstance();

    @Override
    public boolean save(Payment payments) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(payments);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean update(Payment payments) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(payments);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = factoryConfig.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Payment payments = (Payment) session.get(Payment.class, id);
            if (payments != null) {
                session.remove(payments);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Payment> getAll() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<Payment> query = session.createQuery("from Payment ",Payment.class);
            List<Payment> paymentsList = query.list();
            return paymentsList;
        }finally {
            session.close();
        }
    }

    @Override
    public List<String> getAllIds() throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Query<String> query = session.createQuery("SELECT p.paymentId FROM  Payment p", String.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Payment> findById(String id) throws Exception {
        Session session = factoryConfig.getSession();
        try {
            Payment payments = session.get(Payment.class, id);
            return Optional.ofNullable(payments);
        } finally {
            session.close();
        }
    }
}

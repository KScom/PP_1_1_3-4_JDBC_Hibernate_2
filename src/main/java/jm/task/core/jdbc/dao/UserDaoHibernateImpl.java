package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory SESSION_FACTORY = Util.getSessionFactory();
    private Transaction transactional;;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try(Session session = SESSION_FACTORY.getCurrentSession()){

            session.beginTransaction();
            transactional = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User (\n" +
                    "                            id bigint AUTO_INCREMENT,\n" +
                    "                            name varchar(255) not null ,\n" +
                    "                            lastName varchar(255) not null ,\n" +
                    "                            age tinyint not null ,\n" +
                    "                            PRIMARY KEY (id))").executeUpdate();

            session.getTransaction().commit();
        }catch (HibernateException e){
            transactional.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {

        try(Session session = SESSION_FACTORY.getCurrentSession()) {

            transactional = session.beginTransaction();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();
            session.getTransaction().commit();

        }catch (HibernateException e){
            transactional.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try(Session session = SESSION_FACTORY.getCurrentSession()) {

            transactional = session.beginTransaction();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        }catch (HibernateException e){
            transactional.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try(Session session = SESSION_FACTORY.getCurrentSession()) {

            transactional = session.beginTransaction();
            session.beginTransaction();
            session.delete(new User((byte) id));
            session.getTransaction().commit();

        }catch (HibernateException e){
            transactional.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {

        try(Session session = SESSION_FACTORY.getCurrentSession()) {

            transactional = session.beginTransaction();
            session.beginTransaction();
            Query query = session.createQuery("FROM User");
            List<User> users = query.list();
            session.getTransaction().commit();

            return users;

        }catch (HibernateException e){
            transactional.rollback();
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void cleanUsersTable() {

        try(Session session = SESSION_FACTORY.getCurrentSession()) {

            transactional = session.beginTransaction();
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE User").executeUpdate();
            session.getTransaction().commit();

        }catch (HibernateException e){

            transactional.rollback();
            e.printStackTrace();
        }

    }

    public void close(){
        SESSION_FACTORY.close();
    }
}

package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao daoHibernate;

    public UserServiceImpl() {
        this.daoHibernate = new UserDaoHibernateImpl();
    }

    public void createUsersTable() {
        daoHibernate.createUsersTable();
    }

    public void dropUsersTable() {
        daoHibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        daoHibernate.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        daoHibernate.removeUserById(id);
    }

    public List<User> getAllUsers() {

        List<User> users = daoHibernate.getAllUsers();

        return users;
    }

    public void cleanUsersTable() {
        daoHibernate.cleanUsersTable();
    }


}

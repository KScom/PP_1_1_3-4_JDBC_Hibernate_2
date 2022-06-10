package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection CONNECTION;

    static {
        try {
            CONNECTION = Util.getConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try(Statement statement = CONNECTION.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS User (\n" +
                    "                            id bigint AUTO_INCREMENT,\n" +
                    "                            name varchar(255) not null ,\n" +
                    "                            lastName varchar(255) not null ,\n" +
                    "                            age tinyint not null ,\n" +
                    "                            PRIMARY KEY (id))");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {

        try(Statement statement = CONNECTION.createStatement()) {

            statement.execute("DROP TABLE IF EXISTS User");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try(PreparedStatement preparedStatement = CONNECTION.prepareStatement(
                "INSERT INTO User (Name, lastName, age) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, String.valueOf(age));
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {

        try(PreparedStatement preparedStatement = CONNECTION.prepareStatement(
                "DELETE FROM User WHERE id = ?")) {

            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {

        try(Statement statement = CONNECTION.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM User");

            List<User> users = new ArrayList<>();
            while (resultSet.next()){
                users.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }

            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void cleanUsersTable() {

        try(Statement statement = CONNECTION.createStatement()) {

            statement.execute("TRUNCATE TABLE User");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void close() throws SQLException {
        CONNECTION.close();
    }

}

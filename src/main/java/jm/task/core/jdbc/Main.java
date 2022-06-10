package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Иван","Иванов", (byte) 25);
        userService.saveUser("Семен","Семенов", (byte) 34);
        userService.saveUser("Кузьма","Кузмич", (byte) 21);
        userService.saveUser("Петр","Петрович", (byte) 47);

        List<User> users = userService.getAllUsers();
        users.stream().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();


            userService.connectionClose();



    }
}

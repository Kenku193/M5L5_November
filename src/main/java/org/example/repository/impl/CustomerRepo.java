package org.example.repository.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Customer;
import org.example.repository.ConnectionPool;
import org.example.repository.Repo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Repository
@Slf4j
public class CustomerRepo implements Repo<Customer> {

    public static final String GET_BY_ID = "SELECT id, login, password FROM game.users WHERE id=?";

    public static final String UPDATE = "UPDATE game.users SET login=?, password=? WHERE id=?";

    private final ConnectionPool pool;

    public CustomerRepo(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    @SneakyThrows
    public Customer getById(Long id) {
        log.info(">>> Method getById started");
        try(Connection connection = pool.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setLogin(resultSet.getString("login"));
                customer.setPassword(resultSet.getString("password"));
                log.info(">>> Method getById well done");
                return customer;
            }
            else {
                log.warn(">>> Method getById ayayay");
                throw new RuntimeException("incorrect id"); }
        }
    }

    @Override
    @SneakyThrows
    public void update(Customer entity) {
        try(Connection connection = pool.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.execute();
        }
    }
}

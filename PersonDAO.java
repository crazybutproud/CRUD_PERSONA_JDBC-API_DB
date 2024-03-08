package com.example.crud_persona.DAO;

import com.example.crud_persona.Models.Persona;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int PEOPLE_COUNT;
    private static final String URL = "jdbc:postgresql://localhost:5432/persona";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static Connection connection;


    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Persona> index() throws SQLException {
        List<Persona> personas = new ArrayList<>();
        Statement statement = connection.createStatement();
        String SQL = "SELECT * FROM persona";
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            Persona person = new Persona();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
            personas.add(person);
        }
        return personas;
    }

    public Persona show(int id) {
        Persona persona = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM persona WHERE id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            persona = new Persona();

            persona.setId(resultSet.getInt("id"));
            persona.setName(resultSet.getString("name"));
            persona.setEmail(resultSet.getString("email"));
            persona.setAge(resultSet.getInt("age"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return persona;
    }

    public void save(Persona persona) throws SQLException {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO persona VALUES(1, ?, ?, ?)");

            preparedStatement.setString(1, persona.getName());
            preparedStatement.setInt(2, persona.getAge());
            preparedStatement.setString(3, persona.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

        public void update(int id, Persona persona) {
            try {
                PreparedStatement preparedStatement =
                        connection.prepareStatement("UPDATE persona SET name=?, age=?, email=? WHERE id=?");

                preparedStatement.setString(1, persona.getName());
                preparedStatement.setInt(2, persona.getAge());
                preparedStatement.setString(3, persona.getEmail());
                preparedStatement.setInt(4, id);

                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }
    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM persona WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

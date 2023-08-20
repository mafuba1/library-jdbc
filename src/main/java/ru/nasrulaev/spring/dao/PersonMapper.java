package ru.nasrulaev.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.nasrulaev.spring.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setId(rs.getInt("user_id"));
        person.setFullName(rs.getString("full_name"));
        person.setBirthYear(rs.getInt("birth_year"));

        return person;
    }
}

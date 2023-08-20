package ru.nasrulaev.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nasrulaev.spring.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }

    public Optional<Person> show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE user_id=?", new PersonMapper(), id)
                .stream().findAny();
    }

    public Optional<Person> show(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name=?", new PersonMapper(), name)
                .stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, birth_year) VALUES (?, ?)",
                person.getFullName(), person.getBirthYear());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET full_name=?, birth_year=? WHERE user_id=?",
                updatedPerson.getFullName(), updatedPerson.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE user_id=?", id);
    }
}
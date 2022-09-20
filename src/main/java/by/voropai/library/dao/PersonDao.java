package by.voropai.library.dao;

import by.voropai.library.models.Person;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PersonDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return this.jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper(Person.class));
    }

    public Person show(int id) {
        return (Person)this.jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper(Person.class)).stream().findAny().orElse((Object)null);
    }

    public void save(Person person) {
        this.jdbcTemplate.update("INSERT INTO person(full_name, birth_year) VALUES (?, ?)", new Object[]{person.getFullName(), person.getBirthYear()});
    }

    public void update(int id, Person updatedPerson) {
        this.jdbcTemplate.update("UPDATE person SET full_name=?, birth_year=? WHERE id=?", new Object[]{updatedPerson.getFullName(), updatedPerson.getBirthYear(), id});
    }

    public void delete(int id) {
        this.jdbcTemplate.update("DELETE FROM person WHERE id = ?", new Object[]{id});
    }
}

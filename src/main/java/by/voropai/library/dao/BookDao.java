package by.voropai.library.dao;

import by.voropai.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public List<Book> booksInPossession(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, release_year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getReleaseYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, release_year=? WHERE id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getReleaseYear(), id);
    }

    public void assign(int id, int personId) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", personId, id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE id=?", id);
    }
}


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
        return this.jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper(Book.class));
    }

    public Book show(int id) {
        return (Book)this.jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper(Book.class)).stream().findAny().orElse((Object)null);
    }

    public List<Book> booksInPossession(int id) {
        return this.jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper(Book.class));
    }

    public void save(Book book) {
        this.jdbcTemplate.update("INSERT INTO book(title, author, release_year) VALUES (?, ?, ?)", new Object[]{book.getTitle(), book.getAuthor(), book.getReleaseYear()});
    }

    public void update(int id, Book updatedBook) {
        this.jdbcTemplate.update("UPDATE book SET title=?, author=?, release_year=? WHERE id=?", new Object[]{updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getReleaseYear(), id});
    }

    public void changePerson(int id, int personId) {
        this.jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", new Object[]{personId, id});
    }

    public void delete(int id) {
        this.jdbcTemplate.update("DELETE FROM book WHERE id = ?", new Object[]{id});
    }

    public void disownBook(int id) {
        this.jdbcTemplate.update("UPDATE book SET person_id=null WHERE id=?", new Object[]{id});
    }
}


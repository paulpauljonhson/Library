package by.voropai.Project2SpringBoot.services;

import by.voropai.Project2SpringBoot.models.Book;
import by.voropai.Project2SpringBoot.models.Person;
import by.voropai.Project2SpringBoot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(Sort.by("releaseYear"));
        } else
            return bookRepository.findAll();
    }

    public List<Book> findAll(int page, int ItemsPerPage, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, ItemsPerPage, Sort.by("releaseYear"))).getContent();
        } else
            return bookRepository.findAll(PageRequest.of(page, ItemsPerPage)).getContent();
    }

    public Book findById(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Person getOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void setOwner(int id, Person person) {
        bookRepository.findById(id).get().setOwner(person);
        bookRepository.findById(id).get().setTakenAt(LocalDateTime.now());
    }

    @Transactional
    public void release(int id) {
        bookRepository.findById(id).get().setOwner(null);
        bookRepository.findById(id).get().setTakenAt(null);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchByTitle(String query) {
        return bookRepository.findByTitleStartingWith(query);
    }

}

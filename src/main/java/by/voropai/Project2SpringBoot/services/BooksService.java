package by.voropai.Project2SpringBoot.services;

import by.voropai.Project2SpringBoot.models.Book;
import by.voropai.Project2SpringBoot.models.Person;
import by.voropai.Project2SpringBoot.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear) {
            return booksRepository.findAll(Sort.by("releaseYear"));
        } else return booksRepository.findAll();
    }

    public List<Book> findAll(int page, int ItemsPerPage, boolean sortByYear) {
        if (sortByYear) {
            return booksRepository.findAll(PageRequest.of(page, ItemsPerPage, Sort.by("releaseYear"))).getContent();
        } else return booksRepository.findAll(PageRequest.of(page, ItemsPerPage)).getContent();
    }

    public Book findById(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Person getOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void setOwner(int id, Person person) {
        booksRepository.findById(id).get().setOwner(person);
        booksRepository.findById(id).get().setTakenAt(LocalDateTime.now());
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).get().setOwner(null);
        booksRepository.findById(id).get().setTakenAt(null);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public List<Book> searchByTitle(String query) {
        return booksRepository.findByTitleStartingWith(query);
    }

}

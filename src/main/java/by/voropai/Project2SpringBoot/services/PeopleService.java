package by.voropai.Project2SpringBoot.services;

import by.voropai.Project2SpringBoot.models.Book;
import by.voropai.Project2SpringBoot.models.Person;
import by.voropai.Project2SpringBoot.repository.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public List<Book> getBooksInPossession(int id) {
        Optional<Person> owner = peopleRepository.findById(id);
        if (owner.isPresent()) {
            Hibernate.initialize(owner.get().getBooks());
            List<Book> books = owner.get().getBooks();
            books.forEach(this::isExpired);
            return books;
        } else
            return Collections.emptyList();
    }

    private void isExpired(Book book) {
        if (LocalDateTime.now().isAfter(book.getTakenAt().plusDays(10))) {
            book.setExpired(true);
        }
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}

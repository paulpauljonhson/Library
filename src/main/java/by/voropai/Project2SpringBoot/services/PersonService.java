package by.voropai.Project2SpringBoot.services;

import by.voropai.Project2SpringBoot.models.Book;
import by.voropai.Project2SpringBoot.models.Person;
import by.voropai.Project2SpringBoot.repository.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public List<Book> getBooksInPossession(int id) {
        Optional<Person> owner = personRepository.findById(id);
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
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }
}

package by.voropai.Project2SpringBoot.repositories;

import by.voropai.Project2SpringBoot.models.Book;
import by.voropai.Project2SpringBoot.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BooksRepositoryTest {
    @Autowired
    BookRepository underTest;

    @Test
    void itShouldFindBookByTitleStartingWithPrefix() {
        //given
        String prefix = "Peace";
        //when
        List<Book> booksWithPrefix = underTest.findByTitleStartingWith(prefix);
        //then
        assertThat(!booksWithPrefix.isEmpty()).isTrue();
    }
}
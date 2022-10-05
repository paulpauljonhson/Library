package by.voropai.Project2SpringBoot.repositories;

import by.voropai.Project2SpringBoot.models.Book;
import by.voropai.Project2SpringBoot.repository.BooksRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BooksRepositoryTest {
    @Autowired
    BooksRepository underTest;
    @Test
    void itShouldFindBookByTitleStartingWithPrefix() {
        //given
        Book book = Book.builder()
                .title("test_title")
                .author("test_author").build();
        underTest.save(book);
        //when
        List<Book> foundBooks = underTest.findByTitleStartingWith("test");
        //then
        assertThat(foundBooks.isEmpty()).isFalse();
    }
}
package by.voropai.Project2SpringBoot.controllers;


import by.voropai.Project2SpringBoot.models.Book;
import by.voropai.Project2SpringBoot.models.Person;
import by.voropai.Project2SpringBoot.repository.PersonRepository;
import by.voropai.Project2SpringBoot.services.BookService;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/books"})
public class BookController {

    BookService bookService;
    PersonRepository personRepository;

    @Autowired
    public BookController(BookService bookService, PersonRepository personRepository) {
        this.bookService = bookService;
        this.personRepository = personRepository;
    }

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "items_per_page", required = false) Integer booksPerPage,
                        @RequestParam(name = "sortByYear", required = false) boolean sortByYear) {
        if (page == null || booksPerPage == null) {
            model.addAttribute("books", bookService.findAll(sortByYear));
        } else
            model.addAttribute("books", bookService.findAll(page, booksPerPage, sortByYear));
        return "/books/index";
    }

    @GetMapping({"/{id}"})
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute Person person) {
        model.addAttribute("book", bookService.findById(id));
        Person bookOwner = bookService.getOwner(id);

        if (bookOwner == null) {
            model.addAttribute("people", personRepository.findAll());
        } else {
            model.addAttribute("owner", bookOwner);
        }
        return "/books/show";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "/books/search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam(name = "query", required = false) String query) {
        model.addAttribute("books", bookService.searchByTitle(query));
        return "books/search";
    }

    @GetMapping({"/new"})
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        } else {
            bookService.save(book);
            return "redirect:/books";
        }
    }

    @PatchMapping({"{id}/assign"})
    public String assign(@PathVariable("id") int id, @ModelAttribute Person person) {
        bookService.setOwner(id, person);
        return "redirect:/books";
    }

    @PatchMapping({"{id}/release"})
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books";
    }

    @GetMapping({"/{id}/edit"})
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @PatchMapping({"/{id}"})
    public String update(@ModelAttribute("book") @Valid Book updatedBook, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        } else {
            bookService.update(id, updatedBook);
            return "redirect:/books";
        }
    }

    @DeleteMapping({"/{id}"})
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}


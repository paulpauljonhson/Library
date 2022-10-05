package by.voropai.Project2SpringBoot.controllers;


import by.voropai.Project2SpringBoot.models.Book;
import by.voropai.Project2SpringBoot.models.Person;
import by.voropai.Project2SpringBoot.repository.PeopleRepository;
import by.voropai.Project2SpringBoot.services.BooksService;


import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor

@Controller
@RequestMapping({"/books"})
public class BooksController {
    private final BooksService booksService;
    private final PeopleRepository peopleRepository;

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(name = "page", required = false) Integer page,
                        @RequestParam(name = "items_per_page", required = false) Integer booksPerPage,
                        @RequestParam(name = "sortByYear", required = false) boolean sortByYear) {
        if (page == null || booksPerPage == null) {
            model.addAttribute("books", booksService.findAll(sortByYear));
        } else
            model.addAttribute("books", booksService.findAll(page, booksPerPage, sortByYear));
        return "/books/index";
    }

    @GetMapping({"/{id}"})
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute Person person) {
        model.addAttribute("book", booksService.findById(id));
        Person bookOwner = booksService.getOwner(id);

        if (bookOwner == null) {
            model.addAttribute("people", peopleRepository.findAll());
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
        model.addAttribute("books", booksService.searchByTitle(query));
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
            booksService.save(book);
            return "redirect:/books";
        }
    }

    @PatchMapping({"{id}/assign"})
    public String assign(@PathVariable("id") int id, @ModelAttribute Person person) {
        booksService.setOwner(id, person);
        return "redirect:/books";
    }

    @PatchMapping({"{id}/release"})
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/books";
    }

    @GetMapping({"/{id}/edit"})
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }

    @PatchMapping({"/{id}"})
    public String update(@ModelAttribute("book") @Valid Book updatedBook, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        } else {
            booksService.update(id, updatedBook);
            return "redirect:/books";
        }
    }

    @DeleteMapping({"/{id}"})
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
}


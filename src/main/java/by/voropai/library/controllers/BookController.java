package by.voropai.library.controllers;


import by.voropai.library.dao.BookDao;
import by.voropai.library.dao.PersonDao;
import by.voropai.library.models.Book;
import by.voropai.library.models.Person;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/books"})
public class BookController {
    private BookDao bookDao;
    private PersonDao personDao;

    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("books", this.bookDao.index());
        return "/books/index";
    }

    @GetMapping({"/{id}"})
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute Person person) {
        model.addAttribute("book", this.bookDao.show(id));
        if (this.bookDao.show(id).getPersonId() == null) {
            model.addAttribute("people", this.personDao.index());
        } else {
            model.addAttribute("person", this.personDao.show(this.bookDao.show(id).getPersonId()));
        }

        System.out.println(person.getId());
        return "/books/show";
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
            this.bookDao.save(book);
            return "redirect:/books";
        }
    }

    @PatchMapping({"{id}/owner"})
    public String setToPerson(@PathVariable("id") int id, @ModelAttribute Person person) {
        this.bookDao.changePerson(id, person.getId());
        return "redirect:/books";
    }

    @PatchMapping({"{id}/disown"})
    public String disown(@PathVariable("id") int id) {
        this.bookDao.disownBook(id);
        return "redirect:/books";
    }

    @GetMapping({"/{id}/edit"})
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", this.bookDao.show(id));
        return "books/edit";
    }

    @PatchMapping({"/{id}"})
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        } else {
            this.bookDao.update(id, book);
            return "redirect:/books";
        }
    }

    @DeleteMapping({"/{id}"})
    public String delete(@PathVariable("id") int id) {
        this.bookDao.delete(id);
        return "redirect:/books";
    }
}


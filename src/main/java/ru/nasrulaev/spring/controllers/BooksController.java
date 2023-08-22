package ru.nasrulaev.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nasrulaev.spring.dao.BookDAO;
import ru.nasrulaev.spring.dao.PersonDAO;
import ru.nasrulaev.spring.models.Book;
import ru.nasrulaev.spring.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookDAO.show(id);

        if (book.isEmpty())
            return "redirect:/books";

        model.addAttribute("book", book.get());

        Optional<Person> holder = bookDAO.showHolder(id);
        if (holder.isPresent()) {
            model.addAttribute("holder", holder.get());
        }else {
            model.addAttribute("people", personDAO.index());
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());

        return "books/new";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookDAO.show(id);
        if (book.isEmpty())
            return "redirect:/books";

        model.addAttribute("book", book.get());
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/holder")
    public String changeHolder(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult,
                               @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "redirect:/books/"+id;

        bookDAO.changeHolder(id, book.getHolderId());
        return "redirect:/books/"+id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

}

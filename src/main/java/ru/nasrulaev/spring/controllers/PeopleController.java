package ru.nasrulaev.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nasrulaev.spring.dao.BookDAO;
import ru.nasrulaev.spring.dao.PersonDAO;
import ru.nasrulaev.spring.models.Person;
import ru.nasrulaev.spring.validators.PersonValidator;

import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    private final BookDAO bookDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
        this.bookDAO = bookDAO;
    }


    @GetMapping
    public String index(Model model){
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) {
        Optional<Person> person = personDAO.show(id);

        if (person.isEmpty())
            return "redirect:/people";

        model.addAttribute("person", person);
        model.addAttribute("heldBooks", bookDAO.index(id));

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());

        return "people/new";
    }

    @PostMapping()
    public String createPerson(@Valid @ModelAttribute("person") Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Optional<Person> person = personDAO.show(id);

        if (person.isEmpty())
            return "redirect:/people";

        model.addAttribute("person", person);
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}

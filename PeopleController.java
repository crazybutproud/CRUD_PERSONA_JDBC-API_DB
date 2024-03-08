package com.example.crud_persona.Controllers;

import com.example.crud_persona.DAO.PersonDAO;
import com.example.crud_persona.Models.Persona;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private final PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/all")
    public String index(Model model) throws SQLException { //получим всех людей из ДАО и передадим на отображение в представление
        model.addAttribute("personas", personDAO.index());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) { //получим одного человека из ДАО по айди и передадим на отображение в представление
        model.addAttribute("persona", personDAO.show(id));
        return "show";
    }

    @GetMapping("/new")
    public String newPersona(Model model) { //возвращаем форму на добавление нового человека
        model.addAttribute("persona", new Persona());

        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute @Valid Persona persona, BindingResult bindingResult) throws SQLException { //принимает данные и отправляет в базу данных(внедряется объект с ошибками)
        if (bindingResult.hasErrors()) {
            return "new";
        } else {
            personDAO.save(persona);
            return "redirect:/people/all";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("persona", personDAO.show(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    //выдает ошибку 405 из-за скрытого поля,которое создал спринг для обработки запроса.для решения нужен фильтр(application.properties)
    public String update(@ModelAttribute @Valid Persona persona, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        } else {
            personDAO.update(id, persona);
            return "redirect:/people/all";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people/all";
    }
}

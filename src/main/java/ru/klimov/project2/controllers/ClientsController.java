package ru.klimov.project2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.klimov.project2.models.Client;
import ru.klimov.project2.services.ClientsService;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    private final ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clients", clientsService.findAll());
        return "/clients/index";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable("id") int id,
                              Model model) {
        model.addAttribute("client", clientsService.findOne(id));
        return "/clients/details";
    }

    @GetMapping("/new")
    public String newClient(@ModelAttribute("client") Client client) {
        return "/clients/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("client")@Valid Client client,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "clients/new";
        clientsService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model) {
        model.addAttribute("client", clientsService.findOne(id));
        return "clients/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("client")@Valid Client client,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "clients/edit";
        clientsService.update(id, client);
        return "redirect:/clients/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        clientsService.delete(id);
        return "redirect:/clients";
    }


}

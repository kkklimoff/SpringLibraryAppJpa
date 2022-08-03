package ru.klimov.project2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.klimov.project2.models.Book;
import ru.klimov.project2.models.Client;
import ru.klimov.project2.services.BooksService;
import ru.klimov.project2.services.ClientsService;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final ClientsService clientsService;

    @Autowired
    public BooksController(BooksService booksService, ClientsService clientsService) {
        this.booksService = booksService;
        this.clientsService = clientsService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        if(page == null || booksPerPage == null) model.addAttribute("books", booksService.findAll(sortByYear));
        else model.addAttribute("books", booksService.findWithPagination(page, booksPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String showDetails(@PathVariable("id") int id,
                              Model model,
                              @ModelAttribute("client")Client client) {
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);
        if(book.getCurrentOwner() == null)
            model.addAttribute("clients", clientsService.findAll());
        return "books/details";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("book")@Valid Book book,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "books/edit";
        booksService.update(id, book);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("client") Client client){
        booksService.assign(id, client);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "query", required = false) String query,
                         Model model){
        if(query != null)
        model.addAttribute("books", booksService.searchByTitle(query));
        return "books/search";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
}

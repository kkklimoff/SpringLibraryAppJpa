package ru.klimov.project2.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klimov.project2.models.Book;
import ru.klimov.project2.models.Client;
import ru.klimov.project2.repositories.BooksRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(boolean sortByYear){
        if(sortByYear) return booksRepository.findAll(Sort.by("yearOfPublication"));
        else return booksRepository.findAll();
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> findWithPagination(int page, int booksPerPage, boolean sortByYear){
        if(sortByYear) return booksRepository.findAll(PageRequest.of(page,booksPerPage, Sort.by("yearOfPublication"))).getContent();
        return booksRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }

    public List<Book> searchByTitle(String title){
        return booksRepository.findByTitleStartingWith(title);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        updatedBook.setCurrentOwner(booksRepository.findById(id).get().getCurrentOwner());
        updatedBook.setTakenAt(booksRepository.findById(id).get().getTakenAt());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(int id, Client client){
        Optional<Book> bookFound = booksRepository.findById(id);
        if(bookFound.isPresent()){
            Book book = bookFound.get();
            book.setCurrentOwner(client);
            book.setTakenAt(new Date());
            booksRepository.save(book);
        }
    }

    @Transactional
    public void release(int id){
        Optional<Book> bookFound = booksRepository.findById(id);
        if(bookFound.isPresent()){
            Book book = bookFound.get();
            book.setCurrentOwner(null);
            book.setTakenAt(null);
            booksRepository.save(book);
        }
    }
}

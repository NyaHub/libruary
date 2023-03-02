package miruchan.ml.libruary.controllers;

import miruchan.ml.libruary.entities.Author;
import miruchan.ml.libruary.entities.Book;
import miruchan.ml.libruary.models.BookModel;
import miruchan.ml.libruary.repos.BookRepo;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/books")
public class BookController {

    @Autowired
    private BookRepo bookRepo;

    @PostMapping
    public ResponseEntity addBook(@RequestBody Book book){
        try{
            bookRepo.save(book);
            return ResponseEntity.ok("Saved");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping
    public ResponseEntity getBook(@RequestParam(required = false) Integer id, @RequestParam(required = false) String action, @RequestParam(required = false) String search){
        try{


            if(id != null) {
                Optional<Book> b = bookRepo.findById(id);
                return ResponseEntity.ok(b.isPresent() ? new BookModel(b.get()) : "null");
            }
            if(search != null){
                search = search.toLowerCase();
                ArrayList<Book> out = new ArrayList<Book>();
                Iterable<Book> books = bookRepo.findAll();
                for (Book a : books){
                    if (a.getTitle().toLowerCase().contains(search)){
                        out.add(a);
                    }
                }
                return ResponseEntity.ok(out.stream().map(BookModel::toModel).collect(Collectors.toList()));
            }
            if(action != null){
                if(action.equalsIgnoreCase("last")) {
                    return ResponseEntity.ok(bookRepo.findLastN(10).stream().map(BookModel::toModel).collect(Collectors.toList()));
                }
            }

            return ResponseEntity.ok(bookRepo.findAll().stream().map(BookModel::toModel).collect(Collectors.toList()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }
}

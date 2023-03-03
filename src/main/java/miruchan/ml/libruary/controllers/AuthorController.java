package miruchan.ml.libruary.controllers;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import miruchan.ml.libruary.entities.Author;
import miruchan.ml.libruary.entities.Book;
import miruchan.ml.libruary.models.AuthorModel;
import miruchan.ml.libruary.models.BookModel;
import miruchan.ml.libruary.repos.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/authors")
public class AuthorController {

    @Autowired
    private AuthorRepo authorRepo;

    @PostMapping
    public ResponseEntity addAuthor(@RequestBody Author author){
        try{
            authorRepo.save(author);
            return ResponseEntity.ok(author);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping
    public ResponseEntity getAuthor(@RequestParam(required = false) Integer id, @RequestParam(required = false) String action, @RequestParam(required = false) String search){
        try{
            if(id != null) {
                Optional<Author> a = authorRepo.findById(id);
                return ResponseEntity.ok(a.isPresent() ? new AuthorModel(a.get()) : "null");
            }
            if(search != null){
                search = search.toLowerCase();
                ArrayList<Author> out = new ArrayList<Author>();
                Iterable<Author> authors = authorRepo.findAll();
                for (Author a : authors){
                    if (a.getName().toLowerCase().contains(search) || a.getSurname().toLowerCase().contains(search)){
                        out.add(a);
                    }
                }
                return ResponseEntity.ok(out.stream().map(AuthorModel::toModel).collect(Collectors.toList()));
            }
            return ResponseEntity.ok(authorRepo.findAll().stream().map(AuthorModel::toModel).collect(Collectors.toList()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }
}

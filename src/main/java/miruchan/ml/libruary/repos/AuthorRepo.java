package miruchan.ml.libruary.repos;

import miruchan.ml.libruary.entities.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepo extends CrudRepository<Author, Integer> {
    List<Author> findAll();
}

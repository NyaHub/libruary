package miruchan.ml.libruary.repos;

import miruchan.ml.libruary.entities.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends CrudRepository<Book, Integer> {

//    @Query("SELECT p FROM Person p JOIN FETCH p.roles WHERE p.id = (:id)")
//    public Person findByIdAndFetchRolesEagerly(@Param("id") Long id);

    @Query(value = "select * from Book order by id desc limit (:n)", nativeQuery = true)
    List<Book> findLastN(@Param("n") Integer id);
    List<Book> findAll();
}

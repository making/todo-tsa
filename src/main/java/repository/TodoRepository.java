package repository;

import domain.Todo;
import java.util.Collection;

/**
 *
 * @author maki
 */
public interface TodoRepository {

    Todo findOne(String id);

    Collection<Todo> findAll();
    
    Todo create(Todo todo);
    
    Todo update(Todo todo);
    
    void delete(String id);
}

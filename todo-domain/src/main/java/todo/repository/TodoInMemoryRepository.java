package todo.repository;

import todo.domain.Todo;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import javax.inject.Singleton;

/**
 * @author maki
 */
@InMemory
@Singleton
public class TodoInMemoryRepository implements TodoRepository {

    private final Map<String, Todo> todoMap = Collections.synchronizedSortedMap(new TreeMap<String, Todo>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return Integer.valueOf(o2).compareTo(Integer.valueOf(o1));
        }
    }));

    @Override
    public Todo findOne(String id) {
        return todoMap.get(id);
    }

    @Override
    public Collection<Todo> findAll() {
        return todoMap.values();
    }

    @Override
    public Todo create(Todo todo) {
        return update(todo);
    }

    @Override
    public Todo update(Todo todo) {
        String id = todo.getId();
        if (id != null) {
            todoMap.put(id, todo);
        }
        return todo;
    }

    @Override
    public void delete(String id) {
        todoMap.remove(id);
    }
}

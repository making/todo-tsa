package todo.repository;

import todo.domain.Todo;

import java.util.Collection;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * @author maki
 */
@Rest
@Singleton
public class TodoRestRepository implements TodoRepository {

    private static final String TODO_RESOURCE_PATH = "http://localhost:8080/todo-web/api/todo";
    private static GenericType<Collection<Todo>> TODO_COLLECTION_TYPE = new GenericType<Collection<Todo>>() {
    };
    private final Client client; // Thread Safe?

    public TodoRestRepository() {
        this.client = ClientBuilder.newClient();
    }

    public TodoRestRepository(Client client) {
        this.client = client;
    }

    @PreDestroy
    public void preDestroy() {
        this.client.close();
    }

    @Override
    public Todo findOne(String id) {
        return this.client.target(TODO_RESOURCE_PATH).path(id).request(MediaType.APPLICATION_JSON_TYPE)
                .get(Todo.class);
    }

    @Override
    public Collection<Todo> findAll() {
        return this.client.target(TODO_RESOURCE_PATH).request(MediaType.APPLICATION_JSON_TYPE)
                .get(TODO_COLLECTION_TYPE);
    }

    @Override
    public Todo create(Todo todo) {
        return this.client.target(TODO_RESOURCE_PATH).request()
                .post(Entity.entity(todo, MediaType.APPLICATION_JSON_TYPE)).readEntity(Todo.class);
    }

    @Override
    public Todo update(Todo todo) {
        return this.client.target(TODO_RESOURCE_PATH).path(todo.getId()).request()
                .put(Entity.entity(todo, MediaType.APPLICATION_JSON_TYPE)).readEntity(Todo.class);
    }

    @Override
    public void delete(String id) {
        this.client.target(TODO_RESOURCE_PATH).path(id).request().delete();
    }
}

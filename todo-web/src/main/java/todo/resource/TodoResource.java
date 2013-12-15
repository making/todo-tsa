/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package todo.resource;

import todo.domain.Todo;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import todo.repository.InMemory;
import todo.repository.TodoRepository;
import todo.sequencer.Sequencer;

/**
 * REST Web Service
 *
 * @author maki
 */
@Path("api/todo")
@ApplicationScoped
public class TodoResource {

    private static final Logger LOGGER = Logger.getLogger(TodoResource.class.getName());
    @Inject
    protected Sequencer sequencer;
    @Inject
    @InMemory
    protected TodoRepository todoRepository;

    public TodoResource() {
    }

    @PostConstruct
    public void postConstruct() {
        for (Todo todo : Arrays.asList(
                new Todo(sequencer.getNext(), "aaaa"),
                new Todo(sequencer.getNext(), "bbbb"),
                new Todo(sequencer.getNext(), "cccc"),
                new Todo(sequencer.getNext(), "dddd"))) {
            todoRepository.create(todo);
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Todo getTodo(@PathParam("id") String id) {
        LOGGER.log(Level.INFO, "GET Todo {0}", id);

        return todoRepository.findOne(id);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Todo putTodo(@PathParam("id") String id, Todo content) {
        content.setId(id);
        LOGGER.log(Level.INFO, "PUT Todo {0}", id);
        return todoRepository.update(content);
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteTodo(@PathParam("id") String id) {
        LOGGER.log(Level.INFO, "DELETE Todo {0}", id);
        todoRepository.delete(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Todo> getTodos() {
        LOGGER.log(Level.INFO, "GET Todos");
        return todoRepository.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postTodos(Todo content, @Context UriInfo uriInfo) {
        LOGGER.log(Level.INFO, "POST Todos");
        String id = sequencer.getNext();
        content.setId(id);
        todoRepository.create(content);

        URI newUri = uriInfo.getRequestUriBuilder().path(id).build();
        return Response.created(newUri).entity(content).build();
    }
}

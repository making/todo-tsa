/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package faces;

import domain.Todo;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import repository.Rest;
import repository.TodoRepository;

/**
 *
 * @author maki
 */
@ManagedBean(name = "todoManagedBean")
@ViewScoped
public class TodoManagedBean {
    private static final Logger LOGGER = Logger.getLogger(TodoManagedBean.class.getName());

    protected Collection<Todo> todos;
    
    protected Todo todo = new Todo();
    
    @Inject
    @Rest
    protected TodoRepository todoRepository;

    /**
     * Creates a new instance of TodoManagedBean
     */
    public TodoManagedBean() {
    }
    
    @PostConstruct
    public void postContruct() {
        LOGGER.log(Level.INFO, "construct");
        findAll();
    }

    public void reload() {
        findAll();
    }
    
    public void findAll() {
        this.todos = todoRepository.findAll();
    }
    
    public void create() {
        LOGGER.log(Level.INFO, "create {0}", this.todo);
        todoRepository.create(this.todo);
        findAll();
        this.todo = new Todo();
    }
    
    public void delete(String id) {
        LOGGER.log(Level.INFO, "delete {0}", id);
        todoRepository.delete(id);
        findAll();
    }
    
    public void update(Todo todo) {
        LOGGER.log(Level.INFO, "update {0}", todo);
        todoRepository.update(todo);
    }

    public Todo getTodo() {
        return todo;
    }

     
    public Collection<Todo> getTodos() {
        return todos;
    }
    
    
}

package todo.app;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import todo.domain.Todo;

public class TodoFxModel {
    private final Todo todo;

    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final BooleanProperty finished = new SimpleBooleanProperty();

    public TodoFxModel(Todo todo) {
        this.todo = todo;
        id.setValue(todo.getId());
        title.set(todo.getTitle());
        finished.set(todo.isFinished());
    }

    public TodoFxModel() {
        this(new Todo());
    }

    public Todo getTodo() {
        return todo;
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public BooleanProperty finishedProperty() {
        return finished;
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
        todo.setId(id);
    }

    public void setTitle(String title) {
        this.title.set(title);
        todo.setTitle(title);
    }

    public String getTitle() {
        return title.get();
    }

    public void setFinished(boolean finished) {
        this.finished.set(finished);
        todo.setFinished(finished);
    }

    public boolean isFinished() {
        return finished.get();
    }
}

package todo.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import todo.domain.Todo;
import todo.repository.TodoRepository;
import todo.repository.TodoRestRepository;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class TodoController implements Initializable {
    @FXML
    TextField todoTitle;

    @FXML
    Button createButton;

    @FXML
    Button reloadButton;

    @FXML
    Button updateButton;

    @FXML
    Button deleteButton;

    @FXML
    TableView<TodoFxModel> table;

    @FXML
    TableColumn<TodoFxModel, String> idColumn;

    @FXML
    TableColumn<TodoFxModel, String> titleColumn;

    @FXML
    TableColumn<TodoFxModel, Boolean> finishedColumn;

    @FXML
    TableColumn<TodoFxModel, Boolean> deleteColumn;

    ObservableList<TodoFxModel> todoList = FXCollections.observableArrayList();

    TodoRepository todoRepository;

    @FXML
    void handleCreate(ActionEvent event) {
        disable();
        try {
            String title = todoTitle.getText();
            if (title != null && !title.isEmpty()) {
                todoRepository.create(new Todo(null, todoTitle.getText()));
                todoTitle.setText("");
            }
        } finally {
            enable();
        }
        reload();
    }

    @FXML
    void handleReload(ActionEvent event) {
        reload();
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        TodoFxModel selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            update(selected.getTodo());
            reload();
        }
    }

    @FXML
    void handleDelete(ActionEvent event) {
        TodoFxModel selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            delete(selected.getTodo());
            reload();
        }
    }

    void reload() {
        List<TodoFxModel> todoFxModelList = new ArrayList<>();
        for (Todo todo : todoRepository.findAll()) {
            todoFxModelList.add(new TodoFxModel(todo));
        }
        todoList = FXCollections.observableList(todoFxModelList);
        table.setItems(todoList);
    }

    void update(Todo todo) {
        todoRepository.update(todo);
    }

    void delete(Todo todo) {
        todoRepository.delete(todo.getId());
    }

    @FXML
    void handleEdit(ActionEvent event) {
        System.out.println(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(new JacksonFeature()));
        todoRepository = new TodoRestRepository(client);

        idColumn.setCellValueFactory(new PropertyValueFactory<TodoFxModel, String>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<TodoFxModel, String>("title"));
        finishedColumn.setCellValueFactory(new PropertyValueFactory("finished"));
        finishedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(finishedColumn));
        finishedColumn.setEditable(true);
        finishedColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TodoFxModel, Boolean>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TodoFxModel, Boolean> event) {
                // do not work! why?
                Todo target = event.getRowValue().getTodo();
                update(target);
            }
        });
        table.setEditable(true);
        reload();
    }

    void disable() {
        todoTitle.setDisable(true);
        createButton.setDisable(true);
        reloadButton.setDisable(true);
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    void enable() {
        todoTitle.setDisable(false);
        createButton.setDisable(false);
        reloadButton.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }
}

package todo.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import todo.domain.Todo;
import todo.repository.TodoRepository;
import todo.repository.TodoRestRepository;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.net.URL;
import java.util.ResourceBundle;


public class TodoController implements Initializable {
    @FXML
    TextField todoTitle;

    @FXML
    Button createButton;

    TodoRepository todoRepository;

    @FXML
    void handleCreate(ActionEvent event) {
        System.out.println("create");

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

        System.out.println(todoRepository.findAll());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(new JacksonFeature()));
        todoRepository = new TodoRestRepository(client);
    }

    void disable() {
        todoTitle.setDisable(true);
        createButton.setDisable(true);
    }

    void enable() {
        todoTitle.setDisable(false);
        createButton.setDisable(false);
    }
}

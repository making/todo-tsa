/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author maki
 */
public class Todo {

    private String id;
    private String title;
    private boolean finished;

    public Todo() {
    }

    public Todo(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public String toString() {
        return "Todo(" + id + ", " + title + ", " + finished + ")";
    }
}

package api.models;

import java.math.BigInteger;
import java.util.Objects;

public class ToDo {
    private BigInteger id;
    private String text;
    private boolean completed;

    public ToDo() {}

    public ToDo(String  id, String text, boolean completed) {
        this.id = new BigInteger(id);
        this.text = text;
        this.completed = completed;
    }

    public ToDo(String text, boolean completed) {
        this.text = text;
        this.completed = completed;
    }

    public BigInteger getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo toDo = (ToDo) o;
        return completed == toDo.completed && Objects.equals(id, toDo.id) && Objects.equals(text, toDo.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, completed);
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", completed=" + completed +
                '}';
    }
}

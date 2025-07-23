package app.states;

import app.models.Comment;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CommentState {
    private final ObjectProperty<Comment> currentComment = new SimpleObjectProperty<>();

    public ObjectProperty<Comment> currentCommentProperty() {
        return currentComment;
    }

    public Comment getCurrentComment() {
        return currentComment.get();
    }

    public void setCurrentComment(Comment comment) {
        currentComment.set(comment);
    }
}

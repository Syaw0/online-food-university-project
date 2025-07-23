package app.mock;

import app.models.Comment;
import java.util.ArrayList;
import java.util.List;

public class CommentRepo {
    private final List<Comment> comments;

    public CommentRepo() {
        comments = new ArrayList<>();
        initializeMockData();
    }

    private void initializeMockData() {
        
        comments.add(new Comment(
                "1",
                "1", 
                "1", 
                "/assets/images/comment1.png",
                "تجربه عالی",
                "کباب بختیاری واقعاً عالی بود، گوشت تازه و نرم و برنجش هم داغ و خوش پخت. حتماً دوباره سفارش میدم",
                "5"
        ));

        comments.add(new Comment(
                "2",
                "1", 
                "2", 
                null,
                "کیفیت خوب",
                "گوشت کباب بختیاری کیفیت خوبی داشت ولی برنجش کمی شور بود",
                "4"
        ));

        comments.add(new Comment(
                "3",
                "3", 
                "1", 
                "/assets/images/comment3.png",
                "بی نظیر",
                "استیک واقعاً آبدار و لذیذ بود، سیب زمینی دودی هم حسابی مزه داشت. قیمتش کمی بالاست ولی ارزشش رو دارد",
                "5"
        ));

        comments.add(new Comment(
                "4",
                "5", 
                "3", 
                "/assets/images/comment4.png",
                "انتظار بیشتری داشتم",
                "برگر خشک بود و سسش کافی نبود، سیب زمینی هم به اندازه کافی ترد نبود",
                "3"
        ));
    }

    public Comment findById(String id) {
        return comments.stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Comment> findByFoodId(String foodId) {
        return comments.stream()
                .filter(comment -> comment.getFoodId().equals(foodId))
                .toList();
    }

    public List<Comment> findByUserId(String userId) {
        return comments.stream()
                .filter(comment -> comment.getUserId().equals(userId))
                .toList();
    }

    public List<Comment> getAllComments() {
        return new ArrayList<>(comments);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void updateComment(Comment updatedComment) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getId().equals(updatedComment.getId())) {
                comments.set(i, updatedComment);
                return;
            }
        }
    }

    public void removeComment(String id) {
        comments.removeIf(comment -> comment.getId().equals(id));
    }
}

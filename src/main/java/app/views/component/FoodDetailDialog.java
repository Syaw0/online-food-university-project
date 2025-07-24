package app.views.component;

import app.models.Comment;
import app.models.Food;
import app.mock.CommentRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;

import java.util.List;
import java.util.Optional;

public class FoodDetailDialog extends Dialog<Void> {
    private final TextField commentTitleField = new TextField();
    private final TextArea commentDescriptionArea = new TextArea();
    private final ComboBox<Integer> ratingCombo = new ComboBox<>();
    private final Food food;

    public FoodDetailDialog(Food food, boolean showFormAndButton) {
        this.food = food;
        getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        setTitle("مشخصات غذا");
        setHeaderText("جزئیات کامل غذا");
        setResizable(true); 

        
        ScrollPane mainScroll = new ScrollPane();
        mainScroll.setFitToWidth(true);
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(15));

        
        ImageView imageView = createFoodImageView(food);
        mainContent.getChildren().add(imageView);

        
        GridPane grid = createFoodDetailsGrid(food);
        mainContent.getChildren().add(grid);

        
        VBox commentsSection = createCommentsSection(food);
        mainContent.getChildren().add(commentsSection);

        
        if (showFormAndButton) {
            VBox commentForm = createCommentForm();
            mainContent.getChildren().add(commentForm);
        }

        mainScroll.setContent(mainContent);
        getDialogPane().setContent(mainScroll);

        
        setDialogSize();

        
        ButtonType closeButton = new ButtonType("بستن", ButtonBar.ButtonData.CANCEL_CLOSE);
        getDialogPane().getButtonTypes().add(closeButton);

        
        if (showFormAndButton) {
            ButtonType addToCartButton = new ButtonType("افزودن به سبد خرید", ButtonBar.ButtonData.APPLY);
            getDialogPane().getButtonTypes().add(addToCartButton);
        }

        
        setResultConverter(buttonType -> {
            if (buttonType != null && buttonType.getButtonData() == ButtonBar.ButtonData.APPLY) {
                handleAddToCart();
            }
            return null;
        });
    }

    private void setDialogSize() {
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        getDialogPane().setPrefSize(screenWidth * 0.7, screenHeight * 0.7);
    }

    private ImageView createFoodImageView(Food food) {
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(food.getImage()));
        } catch (Exception e) {
            imageView.setImage(new Image("file:src/main/resources/assets/images/default-food.png"));
        }
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private GridPane createFoodDetailsGrid(Food food) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(createLabel("نام:"), 0, 0);
        grid.add(createValueLabel(food.getName()), 1, 0);
        grid.add(createLabel("توضیحات:"), 0, 1);
        grid.add(createValueLabel(food.getDescription()), 1, 1);
        grid.add(createLabel("قیمت:"), 0, 2);
        grid.add(createValueLabel(food.getPrice() + " تومان"), 1, 2);
        grid.add(createLabel("موجودی:"), 0, 3);
        grid.add(createValueLabel(food.getStock()), 1, 3);
        grid.add(createLabel("دسته‌بندی:"), 0, 4);
        grid.add(createValueLabel(food.getCategoryId()), 1, 4);
        grid.add(createLabel("امتیاز:"), 0, 5);

        HBox ratingBox = new HBox(5);
        ratingBox.getChildren().add(createRatingStars(Double.parseDouble(food.getTotalRate())));
        grid.add(ratingBox, 1, 5);

        return grid;
    }

    private VBox createCommentsSection(Food food) {
        VBox commentsSection = new VBox(10);
        commentsSection.setPadding(new Insets(15));
        commentsSection.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        commentsSection.setStyle("-fx-border-color: #e0e0e0; -fx-border-width: 1px;");

        Label commentsHeader = new Label("نظرات کاربران");
        commentsHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        VBox commentsContainer = new VBox(15);

        
        ScrollPane commentsScroll = new ScrollPane(commentsContainer);
        commentsScroll.setFitToWidth(true);
        commentsScroll.setPrefHeight(200); 
        commentsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        
        CommentRepo commentRepo = new CommentRepo();
        List<Comment> comments = commentRepo.findByFoodId(food.getId());

        if (comments.isEmpty()) {
            Label noComments = new Label("هنوز نظری ثبت نشده است");
            noComments.setStyle("-fx-text-fill: #999; -fx-font-style: italic;");
            commentsContainer.getChildren().add(noComments);
        } else {
            for (Comment comment : comments) {
                commentsContainer.getChildren().add(createCommentCard(comment));
            }
        }

        commentsSection.getChildren().addAll(commentsHeader, commentsScroll);
        return commentsSection;
    }

    private VBox createCommentCard(Comment comment) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10; -fx-border-radius: 5;");

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        
        VBox userInfo = new VBox(5);
        Label userName = new Label("کاربر " + comment.getUserId());
        userName.setStyle("-fx-font-weight: bold;");

        
        HBox starsBox = new HBox(5);
        int stars = Integer.parseInt(comment.getStars());
        starsBox.getChildren().add(createRatingStars(stars));

        userInfo.getChildren().addAll(userName, starsBox);

        header.getChildren().add(userInfo);

        
        Label title = new Label(comment.getTitle());
        title.setStyle("-fx-font-weight: bold;");

        Label description = new Label(comment.getDescription());
        description.setWrapText(true);

        card.getChildren().addAll(header, title, description);
        return card;
    }

    private HBox createRatingStars(double rating) {
        HBox starsBox = new HBox(5);
        int fullStars = (int) rating;
        int halfStar = (rating - fullStars) > 0.5 ? 1 : 0;
        int emptyStars = 5 - fullStars - halfStar;

        
        for (int i = 0; i < fullStars; i++) {
            Label star = new Label("★");
            star.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 16;");
            starsBox.getChildren().add(star);
        }

        
        if (halfStar > 0) {
            Label star = new Label("★");
            star.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 16; -fx-opacity: 0.7;");
            starsBox.getChildren().add(star);
        }

        
        for (int i = 0; i < emptyStars; i++) {
            Label star = new Label("☆");
            star.setStyle("-fx-text-fill: #999; -fx-font-size: 16;");
            starsBox.getChildren().add(star);
        }

        return starsBox;
    }

    private VBox createCommentForm() {
        VBox form = new VBox(15);
        form.setPadding(new Insets(15));
        form.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        form.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-width: 1px;");

        Label formTitle = new Label("ثبت نظر جدید");
        formTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        commentTitleField.setPromptText("عنوان نظر");
        commentTitleField.setPrefWidth(300);
        Label titleLabel = new Label("عنوان:");
        titleBox.getChildren().addAll(titleLabel,commentTitleField);

        
        HBox descBox = new HBox(10);
        descBox.setAlignment(Pos.CENTER_LEFT);
        commentDescriptionArea.setPromptText("متن کامل نظر");
        commentDescriptionArea.setPrefRowCount(3);
        Label descLabel = new Label("متن نظر:");
        descBox.getChildren().addAll(descLabel,commentDescriptionArea);

        
        HBox ratingBox = new HBox(10);
        ratingBox.setAlignment(Pos.CENTER_LEFT);
        ratingCombo.getItems().addAll(1, 2, 3, 4, 5);
        ratingCombo.setValue(5);
        ratingCombo.setCellFactory(p -> new RatingListCell());
        ratingCombo.setButtonCell(new RatingListCell());
        Label ratingLabel = new Label("امتیاز:");
        ratingBox.getChildren().addAll(ratingLabel,ratingCombo);

        
        Button submitButton = new Button("ثبت نظر");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        submitButton.setOnAction(e -> submitComment());

        form.getChildren().addAll(formTitle, titleBox, descBox, ratingBox, submitButton);
        return form;
    }

    private void submitComment() {
        if (commentTitleField.getText().isEmpty()) {
            showAlert("خطا", "لطفا عنوان نظر را وارد کنید");
            return;
        }

        if (ratingCombo.getValue() == null) {
            showAlert("خطا", "لطفا امتیاز را انتخاب کنید");
            return;
        }

        
        Comment newComment = new Comment(
                null,
                food.getId(),
                "current_user_id", 
                null,
                commentTitleField.getText(),
                commentDescriptionArea.getText(),
                ratingCombo.getValue().toString()
        );

        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("موفقیت");
        alert.setHeaderText(null);
        alert.setContentText("نظر شما با موفقیت ثبت شد!");
        alert.showAndWait();

        
        commentTitleField.clear();
        commentDescriptionArea.clear();
        ratingCombo.setValue(5);
    }

    private void handleAddToCart() {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("موفقیت");
        alert.setHeaderText(null);
        alert.setContentText(food.getName() + " به سبد خرید اضافه شد!");
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }

    private Label createValueLabel(String text) {
        Label label = new Label(text);
        return label;
    }

    
    private static class RatingListCell extends ListCell<Integer> {
        @Override
        protected void updateItem(Integer rating, boolean empty) {
            super.updateItem(rating, empty);
            if (empty || rating == null) {
                setText(null);
            } else {
                
                StringBuilder stars = new StringBuilder();
                for (int i = 0; i < rating; i++) {
                    stars.append("★");
                }
                setText(stars.toString());
            }
        }
    }
}

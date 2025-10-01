package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import com.example.model.movieModel;

public class MovieCardController {
    @FXML
    private ImageView posterImageView;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label yearLabel;
    
    @FXML
    private FlowPane genresFlowPane;
    
    @FXML
    private Label ratingLabel;
    
    @FXML
    private Label voteCountLabel;
    
    @FXML
    private VBox cardContainer;
    
    @FXML
    private Button detailsButton;
    
    @FXML
    private Button watchLaterButton;
    
    private movieModel movie;
    private MovieCardCallback callback;
    
    public interface MovieCardCallback {
        void onDetailsClicked(movieModel movie);
        void onWatchLaterClicked(movieModel movie);
    }
    
    @FXML
    public void initialize() {
    }
    
    public void setMovie(movieModel movie) {
        this.movie = movie;
        updateDisplay();
    }
    
    public void setCallback(MovieCardCallback callback) {
        this.callback = callback;
    }
    
    @FXML
    private void handleDetailsClick() {
        if (callback != null && movie != null) {
            callback.onDetailsClicked(movie);
        }
    }
    
    @FXML
    private void handleWatchLaterClick() {
        if (callback != null && movie != null) {
            callback.onWatchLaterClicked(movie);
        }
    }
    
    private void updateDisplay() {
        if (movie == null) return;
        
        titleLabel.setText(movie.getTitle());
        
        yearLabel.setText(movie.getYear());
        
        ratingLabel.setText(String.format("★ %.1f", movie.getRating()));
        voteCountLabel.setText(movie.getVoteCount() + " votes");
        
        if (movie.getPosterPath() != null && !movie.getPosterPath().isEmpty()) {
            try {
                Image posterImage = new Image(movie.getPosterPath());
                posterImageView.setImage(posterImage);
            } catch (Exception e) {
                System.err.println("Error loading poster image: " + e.getMessage());
                posterImageView.setImage(null);
            }
        }
        
        updateGenres();
    }
    
    private void updateGenres() {
        genresFlowPane.getChildren().clear();
        
        if (movie.getGenres() != null) {
            for (String genre : movie.getGenres()) {
                Label genreLabel = new Label(genre);
                genreLabel.getStyleClass().add("genre-tag");
                genresFlowPane.getChildren().add(genreLabel);
            }
        }
    }
    
    public movieModel getMovie() {
        return movie;
    }
} 
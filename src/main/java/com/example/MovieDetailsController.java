package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import com.example.model.movieModel;

public class MovieDetailsController {
    @FXML
    private Button backButton;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Button themeToggleButton;
    
    @FXML
    private ImageView posterImageView;
    
    @FXML
    private Label movieTitleLabel;
    
    @FXML
    private Label yearLabel;
    
    @FXML
    private Label ratingLabel;
    
    @FXML
    private Label voteCountLabel;
    
    @FXML
    private Label releaseDateLabel;
    
    @FXML
    private FlowPane genresFlowPane;
    
    @FXML
    private TextArea overviewTextArea;
    
    @FXML
    private Label movieIdLabel;
    
    @FXML
    private Label runtimeLabel;
    
    @FXML
    private Label languageLabel;
    
    @FXML
    private Label budgetLabel;
    
    @FXML
    private Label revenueLabel;
    
    private movieModel movie;
    private boolean isDarkMode = false;
    
    @FXML
    public void initialize() {
        updateThemeButton();
    }
    
    public void setMovie(movieModel movie) {
        this.movie = movie;
        updateDisplay();
    }
    
    @FXML
    private void handleBackClick() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleThemeToggle() {
        isDarkMode = !isDarkMode;
        applyTheme();
        updateThemeButton();
    }
    
    private void applyTheme() {
        if (themeToggleButton.getScene() != null) {
            if (isDarkMode) {
                themeToggleButton.getScene().getRoot().getStyleClass().add("dark-theme");
            } else {
                themeToggleButton.getScene().getRoot().getStyleClass().remove("dark-theme");
            }
        }
    }
    
    private void updateThemeButton() {
        if (isDarkMode) {
            themeToggleButton.setText("Light");
            themeToggleButton.setTooltip(new Tooltip("Switch to Light Mode"));
        } else {
            themeToggleButton.setText("Dark");
            themeToggleButton.setTooltip(new Tooltip("Switch to Dark Mode"));
        }
    }
    
    private void updateDisplay() {
        if (movie == null) return;
        
        movieTitleLabel.setText(movie.getTitle());
        titleLabel.setText("     Movie Details - " + movie.getTitle());
        
        yearLabel.setText(movie.getYear());
        
        ratingLabel.setText(String.format("★ %.1f", movie.getRating()));
        voteCountLabel.setText(movie.getVoteCount() + " votes");
        
        releaseDateLabel.setText("Release Date: " + movie.getReleaseDate());
        
        overviewTextArea.setText(movie.getOverview());
        
        movieIdLabel.setText("Movie ID: " + movie.getId());
        
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
        
        runtimeLabel.setText("Runtime: 120 minutes");
        languageLabel.setText("Language: English");
        budgetLabel.setText("Budget: $50,000,000");
        revenueLabel.setText("Revenue: $200,000,000");
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
} 
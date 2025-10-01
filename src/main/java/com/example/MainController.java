package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.Parent;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import com.example.model.movieModel;
import com.example.api.movieApi;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;

public class MainController {
    @FXML
    private TextField searchTextField;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private ChoiceBox<String> genreChoiceBox;
    
    @FXML
    private MenuButton menuButton;
    
    @FXML
    private Button watchLaterButton;
    
    @FXML
    private Button themeToggleButton;
    
    @FXML
    private Label sectionTitleLabel;
    
    @FXML
    private FlowPane moviesContainer;
    
    private movieApi movieApi;
    private ExecutorService executorService;
    private boolean isUsingFallbackData = false;
    private String currentCategory = "popular";
    private boolean isDarkMode = false;
    
    
    private ObservableList<movieModel> popularMovies = FXCollections.observableArrayList();
    private ObservableList<movieModel> nowShowingMovies = FXCollections.observableArrayList();
    private ObservableList<movieModel> topRatedMovies = FXCollections.observableArrayList();
    private ObservableList<movieModel> upcomingMovies = FXCollections.observableArrayList();
    
    private ObservableList<movieModel> watchLaterMovies = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        movieApi = new movieApi();
        executorService = Executors.newCachedThreadPool();
        
        initializeGenreChoiceBox();
        setupEventHandlers();
        loadAllMovies();
        
        showCategory("popular");
        
        updateThemeButton();
    }
    
    private void initializeGenreChoiceBox() {
        ObservableList<String> genres = FXCollections.observableArrayList(
            "All Genres", "Action", "Adventure", "Comedy", "Drama", "Fantasy", 
            "Horror", "Romance", "Science Fiction", "Thriller", "Animation", "Family"
        );
        genreChoiceBox.setItems(genres);
        genreChoiceBox.setValue("All Genres");
                
        genreChoiceBox.setOnAction(event -> filterMoviesByGenre());
    }
    
    private void setupEventHandlers() {
        searchButton.setOnAction(event -> performSearch());
        
        searchTextField.setOnAction(event -> performSearch());
        
        watchLaterButton.setOnAction(event -> handleWatchLaterClick());
        
        themeToggleButton.setOnAction(event -> handleThemeToggle());
        
        setupCategoryMenu();
    }
    
    @FXML
    private void handleThemeToggle() {
        isDarkMode = !isDarkMode;
        applyTheme();
        updateThemeButton();
    }
    
    private void applyTheme() {
        Scene scene = themeToggleButton.getScene();
        if (scene != null) {
            if (isDarkMode) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
                scene.getRoot().getStyleClass().add("dark-theme");
            } else {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(getClass().getResource("/com/example/styles.css").toExternalForm());
                scene.getRoot().getStyleClass().remove("dark-theme");
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
    
    private void setupCategoryMenu() {
        menuButton.getItems().clear();
        
        MenuItem popularItem = new MenuItem("Popular Movies");
        MenuItem nowShowingItem = new MenuItem("Now Showing");
        MenuItem topRatedItem = new MenuItem("Top Rated");
        MenuItem upcomingItem = new MenuItem("Upcoming");
        MenuItem refreshItem = new MenuItem("Refresh All");
        
        popularItem.setOnAction(event -> showCategory("popular"));
        nowShowingItem.setOnAction(event -> showCategory("now_showing"));
        topRatedItem.setOnAction(event -> showCategory("top_rated"));
        upcomingItem.setOnAction(event -> showCategory("upcoming"));
        refreshItem.setOnAction(event -> loadAllMovies());
        
        menuButton.getItems().addAll(popularItem, nowShowingItem, topRatedItem, upcomingItem, refreshItem);
        
        menuButton.setText("Popular Movies");
    }
    
    private void loadAllMovies() {
        isUsingFallbackData = false;
        
        executorService.submit(() -> loadPopularMovies());
        executorService.submit(() -> loadNowShowingMovies());
        executorService.submit(() -> loadTopRatedMovies());
        executorService.submit(() -> loadUpcomingMovies());
    }
    
    private void loadPopularMovies() {
        List<movieModel> movies = movieApi.getPopularMovies(20);
        Platform.runLater(() -> {
            popularMovies.clear();
            popularMovies.addAll(movies);
            
            if (movieApi.isUsingFallbackData() && !isUsingFallbackData) {
                isUsingFallbackData = true;
                showFallbackMessage();
            }
            
            if (currentCategory.equals("popular")) {
                displayMovies(popularMovies, moviesContainer);
            }
        });
    }
    
    private void loadNowShowingMovies() {
        List<movieModel> movies = movieApi.getNowPlayingMovies(20);
        Platform.runLater(() -> {
            nowShowingMovies.clear();
            nowShowingMovies.addAll(movies);
            
            if (movieApi.isUsingFallbackData() && !isUsingFallbackData) {
                isUsingFallbackData = true;
                showFallbackMessage();
            }
            
            if (currentCategory.equals("now_showing")) {
                displayMovies(nowShowingMovies, moviesContainer);
            }
        });
    }
    
    private void loadTopRatedMovies() {
        List<movieModel> movies = movieApi.getTopRatedMovies(20);
        Platform.runLater(() -> {
            topRatedMovies.clear();
            topRatedMovies.addAll(movies);
            
            if (movieApi.isUsingFallbackData() && !isUsingFallbackData) {
                isUsingFallbackData = true;
                showFallbackMessage();
            }
            
            if (currentCategory.equals("top_rated")) {
                displayMovies(topRatedMovies, moviesContainer);
            }
        });
    }
    
    private void loadUpcomingMovies() {
        List<movieModel> movies = movieApi.getUpcomingMovies(20);
        Platform.runLater(() -> {
            upcomingMovies.clear();
            upcomingMovies.addAll(movies);
            
            if (movieApi.isUsingFallbackData() && !isUsingFallbackData) {
                isUsingFallbackData = true;
                showFallbackMessage();
            }
            
            if (currentCategory.equals("upcoming")) {
                displayMovies(upcomingMovies, moviesContainer);
            }
        });
    }
    
    private void showFallbackMessage() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Network Connection");
            alert.setHeaderText("API Unreachable");
            alert.setContentText("Unable to connect to The Movie Database API. " +
                              "Showing sample data instead. Please check your internet connection " +
                              "and try refreshing the application.");
            alert.showAndWait();
        });
    }
    
    private void displayMovies(ObservableList<movieModel> movies, FlowPane container) {
        container.getChildren().clear();
        
        if (movies.isEmpty()) {
            Label noMoviesLabel = new Label("No movies available");
            noMoviesLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
            container.getChildren().add(noMoviesLabel);
            return;
        }
        
        for (movieModel movie : movies) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieCard.fxml"));
                Parent movieCard = loader.load();
                
                MovieCardController cardController = loader.getController();
                cardController.setMovie(movie);

                cardController.setCallback(new MovieCardController.MovieCardCallback() {
                    @Override
                    public void onDetailsClicked(movieModel movie) {
                        showMovieDetails(movie);
                    }
                    
                    @Override
                    public void onWatchLaterClicked(movieModel movie) {
                        addToWatchLater(movie);
                    }
                });
                
                container.getChildren().add(movieCard);
                
            } catch (IOException e) {
                System.err.println("Error loading movie card: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void showMovieDetails(movieModel movie) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieDetails.fxml"));
            Parent detailsRoot = loader.load();
            
            MovieDetailsController detailsController = loader.getController();
            detailsController.setMovie(movie);
            
            Stage detailsStage = new Stage();
            detailsStage.setTitle("Movie Details - " + movie.getTitle());
            detailsStage.setScene(new Scene(detailsRoot));
            detailsStage.setResizable(true);
            
            if (isDarkMode) {
                detailsStage.getScene().getRoot().getStyleClass().add("dark-theme");
            }
            
            detailsStage.show();
            
        } catch (IOException e) {
            System.err.println("Error loading movie details: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void addToWatchLater(movieModel movie) {
        boolean alreadyExists = watchLaterMovies.stream()
                .anyMatch(m -> m.getId() == movie.getId());
        
        if (!alreadyExists) {
            watchLaterMovies.add(movie);
            
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Watch Later");
                alert.setHeaderText("Movie Added");
                alert.setContentText(movie.getTitle() + " has been added to your Watch Later list.");
                alert.showAndWait();
            });
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Watch Later");
                alert.setHeaderText("Already Added");
                alert.setContentText(movie.getTitle() + " is already in your Watch Later list.");
                alert.showAndWait();
            });
        }
    }
    
    @FXML
    private void handleWatchLaterClick() {
        currentCategory = "watch_later";
        displayMovies(watchLaterMovies, moviesContainer);
        sectionTitleLabel.setText("Watch Later (" + watchLaterMovies.size() + " movies)");
        menuButton.setText("Watch Later");
    }
    
    private void showCategory(String category) {
        currentCategory = category;
        
        moviesContainer.getChildren().clear();
        
        switch (category) {
            case "popular":
                displayMovies(popularMovies, moviesContainer);
                sectionTitleLabel.setText("Popular Movies");
                System.out.println("Showing Popular Movies category");
                break;
            case "now_showing":
                displayMovies(nowShowingMovies, moviesContainer);
                sectionTitleLabel.setText("Now Showing");
                System.out.println("Showing Now Showing category");
                break;
            case "top_rated":
                displayMovies(topRatedMovies, moviesContainer);
                sectionTitleLabel.setText("Top Rated");
                System.out.println("Showing Top Rated category");
                break;
            case "upcoming":
                displayMovies(upcomingMovies, moviesContainer);
                sectionTitleLabel.setText("Upcoming");
                System.out.println("Showing Upcoming category");
                break;
            case "watch_later":
                displayMovies(watchLaterMovies, moviesContainer);
                sectionTitleLabel.setText("Watch Later (" + watchLaterMovies.size() + " movies)");
                System.out.println("Showing Watch Later category");
                break;
        }

        updateMenuButtonText(category);
    }
    
    private void updateMenuButtonText(String category) {
        switch (category) {
            case "popular":
                menuButton.setText("Popular Movies");
                break;
            case "now_showing":
                menuButton.setText("Now Showing");
                break;
            case "top_rated":
                menuButton.setText("Top Rated");
                break;
            case "upcoming":
                menuButton.setText("Upcoming");
                break;
            case "watch_later":
                menuButton.setText("Watch Later");
                break;
            default:
                menuButton.setText("Filter by Category");
                break;
        }
    }
    
    private void performSearch() {
        String searchTerm = searchTextField.getText().trim();
        if (searchTerm.isEmpty()) {
            showCategory(currentCategory);
            return;
        }
        
        executorService.submit(() -> {
            List<movieModel> searchResults = movieApi.searchMovies(searchTerm, 50);
            Platform.runLater(() -> {
                moviesContainer.getChildren().clear();
                
                if (!searchResults.isEmpty()) {
                    displayMovies(FXCollections.observableArrayList(searchResults), moviesContainer);
                    sectionTitleLabel.setText("Search Results for: " + searchTerm + " (" + searchResults.size() + " movies)");
                } else {
                    Label noResultsLabel = new Label("No movies found for: " + searchTerm);
                    noResultsLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 16px; -fx-padding: 20;");
                    moviesContainer.getChildren().add(noResultsLabel);
                    sectionTitleLabel.setText("Search Results");
                }
            });
        });
    }
    
    private List<movieModel> searchMovies(String searchTerm) {
        return new ArrayList<>();
    }
    
    private void filterMoviesByGenre() {
        String selectedGenre = genreChoiceBox.getValue();
        if (selectedGenre == null || selectedGenre.equals("All Genres")) {
            showCategory(currentCategory);
            return;
        }
        
        ObservableList<movieModel> currentMovies = getCurrentCategoryMovies();
        
        ObservableList<movieModel> filteredMovies = filterMoviesByGenre(currentMovies, selectedGenre);
        
        moviesContainer.getChildren().clear();
        
        if (!filteredMovies.isEmpty()) {
            displayMovies(filteredMovies, moviesContainer);
            sectionTitleLabel.setText(sectionTitleLabel.getText() + " - " + selectedGenre);
        } else {
          
            Label noResultsLabel = new Label("No movies found in genre: " + selectedGenre);
            noResultsLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 16px; -fx-padding: 20;");
            moviesContainer.getChildren().add(noResultsLabel);
            sectionTitleLabel.setText("No Results");
        }
    }
    
    private ObservableList<movieModel> getCurrentCategoryMovies() {
        switch (currentCategory) {
            case "popular":
                return popularMovies;
            case "now_showing":
                return nowShowingMovies;
            case "top_rated":
                return topRatedMovies;
            case "upcoming":
                return upcomingMovies;
            case "watch_later":
                return watchLaterMovies;
            default:
                return popularMovies;
        }
    }
    
    private ObservableList<movieModel> filterMoviesByGenre(ObservableList<movieModel> movies, String genre) {
        ObservableList<movieModel> filtered = FXCollections.observableArrayList();
        
        for (movieModel movie : movies) {
            if (movie.getGenres() != null && movie.getGenres().contains(genre)) {
                filtered.add(movie);
            }
        }
        
        return filtered;
    }
    
    public void shutdown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}

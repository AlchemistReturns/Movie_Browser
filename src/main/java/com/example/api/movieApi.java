package com.example.api;

import com.example.model.movieModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

class MovieApiResponse {
    @SerializedName("results")
    List<MovieJsonData> results;
}

class MovieJsonData {
    @SerializedName("id")
    int id;
    
    @SerializedName("original_title")
    String originalTitle;
    
    @SerializedName("poster_path")
    String posterPath;
    
    @SerializedName("vote_average")
    double voteAverage;
    
    @SerializedName("vote_count")
    int voteCount;
    
    @SerializedName("release_date")
    String releaseDate;
    
    @SerializedName("overview")
    String overview;
}

public class movieApi {
    private static final String API_KEY = "26211219711138650c43250c17b6a83a";
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    
    private final HttpClient httpClient;
    private boolean useFallbackData = false;
    private final Gson gson;
    
    public movieApi() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
    }
    
    public List<movieModel> getPopularMovies(int limit) {
        return fetchMovies("/movie/popular", limit);
    }
    
    public List<movieModel> getNowPlayingMovies(int limit) {
        return fetchMovies("/movie/now_playing", limit);
    }
    
    public List<movieModel> getTopRatedMovies(int limit) {
        return fetchMovies("/movie/top_rated", limit);
    }
    
    public List<movieModel> getUpcomingMovies(int limit) {
        return fetchMovies("/movie/upcoming", limit);
    }
    
    public List<movieModel> searchMovies(String query, int limit) {
        return fetchMovies("/search/movie?query=" + java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8), limit);
    }
    
    private List<movieModel> fetchMovies(String endpoint, int limit) {
        List<movieModel> movies = new ArrayList<>();
        
        if (useFallbackData) {
            return getFallbackMovies(endpoint, limit);
        }
        
        try {
            String url = BASE_URL + endpoint;

            if (!endpoint.contains("?")) {
                url += "?api_key=" + API_KEY + "&language=en-US&page=1";
            } else {
                url += "&api_key=" + API_KEY + "&language=en-US&page=1";
            }
            System.out.println("Attempting to fetch from: " + url);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .header("User-Agent", "MovieBox/1.0")
                    .timeout(Duration.ofSeconds(15))
                    .GET()
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                System.out.println("API Response preview: " + jsonResponse.substring(0, Math.min(500, jsonResponse.length())) + "...");
                List<movieModel> parsedMovies = parseMoviesFromJson(jsonResponse, limit);
                movies.addAll(parsedMovies);
                System.out.println("Successfully fetched " + movies.size() + " movies from API");
            } else {
                System.err.println("API request failed with status: " + response.statusCode());
                System.err.println("Response body: " + response.body());
                movies = getFallbackMovies(endpoint, limit);
            }
            
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching movies: " + e.getMessage());
            System.err.println("Network error - using fallback data");
            useFallbackData = true;
            movies = getFallbackMovies(endpoint, limit);
        }
        
        return movies;
    }
    
    private List<movieModel> parseMoviesFromJson(String jsonResponse, int limit) {
        List<movieModel> movies = new ArrayList<>();
        
        try {
            MovieApiResponse response = gson.fromJson(jsonResponse, MovieApiResponse.class);
            
            if (response == null || response.results == null) {
                System.out.println("No results found in JSON response");
                return movies;
            }
            
            int count = 0;
            for (MovieJsonData movieData : response.results) {
                if (count >= limit) break;
                
                String fullPosterPath = movieData.posterPath;
                if (fullPosterPath != null && !fullPosterPath.isEmpty()) {
                    fullPosterPath = IMAGE_BASE_URL + fullPosterPath;
                }
                
                List<String> genres = getGenresForMovie(movieData.id);
                
                movieModel movie = new movieModel(
                    movieData.id,
                    movieData.originalTitle,
                    fullPosterPath,
                    movieData.voteAverage,
                    movieData.voteCount,
                    movieData.releaseDate,
                    movieData.overview,
                    genres
                );
                
                movies.add(movie);
                count++;
                System.out.println("Parsed movie: " + movie.getTitle() + " (ID: " + movie.getId() + ")");
            }
            
        } catch (Exception e) {
            System.err.println("Error parsing JSON with Gson: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Total movies parsed: " + movies.size());
        return movies;
    }
    

    
    private List<movieModel> getFallbackMovies(String endpoint, int limit) {
        System.out.println("API unreachable - returning empty movie list");
        return new ArrayList<>();
    }
    

    
    private List<String> getGenresForMovie(int movieId) {
        List<String> genres = new ArrayList<>();
        
        int genreHash = movieId % 7;
        switch (genreHash) {
            case 0:
                genres.add("Action");
                genres.add("Adventure");
                break;
            case 1:
                genres.add("Comedy");
                genres.add("Romance");
                break;
            case 2:
                genres.add("Drama");
                genres.add("Thriller");
                break;
            case 3:
                genres.add("Science Fiction");
                genres.add("Action");
                break;
            case 4:
                genres.add("Fantasy");
                genres.add("Family");
                break;
            case 5:
                genres.add("Horror");
                genres.add("Thriller");
                break;
            case 6:
                genres.add("Animation");
                genres.add("Family");
                break;
        }
        
        return genres;
    }
    
    public static void setApiKey(String apiKey) {
    }
    
    public boolean isUsingFallbackData() {
        return useFallbackData;
    }
}

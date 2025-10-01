package com.example.model;

import javafx.beans.property.*;
import java.util.List;

public class movieModel {
    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty posterPath;
    private final DoubleProperty rating;
    private final IntegerProperty voteCount;
    private final StringProperty releaseDate;
    private final StringProperty overview;
    private final List<String> genres;
    private final StringProperty year;

    public movieModel(int id, String title, String posterPath, double rating, int voteCount, 
                     String releaseDate, String overview, List<String> genres) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.posterPath = new SimpleStringProperty(posterPath);
        this.rating = new SimpleDoubleProperty(rating);
        this.voteCount = new SimpleIntegerProperty(voteCount);
        this.releaseDate = new SimpleStringProperty(releaseDate);
        this.overview = new SimpleStringProperty(overview);
        this.genres = genres;
        
  
        String yearStr = "";
        if (releaseDate != null && releaseDate.length() >= 4) {
            yearStr = releaseDate.substring(0, 4);
        }
        this.year = new SimpleStringProperty(yearStr);
    }

   
    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }
    public void setId(int id) { this.id.set(id); }

    public String getTitle() { return title.get(); }
    public StringProperty titleProperty() { return title; }
    public void setTitle(String title) { this.title.set(title); }

    public String getPosterPath() { return posterPath.get(); }
    public StringProperty posterPathProperty() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath.set(posterPath); }

    public double getRating() { return rating.get(); }
    public DoubleProperty ratingProperty() { return rating; }
    public void setRating(double rating) { this.rating.set(rating); }

    public int getVoteCount() { return voteCount.get(); }
    public IntegerProperty voteCountProperty() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount.set(voteCount); }

    public String getReleaseDate() { return releaseDate.get(); }
    public StringProperty releaseDateProperty() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate.set(releaseDate); }

    public String getOverview() { return overview.get(); }
    public StringProperty overviewProperty() { return overview; }
    public void setOverview(String overview) { this.overview.set(overview); }

    public List<String> getGenres() { return genres; }

    public String getYear() { return year.get(); }
    public StringProperty yearProperty() { return year; }
    public void setYear(String year) { this.year.set(year); }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", rating=" + getRating() +
                ", year='" + getYear() + '\'' +
                '}';
    }
}

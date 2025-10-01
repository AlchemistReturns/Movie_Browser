# Movie Database Application

A JavaFX-based movie database application that fetches movie information from The Movie Database (TMDB) API and displays it in a modern card-based interface.

## Features

- **Multiple Movie Categories**: Popular, Now Showing, Top Rated, and Upcoming movies
- **Search Functionality**: Search movies by title
- **Genre Filtering**: Filter movies by genre
- **Modern UI**: Card-based layout with hover effects
- **Responsive Design**: Horizontal scrolling for movie sections
- **Real-time Data**: Fetches live data from TMDB API

## Setup Instructions

### 1. Get TMDB API Key

1. Visit [The Movie Database](https://www.themoviedb.org/settings/api)
2. Create an account or log in
3. Request an API key (choose "Developer" option)
4. Copy your API key

### 2. Configure API Key

1. Open `src/api/movieApi.java`
2. Replace `YOUR_API_KEY_HERE` on line 19 with your actual API key:
   ```java
   private static final String API_KEY = "your_actual_api_key_here";
   ```

### 3. Run the Application

1. Make sure you have Java 11+ and JavaFX installed
2. Compile and run the application:
   ```bash
   javac -cp "path/to/javafx-sdk/lib/*" src/*.java src/*/*.java
   java -cp "path/to/javafx-sdk/lib/*:src" Main
   ```

## Project Structure

```
src/
├── Main.java                 # Main application entry point
├── Main.fxml                # Main UI layout
├── MainController.java       # Main controller logic
├── MovieCard.fxml           # Individual movie card layout
├── MovieCardController.java  # Movie card controller
├── styles.css               # Application styling
├── api/
│   └── movieApi.java        # TMDB API integration
└── model/
    └── movieModel.java      # Movie data model
```

## API Integration

The application uses The Movie Database (TMDB) API to fetch:
- Popular movies
- Now playing movies
- Top rated movies
- Upcoming movies

Each category displays up to 10 movies with:
- Movie poster
- Title
- Release year
- Genre tags
- Rating and vote count

## Features in Detail

### Search
- Type in the search box to find movies by title
- Press Enter or click Search button
- Results are displayed across all sections

### Genre Filtering
- Use the genre dropdown to filter movies
- Select "All Genres" to show all movies
- Filter applies to all sections

### Movie Cards
- Hover effects with scaling animation
- Displays movie poster, title, year, genres, and rating
- Responsive design with proper spacing

### Sections
- **Popular Movies**: Currently popular movies
- **Now Showing**: Movies currently in theaters
- **Top Rated**: Highest rated movies
- **Upcoming**: Movies releasing soon

## Styling

The application uses a modern, clean design with:
- Card-based layout
- Drop shadows and hover effects
- Consistent color scheme
- Responsive typography
- Smooth animations

## Troubleshooting

### Network Connection Issues
If you see `java.net.ConnectException` errors:
- **The application will automatically use sample data** when the API is unreachable
- Check your internet connection
- Verify firewall settings aren't blocking the application
- The app will show an information dialog when using fallback data
- You can still test all features with the sample data

### API Issues
- Ensure your API key is correctly set in `src/api/movieApi.java`
- Check internet connection
- Verify TMDB API is accessible
- If API is down, the app will use built-in sample data

### Compilation Issues
- Make sure Java 11+ is installed
- Check that all source files are in the correct locations
- Verify all dependencies are available

### Runtime Issues
- Check console for error messages
- Ensure all FXML files are in the correct location
- Verify CSS file path
- Use the provided `run.bat` script for easy execution

## Future Enhancements

- Movie detail pages
- User ratings and reviews
- Watchlist functionality
- Advanced search filters
- Movie trailers integration
- User authentication

## Dependencies

- Java 11+
- JavaFX
- TMDB API (external)

## License

This project is for educational purposes. Please respect TMDB's API terms of service. 
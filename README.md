# Movie Browser (JavaFX + TMDB API)

This project is a JavaFX-based movie browsing application that fetches real-time movie data from the **TMDB API** and presents it through a clean, modern, and responsive graphical interface.  
It reflects my understanding of **GUI design principles**, **modern UI patterns**, and effective **API integration** within desktop applications.

---

## Project Overview

The goal of this application was to build a visually appealing and intuitive movie browsing experience.  
The interface is designed using JavaFX layouts, CSS styling, and card-based components to create a clean and modern desktop UI.  
The application communicates with the TMDB REST API to retrieve up-to-date information on movies, categories, and genres.

This project demonstrates:
- Practical experience with GUI design best practices  
- Clean separation of UI, logic, and networking layers  
- Handling and rendering of dynamic API data  
- Responsive and interactive desktop UI development  

---

## Key Features

### Multiple Movie Categories
Displays real-time lists for:
- Popular  
- Now Showing  
- Top Rated  
- Upcoming  

Each category uses a horizontal scrollable layout for smooth browsing.

### Search Functionality
Searches TMDB for movies by title and updates the UI with live results.

### Genre Filtering
Retrieves genres from TMDB and allows filtering of displayed movies.

### Modern, Card-Based UI
- Each movie is presented using styled card components.  
- Hover effects, shadows, and smooth transitions create a visually modern experience.  
- Designed with reusable UI components for scalability.

### Responsive Layout
- Works across different window sizes.  
- Horizontal scrolling and adaptive card resizing ensure usability on various displays.

### Real-Time API Integration
- All movie, genre, and search data is fetched live from the TMDB API.  
- Demonstrates use of HTTP requests, JSON parsing, and asynchronous data handling.

---

## GUI & Design Principles Demonstrated

### Separation of Concerns
The application separates UI components, data models, and API services, ensuring maintainability and clarity in the codebase.

### Reusable Components
Movie cards, sections, and layout containers are designed as reusable elements, showing an understanding of scalable GUI development.

### Modern Styling Using JavaFX CSS
- Clean typography  
- Consistent spacing and alignment  
- Hover animations and shadows  
- Layouts that emphasize readability and user engagement  

### User-Centered Interaction
- Smooth scrolling  
- Clear visual hierarchy  
- Minimalistic design with emphasis on movie posters and titles  

---

## API Integration

### TMDB API Usage
- Fetches movie lists, genres, and search results using TMDB endpoints.  
- Demonstrates handling of API keys, query parameters, asynchronous tasks, and error handling.

### JSON Parsing and Data Mapping
- TMDB API responses are mapped into Java model classes.  
- Ensures a clean flow from raw API data to UI rendering.

---

## Tech Stack

- **JavaFX**  
- **TMDB REST API**  
- **Java HTTP Client / Networking**  
- **JSON Parsing (Gson / Jackson)**  
- **JavaFX CSS for UI styling**

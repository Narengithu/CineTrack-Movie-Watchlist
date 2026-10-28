# CineTrack – Movie Discovery and Watchlist Management System

## 1. Project Overview

CineTrack is a Java-based desktop application developed using JavaFX. It helps users browse movies, search for movies by title, filter movies by genre, and manage their personal watchlists through a simple and user-friendly interface.

The application allows users to add movies to their watchlist, remove movies, and mark movies as watched. It also uses file handling to save watchlist information locally, so the saved data can be loaded again when the application is reopened.

## 2. Objectives

- To develop a simple and interactive desktop application using JavaFX.
- To allow users to browse a collection of movies.
- To provide search and genre-filtering functionality.
- To help users maintain a personal movie watchlist.
- To allow users to track movies they have watched.
- To store watchlist information using file handling.

## 3. Features

- **Movie Browsing:** Browse a predefined collection of movies.
- **Search Movies:** Search for movies by title.
- **Genre Filtering:** Filter movies based on their genre.
- **My Watchlist:** View and manage selected movies.
- **Add and Remove Movies:** Add movies to or remove them from the watchlist.
- **Watched Status:** Mark movies as watched and track their status.
- **Local Data Storage:** Save and load watchlist information using a text file.
- **User-Friendly Interface:** Navigate through the application using a JavaFX desktop interface.

## 4. Technologies Used

- **Programming Language:** Java
- **GUI Framework:** JavaFX
- **Data Storage:** Text file (`watchlist.txt`)
- **Development Environment:** Visual Studio Code
- **Java Development Kit:** JDK

## 5. Project Structure

```text
CineTrack-Movie-Watchlist/
│
├── CineTrack.java
├── watchlist.txt
└── README.md
```

### File Description

- `CineTrack.java` – Contains the main application code, JavaFX interface, movie information, search and filtering functions, and watchlist management.
- `watchlist.txt` – Stores the locally saved watchlist information and watched status.
- `README.md` – Provides information about the project, its features, and how to run it.

## 6. How to Run the Application

### Prerequisites

Before running CineTrack, make sure you have:

- Java JDK installed.
- JavaFX SDK downloaded and extracted.
- Visual Studio Code or another Java-compatible development environment.

### Steps

**Step 1:** Clone or download this repository to your computer.

**Step 2:** Make sure the JavaFX SDK is installed. Update the JavaFX library path in the commands below if your SDK is stored in a different location.

**Step 3:** Open the project folder in Visual Studio Code.

**Step 4:** Open the terminal in the project folder and compile the Java file:

```bash
javac --module-path "C:\javafx\javafx-sdk-26.0.1\lib" --add-modules javafx.controls CineTrack.java
```

**Step 5:** Run the application:

```bash
java --module-path "C:\javafx\javafx-sdk-26.0.1\lib" --add-modules javafx.controls -cp . CineTrack
```

**Note:** These commands use the JavaFX SDK path from the development setup. If your JavaFX SDK is located elsewhere, replace the path with your actual JavaFX `lib` directory.

## 7. Concepts Demonstrated

This project demonstrates the use of the following Java programming concepts:

- Object-Oriented Programming (OOP)
- Classes and objects
- JavaFX user interface components
- Event handling
- Collections using `ArrayList` and `List`
- Searching and filtering
- File handling for saving and loading data

## 8. Scope and Limitations

The current version of CineTrack uses a predefined collection of movies and stores watchlist information locally in a text file. It is designed as a desktop application for movie browsing and personal watchlist management.

The application does not currently use an online movie database or cloud-based storage.

## 9. Future Enhancements

Possible future improvements include:

- Integrating an online movie database API.
- Displaying movie posters, ratings, and descriptions.
- Adding user profiles and personalized watchlists.
- Introducing a database for storing movie and watchlist information.
- Adding more advanced sorting and filtering options.

## 10. Conclusion

CineTrack provides a simple way to browse movies and maintain a personal watchlist through a JavaFX desktop application. It combines a graphical user interface, search and filtering features, watchlist management, and file handling in one application. The project also demonstrates practical applications of Java programming and object-oriented concepts.

---
**Project Name:** CineTrack – Movie Discovery and Watchlist Management System  
**Project Type:** JavaFX Desktop Application

// JavaFX base class used to create a desktop application.
import javafx.application.Application;
// Utility for scheduling UI updates on the JavaFX Application Thread.
import javafx.application.Platform;
// Provides padding and spacing measurements for layouts.
import javafx.geometry.Insets;
// Provides alignment options for interface components.
import javafx.geometry.Pos;
// Represents the visual scene shown inside the application window.
import javafx.scene.Scene;
// Imports JavaFX controls such as buttons, labels, text fields, and dropdowns.
import javafx.scene.control.*;
// Imports layout containers such as VBox, HBox, BorderPane, and FlowPane.
import javafx.scene.layout.*;
// Used to set the font of text elements.
import javafx.scene.text.Font;
// Represents the main application window.
import javafx.stage.Stage;
// Used to handle file input/output errors.
import java.io.IOException;
// Provides methods for reading, writing, and checking files.
import java.nio.file.Files;
// Represents a filesystem path.
import java.nio.file.Path;
// Converts a path string into a Path object.
import java.nio.file.Paths;
// ArrayList is a resizable collection.
import java.util.ArrayList;
// List is the interface used for collections of movies and text lines.
import java.util.List;

// Main JavaFX application class.
public class CineTrack extends Application {

    // Stores the complete predefined movie catalogue.
    private final List<Movie> movies = new ArrayList<>();
    // Stores only movies added to the user's watchlist.
    private final List<Movie> watchlist = new ArrayList<>();

    // Location of the local file used to persist the watchlist.
    private final Path filePath = Paths.get("watchlist.txt");

    // Container where movie cards are displayed.
    private VBox movieArea;
    // Text field used to search by title or genre.
    private TextField searchField;
    // Dropdown used to filter movies by genre.
    private ComboBox<String> genreFilter;
    // Heading that changes according to the selected page.
    private Label sectionTitle;
    // Shows feedback messages after user actions.
    private Label messageLabel;

    // Tracks the active section: Home, Explore, or My Watchlist.
    private String currentPage = "Home";

    // Main background colour for the dark interface.
    private final String background = "#101014";
    // Red accent colour used for branding and buttons.
    private final String red = "#e50914";

    // Data model representing one movie.
    // Movie class
    // Static nested class holding a movie's details and status.
    public static class Movie {
        // Movie title.
        String name;
        // Movie genre or category.
        String genre;
        // Short description displayed on the movie card.
        String description;
        // Background colour used for the poster panel.
        String posterColor;
        // Initial status before the user marks the movie as watched.
        String status = "Want to Watch";

        // Constructor receives the details needed to create a Movie object.
        Movie(String name, String genre,
              String description, String posterColor) {
            // Store the supplied title.
            this.name = name;
            // Store the supplied genre.
            this.genre = genre;
            // Store the supplied description.
            this.description = description;
            // Store the supplied poster colour.
            this.posterColor = posterColor;
        }
    }

    // JavaFX calls start() when launching the application.
    @Override
    // stage is the primary window provided by JavaFX.
    public void start(Stage stage) {

        // Add the predefined sample movies to the catalogue.
        // Sample movie collection
        // Create a Movie object and add it to the full movie list.
        movies.add(new Movie(
            "Interstellar", "Sci-Fi",
            "A journey beyond the stars.", "#263c64"));

        movies.add(new Movie(
            "Inception", "Thriller",
            "Dreams within dreams.", "#65364f"));

        movies.add(new Movie(
            "The Dark Knight", "Action",
            "A hero rises in Gotham.", "#343a40"));

        movies.add(new Movie(
            "The Lion King", "Animation",
            "A journey through the circle of life.", "#76502d"));

        movies.add(new Movie(
            "Avatar", "Adventure",
            "Explore a beautiful alien world.", "#176b75"));

        movies.add(new Movie(
            "The Avengers", "Action",
            "Heroes unite to save the world.", "#784139"));

        // Restore the watchlist saved during an earlier session, if available.
        // Load previously saved movies
        loadWatchlist();

        // BorderPane arranges content in top, bottom, left, right, and centre regions.
        // Main layout
        // Create the root layout for the application.
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + background + ";");

        // Create the top navigation bar.
        // Navigation bar
        // Application logo shown in the navigation bar.
        Label logo = new Label("CINETRACK");

        // Apply the logo's red colour, font size, and bold style.
        logo.setStyle(
            "-fx-text-fill: " + red + ";" +
            "-fx-font-size: 25px;" +
            "-fx-font-weight: bold;"
        );

        // Create consistently styled buttons for the main sections.
        Button homeButton = navButton("Home");
        Button exploreButton = navButton("Explore");
        Button watchlistButton = navButton("My Watchlist");

        // Handle a click on Home.
        homeButton.setOnAction(e -> {
            // Mark Home as the active page.
            currentPage = "Home";
            // Clear the previous search term.
            searchField.clear();
            // Reset the genre dropdown to include all genres.
            genreFilter.setValue("All Genres");
            // Refresh the displayed movie cards.
            showMovies();
        });

        // Handle a click on Explore.
        exploreButton.setOnAction(e -> {
            // Mark Explore as the active page.
            currentPage = "Explore";
            // Clear the previous search term.
            searchField.clear();
            // Reset the genre dropdown.
            genreFilter.setValue("All Genres");
            // Refresh the movie display.
            showMovies();
        });

        // Handle a click on My Watchlist.
        watchlistButton.setOnAction(e -> {
            // Mark the watchlist as the active page.
            currentPage = "My Watchlist";
            // Clear the previous search term.
            searchField.clear();
            // Reset the genre dropdown.
            genreFilter.setValue("All Genres");
            // Refresh the display to show watchlist movies.
            showMovies();
        });

        // Arrange the logo and navigation buttons in a horizontal row.
        HBox navigation = new HBox(
            25, logo, homeButton, exploreButton, watchlistButton
        );

        // Align navigation components to the left.
        navigation.setAlignment(Pos.CENTER_LEFT);
        // Add space around the navigation contents.
        navigation.setPadding(new Insets(20, 30, 20, 30));
        // Set a distinct background colour for the navigation bar.
        navigation.setStyle("-fx-background-color: #18181f;");

        // Place the navigation bar at the top of the main layout.
        root.setTop(navigation);

        // Build the main content area below the navigation bar.
        // Main content
        // VBox stacks its child components vertically with spacing.
        VBox content = new VBox(20);
        content.setPadding(new Insets(25, 35, 30, 35));

        // Hero section introduces the application.
        // Hero section
        // Main promotional heading.
        Label heroTitle =
            new Label("Your Next Favourite Movie Awaits");

        // Allow the heading to wrap on smaller windows.
        heroTitle.setWrapText(true);
        // Set the heading font family and size.
        heroTitle.setFont(Font.font("Arial", 30));

        // Style the heading with white, bold text.
        heroTitle.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;"
        );

        // Supporting text under the main heading.
        Label subtitle = new Label(
            "Discover movies. Save your favourites. Enjoy your watchlist."
        );

        // Allow the subtitle to wrap onto multiple lines.
        subtitle.setWrapText(true);

        // Style the subtitle with light grey text.
        subtitle.setStyle(
            "-fx-text-fill: #bbbbc5;" +
            "-fx-font-size: 14px;"
        );

        // Place the heading and subtitle in a vertical layout.
        VBox hero = new VBox(12, heroTitle, subtitle);

        // Align the hero content to the left.
        hero.setAlignment(Pos.CENTER_LEFT);
        // Add internal spacing around the hero content.
        hero.setPadding(new Insets(25));
        // Set the hero section's minimum height.
        hero.setMinHeight(130);

        // Apply the hero background and rounded corners.
        hero.setStyle(
            "-fx-background-color: #29202b;" +
            "-fx-background-radius: 12;"
        );

        // Create the search field and its appearance.
        // Search field
        // Text field where the user enters a search term.
        searchField = new TextField();

        // Show a hint while the search field is empty.
        searchField.setPromptText(
            "Search movies by title or genre..."
        );

        // Set the preferred height of the search field.
        searchField.setPrefHeight(42);

        // Style the search field for the dark theme.
        searchField.setStyle(
            "-fx-background-color: #24242c;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: #aaaaaa;" +
            "-fx-background-radius: 8;" +
            "-fx-font-size: 14px;"
        );

        // Create the dropdown used to filter movies by genre.
        // Genre filter
        // Initialize the genre dropdown.
        genreFilter = new ComboBox<>();

        // Add all genre choices available in this version.
        genreFilter.getItems().addAll(
            "All Genres",
            "Action",
            "Adventure",
            "Animation",
            "Sci-Fi",
            "Thriller"
        );

        // Show all genres by default.
        genreFilter.setValue("All Genres");
        // Set the dropdown's preferred height.
        genreFilter.setPrefHeight(42);
        // Set the dropdown's preferred width.
        genreFilter.setPrefWidth(145);

        // Apply the dropdown's appearance.
        genreFilter.setStyle(
            "-fx-background-color: #30303a;" +
            "-fx-font-size: 13px;"
        );

        // Customize the text displayed for the selected dropdown value.
        // Make selected genre text readable
        // Create a custom cell for the selected genre.
        genreFilter.setButtonCell(new ListCell<String>() {
            @Override
            // JavaFX calls this when the cell's item changes.
            protected void updateItem(String item, boolean empty) {
                // Perform the standard ListCell update first.
                super.updateItem(item, empty);

                // Use a default label if there is no selected item.
                if (empty || item == null) {
                    setText("All Genres");
                } else {
                    setText(item);
                }

                // Make the selected genre text white.
                setTextFill(javafx.scene.paint.Color.WHITE);
                // Style the selected value's background and text.
                setStyle(
                    "-fx-background-color: #30303a;" +
                    "-fx-text-fill: white;"
                );
            }
        });

        // Style the options shown when the genre dropdown opens.
        // Style dropdown options
        // A cell factory creates the individual dropdown option cells.
        genreFilter.setCellFactory(list -> new ListCell<String>() {
            @Override
            // Update the displayed text for a dropdown option.
            protected void updateItem(String item, boolean empty) {
                // Perform the normal cell update.
                super.updateItem(item, empty);

                // Empty cells should not display text.
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }

                // Use white text for readability on the dark background.
                setTextFill(javafx.scene.paint.Color.WHITE);
                setStyle(
                    "-fx-background-color: #30303a;" +
                    "-fx-text-fill: white;"
                );
            }
        });

        // Refresh the movies when the selected genre changes.
        genreFilter.setOnAction(e -> showMovies());

        // Create the Search button.
        // Search button
        // Button used to apply the search.
        Button searchButton = new Button("Search");

        // Style the Search button with the red accent colour.
        searchButton.setStyle(
            "-fx-background-color: " + red + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 7;" +
            "-fx-padding: 12 22 12 22;" +
            "-fx-cursor: hand;"
        );

        // Clicking Search applies the current search and filter settings.
        searchButton.setOnAction(e -> showMovies());
        // Pressing Enter in the search field also applies the search.
        searchField.setOnAction(e -> showMovies());

        // Place the search field, genre dropdown, and button in one row.
        HBox searchBar = new HBox(
            10, searchField, genreFilter, searchButton
        );

        // Align search controls to the left.
        searchBar.setAlignment(Pos.CENTER_LEFT);
        // Let the search field expand into the available horizontal space.
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // Heading above the displayed movie collection.
        // Section heading
        // Initial heading for the Home page.
        sectionTitle = new Label("Popular Movies");

        // Style the section heading.
        sectionTitle.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 23px;" +
            "-fx-font-weight: bold;"
        );

        // Label used to show success and error messages.
        // Message label
        // Allow longer messages to wrap.
        messageLabel = new Label("");

        messageLabel.setWrapText(true);

        // Style the feedback message.
        messageLabel.setStyle(
            "-fx-text-fill: #75df9b;" +
            "-fx-font-size: 14px;"
        );

        // Container that will hold the movie cards or an empty-state message.
        movieArea = new VBox(18);

        // Add the hero, search controls, heading, feedback, and movie area to the content.
        content.getChildren().addAll(
            hero,
            searchBar,
            sectionTitle,
            messageLabel,
            movieArea
        );

        // Make the main content scrollable when it exceeds the window height.
        // Scrollable content
        // Wrap the content in a ScrollPane.
        ScrollPane scrollPane = new ScrollPane(content);

        // Fit the content to the scroll pane's width.
        scrollPane.setFitToWidth(true);
        // Disable horizontal scrolling.
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // Show vertical scrolling only when required.
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Match the scroll pane's background to the application theme.
        scrollPane.setStyle(
            "-fx-background: " + background + ";" +
            "-fx-background-color: " + background + ";"
        );

        // Place the scrollable content in the centre of the main layout.
        root.setCenter(scrollPane);

        // Display the initial set of movies.
        showMovies();

        // Create the scene with its initial width and height.
        Scene scene = new Scene(root, 1050, 750);

        // Set the title shown in the window's title bar.
        stage.setTitle("CineTrack - Movie Discovery");
        // Prevent the window from becoming too narrow.
        stage.setMinWidth(800);
        // Prevent the window from becoming too short.
        stage.setMinHeight(600);
        // Attach the scene to the main window.
        stage.setScene(scene);
        // Display the window.
        stage.show();

        // After layout, move the scroll position to the top.
        // Start at the top when the application opens
        // A vertical scroll value of zero represents the top.
        Platform.runLater(() -> scrollPane.setVvalue(0));
    }

    // Helper method that creates a styled navigation button.
    // Navigation button styling
    // text is the label shown on the button.
    private Button navButton(String text) {

        // Create a button using the supplied label.
        Button button = new Button(text);

        // Apply the common navigation-button style.
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-cursor: hand;"
        );

        // Return the completed button to the caller.
        return button;
    }

    // Rebuilds the movie area according to the selected page and filters.
    // Display movies
    // Called after navigation, searching, filtering, or watchlist changes.
    private void showMovies() {

        // Remove old cards before displaying updated results.
        movieArea.getChildren().clear();

        // Read the search term, remove surrounding spaces, and convert it to lowercase.
        String query = searchField.getText()
            .trim()
            .toLowerCase();

        // Temporary list containing movies that match the current criteria.
        List<Movie> displayed = new ArrayList<>();

        // The watchlist page displays only saved movies.
        if (currentPage.equals("My Watchlist")) {

            // Update the section heading.
            sectionTitle.setText("My Watchlist");

            // Examine every movie in the watchlist.
            for (Movie movie : watchlist) {
                // Check whether the movie matches the current search and genre.
                if (matches(movie, query)) {
                    // Add matching movies to the temporary display list.
                    displayed.add(movie);
                }
            }

        // Home and Explore browse the complete predefined movie collection.
        } else {

            // Choose the heading based on the active page.
            sectionTitle.setText(
                currentPage.equals("Home")
                    ? "Popular Movies"
                    : "Explore Movies"
            );

            // Examine every movie in the full catalogue.
            for (Movie movie : movies) {
                // Apply the same search and genre matching rules.
                if (matches(movie, query)) {
                    // Add matching movies to the temporary display list.
                    displayed.add(movie);
                }
            }
        }

        // Handle the case where no movies match.
        // Display a message if no movies are found
        if (displayed.isEmpty()) {

            // Show a different message for an empty watchlist and an empty search result.
            Label emptyLabel = new Label(
                currentPage.equals("My Watchlist")
                    ? "Your watchlist is empty. Add a movie to get started!"
                    : "No movies found. Try another search."
            );

            // Allow the message to wrap when necessary.
            emptyLabel.setWrapText(true);

            // Style the empty-state message.
            emptyLabel.setStyle(
                "-fx-text-fill: #bbbbc5;" +
                "-fx-font-size: 15px;"
            );

            // Add the message to the movie area.
            movieArea.getChildren().add(emptyLabel);
            // Stop because there are no cards to display.
            return;
        }

        // FlowPane places cards in rows and wraps them when space runs out.
        FlowPane grid = new FlowPane();

        // Set horizontal spacing between cards.
        grid.setHgap(20);
        // Set vertical spacing between rows.
        grid.setVgap(22);
        // Set the preferred width before wrapping cards to another row.
        grid.setPrefWrapLength(900);

        // Create a card for each matching movie.
        for (Movie movie : displayed) {
            // Add the generated movie card to the grid.
            grid.getChildren().add(createMovieCard(movie));
        }

        // Add the completed grid to the main movie area.
        movieArea.getChildren().add(grid);
    }

    // Checks whether a movie matches the text search and selected genre.
    // Search by movie name or genre and apply genre filter
    // movie is the candidate movie; query is the normalized search text.
    private boolean matches(Movie movie, String query) {

        // Check whether the search term appears in the title or genre.
        boolean matchesSearch =
            movie.name.toLowerCase().contains(query)
            || movie.genre.toLowerCase().contains(query);

        // Check whether the movie matches the selected genre filter.
        boolean matchesGenre =
            genreFilter == null
            || genreFilter.getValue() == null
            || genreFilter.getValue().equals("All Genres")
            || movie.genre.equalsIgnoreCase(genreFilter.getValue());

        // A movie is displayed only if both conditions are satisfied.
        return matchesSearch && matchesGenre;
    }

    // Builds the visual card for one movie and returns it.
    // Create a movie card
    // movie supplies the information displayed in the card.
    private VBox createMovieCard(Movie movie) {

        // Large movie title shown in the poster area.
        Label posterTitle = new Label(movie.name);

        // Allow long titles to wrap.
        posterTitle.setWrapText(true);
        // Centre the poster title.
        posterTitle.setAlignment(Pos.CENTER);
        // Let the title use the available width.
        posterTitle.setMaxWidth(Double.MAX_VALUE);

        // Style the poster title.
        posterTitle.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 21px;" +
            "-fx-font-weight: bold;"
        );

        // Place the title over the poster background.
        StackPane poster = new StackPane(posterTitle);

        // Set the poster's preferred dimensions.
        poster.setPrefSize(205, 145);
        // Keep the poster within the card width.
        poster.setMaxWidth(205);

        // Use the movie's poster colour and round the top corners.
        poster.setStyle(
            "-fx-background-color: " + movie.posterColor + ";" +
            "-fx-background-radius: 9 9 0 0;"
        );

        // Movie title shown in the details section.
        Label name = new Label(movie.name);

        // Allow the title to wrap if needed.
        name.setWrapText(true);

        // Style the movie title.
        name.setStyle(
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;"
        );

        // Display the movie's genre.
        Label genre = new Label(movie.genre);

        // Style the genre label.
        genre.setStyle("-fx-text-fill: #bdbdc8;");

        // Display the movie's current watch status.
        // Display movie status
        // Combine a status label with the movie's current status value.
        Label statusLabel = new Label("Status: " + movie.status);

        statusLabel.setWrapText(true);

        statusLabel.setStyle(
            "-fx-text-fill: #75df9b;" +
            "-fx-font-size: 12px;"
        );

        // Display the short movie description.
        Label description = new Label(movie.description);

        // Keep the description within the card width and allow wrapping.
        description.setWrapText(true);
        description.setMaxWidth(185);

        // Style the description text.
        description.setStyle(
            "-fx-text-fill: #dddddd;" +
            "-fx-font-size: 12px;"
        );

        // Create a button that adds the movie or removes it if already saved.
        // Add or remove from watchlist
        // The button text depends on whether the movie is already in the watchlist.
        Button addButton = new Button(
            watchlist.contains(movie)
                ? "Remove from List"
                : "+ My Watchlist"
        );

        // Make the button use the available card width.
        addButton.setMaxWidth(Double.MAX_VALUE);

        // Style the watchlist button.
        addButton.setStyle(
            "-fx-background-color: " + red + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );

        // Handle clicks on the add/remove button.
        addButton.setOnAction(e -> {

            // Records whether this action adds the movie.
            boolean added;

            // If the movie is already saved, remove it.
            if (watchlist.contains(movie)) {
                // Remove the movie from the watchlist.
                watchlist.remove(movie);
                // Record that the movie was removed.
                added = false;
            // Otherwise, add the movie to the watchlist.
            } else {
                // Add the movie to the watchlist.
                watchlist.add(movie);
                // Record that the movie was added.
                added = true;
            }

            // Save the updated watchlist to the file.
            if (saveWatchlist()) {

                // Refresh the cards so their buttons and contents are up to date.
                showMovies();

                // Display a message describing the successful change.
                messageLabel.setText(
                    added
                        ? movie.name + " added to your watchlist!"
                        : movie.name + " removed from your watchlist!"
                );

            } else {

                // If saving fails, undo the in-memory change.
                // Restore the previous state if saving fails
                // Undo the add operation.
                if (added) {
                    // Remove the movie that was just added.
                    watchlist.remove(movie);
                // Undo the remove operation.
                } else {
                    // Restore the movie to the watchlist.
                    watchlist.add(movie);
                }

                // Refresh the display after restoring the previous state.
                showMovies();

                // Inform the user that saving failed.
                messageLabel.setText(
                    "Could not save changes. Please try again."
                );
            }
        });

        // Button used to mark a saved movie as watched.
        // Mark as watched button
        // Change the button label according to the movie's status.
        Button watchedButton = new Button(
            movie.status.equals("Watched")
                ? "Watched ✓"
                : "Mark as Watched"
        );

        // Make the watched button fill the card width.
        watchedButton.setMaxWidth(Double.MAX_VALUE);

        // Disable the button if the movie is not saved or is already watched.
        watchedButton.setDisable(
            !watchlist.contains(movie)
                || movie.status.equals("Watched")
        );

        // Apply the watched button's visual style.
        watchedButton.setStyle(
            "-fx-background-color: #30303a;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 6;" +
            "-fx-cursor: hand;"
        );

        // Handle clicks on the Mark as Watched button.
        watchedButton.setOnAction(e -> {

            // Keep the previous status in case saving fails.
            String oldStatus = movie.status;
            // Update the movie status.
            movie.status = "Watched";

            // Save the new status to the watchlist file.
            if (saveWatchlist()) {

                // Refresh the card to show the new status.
                showMovies();

                // Confirm the status change to the user.
                messageLabel.setText(
                    movie.name + " marked as watched!"
                );

            // Restore the previous status if saving fails.
            } else {

                // Undo the status change.
                movie.status = oldStatus;

                // Refresh the display after restoring the old status.
                showMovies();

                // Inform the user that the status could not be saved.
                messageLabel.setText(
                    "Could not save the status. Please try again."
                );
            }
        });

        // Stack the movie details and action buttons vertically.
        VBox cardDetails = new VBox(
            9,
            name,
            genre,
            statusLabel,
            description,
            addButton,
            watchedButton
        );

        // Add space inside the details section.
        cardDetails.setPadding(new Insets(12));

        // Combine the poster and details into one complete card.
        VBox card = new VBox(poster, cardDetails);

        // Set the movie card's preferred width.
        card.setPrefWidth(205);
        // Keep the card width fixed for a consistent layout.
        card.setMaxWidth(205);

        // Style the card background, border, and rounded corners.
        card.setStyle(
            "-fx-background-color: #202028;" +
            "-fx-background-radius: 9;" +
            "-fx-border-color: #33333d;" +
            "-fx-border-radius: 9;"
        );

        // Return the finished card so it can be displayed.
        return card;
    }

    // Saves the current watchlist to the local text file.
    // FILE HANDLING: Save the watchlist
    // Returns true on success and false if an I/O error occurs.
    private boolean saveWatchlist() {

        // Each movie will be converted into one line of text.
        List<String> lines = new ArrayList<>();

        // Process each movie currently in the watchlist.
        for (Movie movie : watchlist) {
            // Store title, genre, and status separated by vertical bars.
            lines.add(
                movie.name + "|" + movie.genre + "|" + movie.status
            );
        }

        try {

            // Write the lines to watchlist.txt, replacing its previous contents.
            Files.write(filePath, lines);
            // Report that saving succeeded.
            return true;

        // Handle file-writing errors.
        } catch (IOException e) {

            // Display an error dialog if the file cannot be saved.
            new Alert(
                Alert.AlertType.ERROR,
                "Unable to save the watchlist:\n" + e.getMessage()
            ).showAndWait();

            // Report that saving failed to the caller.
            return false;
        }
    }

    // Loads saved watchlist entries when the application starts.
    // FILE HANDLING: Load the saved watchlist
    // Restores matching movies and their saved statuses.
    private void loadWatchlist() {

        // If the file does not exist yet, there is nothing to load.
        if (!Files.exists(filePath)) {
            return;
        }

        try {

            // Read the file as a list of text lines.
            List<String> lines = Files.readAllLines(filePath);

            // Process each saved entry separately.
            for (String line : lines) {

                // Split each line into up to three fields using the | separator.
                String[] data = line.split("\\|", 3);

                // Accept entries with or without the status field.
                // Accept old and new file formats
                // Skip malformed lines that lack title or genre.
                if (data.length < 2) {
                    continue;
                }

                // Find the matching movie in the predefined catalogue.
                for (Movie movie : movies) {

                    // Compare the saved title without case sensitivity.
                    if (movie.name.equalsIgnoreCase(data[0])
                            && movie.genre.equalsIgnoreCase(data[1])) {

                        // Restore the saved status when it is available.
                        if (data.length >= 3
                                && data[2].equalsIgnoreCase("Watched")) {
                            // Set the movie status to Watched.
                            movie.status = "Watched";
                        } else {
                            // Otherwise use the default status.
                            movie.status = "Want to Watch";
                        }

                        // Avoid adding the same movie twice.
                        if (!watchlist.contains(movie)) {
                            // Add the matched movie to the watchlist.
                            watchlist.add(movie);
                        }

                        // Stop once the matching movie has been found.
                        break;
                    }
                }
            }

        // Handle file-reading errors.
        } catch (IOException e) {

            // Show an error dialog if the watchlist cannot be loaded.
            new Alert(
                Alert.AlertType.ERROR,
                "Unable to load the saved watchlist:\n" + e.getMessage()
            ).showAndWait();
        }
    }

    // Java program entry point; launch() starts the JavaFX lifecycle.
    public static void main(String[] args) {
        // Start JavaFX, which subsequently calls start().
        launch(args);
    }
}

# Car Race Simulation

JavaFX car race made with the Model View Controller pattern.

Author: Justin Tremblay

## What it does

- Shows a slideshow of the 4 cars with a greeting message while music plays.
- After the slideshow, the race screen shows the track, the start line and the finish line.
- Every car is drawn with JavaFX shapes (Polygon body and roof, Rectangle windows, two Circle wheels with a Line, Text number).
- Start, Pause and Reset buttons control the race and a slider changes the simulation speed.
- The first car to cross the finish line wins and the final results show in the bottom right.

## MVC

- Model: Car stores the type, number, position, speed and color of one car. CarRaceModel stores the cars, updates their positions and keeps the finishing order.
- View: CarRace.fxml holds the layout (track, banners, buttons, slider, message panel and slideshow).
- Controller: CarRaceController draws the cars and handles the buttons, the slider, the slideshow and the animations.
- App loads the FXML file and shows the window.

## Resources

- CarRace.fxml: the view
- styles.css: styling of the window
- images/car1.png to car4.png: car pictures for the slideshow
- sounds/race_music.wav: music played during the slideshow

## How to run

The project needs javafx-controls, javafx-fxml and javafx-media in the pom.xml. Run App.java.

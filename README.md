Java OOP Star Trek – GUI Viewer Build

📌 About

This project implements the GUI Viewer layer of a Star Trek–themed Java application built using the Model–View–Controller (MVC) architecture.

Unlike the console-based viewer, this build provides a graphical user interface that presents model data visually while maintaining strict separation from business logic. The repository demonstrates object-oriented design, layered architecture, and test-driven development practices.

🧠 Architecture

This repository represents the View component within an MVC system:

- Model – Manages application state and domain objects

- Controller – Coordinates logic and user actions

- Viewer (this project) – Provides graphical presentation

The GUI viewer interacts with the controller but does not directly modify model logic.

✨ Features

- Graphical User Interface implementation

- Clean separation of presentation and logic

- Modular Java package structure

- Object-oriented design principles

- Automated unit tests

- Easily extendable UI components

🖼 GUI Overview

The GUI provides:

- Visual representation of Star Trek entities

- Structured layout for displaying model information

- Event-driven interaction handling

- Responsive updates based on controller actions

🧪 Testing

This project includes automated tests to verify:

- GUI component behaviour

- Proper interaction between Viewer and Controller

- Stability of display logic

Running Tests

Using an IDE:

- Open the project

- Navigate to the test directory

- Run all tests via the IDE’s test runner

Using command line (example with JUnit):

javac -cp .:junit-platform-console-standalone.jar src/**/*.java test/**/*.java
java -jar junit-platform-console-standalone.jar --class-path . --scan-class-path

(Adjust depending on your JUnit version and environment.)

🚀 How to Run the Application

1. Clone the repository:

git clone https://github.com/Ivan53040/Java-OOP-StarTrek-ViewerBuild.git

2. Open in IntelliJ IDEA, Eclipse, or another Java IDE.

3. Compile and run the main GUI class (e.g., Main, App, or equivalent).

📚 Design Principles Demonstrated

- Model–View–Controller architecture

- Encapsulation and modularity

- Event-driven GUI programming

- Separation of concerns

- Test-supported development

🔧 Future Improvements

- Enhanced GUI styling

- Improved layout responsiveness

- Integration demo with Model and Controller builds

- Expanded integration test coverage

Integration demo with Model and Controller builds

Expanded integration test coverage

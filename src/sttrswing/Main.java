package sttrswing;

import sttrswing.controller.GameController;
import sttrswing.model.Game;
import java.awt.Dimension;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the program.
 */
public class Main {

  /**
   * Main entry point method for the program
   *
   * @param args args given to the program when it is run.
   */
  public static void main(String[] args) {
    Game game = new Game();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        var controller = new GameController(new Dimension(800, 600), game);
        controller.start(game);
      }
    });
  }
}

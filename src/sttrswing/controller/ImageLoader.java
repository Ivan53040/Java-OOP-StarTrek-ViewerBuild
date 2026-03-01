package sttrswing.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * {@link ImageLoader} is responsible for loading a {@link BufferedImage} at given filepath.
 */
public class ImageLoader {

  private boolean success = false;
  private ImageIcon icon;

  /**
   * Attempts to load an image from the given filepath and parse it into a {@link ImageIcon} for
   * general use by our program.
   *
   * @param path path we want to load the image from.
   */
  public ImageLoader(final String path) {
    try {
      BufferedImage img = ImageIO.read(new File(path));
      this.icon = new ImageIcon(img);
      this.success = true;
    } catch (IOException exception) {
      System.out.println(exception.getMessage());
    }
  }

  /**
   * A {@link ImageIcon} made from the target {@link BufferedImage}.
   *
   * @return a {@link ImageIcon} made from the target {@link BufferedImage}.
   */
  public ImageIcon get() {
    if (!this.success) {
      return null;
    }
    return this.icon;
  }

  /**
   * Returns a boolean representation for if the {@link ImageLoader} has succeeded in its task.
   *
   * @return a boolean representation for if the {@link ImageLoader} has succeeded in its task.
   */
  public boolean success() {
    return this.success;
  }
}

package sttrswing.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageLoader {

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
    } catch (IOException exception) {
      System.out.println(exception.getMessage());
    }
  }

  public ImageIcon getIcon() {
    return this.icon;
  }


}

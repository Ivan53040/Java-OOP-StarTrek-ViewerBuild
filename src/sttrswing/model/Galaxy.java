package sttrswing.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the {@link Quadrant}s held within the {@link Galaxy}.
 */
public class Galaxy {

  private List<Quadrant> quadrants;

  /**
   * Constructs a new Galaxy with 64 {@link Quadrant}s.
   */
  public Galaxy() {
    quadrants = new ArrayList<>();
    this.quadrants = this.generateQuadrants();
  }

  /**
   * Constructs a new Galaxy using the passed in quadrants rather than generating its own.
   *
   * @param quadrants quadrants we want the {@link Galaxy} to use.
   */
  public Galaxy(ArrayList<Quadrant> quadrants) {
    this.quadrants = quadrants;
    //JB: we could add a check here to error for duplicate x, ys?
  }

  /**
   * Get a {@link List} of {@link Quadrant}s adjacent to one with the given x,y inclusive of the one
   * at the given coordinates. As an example if you pass in 1,1 it should get the {@link Quadrant}s
   * (if they exist) at: 0,0 - top left corner 1,0 - top center 2,0 - top right 0,1 - center left
   * 1,1 - center 2,1 - center right 0,2 - bottom left 1,2 - bottom center 2,2 - bottom right
   *
   * @param x - x coordinate of the center quadrant for the cluster.
   * @param y - y coordinate of the center quadrant for the cluster.
   * @return a {@link List} of {@link Quadrant}s adjacent to one with the given x,y inclusive of the
   * one at the given coordinates.
   */
  public List<Quadrant> getQuadrantClusterAt(final int x, final int y) {
    ArrayList<Quadrant> cluster = new ArrayList<>();
    Quadrant center = this.quadrantAt(x, y);
    if (center == null) {
      return cluster;
    }
    cluster.add(center);

    final int left = x - 1;
    final int top = y - 1;
    final int bottom = y + 1;
    final int right = x + 1;
    Quadrant q = this.quadrantAt(left, top);
    if (q != null) {
      cluster.add(q);
    }

    q = this.quadrantAt(left, y);
    if (q != null) {
      cluster.add(q);
    }

    q = this.quadrantAt(left, bottom);
    if (q != null) {
      cluster.add(q);
    }

    q = this.quadrantAt(x, top);
    if (q != null) {
      cluster.add(q);
    }

    q = this.quadrantAt(x, bottom);
    if (q != null) {
      cluster.add(q);
    }

    q = this.quadrantAt(right, top);
    if (q != null) {
      cluster.add(q);
    }

    q = this.quadrantAt(right, y);
    if (q != null) {
      cluster.add(q);
    }

    q = this.quadrantAt(right, bottom);
    if (q != null) {
      cluster.add(q);
    }

    return cluster;
  }

  /**
   * Generate 64 {@link Quadrant} each with their own unique {@link XyPair} coordinates that is set
   * up to describe them in an 8*8 grid.
   *
   * @return a {@link List} of {@link Quadrant}s
   */
  public List<Quadrant> generateQuadrants() {
    final int maxRows = 8;
    final int maxCols = 8;
    var quadrants = new ArrayList<Quadrant>();
    for (int row = 0; row < maxRows; row += 1) {
      for (int col = 0; col < maxCols; col += 1) {
        quadrants.add(new Quadrant(row, col));
      }
    }
    return quadrants;
  }

  /**
   * Returns how many {@link Klingon} total are in the {@link Quadrant}s in this {@link Galaxy}.
   *
   * @return how many {@link Klingon} total are in the {@link Quadrant}s in this {@link Galaxy}.
   */
  public int klingonCount() {
    int klingons = 0;
    for (Quadrant q : this.quadrants) {
      klingons += q.klingonCount();
    }
    return klingons;
  }


  /**
   * Returns how many {@link Klingon} total are in the {@link Quadrant}s in this {@link Galaxy}.
   *
   * @return how many {@link Klingon} total are in the {@link Quadrant}s in this {@link Galaxy}.
   */
  public int starbaseCount() {
    int starbases = 0;
    for (Quadrant q : this.quadrants) {
      starbases += q.starbaseCount();
    }
    return starbases;
  }

  /**
   * Get a specific {@link Quadrant} using its x and y coordinate to find it.
   *
   * @param x - horizontal coordinate of the {@link Quadrant} we are trying to find in the
   *          {@link Galaxy}.
   * @param y - vertical coordinate of the {@link Quadrant} we are trying to find in the
   *          {@link Galaxy}.
   * @return {@link Quadrant} - the {@link Quadrant} we are looking for.
   */
  public Quadrant quadrantAt(final int x, final int y) {
    for (Quadrant quadrant : this.quadrants) {
      if (quadrant.getX() == x && quadrant.getY() == y) {
        return quadrant;
      }
    }
    return null;
  }

  /**
   * Call .outOfFocusTick() on all {@link Quadrant} except the ones we are asked to skip
   *
   * @param quadrantsToSkip - array list of one or more quadrants we wish to NOT slow tick.
   * @param game - The game that is to be ticked.
   */
  public void outOfFocusTick(ArrayList<Quadrant> quadrantsToSkip, Game game) {
    for (Quadrant quadrant : this.quadrants) {
      if (!quadrantsToSkip.contains(quadrant)) {
        quadrant.outOfFocusTick(game);
      }
    }
  }

  /**
   * Exports Galaxy details as a savable string.
   *
   * @return a string representation of this galaxy.
   */
  public String export() {
    StringBuilder exportString = new StringBuilder();
    for (Quadrant quadrant : this.quadrants) {
      StringBuilder sb = new StringBuilder();
      sb.append("[q]");
      sb.append(" x:" + quadrant.getX());
      sb.append(" y:" + quadrant.getY());
      sb.append(" s:" + quadrant.symbol());
      sb.append(" |");
      sb.append("\n");
      exportString.append(sb);
    }
    return exportString.toString();
  }

}

import processing.core.PApplet;

public class Sketch extends PApplet {

  // Initialize Snowflake Variables
  int numSnowflakes = 30;
  float[] xSnowflakes;
  float[] ySnowflakes;
  float fltSpeed;

  // Initialize Player Variables
  float fltPlayerX = width / 2;;
  float fltPlayerY;
  int intLives = 3;

  // Snowflake Visibility
  boolean[] snowflakeVisibility; // Array to store visibility of snowflakes

  public void settings() {
    size(400, 400);
  }

  public void setup() {
    background(0);
    xSnowflakes = new float[numSnowflakes];
    ySnowflakes = new float[numSnowflakes];
    snowflakeVisibility = new boolean[numSnowflakes];

    // Draw Snowflakes
    for (int i = 0; i < numSnowflakes; i++) {
      xSnowflakes[i] = random(width);
      ySnowflakes[i] = random(-400, -100);
      fltSpeed = 2;
      snowflakeVisibility[i] = true;
    }

    // Initialize Player Position
    fltPlayerX = width / 2;
    fltPlayerY = height - 50;
  }

  public void draw() {
    background(0);

    // Draw Player
    fill(0, 0, 255);
    ellipse(fltPlayerX, fltPlayerY, 20, 20);

    // Draw Lives Indicator
    fill(255);
    for (int i = 0; i < intLives; i++) {
      rect(width - 30, 20 + i * 30, 20, 20);
    }

    // Update Snowflakes
    for (int i = 0; i < numSnowflakes; i++) {
      if (snowflakeVisibility[i]) {
        // Draw Snowflake
        fill(255);
        noStroke();
        ellipse(xSnowflakes[i], ySnowflakes[i], 20, 20);

        // Move Snwoflake
        ySnowflakes[i] += fltSpeed;

        // Reset Snowflakes
        if (ySnowflakes[i] > height) {
          ySnowflakes[i] = random(-400, -100);
          xSnowflakes[i] = random(width);
          fltSpeed = 2;
        }

        // Check Collision With Player
        float d = dist(xSnowflakes[i], ySnowflakes[i], fltPlayerX, fltPlayerY);
        if (d < 18) { 
          intLives--; 
          snowflakeVisibility[i] = false; 
        }
      }
    }

    // Check for game over
    if (intLives <= 0) {
      gameOver();
    }
  }

  // Player Movement
  public void keyPressed() {
    if (key == 'a' || key == 'A') {
      fltPlayerX -= 5;
    } else if (key == 'd' || key == 'D') {
      fltPlayerX += 5;
    } else if (key == 'w' || key == 'W') {
      fltPlayerY -= 5;
    } else if (key == 's' || key == 'S') {
      fltPlayerY += 5;
    } else if (keyCode == UP) { // Decrease falling speed
      for (int i = 0; i < numSnowflakes; i++) {
        fltSpeed = 1;
      }
    } else if (keyCode == DOWN) { // Increase falling speed
      for (int i = 0; i < numSnowflakes; i++) {
        fltSpeed = 5;
      }
    }
  }

  public void keyReleased(){
    fltSpeed = 2;
  }

  // If Snowflaked Clicked, it Disappears
  public void mousePressed() {
    for (int i = 0; i < numSnowflakes; i++) {
      if (snowflakeVisibility[i]) { 
        float d = dist(mouseX, mouseY, xSnowflakes[i], ySnowflakes[i]);
        if (d < 8) { 
          snowflakeVisibility[i] = false; 
        }
      }
    }
  }

  /**
   * This prints the Game Over screen when the player run out of lives
   * 
   * @author Andrew Su
   */
  void gameOver() {
    background(255); 
    fill(0);
    textSize(32);
    textAlign(CENTER, CENTER);
    text("Game Over", width / 2, height / 2);
    noLoop(); 
  }
}

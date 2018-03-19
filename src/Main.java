import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Created by juan on 3/18/18.
 *
 * Main entry point of the game.
 * Heavily documented for Tom ;).
 */
public class Main extends GameApplication {

    /// Constants
    static final int WIDTH = 600;
    static final int HEIGHT = 400;
    static final String TITLE = "Minigame";
    static final String VERSION = "0.1";
    ///

    // Our player entity object
    private Entity playerEnt;

    /**
     * Set game settings on launch.
     */
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(Main.WIDTH);
        gameSettings.setHeight(Main.HEIGHT);
        gameSettings.setTitle(Main.TITLE);
        gameSettings.setVersion(Main.VERSION);
    }

    /**
     * Initialize game objects.
     */
    @Override
    protected void initGame() {
        // Create a player entity at (100, 100), which is represented as  a
        // blue 25x25px rectangle and attach to the current game world.
        playerEnt = Entities.builder()
                .at(100, 100)
                .viewFromNode(new Rectangle(25, 25, Color.BLUE))
                .buildAndAttach(getGameWorld());
    }

    /**
     * Increment the global game variable 'pixelsMoved' by 5.
     */
    private void incrementPixelsMoved() {
        getGameState().increment("pixelsMoved", 5);
    }

    /**
     * Initialize input methods.
     */
    @Override
    protected void initInput() {
        Input input = getInput(); // Get the reference to the current input

        // Add a new 'action,' called 'move right,' bound to the 'D' key.
        input.addAction(new UserAction("Move Right") {
                            @Override
                            protected void onAction() { // Called whenever the input is invoked
                                playerEnt.translateX(5); // Move right 5px
                                incrementPixelsMoved(); // Update global variable
                            }
                        }, KeyCode.D // The key we want to bind it to, in this case D
        );
        // Do the same, but for other actions as well:
        // Left
        input.addAction(new UserAction("Move Left") {
                            @Override
                            protected void onAction() {
                                playerEnt.translateX(-5); // Move 5px left
                                incrementPixelsMoved(); // Update global variable
                            }
                        }, KeyCode.A // Bind to A
        );
        // Up
        input.addAction(new UserAction("Move Up") {
                            @Override
                            protected void onAction() {
                                playerEnt.translateY(-5); // Move 5px up
                                incrementPixelsMoved(); // Update global variable
                            }
                        }, KeyCode.W
        );
        // Down
        input.addAction(new UserAction("Move Down") {
                            @Override
                            protected void onAction() {
                                playerEnt.translateY(5); // Move 5px down
                                incrementPixelsMoved(); // Update global variable
                            }
                        }, KeyCode.S
        );
    }

    /**
     * Initialize UI, including text and other user interface stuff.
     */
    @Override
    protected void initUI() {
        // Text object will hold, you guessed it... text!
        Text text = new Text();
        // Set the position of the text:
        text.setTranslateX(10);
        text.setTranslateY(Main.HEIGHT - 200);
        // Bind the global variable 'pixelsMoved' to the text node
        text.textProperty().bind(getGameState().intProperty("pixelsMoved")
                .asString());
        // And finally, add the UI stuff to the game scene
        getGameScene().addUINode(text);
    }

    /**
     * Initialize global game variables (this can be useful!). Here we
     * setup some values that we can modify throughout the game, so examples
     * would be such as player health, points, etc.
     * @param vars
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        // For this example, we will store a game variable named 'pixelsMoved'
        // to track the amount of pixels the player has moved
        vars.put("pixelsMoved", 0);
    }

    public static void main(String[] args) {
        // Launch with JavaFX
        launch(args);
    }
}

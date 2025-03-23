package SAE31_2024.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles the action to return to the main frame.
 */
public class ReturnToMainFrameActionHandler implements ActionListener {

    private final GameController gameController;

    /**
     * Constructs a ReturnToMainFrameActionHandler with the specified GameController.
     *
     * @param gameController the game controller to use
     */
    public ReturnToMainFrameActionHandler(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Processes the action event to return to the launch page.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        gameController.returnToLaunchPage();
    }
}
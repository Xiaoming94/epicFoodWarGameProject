package gamecomponent.views;

/**
 * Created by xiaoming on 21/04/14.
 *
 * Interface for creating a new screen in the game.
 * Very simple
 * Each gamescreen is logically a singleton
 * <p>use this class whenever you need too create a new view for this game</p>
 */
public interface IGameScreen {

    /**
     * Method for rendering the current screen
     * Used in Main view to render things.
     */
    public void render();

    /**
     * sets the input handler of LibGDX to the one that belongs to this view
     * Handy method basically, Will be called when the view is switched.
     */
    public void setToCorrectInputProcessor();

    /**
     * This method is called whenever to kill this screen.
     */
    public void kill();

    /**
     * This method is used whenever the screen needs to be resized.
     * It sets the window to a specific size depending on the width and height
     * @param width - The new width
     * @param height - the new height
     */

    public void resize(int width, int height);
}

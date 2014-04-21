package gamecomponent.views;

/**
 * Created by xiaoming on 21/04/14.
 *
 * Interface for creating a new screen in the game.
 * Very simple
 * Each gamescreen is logically a singleton
 * <p>use this class whenever you need too create a new view for this game</p>
 */
public interface GameScreen {

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
}

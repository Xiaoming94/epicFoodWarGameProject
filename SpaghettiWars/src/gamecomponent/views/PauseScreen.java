package gamecomponent.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import gamecomponent.Model;

public class PauseScreen implements IGameScreen{

    private GameScreen currentGameScreen;

    private MainView parent;

    private Stage stage;

    private Table table;

    private SpriteBatch batch;

    private Skin skin;

    //private Model model;

    public PauseScreen(GameScreen currentGameScreen,MainView parent){
        this.currentGameScreen = currentGameScreen;
        this.parent = parent;
        create();
    }

    private void create() {

        batch = new SpriteBatch();
        stage = new Stage();
        skin = new Skin();

        table = new Table(skin);

        Pixmap pixmap = new Pixmap(100,100, Pixmap.Format.RGB888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        BitmapFont bfont = new BitmapFont();
        bfont.scale(1);
        skin.add("default",bfont);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        buttonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        buttonStyle.checked = skin.newDrawable("white", Color.BLUE);
        buttonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        TextButton continueGameButton = new TextButton("Continue",textButtonStyle);
        continueGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.startGame();
            }
        });

        table.add(continueGameButton).row();
        table.setPosition((float) currentGameScreen.getModel().getWidth() / 2, (float) currentGameScreen.getModel().getHeight() / 2);
        table.setSize(1200, 1500);
        stage.addActor(table);


    }

    @Override
    public void render() {

        currentGameScreen.render();
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void setToCorrectInputProcessor() {

    }

    @Override
    public void kill() {

    }

    @Override
    public void resize(int width, int height) {

    }
}

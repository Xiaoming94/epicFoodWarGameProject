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

public class PauseScreen extends GameScreen implements IGameScreen{

	private MainView parent;

    private Stage stage;

    private Table table;

    private SpriteBatch batch;

    private Skin skin;

    //private Model model;

    public PauseScreen(MainView parent, Model model){
    	super(model);
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
        table.setPosition((float) getModel().getWidth() / 2, (float) getModel().getHeight() / 2);
        table.setSize(1200, 1500);
        stage.addActor(table);


    }

    @Override
    public void render() {

        super.render();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        table.setPosition(getCamera().position.x, getCamera().position.y);
        Table.drawDebug(stage);

    }

    @Override
    public void setToCorrectInputProcessor() {
    	Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void kill() {

    }

    @Override
    public void resize(int width, int height) {

    }
}

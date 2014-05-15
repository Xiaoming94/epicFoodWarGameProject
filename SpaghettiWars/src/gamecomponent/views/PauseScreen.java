package gamecomponent.views;

import utilities.TextureHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import gamecomponent.Model;

public class PauseScreen extends GameScreen implements IGameScreen{

	private MainView parent;

    private Stage stage;

    private Skin skin;
    
    private Sprite windowSprite;
    
    TextButton continueGameButton;

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
        
        windowSprite = new Sprite(TextureHandler.getTextureHandler().getTextureByName("escwindow.png"));

        Window.WindowStyle ws = new Window.WindowStyle();
        ws.titleFont = new BitmapFont();
        //ws.background = skin.newDrawable("white", Color.BLACK);


        skin.add("default",ws);

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

        continueGameButton = new TextButton("Continue",textButtonStyle);
        continueGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.startGame();
            }
        });



        stage.addActor(continueGameButton);


    }

    @Override
    public void render() {

        super.render();
        
        super.getBatch().begin();
        super.getBatch().draw(windowSprite, getCamera().position.x-windowSprite.getWidth()/2, getCamera().position.y-windowSprite.getHeight()/2);
        super.getBatch().end();
        
        this.continueGameButton.setPosition((float)super.getModel().getWidth()/2, (float)super.getModel().getHeight()/2);
        
        stage.draw();

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

        stage.getViewport().update(width,height,true);
        super.resize(width,height);

    }
}

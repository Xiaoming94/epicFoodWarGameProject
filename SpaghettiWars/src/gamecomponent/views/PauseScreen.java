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

    TextButton disconnectGameButton;
    
    TextButton exitButton;

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

        //Creating Continue game button

        continueGameButton = new TextButton("Continue",textButtonStyle);
        //continueGameButton.setPosition(150,200);
        continueGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.startGame();
                System.out.println("StartGame");
            }
        });



        stage.addActor(continueGameButton);

        //Creating Disconnect game button

       disconnectGameButton = new TextButton("Disconnect", textButtonStyle);
        disconnectGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Disconnect Game
            	parent.getModel().setControllerRunning(false);
            	parent.getModel().disconnect();
            	parent.disconnect();
            	System.out.println ("Game Disconnected");
            }
        });
        
        stage.addActor(disconnectGameButton);
        
      //Creating Disconnect game button

        exitButton = new TextButton("Exit", textButtonStyle);
         exitButton.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent event, Actor actor) {
                 System.exit(0);
             }
         });

         stage.addActor(exitButton);
        
        this.continueGameButton.setPosition(stage.getViewport().getViewportWidth()/2 - 58,stage.getViewport().getViewportHeight()/2 + 70);
        this.disconnectGameButton.setPosition(stage.getViewport().getViewportWidth()/2 - 70,stage.getViewport().getViewportHeight()/2 - 60);
        this.exitButton.setPosition(stage.getViewport().getViewportWidth()/2 - 52,stage.getViewport().getViewportHeight()/2 - 190);


    }

    @Override
    public void render() {

        super.render();
        
        super.getBatch().begin();
        super.getBatch().draw(windowSprite, getCamera().position.x - windowSprite.getWidth() / 2, getCamera().position.y - windowSprite.getHeight() / 2);
        super.getBatch().end();

        stage.draw();

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

        stage.getViewport().update(width,height,true);
        super.resize(width,height);

    }
}

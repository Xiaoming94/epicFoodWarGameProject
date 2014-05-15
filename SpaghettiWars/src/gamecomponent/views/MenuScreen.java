package gamecomponent.views;

import java.io.IOException;

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
import networking.NetworkUtils;
import utilities.IPInputDialog;

/**
 * Created by xiaoming on 21/04/14.
 */
public class MenuScreen implements IGameScreen {

    private Skin skin;
    private Stage stage;

    private MainView parent;

    private Model model;
    
    TextButton joinGameButton;

    public MenuScreen(){
        this(null,null);
    }
    public MenuScreen(Model model, MainView parent){
        this.model = model;
        this.parent = parent;

        create();
    }

    private void create() {
        stage = new Stage();

        skin = new Skin();

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

        joinGameButton = new TextButton("join game",textButtonStyle);
        joinGameButton.setPosition(100, 150);




        //textButton.add
        stage.addActor(joinGameButton);

        joinGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	
                Gdx.input.getTextInput(new IPInputDialog(model),"Input Host IP","Localhost");
            }
        });

        TextButton hostGameButton = new TextButton("Host Game",textButtonStyle);

        hostGameButton.setPosition(300,150 );

        stage.addActor(hostGameButton);
        
        hostGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
                try {
					NetworkUtils.createServer(model);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                parent.startGame();
			}
        	
        });
        
        TextButton exitButton = new TextButton("Exit",textButtonStyle);

        exitButton.setPosition(500,150 );

        stage.addActor(exitButton);
        
        exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.exit(1);
			}
        	
        });

    }

    @Override
    public void render() {
    	
    	if(model.isGameActive())
    		parent.startGame();

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        Table.drawDebug(stage);

    }

    @Override
    public void setToCorrectInputProcessor() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void kill() {

        stage.dispose();
        skin.dispose();

    }

    @Override
    public void resize(int width, int height) {
    	this.stage.getViewport().update(width, height, true);
        model.setViewSize(width, height);

    }
}

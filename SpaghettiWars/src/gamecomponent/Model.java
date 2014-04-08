package gamecomponent;

import java.util.ArrayList;

import utilities.NameTexture;
import utilities.Position;
import utilities.Vector;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Entity;
import entities.Meatball;
import entities.Player;

public class Model {
	
	private boolean upKeyPressed,downKeyPressed,leftKeyPressed,rightKeyPressed;
	ArrayList<Entity> entities;
	Player player;
	
	ArrayList<NameTexture> textures;
	
	int width, height;
	
	public Model (){
		entities = new ArrayList<Entity>();
		
		textures = new ArrayList<NameTexture>();
	}
	
	public ArrayList<Entity> getEntitys(){
		return entities;
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void createPlayer(){
		player = new Player("Sir Eatalot", 5, 5, new Sprite(getTextureByName("ful.png")), 10);
		player.getSprite().setOriginCenter();
	}
	
	public void setTextureList(ArrayList<NameTexture> l){
		textures = l;
	}
	
	public NameTexture getTextureByName(String name){
		for(NameTexture e : textures)
			if(e.getName().equals(name))
				return e;
		return null;
	}
	
	public void checkPressedKey (int keyCode){
		switch (keyCode){
		case Keys.W: 
			upKeyPressed = true;
			break;
		case Keys.A:
			leftKeyPressed = true;
			break;
		case Keys.S:
			downKeyPressed = true;
			break;
		case Keys.D:
			rightKeyPressed = true;
			break;
		default:
			return;
		}
		
		updatePlayerMovingDirection();
		
	}
	
	public void checkReleasedKey (int keyCode){
		switch (keyCode){
		case Keys.W: 
			upKeyPressed = false;
			break;
		case Keys.A:
			leftKeyPressed = false;
			break;
		case Keys.S:
			downKeyPressed = false;
			break;
		case Keys.D:
			rightKeyPressed = false;
			break;
		default:
			return;
		}
		
		updatePlayerMovingDirection();
	}

	private void updatePlayerMovingDirection() {
		if(upKeyPressed && !downKeyPressed){
			if(leftKeyPressed && !rightKeyPressed){
				player.getVector().setVectorByDegree(player.getSpeed(), 135);
			}else if(rightKeyPressed && !leftKeyPressed){
				player.getVector().setVectorByDegree(player.getSpeed(), 45);
			}else{
				player.setVector(0, 1*player.getSpeed());
			}
		}
		else if(downKeyPressed && !upKeyPressed){
			if(leftKeyPressed && !rightKeyPressed){
				player.getVector().setVectorByDegree(player.getSpeed(), 225);
			}else if(rightKeyPressed && !leftKeyPressed){
				player.getVector().setVectorByDegree(player.getSpeed(), 315);
			}else{
				player.setVector(0, (-1)*player.getSpeed());
			}
		}else if(rightKeyPressed && !leftKeyPressed){
			player.setVector(player.getSpeed(), 0);
		}else if(leftKeyPressed && !rightKeyPressed){
			player.setVector((-1)*player.getSpeed(), 0);
		}else{
			player.setVector(0, 0);
		}
	}
	
	public void mouseButtonPressed(int x, int y, int mouseButton){
		if (mouseButton == Buttons.LEFT){
			shoot(x+this.player.getX()-this.width/2, this.height/2+this.player.getY()-y);
		}
	}

	private void shoot(double x, double y) {
		Meatball mb = new Meatball(player.getX(), player.getY(), new Vector(0,0), new Sprite(this.getTextureByName("Kottbulle.png")));
		mb.setVector(new Position(x,y));
		this.addEntity(mb);
		
	}
	
	public void setViewSize(int width, int height){
		this.width = width;
		this.height = height;
	}

	public void mouseMoved(int mouse1, int mouse2) {

		//Borde gå att lösa bättre möjligvis
		
		double playerX = mouse1+this.player.getX()-this.width/2;
		double playerY =  this.height/2+this.player.getY()-mouse2;
		
		double rot = Math.atan(playerX/playerY);

		System.out.println(Math.toDegrees(rot));
		if(playerY > 0){
			player.getSprite().setRotation((float) (360-Math.toDegrees(rot)));
		}else{
			player.getSprite().setRotation((float) (180-Math.toDegrees(rot)));
		}
	}

}

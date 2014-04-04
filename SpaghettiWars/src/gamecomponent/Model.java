package gamecomponent;

import java.util.ArrayList;

import utilities.NameTexture;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.Entity;
import entities.Player;

public class Model {
	
	private boolean upKeyPressed,downKeyPressed,leftKeyPressed,rightKeyPressed;
	ArrayList<Entity> entities;
	Player player;
	
	ArrayList<NameTexture> textures;
	
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
	
//	public void getDirection(){
//		currentDirection = Direction.STAY;
//
//		if(up && !down){
//			if(left && !right){
//				currentDirection = Direction.NORTHWEST;
//			}else if(right && !left){
//				currentDirection = Direction.NORTHEAST;
//			}else{
//				currentDirection = Direction.NORTH;
//			}
//		}
//		else if(down && !up){
//			if(left && !right){
//				currentDirection = Direction.SOUTHWEST;
//			}else if(right && !left){
//				currentDirection = Direction.SOUTHEAST;
//			}else{
//				currentDirection = Direction.SOUTH;
//			}
//		}else if(right && !left){
//			currentDirection = Direction.EAST;
//		}else if(left && !right){
//			currentDirection = Direction.WEST;
//		}
		
		//return currentDirection;

}

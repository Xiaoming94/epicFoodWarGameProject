package gamecomponent;

import java.util.ArrayList;

import entities.Entity;

public class ClientModel {
	
	ArrayList<Entity> entities;
	
	public ClientModel (){
		entities = new ArrayList<Entity>();
	}
	
	public ArrayList<Entity> getEntitys(){
		return entities;
	}

}

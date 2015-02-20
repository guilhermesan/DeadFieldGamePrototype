package com.trabalhoFinal.javaAvancado.deadfield;

import java.util.ArrayList;

public class Player {
	
	public ArrayList<Avatar> avatars;
	Avatar selectedAvatar;
	
	public Player(int life, int mana) {
		super();
		this.life = life;
		this.mana = mana;
		avatars = new ArrayList<Avatar>();
	}
	
	public void atacked(int strength){
			this.life -= strength;
	}
	
	public boolean getDead(){
		return (life <= 0);
	}
	
	private int life;
	private int mana;
	
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getMana() {
		return mana;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}
	
	

}

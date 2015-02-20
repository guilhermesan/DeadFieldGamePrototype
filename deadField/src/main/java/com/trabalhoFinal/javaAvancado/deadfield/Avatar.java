package com.trabalhoFinal.javaAvancado.deadfield;

import android.graphics.Bitmap;
import android.util.Pair;

import com.gdacarv.engine.androidgame.Animation;
import com.gdacarv.engine.androidgame.Sprite;

public class Avatar extends Sprite {
	
	public int speedX, speedY;
	public static final int STATUS_STOP = 0;
	public static final int STATUS_ATACK = 1;
	public static final int STATUS_GUARDIAN = 2;
	public static final int STATUS_FINDING = 3;
	public static final int STATUS_FOLLOWING = 4;
	private Pair<Integer, Integer> rigth;
	private Pair<Integer, Integer> left;
	private Pair<Integer, Integer> up;
	private Pair<Integer, Integer> down;
	private float stopx,stopy;
	private int speed,status;
	protected int life,defence,strength;
	private Avatar avatar;//avatar que esta sendo seguido
	protected int manaCost;
	
	
	
	public int getManaCost() {
		return manaCost;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public Boolean getDead(){
		return (life <= 0);
	}
	
	public void atack(Avatar avatar){
		this.status = STATUS_ATACK;
		avatar.atacked(strength);
		if (avatar.getDead()){
			this.status = STATUS_FINDING;
			avatar = null;
		}
	}
	
	public void goTo(Avatar avatar){
		this.avatar = avatar;
		
	}

	public void setAnimationRigth(int frameStart , int frameEnd){
		rigth = new Pair<Integer, Integer>(frameStart, frameEnd);
	}
	
	public void setAnimationLeft(int frameStart , int frameEnd){
		left = new Pair<Integer, Integer>(frameStart, frameEnd);
	}

	public void setAnimationUp(int frameStart , int frameEnd){
		up = new Pair<Integer, Integer>(frameStart, frameEnd);
	}
	
	public void setAnimationDown(int frameStart , int frameEnd){
		down = new Pair<Integer, Integer>(frameStart, frameEnd);
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public void goRigth(float pos){
		stopx = pos;
		speedX = speed;
		this.setFirstFrame(rigth.first);
		this.setLastFrame(rigth.second);
		setAnimation(Animation.GO);
	}
	
	public void goLeft(float pos){
		stopx = pos;
		speedX = -speed;
		
		this.setFirstFrame(left.first);
		this.setLastFrame(left.second);
		setAnimation(Animation.GO);
	}
	
	public void goUp(float pos){
		stopy = pos;
		
		speedY = -speed;
		this.setFirstFrame(up.first);
		this.setLastFrame(up.second);
		setAnimation(Animation.GO);
	}
	
	public void goDown(float pos){
		stopy = pos;
		
		speedY = speed;
		this.setFirstFrame(down.first);
		this.setLastFrame(down.second);
		setAnimation(Animation.GO);
	}
	
	public Avatar(int x, int y, Bitmap bmp, int bmp_rows, int bmp_columns,int speed) {
		super(bmp, bmp_rows, bmp_columns);
		//setAnimation(Animation.GO);
        this.speed = speed;
		this.x = x;
		this.y = y;
		this.status = this.STATUS_FINDING;
	}

	@Override
	public void update() {
		super.update();
		int auxX, auxY;
		auxX = x;
		auxY = y;
//		if (this.status == STATUS_FOLLOWING){
		try{
			if (avatar.getDead())
				avatar = null;
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		if (avatar != null){
			
			if (this.collides(avatar.x, avatar.y)){
					if ((this.status == STATUS_FOLLOWING)||(this.status == STATUS_ATACK))
						this.atack(avatar);
			}else if (this.status == STATUS_ATACK){
				this.status = STATUS_FOLLOWING;
				
			}
			if (this.status == STATUS_FOLLOWING){
				if (avatar.x < this.x)
					this.goLeft(avatar.x);
				else
					this.goRigth(avatar.x);
				if (avatar.y < this.y)
					this.goUp(avatar.y);
				else
					this.goDown(avatar.y);
			}
		}else{
			this.status = STATUS_FINDING;
		}
		if (speedX > 0){
			if (x <= stopx){
				this.setFirstFrame(rigth.first);
				this.setLastFrame(rigth.second);
				x += speedX;
			}
		}else{
			if (x >= stopx){
				this.setFirstFrame(left.first);
				this.setLastFrame(left.second);
				x += speedX;
			}
		}
		
		if (speedY > 0){
			if (y <= stopy){
				y += speedY;
				this.setFirstFrame(down.first);
				this.setLastFrame(down.second);
			}
		}else{
			if (y >= stopy){
				y += speedY;
				this.setFirstFrame(up.first);
				this.setLastFrame(up.second);
			}
		}
		if ((auxX == x)&&(auxY == y))
			setAnimation(Animation.STOP);
		
		
		
	}
	
	public void atacked(int strength){
		this.life -= (strength-this.defence);
	}

	@Override
	public boolean collides(int testX, int testY) {
		// TODO Auto-generated method stub
		return super.collides(testX, testY);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
	
	
}

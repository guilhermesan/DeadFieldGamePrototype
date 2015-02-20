package com.trabalhoFinal.javaAvancado.deadfield;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import com.gdacarv.engine.androidgame.GameView;
import com.gdacarv.engine.androidgame.Sprite;



public class MainGameView extends GameView {
	
	protected int level = 1, score = 0;
	
	
	private Paint paintText;
	protected ArrayList<Avatar> avatars;
	protected ArrayList<Avatar> oponentes;
	protected Sprite base,baseInimiga;
	protected Background background;
	protected Sprite nextLevelSprite;
	Player jogador,cpu;

	float scoreX, scoreY;
	
	public MainGameView(Context context) {
		super(context,false);
		
	}
	
	public boolean calculaSelecao(float toqueX,float toqueY,float x, float y){
		if (
				(( toqueX >= (x-30) ) && (toqueX <= (x+30)))
				&&
				(( toqueY >= (y-30) ) && (toqueY <= (y+30)))
			)
			return true;
		return false;
	}

	@Override
	public void TouchEvents(MotionEvent event) {
		
		if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){
				boolean selecionou = false;
				for (int i =0; i<avatars.size();i++){
					if (calculaSelecao(event.getX() , event.getY() , avatars.get(i).x,avatars.get(i).y)){
						jogador.selectedAvatar = avatars.get(i);
						selecionou=true;
						break;
					}
				}
				
				
				if (selecionou)
					return;
				if (jogador.selectedAvatar == null)
					return;
				
				for (int i =0; i<oponentes.size();i++){
					if (calculaSelecao(event.getX() , event.getY() , oponentes.get(i).x,oponentes.get(i).y)){
						jogador.selectedAvatar.goTo(oponentes.get(i));
						jogador.selectedAvatar.setStatus(Avatar.STATUS_FOLLOWING);
						break;
					}
					jogador.selectedAvatar.setStatus(Avatar.STATUS_GUARDIAN);
				}
				
				if (jogador.selectedAvatar.getStatus() != Avatar.STATUS_FOLLOWING){
					if (event.getY() > jogador.selectedAvatar.y){
						 jogador.selectedAvatar.goDown(event.getY());
					}else if (event.getY() <  jogador.selectedAvatar.y){
						 jogador.selectedAvatar.goUp(event.getY());
					}
					
					if (event.getX() >  jogador.selectedAvatar.x){
						 jogador.selectedAvatar.goRigth(event.getX());
					}else if (event.getX() <  jogador.selectedAvatar.x){
						 jogador.selectedAvatar.goLeft(event.getX());
					}
				}
			
		}
		
	
	}

	public void addAvatar(Player jogador,Avatar avatar){
		if (jogador.getMana() >= avatar.getManaCost()){
			jogador.setMana(jogador.getMana()-avatar.getManaCost());
			synchronized (this.getHolder()) {
				jogador.avatars.add(avatar);
				mSprites.add(avatar);
				jogador.selectedAvatar = avatar;
	        }
		}
		
	}
	
	@Override
	protected void onLoad() {
		paintText = new Paint();
		paintText.setColor(Color.WHITE);
		paintText.setTextSize(25);
		
		jogador = new Player(1000, 5000);
		
		cpu = new Player(500, 500);
		Resources res = getResources();
		background = new Background(getWidth(), getHeight(), BitmapFactory.decodeResource(res, R.drawable.grama));
		mSprites.add(background);
		
		avatars = jogador.avatars;
		oponentes = cpu.avatars;
		
		
		base = new Sprite(BitmapFactory.decodeResource(res, R.drawable.base_inimiga), 1,1);
		baseInimiga = new Sprite(BitmapFactory.decodeResource(res, R.drawable.base), 1,1);
		baseInimiga.x = this.getWidth()-64;
		mSprites.add(base);
		mSprites.add(baseInimiga);

		
	
	}
	
	@Override
	public void update() {
		super.update();
		if (cpu.getDead())
			return;
		if (jogador.getDead())
			return;
		for (int i=0;i<avatars.size();i++){
			if (avatars.get(i).x >= (getWidth()-64))
				cpu.atacked(avatars.get(i).strength);
		}
			
		try{
			Resources res = getResources();
			Bitmap bitmapKnight = BitmapFactory.decodeResource(res, R.drawable.death);
			Guerreiro avatar = new Guerreiro(this.getWidth()-64, (new Random()).nextInt(this.getHeight()-10), res, bitmapKnight);
			avatar.setSpeed(0);
			addAvatar(cpu,avatar);
		for (int i = 0;i<avatars.size();i++){
			if (avatars.get(i).getDead()){
				mSprites.remove(avatars.get(i));
				avatars.remove(i);
			}
			for (int j = 0;j<oponentes.size();j++){
				if (oponentes.get(j).getDead()){
					mSprites.remove(oponentes.get(j));
					oponentes.remove(j);
				}else{
					if (oponentes.get(j).collides(avatars.get(i).x, avatars.get(i).y)){
						oponentes.get(j).atack(avatars.get(i));
						avatars.get(i).atack(oponentes.get(j));
					}else{
						int aux = (new Random()).nextInt(6);
						if (aux <= 2)
							oponentes.get(j).setSpeed((new Random()).nextInt(6));
						else
							oponentes.get(j).setSpeed(0);
					}
				}
					
			}
		}
		
		if (avatars.size() == 0){
			for (int i = 0; i<oponentes.size();i++){
					if (oponentes.get(i).x > 64)
						oponentes.get(i).goLeft(64f);
					else	
						jogador.atacked(oponentes.get(i).strength);
					
			}
			
		}else{
			for (int i = 0; i<oponentes.size();i++){
				int aux = (new Random()).nextInt(avatars.size());
					try{
						if ((oponentes.get(i).getStatus() == Avatar.STATUS_FINDING)&&(!avatars.get(aux).getDead())){
							oponentes.get(i).goTo(avatars.get(aux));
							oponentes.get(i).setStatus(Avatar.STATUS_FOLLOWING);
						}
						
					}catch (Exception e) {
						// TODO: handle exception
					}
			
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int life = jogador.getLife();
		for (int i = 0;i<avatars.size();i++){
			life += avatars.get(i).life;
		}
		if (jogador.getDead())
			canvas.drawText("Game Over", 65, 30, paintText);
		else if (cpu.getDead())
			canvas.drawText("You Win", 65, 30, paintText);
		else
			canvas.drawText("Life:"+life+" " + "mana: "+jogador.getMana(), 65, 30, paintText);
	
	}
	
	
}

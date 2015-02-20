package com.trabalhoFinal.javaAvancado.deadfield;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.gdacarv.engine.androidgame.Sprite;

public class Background extends Sprite {
	
	private Bitmap mBitmap;

	public Background(int stageWidth, int stageHeigth, Bitmap bmp) {
		super(bmp);
		mBitmap = Bitmap.createBitmap(stageWidth, stageHeigth, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		
		Rect source = new Rect(0, 0, 32, 32);
		Rect destiny = new Rect();
		for(int i = 0; i < canvas.getWidth(); i += 32){
			destiny.right = (destiny.left = i) + 32;
			for(int j = 0; j < canvas.getHeight(); j += 32){
				destiny.bottom = (destiny.top = j) + 32;
				canvas.drawBitmap(bmp, source, destiny, null);
			}
		}
		
	
	}

	@Override
	public void onDraw(Canvas canvas, int cameraX, int cameraY) {
		// TODO Auto-generated method stub
		
		canvas.drawBitmap(mBitmap, 0, 0, null);
	}
	
	

}

package com.trabalhoFinal.javaAvancado.deadfield;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Guerreiro extends Avatar {

	
	public Guerreiro(int x, int y, Bitmap bmp, int bmp_rows, int bmp_columns,
			int speed) {
		super(x, y, bmp, bmp_rows, bmp_columns, speed);
		this.setAnimationRigth(8,11);
		this.setAnimationUp(12, 15);
		this.setAnimationDown(0, 3);
		this.setAnimationLeft(4, 7);
	    this.life = 1000;
	    this.strength = 1;
	    this.manaCost = 100;
		// TODO Auto-generated constructor stub
	}

	public Guerreiro(int x, int y,Resources res,Bitmap bmp) {
		super(x, y, bmp, 4, 4, 3);
		this.setAnimationRigth(8,11);
		this.setAnimationUp(12, 15);
		this.setAnimationDown(0, 3);
		this.setAnimationLeft(4, 7);
		this.life = 1000;
	    this.strength = 10;
	    this.manaCost = 100;
	}

}

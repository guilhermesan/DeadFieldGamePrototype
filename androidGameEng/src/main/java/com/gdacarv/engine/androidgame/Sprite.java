package com.gdacarv.engine.androidgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {

	
	private Animation animation = Animation.GOBACK;
	
    public int x = 0, y = 0; 
    protected Bitmap mBitmap;
    public boolean visible = true;
    
    private final int BMP_ROWS;
    protected final int BMP_COLUMNS;
    
    protected float currentFrame = 0;
    public int width,height;
    private int firstFrame = 0, lastFrame = 1;

	private OnAnimationEndListener endAnimationListener;
    
    protected float animationSpeed = 1f;
    
    private boolean animationDirectionControl = false;
    public Paint mPaint;
    
    public Sprite(Bitmap bmp) {
        this(bmp, 1, 1, 0, 0);
    }
    
    public Sprite(int x, int y, Bitmap bmp) {
        this(bmp, 1, 1, x, y);
    }
   
    public Sprite(Bitmap bmp, int bmp_rows, int bmp_columns) {
          this(bmp, bmp_rows, bmp_columns, 0, 0);
    }
    
    public Sprite(Bitmap bmp, int bmp_rows, int bmp_columns, int x, int y) {
        this.mBitmap = bmp;
        this.BMP_ROWS = bmp_rows;
        this.BMP_COLUMNS = bmp_columns;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        lastFrame = BMP_COLUMNS*BMP_ROWS;
        if(getFrameCount() == 1)
      	  animation = Animation.STOP;
        this.x = x;
        this.y = y;
  }

    public void update() {
    	switch (animation) {
		case GO:
			currentFrame = ((currentFrame+animationSpeed-firstFrame) % (lastFrame-firstFrame)) + firstFrame;
			break;
		case GOBACK:
			if(!animationDirectionControl && currentFrame+animationSpeed >= lastFrame)
				animationDirectionControl = true;
			else if(animationDirectionControl && currentFrame-animationSpeed < firstFrame)
				animationDirectionControl = false;
			currentFrame = currentFrame+(animationDirectionControl ? -animationSpeed : animationSpeed);
			break;
		case JUSTGO:
			if(currentFrame+animationSpeed < lastFrame)
				currentFrame += animationSpeed;
			else{
				setAnimation(Animation.STOP);
				consumeEndAnimationListener();
			}
			break;
		case GO_INVERSE:
			currentFrame = ((currentFrame-animationSpeed+lastFrame-2*firstFrame) % (lastFrame-firstFrame)) + firstFrame;
			break;
		case JUSTGO_INVERSE:
			if(currentFrame-animationSpeed >= firstFrame)
				currentFrame -= animationSpeed;
			else{
				setAnimation(Animation.STOP);
				consumeEndAnimationListener();
			}
			break;
		}
    }

	private void consumeEndAnimationListener() {
		if(endAnimationListener != null){
			OnAnimationEndListener listener = endAnimationListener;
			endAnimationListener = null;
			listener.OnAnimationEnd();
		}
	}
   
    public void onDraw(Canvas canvas, int cameraX, int cameraY) {
    	if(visible){
	        int endX = x - cameraX,
	        	endY = y - cameraY;
	    	if(endX + width > 0 && endX < canvas.getWidth() && endY + height > 0 && endY < canvas.getHeight()){
	    		canvas.drawBitmap(mBitmap, 
		        		getFrameRect(), 
		        		new Rect(endX, endY, endX + width, endY + height), 
		        		mPaint);
	    	}
    	}
    }
    
    protected Rect getFrameRect(){
    	int srcX = (((int)currentFrame) % BMP_COLUMNS) * width,
    	srcY = (((int)currentFrame) / BMP_COLUMNS) * height;
    	return new Rect(srcX, srcY, srcX + width, srcY + height);
    }
    
    public void setFirstFrame(int frame){
    	firstFrame = frame;
    	if(firstFrame >= lastFrame){
    		lastFrame = firstFrame + 1;
    		currentFrame = firstFrame;
    		animationDirectionControl = false;
    	}else
    	if(currentFrame < firstFrame){
    		currentFrame = firstFrame;
    		animationDirectionControl = false;
    	}
    }
    
    public void setLastFrame(int frame){
    	lastFrame = frame;
    	if(lastFrame <= firstFrame){
    		firstFrame = lastFrame - 1;
			currentFrame = firstFrame;
			animationDirectionControl = false;
    	}else
    	if(currentFrame > frame){
    		currentFrame = firstFrame;
    		animationDirectionControl = false;
    	}
    }
    
    public int getFrameCount(){
    	return BMP_COLUMNS*BMP_ROWS;
    }
    
    public void setCurrentFrame(int frame){
    	currentFrame = frame;
    	firstFrame = frame;
    	lastFrame = frame+1;
    }
    
    public boolean setAnimation(int frame, int iframe, int lframe, Animation type){
    	if(frame < iframe || frame > lframe || iframe >= lframe)
    		return false;
    	currentFrame = frame;
    	firstFrame = iframe;
    	lastFrame = lframe;
    	//if(getFrameCount() > 1)
    		animation = type;
    	return true;
    }
    
    public boolean setAnimation(int frame, int iframe, int lframe, Animation type, OnAnimationEndListener endAnimation){
    	boolean result = setAnimation(frame, iframe, lframe, type);
		if(result){
			endAnimationListener = endAnimation;
		}
    	return result;
    }
	
	public boolean setAnimation(int frame, int iframe, int lframe, Animation type, float speed) {
		boolean result = setAnimation(frame, iframe, lframe, type);
		if(result)
			animationSpeed = speed;
		return result;
	}
	
	public boolean setAnimation(int frame, int iframe, int lframe, Animation type, float speed, OnAnimationEndListener endAnimation){
		boolean result = setAnimation(frame, iframe, lframe, type, speed);
		if(result){
			this.endAnimationListener = endAnimation;
		}
		return result;
	}
    
    public boolean setAnimation(Animation type){
    	if(getFrameCount() > 1){
    		animation = type;
    		return true;
    	}
		else
			return false;
    			
    }
    
    public boolean collides(int testX, int testY){
    	return testX >= x-5 && testY >= y-5 && testX <= x+width+5 && testY <= y+height+5;
    }
    
    public interface OnAnimationEndListener{
    	public void OnAnimationEnd();
    }

    public int getFirstFrame() {
		return firstFrame;
	}

	public int getLastFrame() {
		return lastFrame;
	}

	public void posUpdate() {
		
	}
}  
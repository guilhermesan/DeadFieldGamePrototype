package com.trabalhoFinal.javaAvancado.deadfield;

import com.actionbarsherlock.app.SherlockActivity;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;


import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


public class Principal extends SherlockActivity {

	private  MainGameView gv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setTheme(R.style.Theme_Sherlock_Light);
        super.onCreate(savedInstanceState);
        this.setTitle("");
        gv = new MainGameView(this);
        setContentView(gv);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//return super.onCreateOptionsMenu(menu);
		 
		  SubMenu sub = menu.addSubMenu("Criar");
		  sub.add("Guerreiro");
		  sub.add("Mago");
		  sub.add("Arqueiro");
		  
		  MenuItem subMenu1Item = sub.getItem();
	        subMenu1Item.setIcon(R.drawable.gerreiro);
	        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		  

		 sub.getItem(0).setOnMenuItemClickListener(new OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					// TODO Auto-generated method stub
					Resources res = gv.getResources();
					Bitmap bitmapKnight = BitmapFactory.decodeResource(res, R.drawable.gerreiro);
					Guerreiro avatar = new Guerreiro(64, 64, res, bitmapKnight);
					gv.addAvatar(gv.jogador,avatar);
					return true;
				}
			});
      return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		newConfig.orientation = Configuration.ORIENTATION_LANDSCAPE;
		super.onConfigurationChanged(newConfig);
	}
}



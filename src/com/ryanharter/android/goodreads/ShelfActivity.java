package com.ryanharter.android.goodreads;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;

import com.darvds.ribbonmenu.RibbonMenuView;
import com.darvds.ribbonmenu.iRibbonMenuCallback;

public class ShelfActivity extends Activity implements iRibbonMenuCallback
{
	private RibbonMenuView rbmView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		rbmView = (RibbonMenuView) findViewById(R.id.ribbonMenuView);
		rbmView.setMenuClickCallback(this);
		rbmView.setMenuItems(R.menu.ribbon_menu);

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
			{
			case android.R.id.home:
				rbmView.toggleMenu();
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
	}

	@Override
	public void RibbonMenuItemClick(int itemId) {
			
			
			
			
	}
}

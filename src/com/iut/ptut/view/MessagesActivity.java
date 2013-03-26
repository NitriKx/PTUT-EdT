package com.iut.ptut.view;

import java.util.Calendar;
import java.util.Date;

import com.iut.ptut.R;
import com.iut.ptut.view.WeekActivity.ActionBarTabsListener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MessagesActivity extends Activity{

	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.informations);
		
		
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		
		ActionBar.Tab tabToday = actionbar.newTab().setText("Aujourd'hui");
		ActionBar.Tab tabSemaine = actionbar.newTab().setText("Semaine");
		ActionBar.Tab tabMessage = actionbar.newTab().setText("Messages");

		// definition des fragments qui seronts associés
		// http://developer.android.com/guide/components/fragments.html
		Fragment fragToday = new Fragment();
		Fragment fragSemaine = new Fragment();
		Fragment fragMessage = new Fragment();

		tabToday.setTabListener(new ActionBarTabsListener(fragToday));
		tabSemaine.setTabListener(new ActionBarTabsListener(fragSemaine));
		tabMessage.setTabListener(new ActionBarTabsListener(fragMessage));

		actionbar.addTab(tabMessage);
		actionbar.addTab(tabToday);
		actionbar.addTab(tabSemaine);
		

		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
	}
	
	
	 protected void onStart(){
		 super.onStart();
		 
	 }
	
	 
     protected void onRestart(){
    	 super.onRestart();
     }

     protected void onResume(){
    	 super.onResume();
     }

     protected void onPause(){
    	 super.onPause();
     }

     protected void onStop(){
    	 super.onStop();
     }

     protected void onDestroy(){
    	 super.onDestroy();
     }
     
     
     
     
     
     protected class ActionBarTabsListener implements ActionBar.TabListener {

 		private Fragment fragment;
 		int i = 0;

 		public ActionBarTabsListener(Fragment fragment) {
 			this.fragment = fragment;
 		}

 		public void onTabReselected(Tab tab, FragmentTransaction ft) {
 			// TODO Auto-generated method stub
 			i++;
 			if (i >= 7) {
 				Toast.makeText(getBaseContext(), "éh toi! Appuyer une fois c'est suffisant ! ", Toast.LENGTH_SHORT).show();
 			}
 		}

 		public void onTabSelected(Tab tab, FragmentTransaction ft) {
 			if (getActionBar().getSelectedTab().getText() == "Messages"){
 				
 			}

 			if (getActionBar().getSelectedTab().getText() == "Aujourd'hui") {
 				Intent intent = new Intent(MessagesActivity.this, TodayActivity.class);
 				startActivity(intent);
 			}

 			if (getActionBar().getSelectedTab().getText() == "Semaine") {
 				Intent intent = new Intent(MessagesActivity.this, WeekActivity.class);
 				startActivity(intent);
 			}
 			i = 0;
 		}

 		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
 			// TODO Auto-generated method stub

 		}

 	}
 	
}

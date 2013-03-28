package com.iut.ptut.view;

import java.io.Serializable;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import com.iut.ptut.R;
 
public class ActivityNotification extends Activity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres);
		
		//On supprime la notification de la liste de notification comme dans la méthode cancelNotify de l'Activity principale
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(MainActivity.ID_NOTIFICATION);
		
		/*FileOutputStream ostream = new FileOutputStream(new File("C:/Users/Remy Bienvenu/Documents/GitHub")); 
		ObjectOutputStream o = new ObjectOutputStream(ostream); 
		o.writeObject(ActivityNotification.class);*/ 
	}
}
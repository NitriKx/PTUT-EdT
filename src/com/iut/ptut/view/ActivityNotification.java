package com.iut.ptut.view;

import java.io.Serializable;

import com.iut.ptut.MainActivity;
import com.iut.ptut.R;
import com.iut.ptut.R.layout;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
 
public class ActivityNotification extends Activity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres);
		
		//On supprime la notification de la liste de notification comme dans la m�thode cancelNotify de l'Activity principale
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(MainActivity.ID_NOTIFICATION);
		
		/*FileOutputStream ostream = new FileOutputStream(new File("C:/Users/Remy Bienvenu/Documents/GitHub")); 
		ObjectOutputStream o = new ObjectOutputStream(ostream); 
		o.writeObject(ActivityNotification.class);*/ 
	}
}
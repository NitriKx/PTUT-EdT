package com.iut.ptut.ctrl.cron;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	private Logger _log = Logger.getLogger(this.getClass().getName());
	
	@Override
	public void onReceive(Context context, Intent intent) {
		_log.log(Level.INFO, "[Recurrent] Lancement de la mise à jour !");
		
		Intent cron = new Intent(context, CRONFetcher.class);
		context.startService(cron);
	}
}
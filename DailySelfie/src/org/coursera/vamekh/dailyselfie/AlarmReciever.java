package org.coursera.vamekh.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class AlarmReciever extends BroadcastReceiver {
	
	private static final int NOTIFICATION_ID = 1;
	
	private NotificationManager mNotificationManager;
	private Intent mNotificationIntent;
	private PendingIntent mNotificationPendingIntent;

	@Override
	public void onReceive(Context context, Intent intent) {
		mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationIntent = new Intent(context, DailySelfieActivity.class);
		mNotificationPendingIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		
		RemoteViews mContentViev = new RemoteViews("org.coursera.vamekh.dailyselfie", R.layout.selfie_notification_view);
		
		Notification.Builder notificationBuilder = new Notification.Builder(context)
				.setTicker("Selfie time :)")
				.setSmallIcon(android.R.drawable.ic_menu_camera)
				.setContentIntent(mNotificationPendingIntent)
				.setContent(mContentViev);
		
		mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
	}

}

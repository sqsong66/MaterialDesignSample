package com.sqsong.sample.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.sqsong.sample.MainActivity;
import com.sqsong.sample.R;

import butterknife.BindView;

/**
 * Created by 青松 on 2016/12/23.
 */

public class NotificationActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.button01)
    Button button01;

    @BindView(R.id.button02)
    Button button02;

    int messageNum = 1;
    int notificationId = 1;
    private NotificationManager mNotificationManager;

    @Override
    protected int getResourceId() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initEvent() {
        setupToolbar();

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        button01.setOnClickListener(this);
        button02.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button01:
                buildNotification();
                break;
            case R.id.button02:
                clearNotification();
                break;
        }
    }

    private void clearNotification() {
        mNotificationManager.cancelAll();
    }

    private void buildNotification() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_message_white_24dp);
        builder.setContentTitle("Download Test");
        builder.setContentText("Downloading..");
        builder.setNumber(messageNum);
        messageNum++;

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    builder.setProgress(0, 0, true);
//                    mNotificationManager.notify(notificationId, builder.build());

                    SystemClock.sleep(100);
                }
                builder.setContentText("Download complete!");
                builder.setProgress(0, 0, false);
                mNotificationManager.notify(notificationId, builder.build());
            }
        }.start();
        mNotificationManager.notify(notificationId, builder.build());
        notificationId++;
    }
}

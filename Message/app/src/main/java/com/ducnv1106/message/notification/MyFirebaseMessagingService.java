package com.ducnv1106.message.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.ducnv1106.message.Constants;
import com.ducnv1106.message.R;
import com.ducnv1106.message.model.User;
import com.ducnv1106.message.view.acitivity.message.MessageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private User user;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // get token
        String token = FirebaseInstanceId.getInstance().getInstanceId().getResult().getToken();

        if(firebaseUser!=null){
            updateToken(token);
        }
    }

    private void updateToken(String updateToken) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference("Token").child(firebaseUser.getUid());

        Token token = new Token(updateToken);
        databaseReference.setValue(token);



    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String sented = remoteMessage.getData().get("sented");
        String userid = remoteMessage.getData().get("user");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                if(firebaseUser!=null && sented.equals(firebaseUser.getUid())){

                    sendNotification(remoteMessage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.e("FMC",sented);
        Log.e("FMC",firebaseUser.getUid());




    }

    private void sendNotification(RemoteMessage remoteMessage) {

        String userid = remoteMessage.getData().get("user");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String sented = remoteMessage.getData().get("sented");
        String icon = remoteMessage.getData().get("icon");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String NOTIFICAITON_CHANNEL_ID = "FMCService";

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICAITON_CHANNEL_ID
                    ,"Notification"
                    ,NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("EDMT Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        int j = Integer.parseInt(userid.replaceAll("[\\D]", ""));
        Log.e("FCM", j + "");

        Intent intent = new Intent(this, MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.EXTRA_USER, user);
        intent.putExtras(bundle);


        // Click on start Activity Message
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Log.e("FCM",defaultSound+"");


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICAITON_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);


        int i = 0;
        if (j > 0) {
            i = j;
        }
        notificationManager.notify(i, builder.build());

    }


}

package com.agromarket.ampl_chat.utils;

import android.util.Log;

import com.agromarket.ampl_chat.utils.SessionManager;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.util.HttpAuthorizer;

import java.util.HashMap;
import java.util.Map;

public class RealtimeSocketManager {

    private static Pusher pusher;
    private static PrivateChannel channel;

    public static void connect(SessionManager session,
                               int userId,
                               MessageListener listener) {

        if (pusher != null) return;

        String authUrl = "https://amplchat.agromarket.co.in/api/broadcasting/auth";

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + session.getToken());
        headers.put("Accept", "application/json");

        HttpAuthorizer authorizer = new HttpAuthorizer(authUrl);
        authorizer.setHeaders(headers);

        PusherOptions options = new PusherOptions()
                .setAuthorizer(authorizer)
                .setUseTLS(true)
                .setHost("amplchat.agromarket.co.in")
                .setWssPort(443)
                .setWsPort(443)
                .setEncrypted(true)
                .setActivityTimeout(120000)
                .setPongTimeout(30000);

        pusher = new Pusher(
                "lcwlcvigxtbjksfcedjh", // REVERB_APP_KEY
                options
        );

        channel = pusher.subscribePrivate(
                "private-chat-channel." + userId,
                new PrivateChannelEventListener() {

                    @Override
                    public void onEvent(PusherEvent event) {
                        // Required but unused
                    }

                    @Override
                    public void onSubscriptionSucceeded(String channelName) {
                        Log.d("SOCKET", "Subscribed: " + channelName);
                    }

                    @Override
                    public void onAuthenticationFailure(String message, Exception e) {
                        Log.e("SOCKET", "Auth failed: " + message, e);
                    }
                }
        );

        channel.bind(
                "message.sent",
                new PrivateChannelEventListener() {

                    @Override
                    public void onEvent(PusherEvent event) {
                        Log.d("SOCKET", "Event received: " + event.getEventName());
                        Log.d("SOCKET", "Data: " + event.getData());

                        listener.onMessageReceived(event.getData());
                    }

                    @Override public void onSubscriptionSucceeded(String channelName) {}
                    @Override public void onAuthenticationFailure(String message, Exception e) {}
                }
        );

        pusher.connect();
    }

    public static void disconnect() {
        if (pusher != null) {
            pusher.disconnect();
            pusher = null;
            channel = null;
        }
    }

    public interface MessageListener {
        void onMessageReceived(String data);
    }
}
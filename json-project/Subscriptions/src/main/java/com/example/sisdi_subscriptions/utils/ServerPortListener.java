package com.example.sisdi_subscriptions.utils;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ServerPortListener implements ApplicationListener<WebServerInitializedEvent> {
    private static int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }

    public static int getServerPort() {
        return serverPort;
    }
}

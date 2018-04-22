package com.sandeep.dynamo.dao;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by srao1 on 4/21/18.
 */
public class LocalDbCreationRule extends ExternalResource {


    protected DynamoDBProxyServer server;
    public static String port;

    public LocalDbCreationRule() {
        System.setProperty("sqlite4java.library.path", "native-libs");
    }

    @Override
    protected void before() throws Exception {
        port = getAvailablePort();
        this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
        server.start();
    }

    @Override
    protected void after() {
        this.stopUnchecked(server);
    }

    protected void stopUnchecked(DynamoDBProxyServer dynamoDbServer) {
        try {
            dynamoDbServer.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getAvailablePort() throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
        return String.valueOf(serverSocket.getLocalPort());
    }

}

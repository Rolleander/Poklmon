package com.broll.poklmon.network;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class NetworkServer extends NetworkEndpoint {

	private Server server;
	private Connection client;
	private final static int TIMEOUT = 10;
	private static AddressProvider addressProvider;

	public NetworkServer() {

	}

	public static void setAddressProvider(AddressProvider addressProvider){
		NetworkServer.addressProvider=addressProvider;
	}

	public void close() {
		server.stop();
		server.close();
	}

	public void open() throws NetworkException {
		server = new Server();
		register(server.getKryo());
		server.start();
		try {
			server.bind(PORT);
			server.addListener(new Listener() {
				public void received(Connection connection, Object message) {
					onReceive(message);
				}

				@Override
				public void connected(Connection connection) {
					super.connected(connection);
					client = connection;
				}

				@Override
				public void disconnected(Connection arg0) {
					disconnect = true;
					client = null;
					super.disconnected(arg0);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException(e.getMessage());
		}
	}
	
	@Override
	public Object receive(Class wanted) throws NetworkException {
		Log.debug("Server RECEIVE: "+wanted);
		return super.receive(wanted);
	}

	public String getAddress() throws NetworkException {
		try {
			return addressProvider.getIpAddress();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NetworkException(e.getMessage());
		}
	}

	@Override
	public void send(Object object) throws NetworkException {
		if (client == null || !client.isConnected()) {
			throw new NetworkException("Client not connected!");
		}
		Log.debug("Server SEND: "+object);
		client.sendTCP(object);
	}

	public void waitForClient() throws NetworkException {
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() < time + TIMEOUT * 1000) {
			if (client != null && client.isConnected()) {
				return;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		throw new NetworkException("No Client found!");
	}

}

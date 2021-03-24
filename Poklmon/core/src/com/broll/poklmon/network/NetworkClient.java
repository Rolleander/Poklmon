package com.broll.poklmon.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

public class NetworkClient extends NetworkEndpoint {

	private Client client;

	public NetworkClient() {
	}

	public void open(String serverAddress) throws NetworkException {
		client = new Client();
		register(client.getKryo());
		client.start();
		try {
			client.connect(5000, serverAddress, PORT);
			client.addListener(new Listener() {
				@Override
				public void received(Connection connection, Object message) {
					onReceive(message);
				}
				@Override
				public void disconnected(Connection arg0) {
					disconnect=true;
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
		Log.debug("Client RECEIVE: "+wanted);
		return super.receive(wanted);
	}

	@Override
	public void send(Object object) {
		Log.debug("Client SEND: "+object);
		client.sendTCP(object);
	}

	@Override
	public void close() {
		client.stop();
		client.close();
	}

}

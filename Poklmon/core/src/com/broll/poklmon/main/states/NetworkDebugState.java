package com.broll.poklmon.main.states;

import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.debug.DebugPlayerFactory;
import com.broll.poklmon.main.GameState;
import com.broll.poklmon.network.NetworkClient;
import com.broll.poklmon.network.NetworkException;
import com.broll.poklmon.network.NetworkServer;
import com.broll.poklmon.network.transfer.PoklmonTransfer;
import com.esotericsoftware.minlog.Log;

public class NetworkDebugState extends GameState {

	private DataContainer data;
	public static int STATE_ID = -99;

	public NetworkDebugState(DataContainer data) {
		this.data = data;
	}

	private class Client implements Runnable {

		@Override
		public void run() {
			NetworkClient client = new NetworkClient();
			try {
				client.open("192.168.178.69");
				//
				PoklmonTransfer t2 = (PoklmonTransfer) client.receive(PoklmonTransfer.class);
				System.out.println("client received: " + t2.data.getName());
				PoklmonTransfer t = new PoklmonTransfer();
				DebugPlayerFactory dpf = new DebugPlayerFactory(data);
				t.data = dpf.createDebugPoklmon(10, 15);
				t.data.setName("test2");
				client.send(t);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				client.close();
				System.out.println("client end");
			} catch (NetworkException e) {
				e.printStackTrace();
			}
		}

	}

	private class Server implements Runnable {

		@Override
		public void run() {
			NetworkServer server = new NetworkServer();

			try {
				server.open();

				server.waitForClient();
				PoklmonTransfer t = new PoklmonTransfer();
				DebugPlayerFactory dpf = new DebugPlayerFactory(data);
				t.data = dpf.createDebugPoklmon(10, 15);
				t.data.setName("test");
				System.out.println("server send " + t);
				server.send(t);
				PoklmonTransfer t2 = (PoklmonTransfer) server.receive(PoklmonTransfer.class);
				System.out.println("server received: " + t2.data.getName());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				server.close();
				System.out.println("server end");
			} catch (NetworkException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onInit() {
	}

	@Override
	public void onEnter() {
		Log.DEBUG();

		Thread t1 = new Thread(new Server());
		t1.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		Thread t2 = new Thread(new Client());
		t2.start();
	}

	@Override
	public void onExit() {
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Graphics g) {
	}

}

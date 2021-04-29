package msi.gama.headless.daemon;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class GamaDaemon extends WebSocketServer{
	
	//public static final HashMap<String, Runnable> actions = new HashMap<> () {};
	
	public GamaDaemon(int port) {
		super(new InetSocketAddress(port));
	}

	@Override
	public void onClose(WebSocket socket, int code, String reason, boolean remote) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onError(WebSocket socket, Exception ex) {
		ex.printStackTrace();
		if (socket != null) {
			socket.close();
		}
	}

	@Override
	public void onMessage(WebSocket conn, String msg) {
		// dispatch action based on message here
	}

	@Override
	public void onOpen(WebSocket arg0, ClientHandshake arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}

}
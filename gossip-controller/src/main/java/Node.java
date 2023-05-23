import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Node {

	private ExecutorService _executor = null;
	private String _address = null;
	private int _port = -1;
	private ServerSocket _serverSocket = null;
	private ListenerThread _listenerThread = null;
	private GossipperThread _gossipperThread = null;
	private HashMap<String, Member> _alliances = null;
	private Member _thisMember = null;
	private final AtomicBoolean _isHealthy = new AtomicBoolean(true);

	public Node(String address) {
		parseAddress(address);
		initializeNode();
	}

	private void initializeNode() {
		_executor = Executors.newCachedThreadPool();
		_alliances = new HashMap<String, Member>();
		_thisMember = new Member(_address, _port);
		_thisMember.setLocalTime(System.currentTimeMillis());
		_alliances.put(_thisMember.getAddress() + ":" + String.valueOf(_thisMember.getPort()), _thisMember);
	}

	private void parseAddress(String address) {
		if (address == null || address.trim().equals("")) {
			throw new IllegalArgumentException("Please enter a valid address in the proper format: <address>:<port>");
		}
		address = address.replaceAll(" +", " ");
		String[] addrArr = address.split(":");
		_address = addrArr[0];
		_port = Integer.parseInt(addrArr[1]);
	}

    public void run() {

    	try {
    		_serverSocket = new ServerSocket(_port);

	    	//Listener thread for incoming messages
	    	Thread tListenerThread;
	    	if (_listenerThread == null) {
	    		_listenerThread = new ListenerThread(_executor, _serverSocket, _alliances, _isHealthy, _address, _port);
	    	} 
	    	tListenerThread = new Thread(_listenerThread);
			tListenerThread.start();

			//Gossiper thread to broadcast gossip messages
			Thread tGossipperThread;
			if (_gossipperThread == null) {
				_gossipperThread = new GossipperThread(_thisMember, _alliances, _isHealthy);
			} 
			tGossipperThread = new Thread(_gossipperThread);
			tGossipperThread.start();

			//Wait for threads to finish
			try {
				tListenerThread.join();
				tGossipperThread.join();

				Logger.info("Shutting down threads.");
				_serverSocket.close();
				_executor.shutdown();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }

    public void attachThreadAndRun(ListenerThread listenerThread) throws IOException {
    	if (_listenerThread != null) {
    		throw new UnsupportedOperationException("A ListenerThread already exists for this thread.");
    	}
    	_listenerThread = listenerThread;
    	_listenerThread.alliances = _alliances;

		Thread tListenerThread = new Thread(_listenerThread);
		tListenerThread.start();
    }

    public void attachThreadAndRun(GossipperThread gossipperThread) throws IOException {
    	if (_gossipperThread != null) {
    		throw new UnsupportedOperationException("A GossipperThread already exists for this thread.");
    	}

    	_gossipperThread = gossipperThread;
    	_gossipperThread.alliances = _alliances;

		Thread tGossipperThread = new Thread(_gossipperThread);
		tGossipperThread.start();  
    }

    public static void main(String[]args) {
		Node n = new Node("192.168.43.60:8082");
		n.run();
    }
    

}
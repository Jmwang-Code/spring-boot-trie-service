import java.util.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NodeClient {

	private Socket _clientSocket;
	private String _serverAddr;
	private int _serverPort;
	private PrintWriter out;

	public NodeClient(String serverAddress) {
		parseAddress(serverAddress);
	}

	public void run() throws IOException, UnknownHostException {
		_clientSocket = new Socket(_serverAddr, _serverPort);
		
		Scanner in = null;
		try {
			System.out.print(">");
		    in = new Scanner(System.in);
		    out = new PrintWriter(_clientSocket.getOutputStream(), true);

		    while (in.hasNextLine()){
		        String input = in.nextLine();
				if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("q")) {
					break;
				}
				out.println(input);
				System.out.print(">");
		    }
		} finally {
		    in.close();
		    out.close();
		}
	}

	private void parseAddress(String address) {
		if (address == null || address.trim().equals("")) {
			throw new IllegalArgumentException("Please enter a valid address in the proper format: <address>:<port>");
		}
		address = address.replaceAll(" +", " ");
		String[] addrArr = address.split(":");
		_serverAddr = addrArr[0];
		_serverPort = Integer.parseInt(addrArr[1]);
	}
    

    public static void main(String[]args) {
		//args[0]
		NodeClient client = new NodeClient("192.168.43.60:8082");
		try {
			client.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
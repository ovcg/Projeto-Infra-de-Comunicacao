package controle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente extends Thread {

	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	private Socket socket;
	private ServerSocket server;

	

	public Cliente(String adress,int port) {
		try {
			this.socket = new Socket(adress,port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[]args) {
		
		
	}
	

	@Override
	public void run() {
		
	}

}

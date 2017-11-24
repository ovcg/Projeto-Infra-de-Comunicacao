package controle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;


public class Cliente extends Thread {

	
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	private Socket socket;
	

	public Cliente(Socket socket) {
		this.socket = socket;
		
	}


	@Override
	public void run() {
		
	}

}

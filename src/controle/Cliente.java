package controle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

import base.Pacote;

public class Cliente extends Thread {

	private Pacote pacote;
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

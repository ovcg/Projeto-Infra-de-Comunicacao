package controle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JProgressBar;

public class Server implements Runnable {
	
	private ServerSocket server;
	private JProgressBar barraStatus;
	

	public Server(JProgressBar barraStatus) {
		this.barraStatus=barraStatus;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	@Override
	public void run() {
		InputStream input=null;
		OutputStream output=null;
		FileInputStream fileInput=null;
		FileOutputStream fileOutput=null;
		

		byte[] buffer= new byte[5000];
		int bytesLidos = 0;
		long tamArq = 0;
		long arqRecebido = 0;
		
		try {
			System.out.println("Escutando na porta 5000");
			Socket socket=server.accept();
			
			input=socket.getInputStream();
			
			
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}

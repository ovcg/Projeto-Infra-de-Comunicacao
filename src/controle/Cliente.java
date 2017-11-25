package controle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Cliente implements Runnable {

	private String ip;
	private int porta = 0;
	private String nomeArq;
	private String path;
	private int enviar;

	public Cliente(String ip, int porta, String nomeArq, String path, int enviar) {
		this.ip = ip;
		this.porta = porta;
		this.nomeArq = nomeArq;
		this.path = path;
		this.enviar = enviar;

	}

	@Override
	public void run() {
		
		Socket socket = null;
		FileInputStream fileInputStream = null;
		FileOutputStream filOutputStream = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		byte[] buffer= new byte[5000];
		int byteRead = 0;
		long tamArq = 0;
		long arqEnviado = 0;

		try {
			System.out.println("Conectando-se: " + porta);
			socket = new Socket(ip, porta);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			
			

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void pararEnvio(int parar) {

	}

	public void cancelarEnvio(int parar) {
		
	}
	
	public void restart(int restart) {
		
	}

}

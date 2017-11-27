package controle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

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
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		byte[] buffer= new byte[5000];
		int bytesLidos = 0;
		long tamArq = 0;  
		long arqEnviado = 0;
		

		try {
			System.out.println("Conectando-se na porta: " + porta+" IP: "+ip);
			socket = new Socket(ip, porta);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			
			inputStream.read();
			
			if(enviar==1) {
				System.out.println("Cliente enviando endereço IP.");
				//Enviando endereço IP
				String endIP=socket.getLocalSocketAddress().toString();
				outputStream.write(endIP.getBytes("UTF_16"));
				outputStream.flush();				
				inputStream.read();
				
				System.out.println("Cliente enviando nome do arquivo.");
				//Enviando nome do arquivo
				outputStream.write(nomeArq.getBytes("UTF_16"));
				outputStream.flush();
				inputStream.read();
				
				File file=new File(path);
				tamArq=file.length();
						
				System.out.println("Cliente enviando tamanho do arquivo: " + tamArq);

				// Envia tamanho do arquivo
				ByteBuffer bufferTam = ByteBuffer.allocate(Long.BYTES);
				bufferTam.putLong(tamArq);
				outputStream.write(bufferTam.array(), 0, Long.BYTES);
				outputStream.flush();
				inputStream.read();
				
				fileOutputStream=new FileOutputStream(file);
				
				while((bytesLidos=inputStream.read(buffer))!=-1) {//Enviando arquivo
					
					fileOutputStream.write(buffer, 0, bytesLidos);
					fileOutputStream.flush();
					arqEnviado+=bytesLidos;					
				}
				
				outputStream.close();
			}
			
			
			fileOutputStream.close();
			socket.close();

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

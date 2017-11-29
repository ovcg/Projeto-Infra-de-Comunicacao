package controle;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import javax.swing.JProgressBar;

public class Server implements Runnable {

	private ServerSocket server;
	private JProgressBar progressBar;

	public Server(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	@Override
	public void run() {
		InputStream input = null;
		OutputStream output = null;
		DataInputStream data=null;
		FileOutputStream fileOutput = null;
		

		byte prosseguir = 1;//sinal para continuar a receber os dados
		byte[] buffer = new byte[5000];//tam do pacote
		int bytesLidos = 0;//bytes lidos 
		long tamArq = 0;//recebe tam do arquivo
		long arqRecebido = 0;//variavel para calcular a porcentagem na progressbar

		try {
			System.out.println("Escutando na porta: "+server.getLocalPort());
			Socket socket = server.accept();

			input = socket.getInputStream();
			output=socket.getOutputStream();
			output.write(prosseguir);

			// Nome do arquivo
			byte[] nomeArq = new byte[150];
			input.read(nomeArq);

			String nome = new String(nomeArq, StandardCharsets.UTF_16);
			
			int position=nome.indexOf(0);
			if(position!=-1) {
				nome=nome.substring(0,position);
			}
			System.out.println("Recebendo arquivo: " + nome);
			output.write(prosseguir);

			

			// Recebendo tamanho do arquivo
			byte[] aux = new byte[Long.BYTES];
			input.read(aux);
			ByteBuffer bufferTam = ByteBuffer.wrap(aux);
			tamArq = bufferTam.getLong();
			output.write(prosseguir);
			
			System.out.println("Recebendo tamanho do arquivo: " + tamArq/1000000+" MB");

			File arquivo = new File("Recebidos"+File.separator+nome);
			fileOutput = new FileOutputStream(arquivo);
			data=new DataInputStream(input); 

			while ((bytesLidos = data.read(buffer)) >0) {// Recebendo o arquivo
				
				fileOutput.write(buffer, 0, bytesLidos);
				fileOutput.flush();
				
				arqRecebido+=bytesLidos;
				
				// Atualizando ProgessBar
				
				progressBar.setValue((int)((arqRecebido * 100) / tamArq));
				progressBar.setString(Long.toString((arqRecebido * 100) / tamArq) + " %");
				progressBar.setStringPainted(true);
				
			}
			fileOutput.close();
			socket.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}

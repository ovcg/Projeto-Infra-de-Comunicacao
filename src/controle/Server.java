package controle;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import base.RTTRecebendo;

public class Server implements Runnable {

	private int porta;
	private ServerSocket server;
	private JProgressBar progressBar;
	private JTextPane rttRec;
	private JTextField tempoEstimado;
	private JLabel lblIp;

	public Server(int porta, JProgressBar progressBar, JTextPane rttRec, JTextField tempoEstimado, JLabel lblIp) {
		this.porta = porta;
		this.progressBar = progressBar;
		this.rttRec = rttRec;
		this.tempoEstimado = tempoEstimado;
		this.lblIp=lblIp;

	}

	@Override
	public void run() {
		InputStream input = null;
		OutputStream output = null;
		DataInputStream data = null;
		FileOutputStream fileOutput = null;

		byte prosseguir = 1;// sinal para continuar a receber os dados
		byte[] buffer = new byte[5000];// tam do pacote
		int bytesLidos = 0;// bytes lidos
		long tamArq = 0;// recebe tam do arquivo
		long arqRecebido = 0;// variavel para calcular a porcentagem na progressbar
		long tempoInicial = 0;
		long atualizaTempo = 0;
		long duracao = 0;
		double vel = 0;
		double tempoRestante = 0;

		try {
			server = new ServerSocket(porta);
			System.out.println("Escutando na porta: " + server.getLocalPort());
			Socket socket = server.accept();

			input = socket.getInputStream();
			output = socket.getOutputStream();
			output.write(prosseguir);

			RTTRecebendo rtt = new RTTRecebendo(rttRec);
			Thread t = new Thread(rtt);
			t.start();
			rtt.setAuxThread(false);

			// IP recebido
			byte[] ipRec = new byte[150];
			input.read(ipRec);
			
			String ipRecebido=new String(ipRec,StandardCharsets.UTF_16);
			int pos=ipRecebido.indexOf(0);

			if (pos != -1) {
				ipRecebido = ipRecebido.substring(0, pos);
			}
			lblIp.setText("IP recebido: "+ipRecebido);//Colocando ip recebido na gui
			
			// Nome do arquivo
			byte[] nomeArq = new byte[150];
			input.read(nomeArq);

			String nome = new String(nomeArq, StandardCharsets.UTF_16);
			output.write(prosseguir);

			int position = nome.indexOf(0);
			if (position != -1) {
				nome = nome.substring(0, position);
			}
			System.out.println("Recebendo arquivo: " + nome);
			output.write(prosseguir);

			tempoInicial = System.currentTimeMillis();
			atualizaTempo = tempoInicial;

			// Recebendo tamanho do arquivo
			byte[] aux = new byte[Long.BYTES];
			input.read(aux);
			ByteBuffer bufferTam = ByteBuffer.wrap(aux);
			tamArq = bufferTam.getLong();
			output.write(prosseguir);

			System.out.println("Recebendo tamanho do arquivo: " + tamArq / 1000000 + " MB");

			File arquivo = new File("Recebidos" + File.separator + nome);
			fileOutput = new FileOutputStream(arquivo);
			data = new DataInputStream(input);
			String cancelar = "";
			//cancelar = data.readUTF();

			if (cancelar.equalsIgnoreCase("Cancelar")) {
				try {
					buffer.wait(5000);
					Thread.sleep(5000);
					progressBar.setValue(0);
					progressBar.setString(0 + " %");
					progressBar.setStringPainted(true);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				while ((bytesLidos = data.read(buffer)) > 0) {// Recebendo o arquivo

					fileOutput.write(buffer, 0, bytesLidos);
					fileOutput.flush();

					arqRecebido += bytesLidos;

					// Atualizando ProgessBar

					progressBar.setValue((int) ((arqRecebido * 100) / tamArq));
					progressBar.setString(Long.toString((arqRecebido * 100) / tamArq) + " %");
					progressBar.setStringPainted(true);

					if (arqRecebido > 10000 && (System.currentTimeMillis() - atualizaTempo) > 1000) {

						duracao = System.currentTimeMillis() - tempoInicial;
						long div = arqRecebido / duracao;
						vel = div * 1000;
						tempoRestante = (tamArq - arqRecebido) / vel;
						DecimalFormat dec = new DecimalFormat("#");
						String auxDec = "" + dec.format(tempoRestante);
						tempoEstimado.setText(auxDec);
						atualizaTempo = System.currentTimeMillis();
					}

				}

			}
			fileOutput.close();
			data.close();
			socket.close();
			server.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}

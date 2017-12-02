package controle;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;

import javax.swing.JProgressBar;
import javax.swing.JTextField;

import base.RTTEnviando;

public class Cliente implements Runnable {

	private String ip;
	private int porta = 0;
	private String nomeArq;
	private String path;
	private int enviar;
	private JProgressBar progressbar;
	private JTextField rttEnv;
	private JTextField tempoEstimado;

	public Cliente(String ip, int porta, String nomeArq, String path, int enviar, JProgressBar progress,
			JTextField rttEnv, JTextField tempoEstimado) {
		this.ip = ip;
		this.porta = porta;
		this.nomeArq = nomeArq;
		this.path = path;
		this.enviar = enviar;
		this.progressbar = progress;
		this.rttEnv = rttEnv;
		this.tempoEstimado = tempoEstimado;

	}

	@Override
	public void run() {

		Socket socket = null;
		FileInputStream fileInput = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		DataOutputStream out = null;

		try {
			System.out.println("Conectando-se na porta: " + porta + " e IP: " + ip);
			socket = new Socket(ip, porta);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			inputStream.read();

			byte[] buffer = new byte[5000];
			int bytesLidos = 0;
			long tamArq = 0;
			long arqEnviado = 0;
			long tempoInicial = 0;
			long atualizaTempo = 0;
			long duracao = 0;
			double vel = 0;
			double tempoRestante = 0;

			RTTEnviando rtt = new RTTEnviando(ip, rttEnv);
			Thread t = new Thread(rtt);
			t.start();
			rtt.setAuxThread(false);

			if (enviar == 1) {

				File file = new File(path);
				tamArq = file.length();
				nomeArq = file.getName();

				System.out.println("Cliente enviando nome do arquivo:" + nomeArq);
				// Enviando nome do arquivo
				outputStream.write(nomeArq.getBytes("UTF_16"));
				inputStream.read();

				int mega = 1000000;
				System.out.println("Cliente enviando tamanho do arquivo: " + tamArq / mega + " MB");

				// Envia tamanho do arquivo
				ByteBuffer bufferTam = ByteBuffer.allocate(Long.BYTES);
				bufferTam.putLong(tamArq);
				outputStream.write(bufferTam.array(), 0, Long.BYTES);
				inputStream.read();

				fileInput = new FileInputStream(file);
				out = new DataOutputStream(outputStream);

				while ((bytesLidos = fileInput.read(buffer)) > 0) {// Enviando arquivo

					out.write(buffer, 0, bytesLidos);
					out.flush();
					arqEnviado += bytesLidos;

					// Atualizando ProgessBar
					progressbar.setValue((int) ((arqEnviado * 100) / tamArq));
					progressbar.setString(Long.toString(((arqEnviado * 100) / tamArq)) + " %");
					progressbar.setStringPainted(true);

					if (arqEnviado > 10000 && (System.currentTimeMillis() - atualizaTempo) > 1000) {
						duracao = System.currentTimeMillis() - tempoInicial;
						long div = arqEnviado / duracao;
						vel = div * 1000;
						tempoRestante = (tamArq - arqEnviado) / vel;
						DecimalFormat dec = new DecimalFormat("#");
						String auxDec = "" + dec.format(tempoRestante);
						tempoEstimado.setText(auxDec);
						atualizaTempo = System.currentTimeMillis();

					}

				}

			}
			rtt.setAuxThread(true);
			inputStream.close();
			outputStream.close();
			fileInput.close();
			out.close();			
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

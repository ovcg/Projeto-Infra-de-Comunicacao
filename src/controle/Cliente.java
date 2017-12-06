package controle;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import base.RTTEnviando;

public class Cliente implements Runnable {

	private String ip;
	private int porta = 0;
	private String nomeArq;
	private String path;
	private int enviar;
	private JProgressBar progressbar;
	private JTextPane rttEnv;
	private JTextField tempoEstimado;
	private int parar = 0;
	private int cancelar = 0;
	private int reiniciar = 0;

	public Cliente(String ip, int porta, String nomeArq, String path, int enviar, JProgressBar progress,
			JTextPane rttEnv, JTextField tempoEstimado) {
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
			rtt.setAux(0);

			if (enviar == 1) {

				File file = new File(path);
				tamArq = file.length();
				nomeArq = file.getName();

				System.out.println("Cliente enviando nome do arquivo:" + nomeArq);
				// Enviando nome do arquivo
				outputStream.write(nomeArq.getBytes("UTF_16"));
				inputStream.read();

				String ipEnv = InetAddress.getLocalHost().getHostAddress();// Enviando IP
				outputStream.write(ipEnv.getBytes("UTF_16"));
				inputStream.read();

				System.out.println("Cliente enviando IP: " + ipEnv);
				int mega = 1000000;
				System.out.println("Cliente enviando tamanho do arquivo: " + tamArq / mega + " MB");

				// Envia tamanho do arquivo
				ByteBuffer bufferTam = ByteBuffer.allocate(Long.BYTES);
				bufferTam.putLong(tamArq);
				outputStream.write(bufferTam.array(), 0, Long.BYTES);
				inputStream.read();

				tempoInicial = System.currentTimeMillis();
				atualizaTempo = tempoInicial;

				fileInput = new FileInputStream(file);
				out = new DataOutputStream(outputStream);

				while ((bytesLidos = fileInput.read(buffer)) > 0) {// Enviando arquivo

					if (cancelar == 1) {

						tempoEstimado.setText("0");
						enviar = 0;
						rtt.setAux(1);
						rtt.setRTT("0");
						progressbar.setValue(0);
						progressbar.setString(0 + " %");
						progressbar.setStringPainted(true);

						break;

					} else if (parar == 1) {
						System.out.println("Parando transferência!");
						tempoEstimado.setText("Pause");

						while (parar == 1 && enviar == 0) {
							rtt.setRTT("0.0");
						}
						if (parar == 0 && enviar == 1) {
							break;

						}

					} else if (enviar == 1 && reiniciar == 1 && cancelar == 0 && parar == 0) {

						arqEnviado = 0;
						bytesLidos = 0;

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
								vel = 1000 * (arqEnviado / duracao);
								tempoRestante = (tamArq - arqEnviado) / vel;
								tempoEstimado.setText(String.valueOf(new DecimalFormat("#").format(tempoRestante)));
								atualizaTempo = System.currentTimeMillis();
							}

						}

					}

					else {
						out.write(buffer, 0, bytesLidos);
						out.flush();
						arqEnviado += bytesLidos;

						// Atualizando ProgessBar
						progressbar.setValue((int) ((arqEnviado * 100) / tamArq));
						progressbar.setString(Long.toString(((arqEnviado * 100) / tamArq)) + " %");
						progressbar.setStringPainted(true);

						if (arqEnviado > 10000 && (System.currentTimeMillis() - atualizaTempo) > 1000) {
							duracao = System.currentTimeMillis() - tempoInicial;
							vel = 1000 * (arqEnviado / duracao);
							tempoRestante = (tamArq - arqEnviado) / vel;
							tempoEstimado.setText(String.valueOf(new DecimalFormat("#").format(tempoRestante)));
							atualizaTempo = System.currentTimeMillis();
						}

					}

				}

			}

			tempoEstimado.setText("" + 0);
			enviar = 0;
			rtt.setAux(1);
			rtt.setRTT("0");
			inputStream.close();
			outputStream.close();
			fileInput.close();
			out.close();
			socket.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void iniciar(int enviar) {
		this.enviar = enviar;
	}

	public void pararEnvio(int parar) {
		this.parar = parar;

	}

	public void cancelarEnvio(int cancelar) {
		this.cancelar = cancelar;
	}

	public void reiniciar(int reiniciar) {

		this.reiniciar = reiniciar;
	}

}

package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTextField;

//Classe para calcular o RTT
public class RTTEnviando implements Runnable {

	private boolean auxThread;
	private String ip;
	private JTextField rttEnviando;

	public RTTEnviando(String ip, JTextField rttEnviando) {
		this.ip = ip;
		this.rttEnviando = rttEnviando;
	}

	public void setAuxThread(boolean aux) {
		this.auxThread = aux;
	}

	@Override
	public void run() {
		long tempoRTT = 0;
		long tempoInicial = 0;
		String nome = "rtt1\n";
		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			Socket socket = new Socket(ip, 3300);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			InputStreamReader input = new InputStreamReader(inputStream);
			BufferedReader buffer = new BufferedReader(input);

			while (true) {

				tempoInicial = System.nanoTime();
				outputStream.write(nome.getBytes());
				outputStream.flush();

				while (auxThread == false && !buffer.ready())
					;

				if (buffer.ready()) {
					if (buffer.readLine().equals("rtt1")) {
						tempoRTT = System.nanoTime() - tempoInicial;

					}
				}

				nome = "rtt";
				if (auxThread == true) {
					break;
				}
				outputStream.write(nome.getBytes());
				outputStream.flush();

				String rttAtual = "" + tempoRTT / 1000000;
				rttEnviando.setText(rttAtual);

				if (auxThread == true) {
					break;
				}
				Thread.sleep(1000);
				if (auxThread == true) {
					break;
				}

			}

			inputStream.close();
			outputStream.close();
			socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

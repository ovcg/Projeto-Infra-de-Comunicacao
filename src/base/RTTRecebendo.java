package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextField;

public class RTTRecebendo implements Runnable {

	private boolean auxThread = false;
	private JTextField rttRec;

	public RTTRecebendo(JTextField rttRec) {

		this.rttRec = rttRec;
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
			ServerSocket serverSocket = new ServerSocket(3300);
			Socket socket = serverSocket.accept();

			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();

			InputStreamReader input = new InputStreamReader(inputStream);
			BufferedReader buffer = new BufferedReader(input);

			while (true) {

				do {
					if (buffer.ready()) {
						buffer.readLine();
						tempoInicial = System.currentTimeMillis();
					}
					if (auxThread == true) {
						break;
					}
					outputStream.write(nome.getBytes());
					outputStream.flush();
				}

				while (auxThread == false && !buffer.ready());

				do {
					if (buffer.ready()) {
						if (buffer.readLine().equals("rtt")) {
							tempoRTT = System.currentTimeMillis() - tempoInicial;
						}
					}

				} while (auxThread == false && !buffer.ready());

				String rttAtual = "" + tempoRTT;
				rttRec.setText(rttAtual);

				if (auxThread == true)
					break;
				Thread.sleep(1000);
				if (auxThread == true)
					break;

			}

			inputStream.close();
			outputStream.close();
			socket.close();
			serverSocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

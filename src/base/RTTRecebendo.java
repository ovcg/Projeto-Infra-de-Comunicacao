package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextPane;

public class RTTRecebendo implements Runnable {

	private JTextPane showRTT;
	private int auxThread = 0;

	public RTTRecebendo(JTextPane showRTT) {
		this.showRTT = showRTT;
	}

	public void setAux(int auxThread) {
		this.auxThread = auxThread;
	}
	public void setRTT(String t) {
		this.showRTT.setText(t);
	}

	public void run() {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		Socket socket = null;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(3274);
			socket = serverSocket.accept();

			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();

			InputStreamReader entrada = new InputStreamReader(inputStream);

			while (true) {

				while (!leitura.ready() && auxThread == 0);
				if (leitura.ready())
					leitura.readLine();
				initialTime = System.nanoTime();

				if (auxThread == 1) {
					break;
				}
				
				outputStream.write(fileName.getBytes());
				outputStream.flush();
			
				while (!leitura.ready() && auxThread == 0);
				if (leitura.ready()) {
					if (leitura.readLine().equals("RTT2")) {
						RTT_time = System.nanoTime() - initialTime;

					}
				}
				double rtt_ms = rtt_ms / 1000000;

				showRTT.setText(String.valueOf(rtt_ms));

				if (auxThread == 1) {
					break;
				}
				Thread.sleep(1000);
				if (auxThread == 1) {
					break;
				}

			}

			inputStream.close();
			outputStream.close();
			socket.close();
			socket_server.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

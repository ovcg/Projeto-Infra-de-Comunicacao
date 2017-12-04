package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTextPane;

public class RTTEnviando implements Runnable {
	private String ip_text;
	private JTextPane RTT_text;
	private int flagThread = 0;

	public RTTEnviando(String IP, JTextPane RTT_text) {
		ip_text = IP;
		this.RTT_text = RTT_text;
	}

	public void setStop(int i) {
		this.flagThread = i;
	}

	public void run() {
		OutputStream socket_out = null;
		InputStream socket_in = null;
		Socket socket = null;
		try {
			socket = new Socket(ip_text, 3274);

			socket_out = socket.getOutputStream();
			socket_in = socket.getInputStream();

			InputStreamReader entrada = new InputStreamReader(socket_in);
			BufferedReader leitura = new BufferedReader(entrada);
			long RTT_time = 0;
			String fileName = "RTT\n";
			long initialTime;

			System.out.println("Cliente: trying to get rtt");
			while (true) {

				fileName = "RTT\n";
				initialTime = System.nanoTime();
				// ENVIA
				socket_out.write(fileName.getBytes());
				socket_out.flush();
				// RECEBE
				while (!leitura.ready() && flagThread == 0);
				if (leitura.ready()) {
					if (leitura.readLine().equals("RTT")) {
						;
						RTT_time = System.nanoTime() - initialTime;
					}
				}
				fileName = "RTT2\n";
				// ENVIA
				if (flagThread == 1) {
					break;
				}
				socket_out.write(fileName.getBytes());
				socket_out.flush();

				double RTT_micro = RTT_time / 1000;

				RTT_text.setText(String.valueOf(RTT_micro));

				if (flagThread == 1) {
					break;
				}

				Thread.sleep(1000);
				if (flagThread == 1) {
					break;
				}

			}
			socket_in.close();
			socket_out.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

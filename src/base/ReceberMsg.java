package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceberMsg implements Runnable {

	private int cancelar = 0;
	private int reiniciar = 0;

	private String flag;

	public ReceberMsg() {

	}

	public void setCancelar(int cancelar) {
		this.cancelar = cancelar;
	}

	public void setReiniciar(int reiniciar) {
		this.reiniciar = reiniciar;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFlag() {
		return this.flag;
	}

	@Override
	public void run() {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		Socket socket = null;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(3200);
			socket = serverSocket.accept();

			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();

			InputStreamReader input = new InputStreamReader(inputStream);
			BufferedReader buffer = new BufferedReader(input);

			setFlag("ok\n");

			while (true) {

				while (!buffer.ready() && cancelar == 0)
					;
				if (buffer.ready())
					buffer.readLine();

				if (cancelar == 1) {
					break;
				}

				outputStream.write(flag.getBytes());
				outputStream.flush();

				while (!buffer.ready() && cancelar == 0)
					;
				if (buffer.ready()) {
					if (buffer.readLine().equals("ok2")) {
						continue;
					} else if (buffer.readLine().equals("cancel")) {
						cancelar = 1;
					}
				}

				if (cancelar == 1) {
					break;
				}
				Thread.sleep(1000);
				if (cancelar == 1) {
					break;
				}

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

package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class EnviarMsg implements Runnable {

	private int cancelar = 0;
	private int reiniciar = 0;
	private String ip;
	private String flag;

	public EnviarMsg(String ip) {
		this.ip = ip;
	}

	public void setCancelar(int cancelar) {
		this.cancelar = cancelar;
	}

	public void setReiniciar(int reiniciar) {
		this.reiniciar = reiniciar;
	}
	public void setFlag(String flag) {
		this.flag=flag;
	}

	@Override
	public void run() {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		Socket socket = null;
		try {
			socket = new Socket(ip, 3274);

			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();

			InputStreamReader input = new InputStreamReader(inputStream);
			BufferedReader buffer = new BufferedReader(input);
			String flag = "RTT\n";
			

			System.out.println("Status da transferência...");
			while (true) {

				setFlag("ok\n");
			

				outputStream.write(flag.getBytes());
				outputStream.flush();

				while (!buffer.ready() && cancelar == 0 && reiniciar==0)
					;
				if (buffer.ready()) {
					if (buffer.readLine().equals("ok")) {
						continue;
					}
				}

				setFlag("ok2\n");

				if (cancelar == 1) {
					setFlag("cancel\n");
					break;
				}
				
				outputStream.write(flag.getBytes());
				outputStream.flush();

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

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

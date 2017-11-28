package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controle.Cliente;
import controle.Server;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import javax.swing.JProgressBar;

public class TelaInicial extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2622910333830375766L;
	private JPanel contentPane;
	private JTextField textFieldIp;
	private JTextField textFieldPort;
	private JLabel lblNomeDoArquivo;
	private JLabel lblEndereoDestino;
	private JLabel lblPortaDestino;
	private JLabel lblTamanhoDoArquivo;
	private JButton buttonParar;
	private JButton buttonRecomecar;
	private JButton buttonCancelar;
	private JTextField textFieldRTTRec;
	private JTextField textFieldRTTEnv;
	private JTextField textFieldTempoEnv;
	private JTextField textFieldTempoRec;
	private JLabel lblIp;
	private JProgressBar progressBarRecebendo;
	private JProgressBar progressBar;
	
	private ServerSocket serverDefault;
	private Server server;
	private Cliente cliente;
	private String path;
	private String nomeArquivo;
	private File file;
	private int enviar = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial frame = new TelaInicial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public TelaInicial() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldIp = new JTextField();
		textFieldIp.setColumns(10);
		textFieldIp.setBounds(100, 12, 117, 19);
		contentPane.add(textFieldIp);

		JFileChooser fc = new JFileChooser();
		

		JButton btnNewButton = new JButton("Escolher Arquivo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int abrir = fc.showOpenDialog(null);

				if (abrir == JFileChooser.APPROVE_OPTION) {
					path = fc.getSelectedFile().getAbsolutePath();
					file = new File(path);
					nomeArquivo = file.getName();
					lblNomeDoArquivo.setText("Nome do Arquivo: " + nomeArquivo);
					lblTamanhoDoArquivo.setText("Tamanho do Arquivo: " + file.length() / 1000000 + " MB");

				}

			}
		});
		btnNewButton.setBounds(48, 61, 163, 25);
		contentPane.add(btnNewButton);

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ip = textFieldIp.getText();
				int porta = Integer.parseInt(textFieldPort.getText());
				enviar = 1;
				cliente = new Cliente(ip, porta, nomeArquivo, path, enviar,progressBar);
				Thread t = new Thread(cliente);
				t.start();

			}
		});
		btnIniciar.setBounds(48, 346, 117, 25);
		contentPane.add(btnIniciar);

		serverDefault = new ServerSocket(5001);// porta default para receber arquivos
		server = new Server(progressBarRecebendo);
		server.setServer(serverDefault);

		Thread serverThread = new Thread(server);
		serverThread.start();
		
		textFieldPort = new JTextField();
		textFieldPort.setColumns(10);
		textFieldPort.setBounds(322, 12, 75, 19);
		contentPane.add(textFieldPort);

		lblNomeDoArquivo = new JLabel("Nome do arquivo: ");
		lblNomeDoArquivo.setBounds(226, 66, 348, 15);
		contentPane.add(lblNomeDoArquivo);

		lblEndereoDestino = new JLabel("IP:");
		lblEndereoDestino.setBounds(64, 14, 29, 15);
		contentPane.add(lblEndereoDestino);

		lblPortaDestino = new JLabel("Porta:");
		lblPortaDestino.setBounds(258, 14, 75, 15);
		contentPane.add(lblPortaDestino);

		lblTamanhoDoArquivo = new JLabel("Tam. do Arquivo:");
		lblTamanhoDoArquivo.setBounds(52, 109, 522, 15);
		contentPane.add(lblTamanhoDoArquivo);

		JLabel lblEnviando = new JLabel("Enviando:");
		lblEnviando.setBounds(49, 170, 108, 15);
		contentPane.add(lblEnviando);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(163, 160, 234, 25);
		contentPane.add(progressBar);

		JLabel label_3 = new JLabel("RTT:");
		label_3.setBounds(413, 165, 70, 15);
		contentPane.add(label_3);

		JLabel lblTempoEstimado = new JLabel("Tempo Estimado:");
		lblTempoEstimado.setBounds(48, 197, 136, 15);
		contentPane.add(lblTempoEstimado);

		buttonParar = new JButton("Parar");
		buttonParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		buttonParar.setBounds(202, 346, 117, 25);
		contentPane.add(buttonParar);

		buttonRecomecar = new JButton("Recomeçar");
		buttonRecomecar.setBounds(497, 346, 117, 25);
		contentPane.add(buttonRecomecar);

		buttonCancelar = new JButton("Cancelar");
		buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog();
				dialog.setTitle("Transferência cancelada");
				dialog.setVisible(true);

			}
		});
		buttonCancelar.setBounds(352, 346, 117, 25);
		contentPane.add(buttonCancelar);

		JLabel lblRecebendo = new JLabel("Recebendo:");
		lblRecebendo.setBounds(48, 244, 108, 15);
		contentPane.add(lblRecebendo);

		progressBarRecebendo = new JProgressBar();
		progressBarRecebendo.setStringPainted(true);
		progressBarRecebendo.setBounds(163, 243, 234, 25);
		contentPane.add(progressBarRecebendo);

		JLabel label_1 = new JLabel("RTT:");
		label_1.setBounds(413, 248, 70, 15);
		contentPane.add(label_1);

		JLabel lblTempoEstimado_1 = new JLabel("Tempo Estimado:");
		lblTempoEstimado_1.setBounds(48, 297, 124, 15);
		contentPane.add(lblTempoEstimado_1);

		textFieldRTTRec = new JTextField();
		textFieldRTTRec.setBounds(474, 242, 114, 19);
		contentPane.add(textFieldRTTRec);
		textFieldRTTRec.setColumns(10);

		textFieldRTTEnv = new JTextField();
		textFieldRTTEnv.setColumns(10);
		textFieldRTTEnv.setBounds(474, 163, 114, 19);
		contentPane.add(textFieldRTTEnv);

		textFieldTempoEnv = new JTextField();
		textFieldTempoEnv.setColumns(10);
		textFieldTempoEnv.setBounds(202, 197, 114, 19);
		contentPane.add(textFieldTempoEnv);

		textFieldTempoRec = new JTextField();
		textFieldTempoRec.setColumns(10);
		textFieldTempoRec.setBounds(176, 295, 114, 19);
		contentPane.add(textFieldTempoRec);

		JLabel lblMs = new JLabel("ms");
		lblMs.setBounds(327, 197, 70, 15);
		contentPane.add(lblMs);

		JLabel label = new JLabel("ms");
		label.setBounds(295, 299, 70, 15);
		contentPane.add(label);

		JLabel lblIpFonte = new JLabel("IP fonte:");
		lblIpFonte.setBounds(338, 297, 70, 15);
		contentPane.add(lblIpFonte);

		lblIp = new JLabel("IP");
		lblIp.setBounds(413, 297, 201, 15);
		contentPane.add(lblIp);
	}
}

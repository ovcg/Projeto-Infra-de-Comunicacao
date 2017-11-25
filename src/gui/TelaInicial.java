package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controle.Cliente;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JProgressBar;

public class TelaInicial extends JFrame implements Serializable {

	private JPanel contentPane;
	private JTextField textFieldIp;
	private JTextField textFieldPort;
	private String path;
	private String nomeArquivo;
	private JLabel lblNomeDoArquivo;
	private JLabel lblEndereoDestino;
	private JLabel lblPortaDestino;
	private JLabel lblTamanhoDoArquivo;
	private JLabel lblTransfer;
	private Cliente cliente;
	private JButton button_1;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private File file;
	

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
	 */
	public TelaInicial() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldIp = new JTextField();
		textFieldIp.setColumns(10);
		textFieldIp.setBounds(172, 80, 300, 19);
		contentPane.add(textFieldIp);

		JButton button = new JButton("Enviar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int port=Integer.parseInt(textFieldIp.getText());
				
				cliente=new Cliente(textFieldIp.getText(),port);
				
				cliente.start();
			}
		});
		button.setBounds(25, 255, 117, 25);
		contentPane.add(button);

		textFieldPort = new JTextField();
		textFieldPort.setColumns(10);
		textFieldPort.setBounds(172, 111, 300, 19);
		contentPane.add(textFieldPort);

		JButton btnNewButton = new JButton("Escolher Arquivo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();

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
		btnNewButton.setBounds(25, 158, 163, 25);
		contentPane.add(btnNewButton);

		lblNomeDoArquivo = new JLabel("Nome do arquivo: ");
		lblNomeDoArquivo.setBounds(231, 163, 348, 15);
		contentPane.add(lblNomeDoArquivo);

		lblEndereoDestino = new JLabel("Endereço destino:");
		lblEndereoDestino.setBounds(25, 82, 188, 15);
		contentPane.add(lblEndereoDestino);

		lblPortaDestino = new JLabel("Porta destino:");
		lblPortaDestino.setBounds(25, 113, 102, 15);
		contentPane.add(lblPortaDestino);

		lblTamanhoDoArquivo = new JLabel("Tamanho do Arquivo:");
		lblTamanhoDoArquivo.setBounds(25, 210, 522, 15);
		contentPane.add(lblTamanhoDoArquivo);

		lblTransfer = new JLabel("Transfer");
		lblTransfer.setForeground(Color.GREEN);
		lblTransfer.setBounds(27, 12, 100, 19);
		lblTransfer.setSize(100, 50);
		contentPane.add(lblTransfer);

		JLabel label = new JLabel("Progresso:");
		label.setBounds(199, 255, 108, 15);
		contentPane.add(label);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(313, 255, 234, 14);
		contentPane.add(progressBar);

		JLabel label_1 = new JLabel("Status:");
		label_1.setBounds(35, 297, 70, 15);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("Status2");
		label_2.setBounds(118, 297, 70, 15);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("RTT:");
		label_3.setBounds(288, 297, 70, 15);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("Tempo");
		label_4.setBounds(342, 297, 70, 15);
		contentPane.add(label_4);

		button_1 = new JButton("Parar");
		button_1.setBounds(209, 359, 117, 25);
		contentPane.add(button_1);

		button_2 = new JButton("Continuar");
		button_2.setBounds(366, 359, 117, 25);
		contentPane.add(button_2);

		button_3 = new JButton("Recomeçar");
		button_3.setBounds(537, 359, 117, 25);
		contentPane.add(button_3);

		button_4 = new JButton("Cancelar");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JDialog dialog = new JDialog();
				dialog.setTitle("Transferência cancelada");
				dialog.setVisible(true);
				;
			}
		});
		button_4.setBounds(48, 359, 117, 25);
		contentPane.add(button_4);
	}
}

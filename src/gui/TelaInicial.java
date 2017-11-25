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
		textFieldIp.setBounds(100, 12, 117, 19);
		contentPane.add(textFieldIp);

		JButton button = new JButton("Enviar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int port=Integer.parseInt(textFieldIp.getText());
				
			}
		});
		button.setBounds(48, 136, 117, 25);
		contentPane.add(button);

		textFieldPort = new JTextField();
		textFieldPort.setColumns(10);
		textFieldPort.setBounds(322, 12, 75, 19);
		contentPane.add(textFieldPort);
		
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

		lblNomeDoArquivo = new JLabel("Nome do arquivo: ");
		lblNomeDoArquivo.setBounds(226, 66, 348, 15);
		contentPane.add(lblNomeDoArquivo);

		lblEndereoDestino = new JLabel("IP:");
		lblEndereoDestino.setBounds(64, 14, 29, 15);
		contentPane.add(lblEndereoDestino);

		lblPortaDestino = new JLabel("Porta:");
		lblPortaDestino.setBounds(258, 14, 75, 15);
		contentPane.add(lblPortaDestino);

		lblTamanhoDoArquivo = new JLabel("Tamanho do Arquivo:");
		lblTamanhoDoArquivo.setBounds(52, 109, 522, 15);
		contentPane.add(lblTamanhoDoArquivo);

		JLabel lblEnviando = new JLabel("Enviando:");
		lblEnviando.setBounds(49, 191, 108, 15);
		contentPane.add(lblEnviando);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(163, 192, 234, 14);
		contentPane.add(progressBar);

		JLabel label_3 = new JLabel("RTT:");
		label_3.setBounds(413, 191, 70, 15);
		contentPane.add(label_3);

		JLabel lblTempoEstimado = new JLabel("Tempo Estimado:");
		lblTempoEstimado.setBounds(59, 218, 136, 15);
		contentPane.add(lblTempoEstimado);

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
		
		JLabel lblRecebendo = new JLabel("Recebendo:");
		lblRecebendo.setBounds(48, 245, 108, 15);
		contentPane.add(lblRecebendo);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setStringPainted(true);
		progressBar_1.setBounds(175, 246, 234, 14);
		contentPane.add(progressBar_1);
		
		JLabel label_1 = new JLabel("RTT:");
		label_1.setBounds(425, 245, 70, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Tempo");
		label_2.setBounds(71, 297, 70, 15);
		contentPane.add(label_2);
	}
}

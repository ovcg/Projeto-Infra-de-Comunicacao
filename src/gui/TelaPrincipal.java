package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.Serializable;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;

public class TelaPrincipal extends JFrame implements Serializable {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private String path;
	private JLabel lblNomeDoArquivo;
	private JLabel lblEndereoDestino;
	private JLabel lblPortaDestino;
	private JLabel lblTamanhoDoArquivo;
	private JLabel lblTransfer;
	private JLabel lblPortaParaRtt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(172, 80, 300, 19);
		contentPane.add(textField);
		
		
		JButton button = new JButton("Enviar");
		button.setBounds(27, 327, 117, 25);
		contentPane.add(button);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(172, 153, 300, 19);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("Escolher Arquivo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser file=new JFileChooser();
				int abrir = file.showOpenDialog(null);
				if(abrir==JFileChooser.APPROVE_OPTION) {
					
				}
			}
		});
		btnNewButton.setBounds(27, 230, 163, 25);
		contentPane.add(btnNewButton);
		
		lblNomeDoArquivo = new JLabel("Nome do arquivo: ");
		lblNomeDoArquivo.setBounds(233, 235, 348, 15);
		contentPane.add(lblNomeDoArquivo);
		
		lblEndereoDestino = new JLabel("Endereço destino:");
		lblEndereoDestino.setBounds(25, 82, 188, 15);
		contentPane.add(lblEndereoDestino);
		
		lblPortaDestino = new JLabel("Porta destino:");
		lblPortaDestino.setBounds(25, 155, 102, 15);
		contentPane.add(lblPortaDestino);
		
		lblTamanhoDoArquivo = new JLabel("Tamanho do Arquivo:");
		lblTamanhoDoArquivo.setBounds(27, 282, 249, 15);
		contentPane.add(lblTamanhoDoArquivo);
		
		lblTransfer = new JLabel("Transfer");
		lblTransfer.setForeground(Color.GREEN);
		lblTransfer.setBounds(27, 27, 163, 60);
		lblTransfer.setSize(100,50);
		contentPane.add(lblTransfer);
		
		lblPortaParaRtt = new JLabel("Porta para RTT:");
		lblPortaParaRtt.setBounds(27, 195, 117, 15);
		contentPane.add(lblPortaParaRtt);
	}
	private static class __Tmp {
		private static javax.swing.JTextField textField_2;
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
			  
			  textField_2 = new javax.swing.JTextField();
			  __wbp_panel.add(textField_2);
			  textField_2.setColumns(10);
		}
	}
}

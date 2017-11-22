package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controle.Cliente;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaEnviando extends JFrame {

	private JPanel contentPane;
	private Cliente cliente;

	public TelaEnviando(Cliente cliente) {
		this.cliente = cliente;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblProgresso = new JLabel("Progresso:");
		lblProgresso.setBounds(55, 39, 108, 15);
		contentPane.add(lblProgresso);

		JLabel lblRtt = new JLabel("RTT:");
		lblRtt.setBounds(55, 171, 70, 15);
		contentPane.add(lblRtt);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(169, 39, 234, 14);
		contentPane.add(progressBar);

		JButton btnParar = new JButton("Parar");
		btnParar.setBounds(63, 246, 117, 25);
		contentPane.add(btnParar);

		JButton continuar = new JButton("Continuar");
		continuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		continuar.setBounds(220, 246, 117, 25);
		contentPane.add(continuar);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(55, 104, 70, 15);
		contentPane.add(lblStatus);

		JLabel lblStatus_1 = new JLabel("Status2");
		lblStatus_1.setBounds(169, 104, 70, 15);
		contentPane.add(lblStatus_1);

		JLabel lblTempo = new JLabel("Tempo");
		lblTempo.setBounds(169, 171, 70, 15);
		contentPane.add(lblTempo);

		JButton btnRecomear = new JButton("Recomeçar");
		btnRecomear.setBounds(391, 246, 117, 25);
		contentPane.add(btnRecomear);
	}
}

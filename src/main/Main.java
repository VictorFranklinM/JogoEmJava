package main;

import javax.swing.JFrame; // Importa as propriedades da classe JFrame. (Cria apenas a janela)

public class Main {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame(); // Cria a janela do jogo com a classe JFrame.
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // A operação padrão ao clicar no "X" será fechar o jogo.
		window.setResizable(false); // Se é possível mudar o tamanho da janela.
		window.setTitle("Nome do Jogo"); // Nome da Janela.
		window.setLocationRelativeTo(null); // Vai renderizar a janela no meio da tela.
		
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); // Renderiza em fullscreen
		window.setUndecorated(true); // Tira as bordas da janela
		window.setVisible(true); // Se é vísivel
	}
}

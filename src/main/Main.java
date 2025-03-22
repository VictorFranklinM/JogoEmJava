package main;

import javax.swing.JFrame; // Importa as propriedades da classe JFrame. (Cria apenas a janela)
import java.awt.Graphics2D;
public class Main {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame(); // Cria a janela do jogo com a classe JFrame.
		Graphics2D g2;
		Screen Tela = new Screen(); //instanciando nossa classe Screen na main.
		window.add(Tela); //adicionando as features da nossa classe Screen dentro da nossa "window" que seria a tela
		window.setUndecorated(true); // Tira as bordas da janela
		window.pack();//faz com que as coisas aqui se adequem a tela
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // A operação padrão ao clicar no "X" será fechar o jogo.
		window.setResizable(false); // Se é possível mudar o tamanho da janela.
		window.setTitle("Nome do Jogo"); // Nome da Janela.
		window.setLocationRelativeTo(null); // Vai renderizar a janela no meio da tela.
		
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); // Renderiza em fullscreen
		window.setVisible(true); // Se é vísivel
		
		
		
		
		
		Tela.startGameThread();
	}
}

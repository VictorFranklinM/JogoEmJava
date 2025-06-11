package main;

import javax.swing.JFrame; // Importa as propriedades da classe JFrame. (Cria apenas a janela)
public class Main {
	 
	public static void main(String[] args) {
		
		JFrame window = new JFrame(); // Cria a janela do jogo com a classe JFrame.
		Screen screen = new Screen(); // Instanciando nossa classe Screen na main.
		

		window.add(screen);
		
		screen.config.loadConfig();
		
		window.setTitle("Shin Megami Tensei: Persona VI"); // Nome da Janela.
		window.setUndecorated(true); // Tira as bordas da janela.
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // A operação padrão ao clicar no "X" será fechar o jogo.
		window.setResizable(false); // Se é possível mudar o tamanho da janela.
		window.setLocationRelativeTo(null); // Vai renderizar a janela no meio da tela.
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); // Renderiza em fullscreen.
		window.setVisible(true); // Se é vísivel.
		
		screen.setupGame();
	
		screen.startGameThread(); // Começa a rodar a linha de processamento para atualizar a tela do jogo.
		
	}
}

package main;

import javax.swing.JFrame; // Importa as propriedades da classe JFrame. (Cria apenas a janela)
import java.awt.Graphics2D; // Importa as propriedades da classe JFrame. [Gerenciamento dos gráficos do jogo (eixos x e y, cor, texto, etc)]
public class Main {
	
	public static void main(String[] args) {
		Graphics2D g2;
		
		JFrame window = new JFrame(); // Cria a janela do jogo com a classe JFrame.
		Screen screen = new Screen(); // Instanciando nossa classe Screen na main.
		
		window.add(screen); /* Adicionando as features do objeto screen dentro do objeto window. 
			Window é apenas a janela do aplicativo, enquanto screen é a interface. */
		
		window.setTitle("Nome do Jogo"); // Nome da Janela.
		window.setUndecorated(true); // Tira as bordas da janela.
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // A operação padrão ao clicar no "X" será fechar o jogo.
		window.setResizable(false); // Se é possível mudar o tamanho da janela.
		window.setLocationRelativeTo(null); // Vai renderizar a janela no meio da tela.
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); // Renderiza em fullscreen.
		window.setVisible(true); // Se é vísivel.
		
		screen.setupObjects(); // Posiciona os objetos no jogo.
		
	
		screen.startGameThread(); // Começa a rodar a linha de processamento para atualizar a tela do jogo.
		
	}
}

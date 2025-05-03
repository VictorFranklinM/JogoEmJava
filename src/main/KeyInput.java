package main;

import java.awt.event.KeyEvent; // Biblioteca para checar o estado das teclas (Pressionada, solta, etc).
import java.awt.event.KeyListener; // Biblioteca para reagir ao estado das teclas (Quando pressionada fará ação X).

// Esta classe serve para receber os dados inputs do teclado.
public class KeyInput implements KeyListener {
	public boolean upHold, downHold, leftHold , rightHold, ePressed; // Lógica booleana pra estados de movimento, indo pro lado, cima, etc.
	@Override
	// Método padrão java para receber se a tecla foi pressionada ou não.
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	// Função que processa quando uma tecla é pressionada.
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); // Identifica a tecla pressionada.
		
		// Caso o a tecla seja "W" ou a seta para cima das teclas direcionais a lógica diz que o personagem deve se mover para cima.
		if(code == KeyEvent.VK_W) {
			upHold = true;
		}
		
		// Caso o a tecla seja "A" ou a seta para a esquerda das teclas direcionais a lógica diz que o personagem deve se mover para esquerda.
		if(code == KeyEvent.VK_A) {
			leftHold = true;
		}
		
		// Caso o a tecla seja "S" ou a seta para baixo das teclas direcionais a lógica diz que o personagem deve se mover para baixo.
		if(code == KeyEvent.VK_S) {
			downHold = true;
		}
		
		// Caso o a tecla seja "D" ou a seta para a direita das teclas direcionais a lógica diz que o personagem deve se mover para direita.
		if(code == KeyEvent.VK_D) {
			rightHold = true;
		}
		
		if(code == KeyEvent.VK_E) {
			ePressed = true;
		}
	}
	
	@Override
	// Quando a tecla for solta, teremos a lógica de desligar o comando de movimento.
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		// Lógica idêntica a de quando for pressionado, porém a lógica de movimento será falsa.
		if(code == KeyEvent.VK_W) {
			upHold = false;
		}
		
		// Lógica idêntica a de quando for pressionado, porém a lógica de movimento será falsa.
		if(code == KeyEvent.VK_A) {
			leftHold = false;
		}
		
		// Lógica idêntica a de quando for pressionado, porém a lógica de movimento será falsa.
		if(code == KeyEvent.VK_S) {
			downHold = false;
		}
		
		// Lógica idêntica a de quando for pressionado, porém a lógica de movimento será falsa.
		if(code == KeyEvent.VK_D) {
			rightHold = false;
		}
	}
		
}



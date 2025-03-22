package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public  class TeclasInput implements KeyListener {//esta classe serve para receber os dados input do teclado
	public boolean cimaPressionado,baixoPressionado,esquerdaPressionado,direitaPressionado;//lógica booleana pra estados de movimento , indo pro lado, cima etc.
	@Override
	public void keyTyped(KeyEvent e) {//método padrão java para receber se a tecla foi escrita ou não
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {//quando a tecla for pressionada, iremos receber o código daquela tecla e vermos sua lógica
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {//caso o código da tecla seja ''W'' a lógica diz que o boneco quer se mover para cima
			cimaPressionado = true;

		}
		if(code == KeyEvent.VK_A) {//mesma lógica de quando for W
			esquerdaPressionado = true;

		}
		if(code == KeyEvent.VK_S) {//mesma lógica de quando for W
			baixoPressionado = true;
	
	}
		if(code == KeyEvent.VK_D) {//mesma lógica de quando for W
			direitaPressionado = true;
			
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {//quando a tecla for solta, teremos a lógica de desligar o comando de mover para cima, lado etc.
		
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {//lógica idêntica a de quando for pressionado, porém a lógica de ir para cima será falsa.
			cimaPressionado = false;

		}
		if(code == KeyEvent.VK_A) {//lógica idêntica a de quando for pressionado, porém a lógica de ir para cima será falsa.
			esquerdaPressionado = false;

		}
		if(code == KeyEvent.VK_S) {//lógica idêntica a de quando for pressionado, porém a lógica de ir para cima será falsa.
			baixoPressionado = false;
	
	}
		if(code == KeyEvent.VK_D) {//lógica idêntica a de quando for pressionado, porém a lógica de ir para cima será falsa.
			direitaPressionado = false;
			
		}
	}

		
	}



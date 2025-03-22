package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel; // Importa as propriedades da classe JPanel. (Define as interações quando a janela está selecionada)


// Sub-Classe da Classe JPanel
public class Screen extends JPanel implements Runnable{
	
	final int originalTileSize = 16; // Tamanho dos Tiles do jogo. (16x16)
	final int scale = 3; // Escala dos pixels.
	final int tileSize = originalTileSize * scale; // Tile redimensionado.
	
	final int horizontalTiles = 16; // Quantos tiles horizontais (ainda não tá implementado);
	final int verticalTiles = 9; // Quantos tiles verticais (ainda não tá implementado);	
// Tamanho da tela (comentei pq não sei se precisa, já que a tela tá maximizada, então o calculo de tamanho é outro)
	//final int screenWidth = tileSize * horizontalTiles; 
	//final int screenHeight = tileSize * verticalTiles;
	
	TeclasInput tecla = new TeclasInput();
	Thread gameThread; //criar uma segunda linha de processamento por trás, para executar algo por cima do clock base
	int jogadorx = 100;
	int jogadory = 100;
	int movspeed = 4;
	int FPS = 60;
	public Screen() {
		
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(tecla);
		this.setFocusable(true);
		
	}
    public void startGameThread() {
    	gameThread = new Thread(this);
    	gameThread.start();
    	
    	
    	
    	
    	 
    	
    }
    public void atualizar() {//função que atualiza o tempo, para atualizazr as informações na tela
    	if(tecla.cimaPressionado == true) {
    		jogadory -= movspeed;
    	}
    	else if(tecla.baixoPressionado == true) {
    		jogadory += movspeed;
    	}
    	else if(tecla.esquerdaPressionado == true) {
    		jogadorx -= movspeed;
    	}
    	else if(tecla.direitaPressionado == true) {
    		jogadorx += movspeed;
    	}
    	
    	
    	
    	
    }
    public void paintComponent(Graphics g) {//função na qual desenha algo na tela, e a movimenta
    	
    	super.paintComponent(g);//super significa que esta função é filha da função pai, que seria screen. então ela ocorre por um plano abaixo
    	Graphics2D g2 = (Graphics2D)g;//mudando o gráfico do pincel para 2D, assim podendo mudar em x,y e entre outros
    	g2.setColor(Color.WHITE);//escolhendo a cor do nosso objeto g2, que seria o quadrado
    	g2.fillRect(jogadorx, jogadory, tileSize, tileSize);//criando nosso quadrado g2, com as coordenadas x,y e o tamanho 16/16
    	g2.dispose();//liberando memória, após função gráfica
    	
    	
    }

	public void run() {//linha de execução secundária do jogo, onde ocorrem as coisas na tela
		double drawInterval = 1000000000/FPS;//nosso intervalo de atualizações necessárias(1 nano segundo convertido para segundo/fps
		double delta = 0;//nossa variável de controle, ela servirá como um contador de segundos
		long lastTime = System.nanoTime();//armazenamos nosso tempo atual(que será trocado, então ele é nosso ultimo tempo
		long currentTime;//tempo atual, este será contado na hora de pintar e desenhar
		while (gameThread != null) {//enquanto o thread do jogo(tempo) está rodando,ele irá contar
			currentTime = System.nanoTime();//nosos tempo atual na thread recebe o tempo em nano segundos
			delta += (currentTime - lastTime) / drawInterval;//nosso delta vai receber o resto da diferença entre o tempo passado e o atual, dividido pelo fps. e quando der 1 segundo, irá aplicar
			
			lastTime = currentTime;//resetamos nosso tempo passado, para atualizar o loop, já que o cálculo ja foi feito
			if(delta >= 1) {//quando delta der o tempo de 1/60 frames por segundo, ele irá atualizar um frame
			atualizar();//atualiza o tempo, e desenha novamente
            repaint();//desenhar
            delta --;//reduz o delta para recalcular no nosso loop
			}
			
		}
		
	}
	
	
	

}

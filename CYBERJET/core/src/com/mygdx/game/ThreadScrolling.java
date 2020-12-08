package com.mygdx.game;

public class ThreadScrolling extends Thread {
	private float tempo;
	private final float VELOCIDADE;
	private float valor;
	
	public ThreadScrolling() {
		VELOCIDADE = 0.001f;
		valor = 11;
		this.start();
	}
	
	public void run() {
		
		System.out.println("Thread Scrolling Iniciada");
		
		while(true) {
			
			if (GameScreen.pausa == false){
				tempo= 1f - GameScreen.tempoJogo;
				GameScreen.backgroundOffset+=5;
				if (GameScreen.backgroundOffset % GameScreen.WORLD_WIDTH == 0) {
					GameScreen.backgroundOffset = 0;
				}
					
				try {
					if (valor>5) {
						valor-=VELOCIDADE;
						}
					Thread.sleep((long) (valor));
				}
				catch(InterruptedException e) {
					System.out.println("Erro Thread Scrolling: " + e);
				}
			}
			else {
				Thread.interrupted();
			}
			if (GameScreen.permissaoThread == true) {
				break;
			}
		}
		System.out.println("Thread Scrolling Finalizada");
		
	}
	
}

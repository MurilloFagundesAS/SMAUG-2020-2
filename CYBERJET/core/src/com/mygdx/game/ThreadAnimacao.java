package com.mygdx.game;

public class ThreadAnimacao extends Thread {

	public ThreadAnimacao() {
		this.start();
	}

	
	public void run() {
		
		System.out.println("Thread Animação Iniciada");
		
		while (true) {
			if (GameScreen.pausa == false) {
				try {
					GameScreen.frameAtual++;
					if (GameScreen.frameAtual > GameScreen.qtdeFrames) {
						GameScreen.frameAtual = 1;
					}
						
					Thread.sleep(100);
				}
				catch(InterruptedException e) {
					System.out.println("Erro Thread Animação: " + e);
				}
			}
			else {
				Thread.interrupted();
			}
			
			
			if (GameScreen.permissaoThread == true) {
				break;
			}
		}
		System.out.println("Thread Animação Finalizada");
	}
}

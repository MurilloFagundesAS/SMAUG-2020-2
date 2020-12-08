package com.mygdx.game;


public class ThreadPontuacao extends Thread {	
	
	public ThreadPontuacao() {
		this.start();	
	}
	
	
	public void run() {
		
		System.out.println("Thread Pontuação Iniciando");
		
		while (true) {
			if (GameScreen.pausa == false) {
				if (GameScreen.personagem.vidas > 0) {
					if (GameScreen.personagem.escudoTempo > 0) {
						GameScreen.personagem.escudoTempo -=1;
					}
					
					GameScreen.pontuacao +=1;
							
					try {
						Thread.sleep(1000);
					}
					catch(InterruptedException e) {
						System.out.println("Erro Thread PontuaÃ§Ã£o: " + e);
					}
				}
			}
			else {
				Thread.interrupted();
			}
			if (GameScreen.permissaoThread == true) {
				break;
			}
		}
		System.out.println("Thread Pontuação Finalizada");
	}
	
	
}

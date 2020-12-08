package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class ThreadColisaoMunicao extends Thread {

	Sound fx2;
	
	public ThreadColisaoMunicao() {
		fx2 = Gdx.audio.newSound(Gdx.files.internal("fx/arranco.wav"));
		this.start();
	}
	
	
	public void run() {
		System.out.println("Thread Colisão Municao Iniciada");
		
		while (true) {
			if (GameScreen.pausa == false) {
				if (GameScreen.listaObstaculos.size>0) {
					for (int i=0; i< GameScreen.listaObstaculos.size; i++) {
						Obstaculo obstaculo = GameScreen.listaObstaculos.get(i);
						for (int j=0; j< GameScreen.listaMunicao.size; j++) {
							Municao municao = GameScreen.listaMunicao.get(j);
							if (obstaculo.getHitbox().overlaps(municao.getHitbox())) {
								fx2.play();
								municao.remove();
								GameScreen.listaMunicao.removeValue(municao, true);
								obstaculo.remove();
								GameScreen.listaObstaculos.removeValue(obstaculo, true);
							}
							}
						}
					}
				
				try {
					Thread.sleep(10);
				}
				catch (InterruptedException e) {
					System.out.println("Erro Thread Colisão: " + e);
				}
			}
			else {
				Thread.interrupted();
			}
			
			
			if (GameScreen.permissaoThread == true) {
				break;
			}		
		}
		System.out.println("Thread Colisão Municao Finalizada");
	}
	
	
}

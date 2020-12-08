package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class ThreadColisaoObstaculo extends Thread {

	Sound fx1;
	
	
	public ThreadColisaoObstaculo() {
		fx1 = Gdx.audio.newSound(Gdx.files.internal("fx/arranco.wav"));
		this.start();
	}
	
	public void run (){
		
		System.out.println("Thread Colisão Obstaculo Iniciada");
		
		while (true) {
			if (GameScreen.pausa == false){
				if (GameScreen.listaObstaculos.size>0) {
					for (int i=0; i< GameScreen.listaObstaculos.size; i++) {
						Obstaculo obstaculo = GameScreen.listaObstaculos.get(i);
						if(obstaculo.ispersonagemBateu(GameScreen.personagem)&& !obstaculo.isLevandoHit()) {
							if (GameScreen.personagem.escudoTempo == 0) {
								if(GameScreen.personagem.getSprite().getY() == obstaculo.getSprite().getY()){
									fx1.play(2f);
									obstaculo.levaHit();
									GameScreen.personagem.vidas -= 1;
								}
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
		System.out.println("Thread Colisão Obstaculo Finalizada");
		
	}
		
	
}
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class ThreadColisaoColetavel extends Thread {

	Sound fx3;
	
	
	public ThreadColisaoColetavel() {
		fx3 = Gdx.audio.newSound(Gdx.files.internal("fx/upgrade.wav"));
		this.start();
	}
	
	
	public void run() {
		System.out.println("Thread Colisão Coletáveis Iniciada");
		
		while (true) {
			if (GameScreen.pausa == false) {
				if (GameScreen.listaColetavelMunicao.size>0) {
					for (int i=0; i< GameScreen.listaColetavelMunicao.size; i++) {
						ColetavelMunicao municoes = GameScreen.listaColetavelMunicao.get(i);
						if(municoes.ispersonagemBateu(GameScreen.personagem)&& !municoes.isLevandoHit()) {
							if (municoes.getSprite().getY() == GameScreen.personagem.getSprite().getY()) {
								fx3.play(2f);
								municoes.levaHit();
								while (GameScreen.personagem.municao <5) {
									GameScreen.personagem.municao += 1;
								}
								municoes.remove();
								GameScreen.listaColetavelMunicao.removeValue(municoes, true);
							}
						}
					}
				}
				
				if (GameScreen.listaColetavelEscudo.size>0) {
					for (int i=0; i< GameScreen.listaColetavelEscudo.size; i++) {
						ColetavelEscudo escudo = GameScreen.listaColetavelEscudo.get(i);
						if(escudo.ispersonagemBateu(GameScreen.personagem)&& !escudo.isLevandoHit()) {
							if (escudo.getSprite().getY() == GameScreen.personagem.getSprite().getY()) {
								fx3.play(2f);
								escudo.levaHit();
								GameScreen.personagem.escudoTempo += 5;
								escudo.remove();
								GameScreen.listaColetavelEscudo.removeValue(escudo, true);
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
		System.out.println("Thread Colis�o Coletáveis Finalizada");
	}
	

}

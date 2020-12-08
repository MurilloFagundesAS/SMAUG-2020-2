package com.mygdx.game;

import java.util.Random;


public class ThreadObstaculo extends Thread {
	
	private int tempo;
	private Random randomico;
	private int x;
	
	public static int qtdeInimigos;
	public static boolean permissao;
	public static int[] posicaoY;
	
	
	public ThreadObstaculo() {
		tempo = 3500;
		qtdeInimigos = 0;
		posicaoY = new int[2];
		permissao = false;
		randomico = new Random();
		this.start(); 
	}
	
	public void run() {
		
		System.out.println("Thread Obst�culo Iniciada");
		
		while (true) {
			
			if (GameScreen.pausa == false) {
							
				x = randomico.nextInt(6)+1;
				permissao = true;
				
				switch(x) {
					case 1:
	//					System.out.println("A");
						qtdeInimigos = 1;
						posicaoY[0] = 0;
						break;
					case 2:
	//					System.out.println("B");
						qtdeInimigos = 1;
						posicaoY[0] = 50;
						break;
					case 3:
	//					System.out.println("C");
						qtdeInimigos = 1;
						posicaoY[0] = 100;
						break;
					case 4:
	//					System.out.println("A e B");
						qtdeInimigos = 2;
						posicaoY[0] = 50;
						posicaoY[1] = 0;
						break;
					case 5:
	//					System.out.println("A e C");
						qtdeInimigos = 2;
						posicaoY[0] = 100;
						posicaoY[1] = 0;
						break;
					case 6:
	//					System.out.println("B e C");
						qtdeInimigos = 2;
						posicaoY[0] = 100;
						posicaoY[1] = 50;
						break;
				}
				
				if (GameScreen.pontuacao % 10 == 0 && tempo > 1000) {
					tempo -= 500;
				}
				
				try {
					Thread.sleep(tempo);
				}
				catch(InterruptedException e) {
					System.out.println("Erro Thread Obst�culo: " + e);
				}
			}
			else {
				Thread.interrupted();
			}
			if (GameScreen.permissaoThread == true) {
				break;
			}
		}
		
		System.out.println("Thread Obst�culo Finalizada");
	}

	
}
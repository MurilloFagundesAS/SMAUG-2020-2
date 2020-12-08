package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class ThreadMusica extends Thread {
	
	public static Music musica;
	
	public ThreadMusica() {
		musica = Gdx.audio.newMusic(Gdx.files.internal("sounds/theme.wav"));
		musica.setVolume(1f);
		this.start();
	}
	
	
	public void run() {
		System.out.println("Thread Musica Iniciada");
		
		musica.setLooping(true);
		musica.play();
		
		System.out.println("Thread Musica Finalizada");			
		
	}
}

package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;


public class Obstaculo extends Objeto {

	TextureRegion obstaculoRegion;
	
	private static boolean levaHit;

	
	public Obstaculo(int posicaoY,int obstaculoFrame) {
		
		this.obstaculoRegion = GameScreen.obstaculoAtlas.findRegion("obstaculo"+String.format("%s", obstaculoFrame));
		Sprite sp = new Sprite(obstaculoRegion);
		sp.setSize(80,80);
		setSprite(sp);
		
		this.setPosition(GameScreen.WORLD_WIDTH, posicaoY);
		this.setBounds(getSprite().getX(), getSprite().getY(), getSprite().getWidth(), getSprite().getHeight());
		
		
		setHitbox( new Circle(this.getX()+(getSprite().getWidth()/2),
				this.getY()+(getSprite().getHeight()/2), 25));
		
		levaHit = false;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.getHitbox().setPosition(this.getSprite().getX()+(this.getSprite().getWidth()/2),
				this.getSprite().getY()+(getSprite().getHeight()/2));
		getSprite().draw(GameScreen.batch);
	}
	
	public boolean ispersonagemBateu(Protagonista protagonista) {
		if (protagonista.isVivo()){
		return Intersector.overlaps(protagonista.getHitbox(), this.getHitbox());
		}
		return false;
	}
	
	public boolean isLevandoHit() {
		return levaHit;
	}
	
	public void levaHit() {
		levaHit = true;
	}
	
	@Override
	public void act(float delta) {
		if (GameScreen.pausa == false) {
			super.act(delta);
			getSprite().translateX(-5);
			this.setPosition(getSprite().getX(), getSprite().getY());
			if (this.getX() + this.getWidth() < 0) {
				GameScreen.listaObstaculos.removeValue(this, true);
			}
		}
	}

}

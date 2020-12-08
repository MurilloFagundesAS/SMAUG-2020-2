package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;


public class Municao extends Objeto {
	
	
	public Municao(float posicaoX, float posicaoY) {
		
		setSprite(new Sprite(new Texture("disparo.png")));
		
		setPosition(posicaoX, posicaoY);
		
		setHitbox(new Circle(getSprite().getX()+(getSprite().getWidth()/2),
				getSprite().getY()+(getSprite().getHeight()/2), 20));
		
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.getHitbox().setPosition(getSprite().getX()+(getSprite().getWidth()/2), getSprite().getY()+(getSprite().getHeight()/2));
		getSprite().draw(GameScreen.batch);
	}
	
	@Override
	public void act(float delta) {
		if (GameScreen.pausa == false) {
			super.act(delta);
			getSprite().translateX(+5f);
			this.setPosition(getSprite().getX(), getSprite().getY());
			if (this.getX() > GameScreen.WORLD_WIDTH) {
				GameScreen.listaMunicao.removeValue(this, true);
			}
		}
	}

}

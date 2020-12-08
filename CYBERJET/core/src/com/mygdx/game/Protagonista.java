package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;


public class Protagonista extends Objeto {
		
	TextureAtlas personagemAtlas;
	TextureRegion personagemRegion;
	ShapeRenderer shapeRenderer;
	
	Sound fx4;
	
	public static int vidas;
	
	public static boolean disparo;
	public static int municao;		
	public static int escudoTempo;
	
	public Protagonista() {
		personagemAtlas = new TextureAtlas(Gdx.files.internal("moto.atlas"));
		personagemRegion = personagemAtlas.findRegion("Moto1");
		setSprite(new Sprite(personagemRegion));
		setPosition(100, 0);
		
		fx4 = Gdx.audio.newSound(Gdx.files.internal("fx/disparo.wav"));
		
		vidas = 1;
		disparo = false;
		municao = 0;
		escudoTempo = 0;
		
		setHitbox(new Circle(this.getX()+(getSprite().getWidth()/2),
				this.getY()+(getSprite().getHeight()/2), 25));

		addListener(new InputListener() {
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
					if (keycode == Input.Keys.UP && GameScreen.pausa == false) {
						if(getSprite().getY()+50 < 101) {
							getSprite().translateY(50);
							setPosition(100, getSprite().getY());
						}
					}
					if (keycode == Input.Keys.DOWN && GameScreen.pausa == false){
						if (getSprite().getY()-50 > -1) {
							getSprite().translateY(-50);
							setPosition(100, getSprite().getY());
						}
					}
					if (keycode == Input.Keys.SPACE && GameScreen.pausa == false) {
						if (municao > 0) {
							disparo = true;
							fx4.play();
						}
					}
					return true;
			}
			
		});
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (escudoTempo > 0) {
			getSprite().setRegion(personagemAtlas.findRegion("Escudo"+String.format("%s", GameScreen.frameAtual)));
		}
		else{
			getSprite().setRegion(personagemAtlas.findRegion("Moto"+String.format("%s", GameScreen.frameAtual)));
		}
		getHitbox().setPosition(getSprite().getX()+(getSprite().getWidth()/2), getSprite().getY()+(getSprite().getHeight()/2));
		getSprite().draw(GameScreen.batch);
	}
	
	@Override
	public void act(float delta) {
		if (GameScreen.pausa == false) {
			this.setPosition(getSprite().getX(), getSprite().getY());
			if (vidas <= 0) {
				this.addAction(Actions.removeActor());
				
				setHitbox(null); 
			}
			super.act(delta);
		}
	}
	
	public boolean isVivo() {
		return vidas>0;
	}

}

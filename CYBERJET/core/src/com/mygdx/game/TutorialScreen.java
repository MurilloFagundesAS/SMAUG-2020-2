package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TutorialScreen extends ScreenAdapter{
	Stage stage;
	SpriteBatch batch;
	private Texture voltarImg;
	private MainCyberJet mainCyberJet;
	private Image background;
	
	
	public TutorialScreen(MainCyberJet mainCyberJet) {
		this.mainCyberJet = mainCyberJet;
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		voltarImg = new Texture(Gdx.files.internal("back.png"));
		
		background = new Image (new Texture (Gdx.files.internal("tutorial2.jpg")));
		stage.addActor(background);
		
		TextureRegionDrawable voltar = new TextureRegionDrawable(voltarImg);
		voltar.setMinSize(80,80);
		ImageButton voltarBtn = new ImageButton(voltar);
		stage.addActor(voltarBtn);
		voltarBtn.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				mainCyberJet.setScreen(new MenuScreen(mainCyberJet,true));
			}
		});
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		stage.act();
		stage.draw();
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		voltarImg.dispose();
	}

}

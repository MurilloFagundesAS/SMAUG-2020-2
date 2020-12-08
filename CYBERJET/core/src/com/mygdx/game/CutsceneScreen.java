package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class CutsceneScreen extends ScreenAdapter{
	
	private SpriteBatch batch;
	private BitmapFont fonte;
	
	private MainCyberJet mainCyberJet;	
	
	TextureAtlas cutsceneAtlas;
	Animation<TextureRegion> animation;
	float tempo;
	
	private Music musicaCutscene;
	
	
	public CutsceneScreen(MainCyberJet mainCyberJet) {
		this.mainCyberJet = mainCyberJet;
	}
	

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		batch = new SpriteBatch();
		
		fonte = new BitmapFont(Gdx.files.internal("fonte/cyberdynexpand.fnt"));
		fonte.setColor(Color.BLACK);
				
		cutsceneAtlas = new TextureAtlas(Gdx.files.internal("cenas.atlas"));
		animation = new Animation<TextureRegion> (1/1f, cutsceneAtlas.getRegions());
		animation.setFrameDuration(5);
		tempo = 0;
		
		musicaCutscene = Gdx.audio.newMusic(Gdx.files.internal("sounds/theme.wav"));
		musicaCutscene.play();
		
	}

	
	@Override
	public void render(float delta) {
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			this.dispose();
		}
		
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		tempo += Gdx.graphics.getDeltaTime();
		
		batch.draw(animation.getKeyFrame(tempo, false), 0, 0);
		
		
		fonte.draw(batch, "PRESSIONE", 10, Gdx.graphics.getHeight()-fonte.getXHeight());
		fonte.draw(batch, "SPACE", 80, Gdx.graphics.getHeight()-(2*fonte.getXHeight()));
		
		if (animation.isAnimationFinished(tempo)) {
			this.dispose();
		}
		
		
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
		musicaCutscene.stop();
		musicaCutscene.dispose();
		mainCyberJet.setScreen(new MenuScreen(mainCyberJet));
	}

}

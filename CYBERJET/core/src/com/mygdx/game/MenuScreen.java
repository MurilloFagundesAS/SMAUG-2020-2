package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class MenuScreen extends ScreenAdapter {
	private Image background;
//	private Texture jogarImg;
	private Texture creditosImg;
	private Texture tutorialImg;
	private Texture rankingImg;
	private Stage stage;
	private Music musicaMenu;
	private Boolean isMusicaTocando = false;
	private MainCyberJet mainCyberJet;
	public static Array<MenuScreen> listaMenu= new Array<MenuScreen>();
	
	public MenuScreen(MainCyberJet mainCyberJet) {		
		this.mainCyberJet = mainCyberJet;
		MenuScreen.listaMenu.add(this);
	}
	public MenuScreen(MainCyberJet mainCyberJet,Boolean isMusicaTocando) {		
		this(mainCyberJet);
		this.isMusicaTocando=isMusicaTocando;
	}
	
	
	@Override
	public void show() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		//Carrega as imagens 
//		jogarImg = new Texture(Gdx.files.internal("Play.png"));
		creditosImg = new Texture(Gdx.files.internal("logo2.png"));
		tutorialImg = new Texture(Gdx.files.internal("tuto.png"));
		rankingImg = new Texture(Gdx.files.internal("score.png"));
		
		//background
		background = new Image (new Texture(Gdx.files.internal("menu.jpg")));
		stage.addActor(background);
		
		musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("sounds/theme.wav"));
		if (!isMusicaTocando) {
		musicaMenu.play();
		musicaMenu.setLooping(true);
		}
		//Botao de creditos 
		TextureRegionDrawable imagemcreditos = new TextureRegionDrawable(creditosImg);
		imagemcreditos.setMinSize(100, 100);
		ImageButton creditos = new ImageButton(imagemcreditos);
		creditos.setPosition(Gdx.graphics.getWidth()/2-(imagemcreditos.getMinWidth()/2), 10);
		stage.addActor(creditos);
		creditos.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				// TODO Auto-generated method stub
				super.tap(event, x, y, count, button);
				mainCyberJet.setScreen(new CreditosScreen(mainCyberJet));
				dispose();
			}
		});
		
		//Bot�o de tutorial 
		TextureRegionDrawable imagetutorial = new TextureRegionDrawable(tutorialImg);
		imagetutorial.setMinSize(100, 100);
		ImageButton tutorial = new ImageButton(imagetutorial);
		tutorial.setPosition(Gdx.graphics.getWidth()/2-(imagetutorial.getMinWidth()/2)-imagetutorial.getMinWidth(), 10);
		stage.addActor(tutorial);
		tutorial.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				//TODO Auto-generated method stub
				super.tap(event, x, y, count, button);
				mainCyberJet.setScreen(new TutorialScreen(mainCyberJet));
				dispose();
			}
		});
		
		//Bot�o de ranking 
		TextureRegionDrawable imageranking = new TextureRegionDrawable(rankingImg);
		imageranking.setMinSize(100, 100);
		ImageButton ranking = new ImageButton(imageranking);
		ranking.setPosition(Gdx.graphics.getWidth()/2-(imageranking.getMinWidth()/2)+imageranking.getMinWidth(), 10);
		stage.addActor(ranking);
		ranking.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				//TODO Auto-generated method stub
				super.tap(event, x, y, count, button);
				mainCyberJet.setScreen(new RankingScreen(mainCyberJet));
				dispose();
			}
		});
				
		//Bot�o jogar
//		TextureRegionDrawable imagemBotao = new TextureRegionDrawable(jogarImg);
//		imagemBotao.setMinSize(100, 100);
//		ImageButton jogar = new ImageButton(imagemBotao);
//		jogar.setPosition(Gdx.graphics.getWidth()/2-(imagemBotao.getMinWidth()/2), Gdx.graphics.getHeight()/2);
//		
//		stage.addActor(jogar);
//		jogar.addListener(new ActorGestureListener() {
//			@Override
//			public void tap(InputEvent event, float x, float y, int count, int button) {
//				super.tap(event, x, y, count, button);
//				mainCyberJet.setScreen(new GameScreen(mainCyberJet));
//				dispose();
//			}
//		});
		
	}

	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//inicia jogo
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			
			for (MenuScreen menuScreen : listaMenu) {
				menuScreen.musicaMenu.stop();
				menuScreen.musicaMenu.dispose();
			}
			this.mainCyberJet.setScreen(new GameScreen(mainCyberJet));
		}
		
		stage.act();
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {

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
//		jogarImg.dispose();
		tutorialImg.dispose();
		rankingImg.dispose();
		creditosImg.dispose();
		
	}
}

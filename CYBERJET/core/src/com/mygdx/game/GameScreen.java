package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GameScreen extends ScreenAdapter {
	
	//cen�rio
	public static SpriteBatch batch;
	Sprite background;
	ShapeRenderer shapeRenderer;
	
	public static Stage stage;
	
	//public static Sprite personagem;
	public static Protagonista personagem;
	
	public static TextureAtlas obstaculoAtlas;
	
	
	//sprites do hud
	private Sprite spriteMunicao;
	private Sprite spritePausa;
	
	TextureAtlas vidaAtlas;
	Animation<TextureRegion> animacaoVida;
	float tempo;

	private int posicaoVida;
	private int posicaoMunicao;
	
	//fonte
	BitmapFont fonte;
	
	//vari�veis para o scrolling
	public static int backgroundOffset;
	public static int WORLD_WIDTH = 800;
	public static int WORLD_HIGHT = 600;
	
	public static  int pontuacao;

	//vari�veis para anima��o
	public static int frameAtual;
	public static int qtdeFrames;
		
	public static Array<Obstaculo> listaObstaculos = new Array<>();
	public static Array<Municao> listaMunicao = new Array<>();
	public static Array<ColetavelVida> listaColetavelVida = new Array<>();
	public static Array<ColetavelMunicao> listaColetavelMunicao = new Array<>();
	public static Array<ColetavelEscudo> listaColetavelEscudo = new Array<>();
	
	
	public static float tempoJogo;
	
	//vari�veis dos colet�veis e obst�culos
	int contagemObstaculos;
	int contagemVida;
	int contagemMunicao;
	int contagemEscudo;
	Random randomColetaveis;
	int coletavel;
	boolean permisaoColetavel;
	Random randomObstaculos;
	int frameObstaculo;
	
	public static boolean pausa;
	
	//threads
	public static boolean permissaoThread;
	
	ThreadMusica threadMusica;
	ThreadColisaoObstaculo threadColisaoObstaculo;
	ThreadAnimacao threadAnimacao;
	ThreadScrolling threadScrolling;
	ThreadColisaoColetavel threadColisaoColetavel;
	ThreadColisaoMunicao threadColicaoMunicao;
	ThreadObstaculo threadObstaculo;
	ThreadPontuacao threadPontuacao;
	
	
	
	private MainCyberJet mainCyberJet;
	
	
	public GameScreen(MainCyberJet mainCyberJet) {
		this.mainCyberJet = mainCyberJet;
	}
	
	
	@Override
	public void show () {
		
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		
		
		personagem = new Protagonista();
		stage.addActor(personagem);
		stage.setKeyboardFocus(personagem);
				
		
		batch = new SpriteBatch();
		background = new Sprite(new Texture(Gdx.files.internal("background.jpeg")));
		backgroundOffset = 0;
		
		fonte = new BitmapFont(Gdx.files.internal("fonte/cyberdynexpand.fnt"));
		
		spritePausa = new Sprite(new Texture(Gdx.files.internal("pause.png")));
		
		frameAtual = 1;
		qtdeFrames = 2;
		
		vidaAtlas = new TextureAtlas(Gdx.files.internal("vida.atlas"));
		animacaoVida = new Animation<TextureRegion> (1/1f, vidaAtlas.getRegions());
		animacaoVida.setFrameDuration(1);
		tempo = 0;		
		posicaoVida = Gdx.graphics.getWidth() - 64;
		
		obstaculoAtlas = new TextureAtlas(Gdx.files.internal("obstaculos.atlas"));
		
		spriteMunicao = new Sprite(new Texture(Gdx.files.internal("municao.png")));
		posicaoMunicao = Gdx.graphics.getWidth() - (int) spriteMunicao.getWidth();
		
		pausa = false;
		permissaoThread = false;
		
		threadScrolling = new ThreadScrolling();
		threadAnimacao = new ThreadAnimacao();
		threadObstaculo = new ThreadObstaculo();
		threadColisaoObstaculo = new ThreadColisaoObstaculo();
		threadColicaoMunicao = new ThreadColisaoMunicao();
		threadColisaoColetavel = new ThreadColisaoColetavel();
		threadMusica = new ThreadMusica();
		threadPontuacao = new ThreadPontuacao();
		
		pontuacao = 0;
		contagemObstaculos = 0;
		randomColetaveis = new Random();
		contagemVida = 0;
		contagemMunicao = 0;
		contagemEscudo = 0;
		permisaoColetavel = false;
		
		randomObstaculos = new Random();
		
		shapeRenderer = new ShapeRenderer();
	}
	
	
	@Override
	public void render (float delta) {
		
		
		//sistema de pausa
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			if(pausa == false) {
				pausa = true;
				threadMusica.musica.pause();
				this.pause();
			}
			else {
				pausa = false;
				threadMusica.musica.play();
				this.resume();
			}
		}
		

		if (!personagem.isVivo()) {
			try {
				personagem.getSprite().translateX(-15);
				if (personagem.getSprite().getX()<0-personagem.getSprite().getWidth()*4) {
					permissaoThread = true;
					threadMusica.musica.stop();
					mainCyberJet.setScreen(new MorteScreen(mainCyberJet));
					this.dispose();
				}
				//
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		//limpeza da tela
		Gdx.gl.glClearColor(0, 0, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tempoJogo = delta;
		
		batch.begin();
			
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
				
		//scrolling horizontal
		Scrolling();
		
		
		stage.act(Gdx.graphics.getDeltaTime());
		
		//Removendo atores da tela
		for (Actor ator : stage.getActors()) {
			if (ator.getX() + ator.getWidth() < 0) {
				ator.addAction(Actions.removeActor());
//				System.out.println(ator + " Removido!");
			}
			if (ator.getX() > WORLD_WIDTH) {
				ator.addAction(Actions.removeActor());
//				System.out.println(ator + " Removido!");
			}
		}
		
		if (threadObstaculo.permissao == true) {
			permisaoColetavel = true;
			for (int i = 0; i< threadObstaculo.qtdeInimigos; i++) {
				coletavel = randomColetaveis.nextInt(10) + 1;
				
				if (coletavel == 10 && permisaoColetavel == true) {
					ColetavelMunicao coletavelMunicao = new ColetavelMunicao(threadObstaculo.posicaoY[i]);
					
					coletavelMunicao.setName("coletavelMunicao" + String.format("%s", contagemMunicao));
					listaColetavelMunicao.add(coletavelMunicao);
					stage.addActor(coletavelMunicao);
					contagemMunicao +=1;
					
					permisaoColetavel = false;					
				} else {
					if(coletavel == 9 && permisaoColetavel == true) {
						ColetavelEscudo coletavelEscudo = new ColetavelEscudo(threadObstaculo.posicaoY[i]);
						coletavelEscudo.setName("coletavelEscudo" + String.format("%s", contagemEscudo));
						listaColetavelEscudo.add(coletavelEscudo);
						stage.addActor(coletavelEscudo);
						contagemEscudo +=1;
						
						permisaoColetavel = false;
					
					}else {
						frameObstaculo = randomObstaculos.nextInt(8) + 1;
						
						Obstaculo obstaculo = new Obstaculo(threadObstaculo.posicaoY[i], frameObstaculo);
						
						obstaculo.setName("obstaculo" + String.format("%s", contagemObstaculos));
						listaObstaculos.add(obstaculo);
						stage.addActor(obstaculo);
						
						contagemObstaculos +=1;
//						}
					}
				}
			}
			threadObstaculo.permissao = false;
			permisaoColetavel = true;
		}
		
		
		//disparo
		if (personagem.disparo == true) {
			Municao municao = new Municao(personagem.getSprite().getX()+(personagem.getSprite().getWidth()/2),
					personagem.getSprite().getY()+(personagem.getSprite().getHeight()/2));
			
			municao.setName("municao"+String.format("%s", personagem.municao));
			listaMunicao.add(municao);
			stage.addActor(municao);
			
			personagem.municao -=1;
			personagem.disparo = false;
		}
		
		
		//stage draw por camada
		int[] posicaoAtores = {100, 100+(int)(personagem.getSprite().getHeight()/2),
				50, 50+(int)(personagem.getSprite().getHeight()/2), 0, 0+(int)(personagem.getSprite().getHeight()/2)};
		for (int i = 0; i < posicaoAtores.length; i++) {
			for (Actor ator: stage.getActors()) {
				if (ator.getY() == posicaoAtores[i]) {
					ator.draw(batch, 0f);
				}
			}
		}
		
		//desenhando fonte
		fonte.draw(batch, "Pontos: " + String.format("%s", pontuacao), 20, Gdx.graphics.getHeight() - fonte.getXHeight());//desenhando fonte
		
		//desenhando sprite da vida
		posicaoVida = Gdx.graphics.getWidth() - 64;
		tempo += Gdx.graphics.getDeltaTime();
		for (int i = 1; i < (personagem.vidas+1); i++) {
			if (personagem.escudoTempo > 0) {
				fonte.draw(batch, String.format("%d", personagem.escudoTempo), 600, Gdx.graphics.getHeight() - fonte.getXHeight());
			}
			batch.draw(animacaoVida.getKeyFrame(tempo, true), posicaoVida, Gdx.graphics.getHeight() - 64);
			posicaoVida -= 64;
		}
		
		//desenhando sprite municao
		posicaoMunicao = Gdx.graphics.getWidth() - (int) spriteMunicao.getWidth();
		for (int i = 1; i < (personagem.municao+1); i++) {
			batch.draw(spriteMunicao, posicaoMunicao, Gdx.graphics.getHeight() - spriteMunicao.getHeight() - 64);
			posicaoMunicao -= spriteMunicao.getWidth();
		}
		
		if (pausa == true) {
			batch.draw(spritePausa, (Gdx.graphics.getWidth()/2)-100, Gdx.graphics.getHeight()/2);	
		}
		
		batch.end();
		shapeRenderer.end();
		 		
	}
	
	
	@Override
	public void dispose () {
		listaColetavelEscudo.clear();
		listaColetavelMunicao.clear();
		listaColetavelVida.clear();
		listaMunicao.clear();
		listaObstaculos.clear();
									
		threadMusica.musica.dispose();
		threadColisaoObstaculo.fx1.dispose();
		threadColicaoMunicao.fx2.dispose();
		threadColisaoColetavel.fx3.dispose();
		
		obstaculoAtlas.dispose();
		shapeRenderer.dispose();		
		stage.dispose();
		fonte.dispose();
	}
	
	public void Scrolling() {		
		batch.draw(background, -backgroundOffset, 0, WORLD_WIDTH, WORLD_HIGHT);
		//projeta imagem j� remodelando no tamanho da tela
		batch.draw(background, -backgroundOffset+WORLD_WIDTH, 0, WORLD_WIDTH, WORLD_HIGHT);
	}

}
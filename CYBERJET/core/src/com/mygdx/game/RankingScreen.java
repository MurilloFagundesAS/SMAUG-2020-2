package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class RankingScreen extends ScreenAdapter{
	Stage stage;
	private BitmapFont fonteTitulo;
	private BitmapFont fonteCorpo;
	SpriteBatch batch;
	private Texture voltarImg;
	private MainCyberJet mainCyberJet;
	private Image background;
	
	public static Array<Jogador> listaJogadores = new Array<>();
	public static Array<Jogador> listaRanking = new Array<>();
	Jogador primeiro;
	
	float altura;
	
	public RankingScreen(MainCyberJet mainCyberJet) {
		this.mainCyberJet = mainCyberJet;
		ConexaoBD.leituraDados();
	}

	@Override
	public void show() {		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		fonteCorpo = new BitmapFont(Gdx.files.internal("fonte/cyberdynexpandCorpo.fnt"));
		fonteTitulo = new BitmapFont(Gdx.files.internal("fonte/cyberdynexpand.fnt"));
		voltarImg = new Texture(Gdx.files.internal("back.png"));
		
		background = new Image (new Texture (Gdx.files.internal("ranking2.png")));
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
				dispose();
			}
		});
		
		altura = Gdx.graphics.getHeight()- fonteCorpo.getXHeight()-100;
				
		for (int i = 0; i < 5; i++){
			boolean condicao = true;
			for(Jogador jogador : listaJogadores) {
				if(condicao == true){
					primeiro = jogador;
					condicao = false;
				}
				if(jogador.pontuacao > primeiro.pontuacao) {
					primeiro = jogador;
				}
				
			}
			listaJogadores.removeValue(primeiro, true);
			listaRanking.add(primeiro);
		}
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		stage.act();
		stage.draw();
		
		fonteTitulo.draw(batch, "RANKING", Gdx.graphics.getWidth()/2-100, Gdx.graphics.getHeight()-10);
		
		altura = Gdx.graphics.getHeight()- fonteCorpo.getXHeight()-100;
		for(Jogador jogador : listaRanking) {
			fonteCorpo.draw(batch,
					jogador.nome + " - " + jogador.pontuacao + " - " + jogador.data,
					20, altura);
			altura -= 80;
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
		listaJogadores.clear();
		listaRanking.clear();
		stage.dispose();
		fonteCorpo.dispose();
		fonteTitulo.dispose();
		voltarImg.dispose();
		

	}

}

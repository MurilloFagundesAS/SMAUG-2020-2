package com.mygdx.game;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MorteScreen extends ScreenAdapter{
	Stage stage;
	private BitmapFont fonte;
	SpriteBatch batch;
	private Texture voltarImg;
	private Texture playImg;
	private MainCyberJet mainCyberJet;
	private Image background;
	private StringBuilder typeArea;
	
	SimpleDateFormat formato;
	boolean permissao;
	
	public MorteScreen(MainCyberJet mainCyberJet) {
		this.mainCyberJet = mainCyberJet;
		formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	}

	@Override
	public void show() {
		typeArea = new StringBuilder();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		batch = new SpriteBatch();
		fonte = new BitmapFont(Gdx.files.internal("fonte/cyberdynexpand.fnt"));
		voltarImg = new Texture(Gdx.files.internal("back.png"));
		playImg = new Texture(Gdx.files.internal("Play.png"));
		
		background = new Image (new Texture (Gdx.files.internal("fundo.png")));
		stage.addActor(background);
		
		permissao = true;
		
		//botão voltar menu
		TextureRegionDrawable voltar = new TextureRegionDrawable(voltarImg);
		voltar.setMinSize(80,80);
		ImageButton voltarBtn = new ImageButton(voltar);
		stage.addActor(voltarBtn);
		voltarBtn.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				super.tap(event, x, y, count, button);
				mainCyberJet.setScreen(new MenuScreen(mainCyberJet));
			}
		});
		
		//botão jogar novamente
		TextureRegionDrawable play = new TextureRegionDrawable(playImg);
		play.setMinSize(80,80);
		ImageButton jogarNovamente = new ImageButton(play);
		jogarNovamente.setPosition((Gdx.graphics.getWidth()/2)-play.getLeftWidth()-15, 180);
		stage.addActor(jogarNovamente);
		
		jogarNovamente.addListener(new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count, int button) {
				mainCyberJet.setScreen(new GameScreen(mainCyberJet));
				dispose();
			}
		});
		
		InputListener il = new InputListener(){
	        @Override
	        public boolean keyDown(InputEvent event, int keycode) {
	              
	            if(permissao == true) {
	                switch (keycode) {
					case Keys.DEL:
						if (typeArea.length()>0){
						typeArea.deleteCharAt(typeArea.length()-1);
						}
						break;
					case Keys.SPACE:
						typeArea.append(" ");
						break;
					case Keys.ENTER:
						
						Date data = new Date();
						
						ConexaoBD.inserirDados(String.format("%s", typeArea),
								String.format("%s", GameScreen.pontuacao),
								formato.format(data));
						
						permissao = false;
						
						break;
					default:
						if (keycode!=Keys.ALT_LEFT&&keycode!=Keys.ALT_RIGHT
							&&keycode!=Keys.SHIFT_LEFT&&keycode!=Keys.SHIFT_RIGHT
							&&keycode!=Keys.CONTROL_LEFT&&keycode!=Keys.CONTROL_RIGHT
							&&keycode!=Keys.TAB&&keycode!=Keys.SYM
							&&keycode!=Keys.NUM&&keycode!=Keys.NUMPAD_0
							&&keycode!=Keys.NUMPAD_0&&keycode!=Keys.NUMPAD_1
							&&keycode!=Keys.NUMPAD_2&&keycode!=Keys.NUMPAD_3
							&&keycode!=Keys.NUMPAD_4&&keycode!=Keys.NUMPAD_5
							&&keycode!=Keys.NUMPAD_6&&keycode!=Keys.NUMPAD_7
							&&keycode!=Keys.NUMPAD_8&&keycode!=Keys.NUMPAD_9
							&&keycode!=Keys.PLUS&&keycode!=Keys.EXPLORER
							&&keycode!=Keys.LEFT&&keycode!=Keys.RIGHT
							&&keycode!=Keys.UP&&keycode!=Keys.DOWN
							&&keycode!=Keys.MEDIA_PLAY_PAUSE) {
						typeArea.append(Keys.toString(keycode));
						}
						break;
					}
	        	}
	                
	                
	            return super.keyDown(event, keycode);
	        }
	    };
	    
	    stage.addListener(il);
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		fonte.draw(batch, "PONTUAÇÃO: " + String.format("%s", GameScreen.pontuacao), Gdx.graphics.getWidth()/3-25, Gdx.graphics.getHeight()-(3*fonte.getXHeight()));
		
		fonte.draw(batch, typeArea, Gdx.graphics.getWidth()/2-(typeArea.length()*15), Gdx.graphics.getHeight()- fonte.getXHeight()-100);
		
		if(permissao == false) {
			fonte.draw(batch, "DADOS SALVOS!", Gdx.graphics.getWidth()/3-50, Gdx.graphics.getHeight()- fonte.getXHeight()-200);
		}
		else {
			fonte.draw(batch, "PRESSIONE ENTER", 160, Gdx.graphics.getHeight()- fonte.getXHeight()-200);
			fonte.draw(batch, "PARA SALVAR", 240, Gdx.graphics.getHeight()- fonte.getXHeight()-300);
		}
		
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
		fonte.dispose();
		voltarImg.dispose();
		playImg.dispose();
	}

}
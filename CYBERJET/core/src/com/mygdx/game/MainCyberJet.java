package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class MainCyberJet extends Game{
	
	private AssetManager AssetManager;
	
	
	public AssetManager getAssetManager() {
		return AssetManager;
	}
	public void setAssetManager(AssetManager assetManager) {
		AssetManager = assetManager;
	}
	@Override
	public void create() {
		setScreen(new CutsceneScreen(this));
	}
}

package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Objeto extends Actor{
	private Sprite sprite;
	private Circle hitbox;
	
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	public Circle getHitbox() {
		return hitbox;
	}
	public void setHitbox(Circle hitbox) {
		this.hitbox = hitbox;
	}
	
	
	
	@Override
	protected void positionChanged() {
			this.sprite.setPosition(getX(), getY());
			super.positionChanged();
	
	}
	public void draw(Batch batch, float parentAlpha) {
		this.hitbox.setPosition(this.sprite.getX()+(this.sprite.getWidth()/2), this.sprite.getY()+(this.sprite.getHeight()/2));
		sprite.draw(GameScreen.batch);
	}
	public void drawDebug(ShapeRenderer shapeRenderer) {
		shapeRenderer.circle(this.sprite.getX()+(this.sprite.getWidth()/2), this.sprite.getY()+(this.sprite.getHeight()/2), this.hitbox.radius);
	}
}

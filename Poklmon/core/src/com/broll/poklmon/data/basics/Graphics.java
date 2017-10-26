package com.broll.poklmon.data.basics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Graphics {

	public SpriteBatch sb;
	private Color color = new Color();
	private ShapeRenderer shapeRenderer;
	private BitmapFont font = new BitmapFont(true);

	public Graphics() {
		sb = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
	}

	public void prepareRender(Camera camera) {
		camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		sb.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void drawString(String string, float x, float y) {
		font.setColor(color);
		font.draw(sb, string, x, y + font.getCapHeight() / 2);
	}

	public void fillArc(float x, float y, float radius, float start, float end) {
		sb.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		shapeRenderer.arc(x+radius, y+radius, radius, start, end);
		shapeRenderer.end();
		sb.begin();
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	public void fillRect(float x, float y, float w, float h) {
		sb.end();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(x, y, w, h);
		shapeRenderer.end();
		sb.begin();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	public void drawRect(float x, float y, float w, float h) {
		sb.end();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(x, y, w, h);
		shapeRenderer.end();
		sb.begin();
	}

	public void drawImage(Image image, float x, float y) {
		image.draw(x, y);
	}

	public void fillRoundRect(float x, float y, float width, float height, float radius) {

		sb.end();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
			// Central rectangle
			shapeRenderer.rect(x + radius, y + radius, width - 2 * radius, height - 2 * radius);

			// Four side rectangles, in clockwise order
			shapeRenderer.rect(x + radius, y, width - 2 * radius, radius);
			shapeRenderer.rect(x + width - radius, y + radius, radius, height - 2 * radius);
			shapeRenderer.rect(x + radius, y + height - radius, width - 2 * radius, radius);
			shapeRenderer.rect(x, y + radius, radius, height - 2*radius);

			// Four arches, clockwise too
			shapeRenderer.arc(x + radius, y + radius, radius, 180f, 90f);
			shapeRenderer.arc(x + width - radius, y + radius, radius, 270f, 90f);
			shapeRenderer.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
			shapeRenderer.arc(x + radius, y + height - radius, radius, 90f, 90f);
		shapeRenderer.end();
		sb.begin();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	public Color getColor() {
		return color;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void drawLine(float x, float y, float x2, float y2) {
		sb.end();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(color);
		shapeRenderer.line(x, y, x2, y2);
		shapeRenderer.end();
		sb.begin();
	}

}

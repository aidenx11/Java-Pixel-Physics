package com.aidenx11.game.color;

import com.badlogic.gdx.graphics.Color;

public class CustomColor extends ColorManager {

	private Color color = new Color();
	private int r;
	private int g;
	private int b;

	public CustomColor(ColorValues colorValue) {
		int[] rgb = colorValue.getRGB();
		r = rgb[0];
		g = rgb[1];
		b = rgb[2];
		setColor(r, g, b);
	}

	public CustomColor(int[] rgb) {
		r = rgb[0];
		g = rgb[1];
		b = rgb[2];
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public int[] varyColor() {
		return super.varyColor(this);
	}

	public void setColor(int r, int g, int b) {
		color.set((float) (r / 255.0), (float) (g / 255.0), (float) (b / 255.0), 1);
	}

	public Color getColor() {
		return color;
	}

}

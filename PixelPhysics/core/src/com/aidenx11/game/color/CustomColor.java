package com.aidenx11.game.color;

import com.badlogic.gdx.graphics.Color;

public class CustomColor extends ColorManager {

	private Color color = new Color();
	private int r;
	private int g;
	private int b;
	private int a;
	
	public enum ColorValues {

		SAND_COLOR(247, 222, 137), WOOD_COLOR(160, 121, 61), SKY_COLOR(53, 81, 92), RED(255, 0, 0), ORANGE(255, 127, 0),
		YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 127, 255), PURPLE(127, 0, 255), SMOKE(132, 136, 132);

		/** Hex Code of the color */
		private int r;
		private int g;
		private int b;

		/**
		 * Constructor for ColorValue
		 * 
		 * @param hexCode hexCode of the color
		 */
		private ColorValues(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}

		/**
		 * Returns the hexCode of the color value
		 * 
		 * @return the hexCode of the color
		 */
		public int[] getRGB() {
			return new int[] { r, g, b };
		}

	}

	public enum ColorSets {
		
	}

	public CustomColor(ColorValues colorValue) {
		int[] rgb = varyColor(new CustomColor(colorValue.getRGB()));
		setColor(rgb);
	}

	public CustomColor(int[] rgb) {
		r = rgb[0];
		g = rgb[1];
		b = rgb[2];
		if (rgb.length == 4) {
			a = rgb[3];
		}
	}
	
	public CustomColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public CustomColor(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
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
	
	public int getA() {
		return a;
	}

	public int[] varyColor() {
		return super.varyColor(this);
	}
	
	public void randomizeColor() {
		r = (int) (Math.random() * 256);
		g = (int) (Math.random() * 256);
		b = (int) (Math.random() * 256);
		setColor(r, g, b);
	}

	public void setColor(int[] rgb) {
		r = rgb[0];
		g = rgb[1];
		b = rgb[2];
		if (rgb.length == 4) {
			setColor(r, g, b, rgb[3]);
		} else {
			setColor(r, g, b);
		}
	}
	
	public void setColor(int r, int g, int b) {
		color.set((float) (r / 255.0), (float) (g / 255.0), (float) (b / 255.0), 1);
	}
	
	public void setColor(int r, int g, int b, int a) {
		color.set((float) (r / 255.0), (float) (g / 255.0), (float) (b / 255.0), (float) (a / 100));
	}

	public Color getColor() {
		return color;
	}

}

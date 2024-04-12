package com.aidenx11.game.color;

import com.badlogic.gdx.graphics.Color;

/**
 * Class to handle custom colors for the simulation. Primarily created to use the
 * colorValue enumeration. May be better to just use the color class.
 * 
 * @author Aiden Schroeder
 */
public class CustomColor extends ColorManager {

	/** Keeps track of the color in a LibGDX color object */
	private Color color = new Color();
	/** Red value of this color */
	private int r;
	/** Green value of this color */
	private int g;
	/** Blue value of this color */
	private int b;
	/** Alpha value of this color */
	private int a;

	/**
	 * Enumeration to handle custom color values for elements and rainbow colors
	 */
	public enum ColorValues {

		SAND_COLOR(247, 222, 137), WOOD_COLOR(122, 83, 48), SKY_COLOR(53, 81, 92), RED(255, 0, 0), ORANGE(255, 127, 0),
		YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 127, 255), PURPLE(127, 0, 255), SMOKE(132, 136, 132),
		FIRE(242, 125, 12), WATER(15, 94, 156), WET_SAND(202, 164, 88), STEAM(199, 213, 224), LEAF(109, 179, 63),
		DIRT(89, 58, 14), WET_DIRT(153, 117, 65), STONE(152, 160, 167), LAVA_RED(207, 51, 30),
		LAVA_ORANGE(242, 157, 44), OBSIDIAN(51, 35, 64), LAVA_YELLOW(219, 173, 36), STEEL(113, 121, 126),
		RUST(151, 77, 45);

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

	/**
	 * Enumeration for sets of colors. May be used later.
	 */
	public enum ColorSets {

	}

	/**
	 * Creates a new custom color using a color value from the ColorValues
	 * enumeration
	 * 
	 * @param colorValue color value being used
	 */
	public CustomColor(ColorValues colorValue, boolean varyColor) {
		int[] rgb;
		if (varyColor) {
			rgb = varyColor(new CustomColor(colorValue.getRGB()));
		} else {
			rgb = colorValue.getRGB();
		}
		setColor(rgb);
	}

	/**
	 * Creates a custom color given an integer array of RGB values. If alpha is
	 * included in array, sets it. Otherwise, sets to zero.
	 * 
	 * @param rgb integer array of rgb values
	 */
	public CustomColor(int[] rgb) {
		r = rgb[0];
		g = rgb[1];
		b = rgb[2];
		if (rgb.length == 4) {
			a = rgb[3];
		}
	}

	/**
	 * Creates a custom color given red, green, and blue values
	 * 
	 * @param r red value
	 * @param g green value
	 * @param b blue values
	 */
	public CustomColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Creates a custom color given red, green, blue, and alpha values
	 * 
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 * @param a alpha value
	 */
	public CustomColor(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Returns the r value of this color
	 * 
	 * @return the r value of this color
	 */
	public int getR() {
		return r;
	}

	/**
	 * Returns the g value of this color
	 * 
	 * @return the g value of this color
	 */
	public int getG() {
		return g;
	}

	/**
	 * Returns the b value of this color
	 * 
	 * @return the b value of this color
	 */
	public int getB() {
		return b;
	}

	/**
	 * Returns the a value of this color
	 * 
	 * @return the a value of this color
	 */
	public int getA() {
		return a;
	}

	/**
	 * Varies the color of this color (saturation and lightness) using the varyColor
	 * method in ColorManager
	 * 
	 * @return and integer array containing the rgb values of the varied color
	 */
	public int[] varyColor() {
		return super.varyColor(this);
	}

	/**
	 * Sets the color of this color given an integer array of rgb(and possibly a)
	 * values
	 * 
	 * @param rgb integer array of the color values
	 */
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

	/**
	 * Sets the color given r, g, and b as integers
	 * 
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 */
	public void setColor(int r, int g, int b) {
		color.set((float) (r / 255.0), (float) (g / 255.0), (float) (b / 255.0), 1);
	}

	/**
	 * Sets the color given r, g, b, and a as integers
	 * 
	 * @param r red value
	 * @param g green value
	 * @param b blue value
	 * @param a alpha value
	 */
	public void setColor(int r, int g, int b, int a) {
		color.set((float) (r / 255.0), (float) (g / 255.0), (float) (b / 255.0), (float) (a / 100));
	}

	public Color getColor() {
		return color;
	}

}

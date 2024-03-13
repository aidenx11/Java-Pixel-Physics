package com.aidenx11.game.color;

public enum ColorValues {

	SAND_COLOR(255, 224, 138);

	/** Hex Code of the color */
	private int r;
	private int g;
	private int b;

	/**
	 * Constructor for ColorValue
	 * 
	 * @param hexCode hexCode of the color
	 */
	ColorValues(int r, int g, int b) {
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
		return new int[] {r, g, b};
	}

}

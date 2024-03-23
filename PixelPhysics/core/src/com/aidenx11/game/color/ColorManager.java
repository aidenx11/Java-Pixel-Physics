package com.aidenx11.game.color;

import com.aidenx11.game.color.CustomColor.ColorValues;
import com.badlogic.gdx.graphics.Color;

public class ColorManager extends Color {

	private static int placeInRainbow = 0;

	/**
	 * Varies the saturation and lightness of a color and returns it as a hex string
	 * 
	 * @param color color to be varied
	 * @return the varied color as a hex string
	 */
	public static int[] varyColor(CustomColor color) {
		int[] hslValues = convertToHSLFromRGB(new int[] { color.getR(), color.getG(), color.getB() });

		double saturation = hslValues[1] + Math.floor(Math.random() * (20) - 20);

		if (saturation > 100) {
			saturation = 100;
		} else if (saturation < 0) {
			saturation = 0;
		}

		double lightness = hslValues[2] + Math.floor(Math.random() * (20) - 10);

		if (lightness > 100) {
			lightness = 100;
		} else if (lightness < 0) {
			lightness = 0;
		}

		hslValues[1] = (int) Math.round(saturation);
		hslValues[2] = (int) Math.round(lightness);

		return convertToRGBFromHSL(hslValues);
	}

	/**
	 * Converts a hexadecimal color to an HSL color in the format
	 * "hue,saturation%,lightness%"
	 * 
	 * @param hex hexadecimal string to convert
	 * @return HSL color in the format "hue,saturation%,lightness%"
	 */
	public static int[] convertToHSLFromRGB(int[] rgb) {
		// Parse RGB values from hexadecimal color
		double r = rgb[0];
		double g = rgb[1];
		double b = rgb[2];

		double rPrime = r / 255.0;
		double gPrime = g / 255.0;
		double bPrime = b / 255.0;

		double max = Math.max(Math.max(rPrime, gPrime), bPrime);
		double min = Math.min(Math.min(rPrime, gPrime), bPrime);

		double chroma = max - min;
		double hue;
		double saturation;
		double lightness;

		// Calculate hue
		if (chroma == 0) {
			hue = 0;
		} else if (max == rPrime) {
			hue = ((gPrime - bPrime) / chroma) % 6;
		} else if (max == gPrime) {
			hue = (bPrime - rPrime) / chroma + 2;
		} else {
			hue = (rPrime - gPrime) / chroma + 4;
		}

		hue = Math.round(hue * 60);

		if (hue < 0) {
			hue += 360;
		}

		// Calculate lightness and saturation
		lightness = (max + min) / 2;
		saturation = chroma == 0 ? 0 : chroma / (1 - Math.abs(2 * lightness - 1));

		// Convert to whole percentages
		lightness = lightness * 100;
		saturation = saturation * 100;

		return new int[] { (int) Math.round(hue), (int) Math.round(saturation), (int) Math.round(lightness) };
	}

	/**
	 * Converts the given HSL color to hexadecimal and returns it in the form
	 * #RRGGBB
	 * 
	 * @param hsl HSL string to convert to HEX
	 * @return HEX string of the color in the form #RRGGBB
	 */
	public static int[] convertToRGBFromHSL(int[] hslValues) {

		double hue = hslValues[0];
		double saturation = hslValues[1] / 100.0;
		double lightness = hslValues[2] / 100.0;

		double chroma = ((1 - Math.abs(2 * lightness - 1)) * saturation);
		double x = (chroma * (1 - Math.abs((hue / 60) % 2 - 1)));
		double m = lightness - chroma / 2;
		double r = 0;
		double g = 0;
		double b = 0;

		if (0 <= hue && hue < 60) {
			r = chroma;
			g = x;
			b = 0;
		} else if (60 <= hue && hue < 120) {
			r = x;
			g = chroma;
			b = 0;
		} else if (120 <= hue && hue < 180) {
			r = 0;
			g = chroma;
			b = x;
		} else if (180 <= hue && hue < 240) {
			r = 0;
			g = x;
			b = chroma;
		} else if (240 <= hue && hue < 300) {
			r = x;
			g = 0;
			b = chroma;
		} else if (300 <= hue && hue < 360) {
			r = chroma;
			g = 0;
			b = x;
		}

		r = Math.round((r + m) * 255);
		g = Math.round((g + m) * 255);
		b = Math.round((b + m) * 255);

		int[] rgb = new int[] { (int) r, (int) g, (int) b };

		return rgb;
	}

	public static int[] reduceLightness(CustomColor color) {
		int[] rgb = new int[] { color.getR(), color.getB(), color.getG() };
		if (rgb[2] > 0) {
			rgb[2] = rgb[2] - 1;
		}
		return rgb;
	}

	/**
	 * Generates a color value in the rainbow
	 * 
	 * @return a color value in the rainbow
	 */
	public static ColorValues generateRainbowColor() {

		switch (placeInRainbow) {
		case 0:
			placeInRainbow++;
			return ColorValues.RED;
		case 1:
			placeInRainbow++;
			return ColorValues.ORANGE;
		case 2:
			placeInRainbow++;
			return ColorValues.YELLOW;
		case 3:
			placeInRainbow++;
			return ColorValues.GREEN;
		case 4:
			placeInRainbow++;
			return ColorValues.BLUE;
		case 5:
			placeInRainbow = 0;
			return ColorValues.PURPLE;
		}

		return ColorValues.RED;
	}
}

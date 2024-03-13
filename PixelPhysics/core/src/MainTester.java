import java.util.Arrays;

import com.aidenx11.game.color.ColorManager;
import com.aidenx11.game.color.ColorValues;
import com.aidenx11.game.color.CustomColor;

public class MainTester {

	public static void main(String[] args) {
		CustomColor sand = new CustomColor(ColorValues.SAND_COLOR);
		System.out.println(Arrays.toString(sand.varyColor()));
		int[] hsl = ColorManager.convertToHSLFromRGB(ColorValues.SAND_COLOR.getRGB());
		System.out.println(Arrays.toString(hsl));
		System.out.println(Arrays.toString(ColorManager.convertToRGBFromHSL(hsl)));

	}

}

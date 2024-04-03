package com.aidenx11.game;

import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.input.MouseInput;
import com.aidenx11.game.input.MouseInput.BrushTypes;
import com.aidenx11.game.ui.UIStage;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class pixelPhysicsGame extends ApplicationAdapter {

	/** Width of the screen */
	public static final int SCREEN_WIDTH = 1447;
	/** Height of the screen */
	public static final int SCREEN_HEIGHT = 900;
	/** Offset of the ui */
	public static final int uiOffset = 170;
	/** */
	public static final float GRAVITY_ACCELERATION = 0.1f;
	/** Pixel size modifier of the game */
	public static int pixelSizeModifier = 3;
	/** Matrix for use in the game */
	public static CellularMatrix matrix;
	public static boolean lightsOn;

	/** Number of rows of the matrix */
	public static int rows;
	/** Number of columns of the matrix */
	public static int columns;

	/** Shape renderer to be used for this game */
	public ShapeRenderer shapeRenderer;
	/** Camera to be used for this game */
	private OrthographicCamera camera;
	/** Viewport of this game */
	private Viewport viewport;
	/** Mouse input controller */
	private MouseInput mouse;

	public static Vector3 mousePosLastFrame = new Vector3();
	/** Stage to handle buttons/ui elements */
	private UIStage buttonStage;

//	private Label frameLabel;

	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		camera.update();

		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setAutoShapeType(true);

		rows = (int) Math.ceil(SCREEN_HEIGHT / pixelSizeModifier);
		columns = (int) Math.ceil((SCREEN_WIDTH - uiOffset) / pixelSizeModifier);

		matrix = new CellularMatrix(rows, columns, pixelSizeModifier);

		mouse = new MouseInput(matrix, camera);
		mouse.setElementType(ElementTypes.SAND);
		mouse.setBrushType(BrushTypes.SQUARE);
		mouse.setBrushSize(1);
		mouse.setCursorSize(1);

		lightsOn = true;
		buttonStage = new UIStage(viewport, mouse, matrix);

		Gdx.input.setInputProcessor(buttonStage);

	}

	@Override
	public void render() {

		// Set blue background
		if (lightsOn) {
			ScreenUtils.clear(135 / 255f, 206 / 255f, 235 / 255f, 1);
		} else {
			ScreenUtils.clear(9 / 255f, 30 / 255f, 54 / 255f, 1);
		}

		shapeRenderer.begin(ShapeType.Filled);
		if (lightsOn) {
			shapeRenderer.setColor(Color.GRAY);
		} else {
			shapeRenderer.setColor(Color.DARK_GRAY);
		}
		shapeRenderer.rect(SCREEN_WIDTH - uiOffset, 0, uiOffset + 3, SCREEN_HEIGHT);
		shapeRenderer.setColor(Color.BROWN);
		shapeRenderer.rect(SCREEN_WIDTH - uiOffset - 2, 0, 2f, SCREEN_HEIGHT);
		shapeRenderer.end();

		// Draw the buttons to the screen
		buttonStage.draw();

		mousePosLastFrame.set(Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY(), 0);

		// Detects mouse input and sets pixel if it is in bounds
		mouse.detectInput();

		// Perform sand settling logic
		matrix.draw(shapeRenderer);
		matrix.updateFrame(shapeRenderer);

		mouse.drawCursor(shapeRenderer);

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.setToOrtho(false, width, height);
	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}

}

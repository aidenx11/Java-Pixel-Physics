package com.aidenx11.game;

import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.input.MouseInput;
import com.aidenx11.game.ui.UIStage;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class pixelPhysicsGame extends ApplicationAdapter {

	/** Width of the screen */
	public static final int SCREEN_WIDTH = 1447;
	/** Height of the screen */
	public static final int SCREEN_HEIGHT = 900;
	/** */
	public static final float GRAVITY_ACCELERATION = 0.3f;
	/** Pixel size modifier of the game */
	public static int pixelSizeModifier = 7;
	/** Matrix for use in the game */
	public static CellularMatrix matrix;

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

		rows = (int) Math.ceil((SCREEN_HEIGHT - 50) / pixelSizeModifier) + 1;
		columns = (int) Math.ceil(SCREEN_WIDTH / pixelSizeModifier) + 1;

		matrix = new CellularMatrix(rows, columns, pixelSizeModifier);

		mouse = new MouseInput(matrix, camera);
		mouse.setElementType(ElementTypes.SAND);

		buttonStage = new UIStage(viewport, mouse, matrix);

		Gdx.input.setInputProcessor(buttonStage);
		
//		frameLabel = new Label("", new Skin(Gdx.files.internal("skin/uiskin.json")));
//		frameLabel.setWidth(50f);
//		frameLabel.setHeight(20f);
//		frameLabel.setPosition(850f, Gdx.graphics.getHeight() - 25f);
//		buttonStage.addActor(frameLabel);

	}

	@Override
	public void render() {

		// Set blue background
		ScreenUtils.clear(135 / 255f, 206 / 255f, 235 / 255f, 1);
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(0, SCREEN_HEIGHT - 48, SCREEN_WIDTH, 1);
		shapeRenderer.end();

		// Draw the buttons to the screen
		buttonStage.draw();

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

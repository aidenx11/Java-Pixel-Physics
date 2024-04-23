package com.aidenx11.game;

import com.aidenx11.game.elements.Element;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.elements.Empty;
import com.aidenx11.game.input.MouseInput;
import com.aidenx11.game.input.MouseInput.BrushTypes;
import com.aidenx11.game.ui.UIStage;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Main class of the simulation. Handles all fields related to window size, UI,
 * gravity, matrix fields, and input. Constructs all necessary objects and
 * handles updating of the simulation every frame, as well as updating of the
 * simulation when the window is resized.
 * 
 * @author Aiden Schroeder
 */
public class pixelPhysicsGame extends ApplicationAdapter {

	/** Width of the screen */
	public static int SCREEN_WIDTH = 1440;

	/** Height of the screen */
	public static int SCREEN_HEIGHT = 900;

	/** Offset of the ui */
	public static final int uiOffset = 170;

	/** Acceleration due to gravity. Used in movable elements */
	public static final float GRAVITY_ACCELERATION = 0.1f;

	/** Pixel size modifier of the game */
	public static int pixelSizeModifier = 5;

	/** Matrix for use in the game */
	public static CellularMatrix matrix;

	/** Number of rows of the matrix */
	public static int rows;

	/** Number of columns of the matrix */
	public static int columns;

	/** Controls whether the game is in light or dark mode */
	public static boolean lightsOn = true;

	/** Controls whether the simulation is paused or not */
	public static boolean isPaused = false;

	/** Shape renderer to be used for this game */
	public static ShapeRenderer shapeRenderer;

	/** Camera to be used for this game */
	private OrthographicCamera camera;

	/** Viewport of this game */
	private Viewport viewport;

	/** Mouse input controller */
	private MouseInput mouse;

	/** Brush type of the mouse */
	public static BrushTypes mouseBrushType = BrushTypes.CIRCLE;

	/** Element type of the mouse */
	public static ElementTypes mouseElementType = ElementTypes.SAND;

	/** Size of the mouse brush */
	public static int mouseBrushSize = 1;

	/**
	 * Keeps track of the position of the mouse in the previous frame. Used to draw
	 * continuous lines.
	 */
	public static Vector3 mousePosLastFrame = new Vector3();

	/** Stage to handle buttons/ui elements */
	private UIStage buttonStage;

	private InputMultiplexer im;

	/** Color of the light mode background */
	public static float[] skyColorLight = new float[] { 114 / 255f, 194 / 255f, 231 / 255f };

	/** Color of the dark mode background */
	public static float[] skyColorDark = new float[] { 9 / 255f, 30 / 255f, 54 / 255f };

	/**
	 * Initializes all fields needed for running the game, including camera,
	 * viewport, shape renderer, matrix, mouse input, and UI.
	 */
	@Override
	public void create() {

		// Initializes the camera to screen width and height
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		camera.update();

		// Initializes viewport to screen width and height
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);

		// Initializes the shape renderer to project onto the camera
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setAutoShapeType(true);

		// Determines number of rows and columns for the matrix based on pixel size
		// modifier
		rows = (int) Math.ceil(SCREEN_HEIGHT / pixelSizeModifier);
		columns = (int) Math.ceil((SCREEN_WIDTH - uiOffset) / pixelSizeModifier);

		// Make sure rows and columns are at least zero to avoid negative sized arrays
		if (rows < 0) {
			rows = 0;
		}

		if (columns < 0) {
			columns = 0;
		}

		// Initialize the matrix
		matrix = new CellularMatrix(rows, columns, pixelSizeModifier);

		// Initialize mouse input and set brush and cursor size to 1, and brush set to
		// circle with sand selected
		mouse = new MouseInput(matrix, camera);
		mouse.setElementType(mouseElementType);
		mouse.setBrushType(mouseBrushType);
		mouse.setBrushSize(mouseBrushSize);
		mouse.setCursorSize(mouseBrushSize);

		// initializes the UI
		buttonStage = new UIStage(viewport, mouse, matrix);

		// Set input processor to UI so UI can detect input
		im = new InputMultiplexer();

		im.addProcessor(buttonStage);
		im.addProcessor(mouse);

		Gdx.input.setInputProcessor(im);

	}

	/**
	 * Updates the viewport each frame. Performs all game logic and drawing of
	 * background and UI every frame.
	 */
	@Override
	public void render() {

		// Set background color based on light mode
		if (lightsOn) {
			ScreenUtils.clear(skyColorLight[0], skyColorLight[1], skyColorLight[2], 1);
		} else {
			ScreenUtils.clear(skyColorDark[0], skyColorDark[1], skyColorDark[2], 1);
		}

		// Draw the UI background on the right side of the screen
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

		// Draw the UI to the screen
		buttonStage.draw();

		// Update the mouse position last frame for use in MouseInput
		mousePosLastFrame.set(Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY(), 0);

		// Detects mouse input and performs manipulation on matrix depending on brush
		// type/size/element etc.
		mouse.detectInput(shapeRenderer);

		// Perform matrix update logic for all elements and draw it to the screen
		if (!isPaused) {
			matrix.updateFrame(shapeRenderer);
		}

		matrix.draw(shapeRenderer);

		// Draw the mouse cursor to the screen (left at end of render so it appears on
		// top)
		mouse.drawCursor(shapeRenderer);

	}

	/**
	 * Activates on resizing of window. Updates screen width and height and
	 * recreates the scene, deleting all elements in matrix.
	 * 
	 * @param width  new width of screen
	 * @param height new height of screen
	 */
	@Override
	public void resize(int width, int height) {

		// Establish new screen width and height
		SCREEN_WIDTH = width;
		SCREEN_HEIGHT = height;

		// Store the old matrix
		CellularMatrix oldMatrix = new CellularMatrix(rows >= 0 ? rows : 0, columns >= 0 ? columns : 0,
				pixelSizeModifier);
		int oldRows = rows;
		int oldCols = columns;

		// fill the old matrix
		for (int row = 0; row < CellularMatrix.rows; row++) {
			for (int col = 0; col < CellularMatrix.columns; col++) {
				oldMatrix.setElement(matrix.getElement(row, col));
			}
		}

		// update the screen to set it to new size
		this.create();

		// Re populate the new size matrix with as much of the old matrix as possible
		for (int row = 0; row < CellularMatrix.rows; row++) {
			for (int col = 0; col < CellularMatrix.columns; col++) {
				Element elementToSet;
				if (row >= 0 && row < oldRows && col >= 0 && col < oldCols) {
					elementToSet = oldMatrix.getElement(row, col);
				} else {
					elementToSet = new Empty(row, col);
				}
				elementToSet.setRow(row);
				elementToSet.setColumn(col);
				elementToSet.parentMatrix = matrix;
				matrix.setElement(elementToSet);
			}
		}

		// Get rid of the old matrix
		oldMatrix.clear();

	}

	/**
	 * Disposes of resources in RAM. Executes when the window is closed.
	 */
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		buttonStage.dispose();
		matrix = null;
	}

}

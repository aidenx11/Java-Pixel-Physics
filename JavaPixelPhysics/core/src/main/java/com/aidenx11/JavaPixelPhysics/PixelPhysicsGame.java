package com.aidenx11.JavaPixelPhysics;

import com.aidenx11.JavaPixelPhysics.elements.Element;
import com.aidenx11.JavaPixelPhysics.elements.Element.ElementTypes;
import com.aidenx11.JavaPixelPhysics.elements.Empty;
import com.aidenx11.JavaPixelPhysics.input.MouseInput;
import com.aidenx11.JavaPixelPhysics.input.MouseInput.BrushTypes;
import com.aidenx11.JavaPixelPhysics.ui.UIStage;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import space.earlygrey.shapedrawer.*;

/**
 * Main class of the simulation. Handles all fields related to window size, UI,
 * gravity, matrix fields, and input. Constructs all necessary objects and
 * handles updating of the simulation every frame, as well as updating of the
 * simulation when the window is resized.
 * 
 * @author Aiden Schroeder
 */
public class PixelPhysicsGame extends ApplicationAdapter {

//	public FPSLogger logger = new FPSLogger();

	/** Width of the screen */
	public static int SCREEN_WIDTH = 1440;

	/** Height of the screen */
	public static int SCREEN_HEIGHT = 900;

	/** Offset of the ui */
	public static final int uiOffset = 130;

	/** Acceleration due to gravity. Used in movable elements */
	public static final float GRAVITY_ACCELERATION = 0.1f;

	/** Pixel size modifier of the game */
	public static int pixelSizeModifier = 6;

	public static int chunkSize = 10;

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

	/** Shape drawer to be used in the simulation */
	public static ShapeDrawer shapeDrawer;

	public static Texture texture;

	/** Batch for shapeDrawer */
	public static PolygonSpriteBatch batch;

	/** Camera to be used for this game */
	private OrthographicCamera camera;

	/** Viewport of this game */
	private Viewport viewport;

	/** Mouse input controller */
	private MouseInput mouse;

	/** Brush type of the mouse */
	public static BrushTypes mouseBrushType = BrushTypes.SQUARE;

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

		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel(0, 0);
		texture = new Texture(pixmap); // remember to dispose of later
		pixmap.dispose();
		TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);

		batch = new PolygonSpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		shapeDrawer = new ShapeDrawer(batch, region);
		shapeDrawer.update();

		// Determines number of rows and columns for the matrix based on pixel size
		// modifier
		rows = (int) Math.ceil(SCREEN_HEIGHT / (float) pixelSizeModifier);
		columns = (int) Math.ceil((SCREEN_WIDTH - uiOffset + pixelSizeModifier) / (float) pixelSizeModifier);

		// Make sure rows and columns are at least zero to avoid negative sized arrays
		if (rows < 0) {
			rows = 0;
		}

		if (columns < 0) {
			columns = 0;
		}

		// Initialize the matrix
		matrix = new CellularMatrix(rows, columns, pixelSizeModifier, chunkSize);

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

//		System.out.println("rows: " + rows);
//		System.out.println("cols: " + columns);

	}

	/**
	 * Updates the viewport each frame. Performs all game logic and drawing of
	 * background and UI every frame.
	 */
	@Override
	public void render() {

		// Set background color based on light mode
		if (lightsOn) {
			ScreenUtils.clear(skyColorLight[0], skyColorLight[1], skyColorLight[2], 1, false);
		} else {
			ScreenUtils.clear(skyColorDark[0], skyColorDark[1], skyColorDark[2], 1, false);
		}

		batch.begin();

		// Update the mouse position last frame for use in MouseInput
		mousePosLastFrame.set(Gdx.input.getX(), SCREEN_HEIGHT - Gdx.input.getY(), 0);

		// Detects mouse input and performs manipulation on matrix depending on brush
		// type/size/element etc.
		mouse.detectInput(shapeDrawer);

		// Perform matrix update logic for all elements and draw it to the screen
		if (!isPaused) {
			matrix.updateFrame(shapeDrawer);
		}

		matrix.draw(shapeDrawer);

		if (lightsOn) {
			shapeDrawer.setColor(Color.GRAY);
		} else {
			shapeDrawer.setColor(Color.DARK_GRAY);
		}

		shapeDrawer.filledRectangle(SCREEN_WIDTH - uiOffset + pixelSizeModifier, 0, uiOffset + 3, SCREEN_HEIGHT);
		shapeDrawer.setColor(Color.BROWN);
		shapeDrawer.filledRectangle(SCREEN_WIDTH - uiOffset + pixelSizeModifier, 0, 2f, SCREEN_HEIGHT);

		buttonStage.act();

//		 Used for debugging
//		CellularMatrix.drawChunks(shapeDrawer);

		batch.end();

		// Draw the UI to the screen
		buttonStage.draw();

		batch.begin();

		mouse.drawCursor(shapeDrawer);
		
		batch.end();

		CellularMatrix.stepChunks();

		if (UIStage.brushSizeSlider.isDragging()) {
			mouse.setBrushSize((int) UIStage.brushSizeSlider.getValue());
			mouse.setCursorSize((int) UIStage.brushSizeSlider.getValue());
		}

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
				pixelSizeModifier, chunkSize);
		int oldRows = rows;
		int oldCols = columns;

		CellularMatrix.resetChunks();

		// fill the old matrix
		for (int row = 0; row < CellularMatrix.rows; row++) {
			for (int col = 0; col < CellularMatrix.columns; col++) {
				oldMatrix.setElement(matrix.getElement(row, col, false, false));
			}
		}

		// update the screen to set it to new size
		this.create();

		// Re populate the new size matrix with as much of the old matrix as possible
		for (int row = 0; row < CellularMatrix.rows; row++) {
			for (int col = 0; col < CellularMatrix.columns; col++) {
				Element elementToSet;
				if (row >= 0 && row < oldRows && col >= 0 && col < oldCols) {
					elementToSet = oldMatrix.getElement(row, col, false, false);
				} else {
					elementToSet = new Empty(row, col);
				}
				elementToSet.setRow(row);
				elementToSet.setColumn(col);
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
		batch.dispose();
		texture.dispose();
		buttonStage.dispose();
		matrix = null;
		System.gc();
	}

}

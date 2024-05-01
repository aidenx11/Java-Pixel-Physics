package com.aidenx11.JavaPixelPhysics.input;

import java.util.Arrays;

import com.aidenx11.JavaPixelPhysics.CellularMatrix;
import com.aidenx11.JavaPixelPhysics.PixelPhysicsGame;
import com.aidenx11.JavaPixelPhysics.elements.Element.ElementTypes;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Fire;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Leaf;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Steel;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Stone;
import com.aidenx11.JavaPixelPhysics.elements.immovable.Wood;
import com.aidenx11.JavaPixelPhysics.elements.movable.gas.Smoke;
import com.aidenx11.JavaPixelPhysics.elements.movable.liquid.Lava;
import com.aidenx11.JavaPixelPhysics.elements.movable.liquid.Water;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.Dirt;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.Obsidian;
import com.aidenx11.JavaPixelPhysics.elements.movable.movable_solid.Sand;
import com.aidenx11.JavaPixelPhysics.ui.UIStage;
import com.aidenx11.JavaPixelPhysics.elements.Empty;
import com.aidenx11.JavaPixelPhysics.elements.Void;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

/**
 * Class to handle detection of mouse input and drawing of elements in the
 * matrix.
 * 
 * @author Aiden Schroeder
 */
public class MouseInput implements InputProcessor {

	/** Camera to base the mouse on */
	private OrthographicCamera camera;

	/** Matrix to be modified by the mouse */
	private CellularMatrix matrix;

	/** Pixel size modifier of this simulation */
	private static int pixelSizeModifier = PixelPhysicsGame.pixelSizeModifier;

	/** Position of the mouse in 3D space. Z is always zero */
	public Vector3 mousePos = new Vector3();

	/** Position of the mouse last frame */
	public Vector3 lastMousePos = new Vector3();

	/** Size of brush (radius of circle) */
	private int brushSize;

	/** Size of cursor */
	private int cursorSize;

	private float rectOriginCol = 0;

	private float rectOriginRow = 0;

	/** Element type being drawn by the mouse */
	private ElementTypes elementType;

	/** Type of brush, from the enumeration BrushTypes */
	private BrushTypes brushType;

	private float rectOriginY;

	private float rectOriginX;

	/** Public enumeration to handle the different types of brush type */
	public enum BrushTypes {
		CIRCLE, SQUARE, RECTANGLE
	}

	/**
	 * Constructs the MouseInput with given matrix and camera
	 * 
	 * @param matrix matrix input is inside
	 * @param camera camera input should be in
	 */
	public MouseInput(CellularMatrix matrix, OrthographicCamera camera) {
		this.setCamera(camera);
		this.matrix = matrix;
	}

	/**
	 * Sets the element type the mouse is drawing to the matrix
	 * 
	 * @param type type of element to be drawn
	 */
	public void setElementType(ElementTypes type) {
		this.elementType = type;
		PixelPhysicsGame.mouseElementType = type;
	}

	/**
	 * Returns the current mouse position as a Vector3
	 * 
	 * @return the current mouse position
	 */
	public Vector3 getMousePos() {
		return mousePos;
	}

	/**
	 * Returns the current brush size
	 * 
	 * @return the current brush size
	 */
	public int getBrushSize() {
		return brushSize;
	}

	/**
	 * Sets the brush size
	 * 
	 * @param brushSize size to set
	 */
	public void setBrushSize(int brushSize) {
		this.brushSize = brushSize;
		PixelPhysicsGame.mouseBrushSize = brushSize;
	}

	/**
	 * Returns the cursor size
	 * 
	 * @return the cursor size
	 */
	public int getCursorSize() {
		return cursorSize;
	}

	/**
	 * Sets the cursor size
	 * 
	 * @param cursorSize size to set
	 */
	public void setCursorSize(int cursorSize) {
		this.cursorSize = cursorSize;
	}

	/**
	 * Returns the current brush type of this input
	 * 
	 * @return the current brush type of this input
	 */
	public BrushTypes getBrushType() {
		return brushType;
	}

	/**
	 * Sets the brush type of this input, and sets the brush type field in the
	 * simulation file to the given brushType
	 * 
	 * @param brushType Brush type to set
	 */
	public void setBrushType(BrushTypes brushType) {
		this.brushType = brushType;
		PixelPhysicsGame.mouseBrushType = brushType;
	}

	/**
	 * Returns the current input's element type
	 * 
	 * @return the current input's element type
	 */
	public ElementTypes getElementType() {
		return elementType;
	}

	/**
	 * Returns the camera being used by this input
	 * 
	 * @return the camera being used by this input
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 * Sets the camera for this input
	 * 
	 * @param camera camera to set
	 */
	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	/**
	 * Draws the cursor around the mouse on the screen
	 * 
	 * @param sr     shapeRenderer of the viewport
	 * @param radius radius of the circle
	 */
	public void drawCursor(ShapeRenderer sr) {
		sr.begin();
		sr.set(ShapeType.Line);
		sr.setColor(Color.RED);
		if (getBrushType() == BrushTypes.CIRCLE) {

			sr.circle(Gdx.input.getX(), PixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY(),
					getCursorSize() * pixelSizeModifier / 2);

		} else if (getBrushType() == BrushTypes.SQUARE) {

			float xOrigin = pixelSizeModifier * Math.round(Gdx.input.getX() / pixelSizeModifier)
					- getBrushSize() * pixelSizeModifier / 2;
			float yOrigin = pixelSizeModifier
					* Math.round((PixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY()) / pixelSizeModifier)
					- getBrushSize() * pixelSizeModifier / 2;

			sr.rect(pixelSizeModifier * Math.round(xOrigin / pixelSizeModifier),
					pixelSizeModifier * Math.round(yOrigin / pixelSizeModifier), getCursorSize() * pixelSizeModifier,
					getCursorSize() * pixelSizeModifier);

		} else if (getBrushType() == BrushTypes.RECTANGLE && Gdx.input.isTouched()) {
			this.detectAndDrawRectangleBoundingBox(sr, rectOriginCol * pixelSizeModifier,
					rectOriginRow * pixelSizeModifier);
		}
		sr.end();
	}

	/**
	 * Detects the input of the mouse and sets the matrix values corresponding to
	 * the mouse location and elementType.
	 */
	public void detectInput(ShapeRenderer sr) {

		if (Gdx.input.isTouched()) {

			if (Gdx.input.justTouched()) {
				if (this.getBrushType() == BrushTypes.RECTANGLE) {

					float xOrigin = (float) (pixelSizeModifier * Math.floor(Gdx.input.getX() / pixelSizeModifier));
					float yOrigin = (float) (pixelSizeModifier
							* Math.floor((PixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY()) / pixelSizeModifier));

					this.rectOriginX = xOrigin;
					this.rectOriginY = yOrigin;

					this.rectOriginCol = Math.round(xOrigin / pixelSizeModifier);
					this.rectOriginRow = Math.round(yOrigin / pixelSizeModifier);
				}
				lastMousePos.set(PixelPhysicsGame.mousePosLastFrame);

			} else {
				lastMousePos.set(mousePos.x, mousePos.y, 0);
			}

			mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			getCamera().unproject(mousePos);

			if (mousePos.x / PixelPhysicsGame.pixelSizeModifier < 0
					|| mousePos.x / PixelPhysicsGame.pixelSizeModifier >= PixelPhysicsGame.columns
					|| mousePos.y / PixelPhysicsGame.pixelSizeModifier < 0
					|| mousePos.y / PixelPhysicsGame.pixelSizeModifier >= PixelPhysicsGame.rows) {
				return;
			}

			int[][] points = CellularMatrix.traverseMatrix(mousePos.x, mousePos.y, lastMousePos.x, lastMousePos.y);
			if (points.length == 1) {
				points[0][0] = (int) (mousePos.y / pixelSizeModifier);
				points[0][1] = (int) (mousePos.x / pixelSizeModifier);
			}
			for (int i = 0; i < points.length; i++) {
				float probability;
				switch (elementType) {
				case SAND:
				case EMPTY:
				case WOOD:
				case DIRT:
				case LAVA:
				case STONE:
				case OBSIDIAN:
				case STEEL:
				case VOID:
					probability = 1;
					break;
				case LEAF:
					probability = 0.005f * (76 - this.brushSize);
					break;
				case WATER:
				case FIRE:
					probability = 0.06f * (76 - this.brushSize);
					break;
				default:
					probability = 1;
					break;
				}

				if (getBrushType() == BrushTypes.CIRCLE) {
					drawCircle(points[i][0], points[i][1], getBrushSize() / 2, elementType, probability);
				} else if (getBrushType() == BrushTypes.SQUARE) {
					drawRectangle(points[i][0], points[i][1], getBrushSize() - 1, getBrushSize() - 1, probability);
				} else if (getBrushType() == BrushTypes.RECTANGLE) {
					return;
				}

			}

		}

	}

	/**
	 * Draws a circle to the matrix of the brush's element type, at brush size, p%
	 * of the time
	 * 
	 * @param row    row of the center of the circle
	 * @param column column of the center of the circle
	 * @param radius radius of the circle
	 * @param type   element type to be drawn
	 * @param p      probability of each pixel in the square being drawn
	 */
	public void drawCircle(int row, int column, int radius, ElementTypes type, double p) {

		if (radius == 1) {
			drawRectangle(row, column, 1, 1, p);
			return;
		}

		// Define bounding box
		int top = row + radius;
		int bottom = row - radius;
		int left = column - radius;
		int right = column + radius;

		if (top < 0) {
			top = 0;
		}

		if (top >= CellularMatrix.rows) {
			top = CellularMatrix.rows - 1;
		}

		if (bottom < 0) {
			bottom = 0;
		}

		if (bottom >= CellularMatrix.rows) {
			bottom = CellularMatrix.rows - 1;
		}

		if (left < 0) {
			left = 0;
		}

		if (left >= CellularMatrix.columns) {
			left = CellularMatrix.columns - 1;
		}

		if (right < 0) {
			right = 0;
		}

		if (right >= CellularMatrix.columns) {
			right = CellularMatrix.columns - 1;
		}

		for (int rowCount = bottom; rowCount <= top; rowCount++) {
			for (int colCount = left; colCount <= right; colCount++) {
				if (insideCircle(row, column, radius, rowCount, colCount)) {
					if (Math.random() > p) {
						continue;
					}
					switch (type) {
					case SAND:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Sand)) {
							matrix.setElement(new Sand(rowCount, colCount));
						}
						break;
					case EMPTY:
						matrix.setElement(new Empty(rowCount, colCount));
						break;
					case WOOD:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Wood)) {
							matrix.setElement(new Wood(rowCount, colCount));
						}
						break;
					case SMOKE:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Smoke)) {
							matrix.setElement(new Smoke(rowCount, colCount));
						}
						break;
					case FIRE:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Fire)) {
							matrix.setElement(new Fire(rowCount, colCount));
						}
						break;
					case WATER:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Water)) {
							matrix.setElement(new Water(rowCount, colCount));
						}
						break;
					case LEAF:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Leaf)) {
							matrix.setElement(new Leaf(rowCount, colCount));
						}
						break;
					case DIRT:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Dirt)) {
							matrix.setElement(new Dirt(rowCount, colCount));
						}
						break;
					case STONE:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Stone)) {
							matrix.setElement(new Stone(rowCount, colCount));
						}
						break;
					case LAVA:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Lava)) {
							matrix.setElement(new Lava(rowCount, colCount));
						}
						break;
					case OBSIDIAN:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Obsidian)) {
							matrix.setElement(new Obsidian(rowCount, colCount));
						}
						break;
					case STEEL:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Steel)) {
							matrix.setElement(new Steel(rowCount, colCount));
						}
						break;
					case VOID:
						if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Void)) {
							matrix.setElement(new Void(rowCount, colCount));
						}
						break;
					default:
						break;
					}
				}
			}
		}
	}

	// Private method to calculate if a matrix index is within the circle
	private boolean insideCircle(int centerRow, int centerCol, int radius, int cellRow, int cellCol) {
		double dx = centerCol - cellCol;
		double dy = centerRow - cellRow;
		double distanceSquared = dx * dx + dy * dy;
		return distanceSquared <= radius * radius;
	}

	/**
	 * Draws a square of the brush's element type and size to the matrix.
	 * 
	 * @param row    row of the bottom left of the square
	 * @param column column of the bottom left of the square
	 * @param height width of the square
	 * @param p      probability of each pixel in the square being drawn
	 * @param type   element type to be drawn
	 */
	public void drawRectangle(int row, int column, int height, int width, double p) {

		int rowDifference;
		int colDifference;

		if (width <= 1) {
			colDifference = 0;
		} else {
			colDifference = (int) Math.floor(width / 2f);
		}

		if (height <= 1) {
			rowDifference = 0;
		} else {
			rowDifference = (int) Math.floor(height / 2f);
		}

		int extraColOffset = 0;
		int extraRowOffset = 0;

		if (brushType == BrushTypes.SQUARE) {
			if (height % 2 != 0) {
				extraRowOffset = 1;
			}

			if (width % 2 != 0) {
				extraColOffset = 1;
			}
		} else if (brushType == BrushTypes.RECTANGLE) {
			if (height % 2 == 0) {
				extraRowOffset = -1;
			}

			if (width % 2 == 0) {
				extraColOffset = -1;
			}
		}

		for (int colCount = column - colDifference - extraColOffset; colCount <= column + colDifference; colCount++) {
			for (int rowCount = row - rowDifference - extraRowOffset; rowCount <= row + rowDifference; rowCount++) {

				if (Math.random() > p) {
					continue;
				}

				if (rowCount < 0) {
					continue;
				}

				if (rowCount >= CellularMatrix.rows) {
					continue;
				}

				if (colCount < 0) {
					continue;
				}

				if (colCount >= CellularMatrix.columns) {
					continue;
				}

				switch (this.elementType) {
				case SAND:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Sand)) {
						matrix.setElement(new Sand(rowCount, colCount));
					}
					break;
				case EMPTY:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Empty)) {
						matrix.setElement(new Empty(rowCount, colCount));
					}
					break;
				case WOOD:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Wood)) {
						matrix.setElement(new Wood(rowCount, colCount));
					}
					break;
				case FIRE:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Fire)) {
						matrix.setElement(new Fire(rowCount, colCount));
					}
					break;
				case LEAF:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Leaf)) {
						matrix.setElement(new Leaf(rowCount, colCount));
					}
					break;
				case WATER:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Water)) {
						matrix.setElement(new Water(rowCount, colCount));
					}
					break;
				case DIRT:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Dirt)) {
						matrix.setElement(new Dirt(rowCount, colCount));
					}
					break;
				case STONE:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Stone)) {
						matrix.setElement(new Stone(rowCount, colCount));
					}
					break;
				case LAVA:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Lava)) {
						matrix.setElement(new Lava(rowCount, colCount));
					}
					break;
				case OBSIDIAN:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Obsidian)) {
						matrix.setElement(new Obsidian(rowCount, colCount));
					}
					break;
				case STEEL:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Steel)) {
						matrix.setElement(new Steel(rowCount, colCount));
					}
					break;
				case VOID:
					if (!(matrix.getElement(rowCount, colCount, false, false) instanceof Void)) {
						matrix.setElement(new Void(rowCount, colCount));
					}
					break;
				case SMOKE:
				case STEAM:
				case WET_SAND:
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * Detects user input for the rectangle BrushType and draws a bounding box
	 * starting at where the user clicked, and following the cursor.
	 * 
	 * @param sr shapeRenderer to draw the box with
	 * @param x  x location of the start of the box
	 * @param y  y location of the start of the box
	 */
	private void detectAndDrawRectangleBoundingBox(ShapeRenderer sr, float x, float y) {

		float width = Gdx.input.getX() - x;
		float height = PixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY() - y;

		width = (float) (pixelSizeModifier * Math.round(width / pixelSizeModifier));
		height = (float) (pixelSizeModifier * Math.round(height / pixelSizeModifier));

		sr.rect(x, y, width, height);
	}

	/**
	 * Detects when the the input on the screen is released. If the brush type is
	 * rectangle, this is where the brush actually draws to the screen using the
	 * bounding box generated by the user's input.
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (brushType == BrushTypes.RECTANGLE) {

			int[][] traversedMatrix = CellularMatrix.traverseMatrix(rectOriginX, rectOriginY, screenX,
					PixelPhysicsGame.SCREEN_HEIGHT - screenY);

			int startRow = Math.min(traversedMatrix[0][0], traversedMatrix[traversedMatrix.length - 1][0]);
			int startCol = Math.min(traversedMatrix[0][1], traversedMatrix[traversedMatrix.length - 1][1]);

			boolean drawnRight = rectOriginX < screenX;
			boolean drawnUp = rectOriginY < PixelPhysicsGame.SCREEN_HEIGHT - screenY;

			int width = 0;
			int height = 0;

			if (drawnRight) {
				width = (int) Math.round((screenX - rectOriginX) / (float) pixelSizeModifier);
			} else {
				width = (int) Math.round((rectOriginX - screenX) / (float) pixelSizeModifier);
			}

			if (drawnUp) {
				height = (int) Math
						.round(((PixelPhysicsGame.SCREEN_HEIGHT - screenY) - rectOriginY) / (float) pixelSizeModifier);
			} else {
				height = (int) Math
						.round((rectOriginY - (PixelPhysicsGame.SCREEN_HEIGHT - screenY)) / (float) pixelSizeModifier);
			}

			for (int i = 4; i < width; i += 2) {
				startCol++;
			}

			if (height == 2) {
				startRow--;
			}

			if (height > 4) {
				startRow++;
			}
			if (height > 6) {
				startRow++;
			}
			if (height > 8) {
				for (int i = 8; i < height; i += 2) {
					startRow++;
				}
			}

			if (width > 2) {
				if (!drawnRight) {
					startCol++;
				}

				if (drawnRight && height > width) {
					startCol++;
				}
			}

			if (height > 1) {
				if (!drawnUp) {
					startRow++;
				}

				if (drawnUp && width > height) {
					startRow++;
				}
			}

			drawRectangle(startRow, startCol, height, width, 1);

		}
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		UIStage.brushSizeSlider
				.setValue(UIStage.brushSizeSlider.getValue() - amountY * UIStage.brushSizeSlider.getStepSize());
		this.setBrushSize((int) UIStage.brushSizeSlider.getValue());
		this.setCursorSize((int) UIStage.brushSizeSlider.getValue());
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

}
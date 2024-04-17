package com.aidenx11.game.input;

import com.aidenx11.game.CellularMatrix;
import com.aidenx11.game.pixelPhysicsGame;
import com.aidenx11.game.elements.Element.ElementTypes;
import com.aidenx11.game.elements.immovable.Fire;
import com.aidenx11.game.elements.immovable.Leaf;
import com.aidenx11.game.elements.immovable.Steel;
import com.aidenx11.game.elements.immovable.Stone;
import com.aidenx11.game.elements.immovable.Wood;
import com.aidenx11.game.elements.movable.gas.Smoke;
import com.aidenx11.game.elements.movable.liquid.Lava;
import com.aidenx11.game.elements.movable.liquid.Water;
import com.aidenx11.game.elements.movable.movable_solid.Dirt;
import com.aidenx11.game.elements.movable.movable_solid.Obsidian;
import com.aidenx11.game.elements.movable.movable_solid.Sand;
import com.aidenx11.game.elements.Empty;
import com.aidenx11.game.elements.Void;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
	private static int pixelSizeModifier = pixelPhysicsGame.pixelSizeModifier;

	/** Position of the mouse in 3D space. Z is always zero */
	public Vector3 mousePos = new Vector3();

	/** Position of the mouse last frame */
	public Vector3 lastMousePos = new Vector3(1, 1, 0);

	/** Size of brush (radius of circle) */
	private int brushSize;

	/** Size of cursor */
	private int cursorSize;

	private float rectOriginX = 150;

	private float rectOriginY = 150;

	/** Element type being drawn by the mouse */
	private ElementTypes elementType;

	/** Type of brush, from the enumeration BrushTypes */
	private BrushTypes brushType;

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
		pixelPhysicsGame.mouseElementType = type;
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
		pixelPhysicsGame.mouseBrushSize = brushSize;
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
		pixelPhysicsGame.mouseBrushType = brushType;
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
		sr.setColor(Color.WHITE);
		if (getBrushType() == BrushTypes.CIRCLE) {
			sr.circle(Gdx.input.getX(), pixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY(),
					(getCursorSize() * pixelSizeModifier) / 2);
		} else if (getBrushType() == BrushTypes.SQUARE) {
			sr.rect(Gdx.input.getX() - getBrushSize() * pixelSizeModifier / 2,
					pixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY() - getBrushSize() * pixelSizeModifier / 2
							- pixelSizeModifier,
					getCursorSize() * pixelSizeModifier, getCursorSize() * pixelSizeModifier);
		} else if (getBrushType() == BrushTypes.RECTANGLE && Gdx.input.isTouched()) {
			this.detectAndDrawRectangle(sr, rectOriginX, rectOriginY);
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
					this.rectOriginX = Gdx.input.getX();
					this.rectOriginY = pixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY();
				}
				lastMousePos.set(pixelPhysicsGame.mousePosLastFrame);
			} else {
				lastMousePos.set(mousePos.x, mousePos.y, 0);
			}

			mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			getCamera().unproject(mousePos);

			int[][] points = CellularMatrix.traverseMatrix(mousePos.x, mousePos.y, lastMousePos.x, lastMousePos.y);
			if (points.length == 1) {
				points[0][0] = (int) (mousePos.y / pixelSizeModifier);
				points[0][1] = (int) (mousePos.x / pixelSizeModifier);
			}
			for (int i = 0; i < points.length; i++) {
				if (CellularMatrix.isWithinBounds(points[i][0], points[i][1])) {
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
						probability = 0.03f * (76 - this.brushSize);
						break;
					default:
						probability = 1;
						break;
					}

					if (getBrushType() == BrushTypes.CIRCLE) {
						drawCircle(points[i][0], points[i][1], getBrushSize() / 2, elementType, probability);
					} else if (getBrushType() == BrushTypes.SQUARE) {
						drawRectangle(points[i][0], points[i][1], getBrushSize(), getBrushSize(), probability);
					} else if (getBrushType() == BrushTypes.RECTANGLE) {
						
					}

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
		int top = (int) (Math.ceil(row + radius) < 0 ? 0 : Math.ceil(row + radius));
		int bottom = (int) (Math.floor(row - radius) < 0 ? 0 : Math.floor(row - radius));
		int left = (int) (Math.floor(column - radius) < 0 ? 0 : Math.floor(column - radius));
		int right = (int) (Math.ceil(column + radius) < 0 ? 0 : Math.ceil(column + radius));

		for (int rowCount = bottom; rowCount <= top; rowCount++) {
			for (int colCount = left; colCount <= right; colCount++) {
				if (insideCircle(row, column, radius, rowCount, colCount)) {
					if (!CellularMatrix.isWithinBounds(rowCount, colCount) || Math.random() > p) {
						continue;
					}
					switch (type) {
					case SAND:
						if (!(matrix.getElement(rowCount, colCount) instanceof Sand)) {
							matrix.setElement(new Sand(rowCount, colCount));
						}
						break;
					case EMPTY:
						matrix.setElement(new Empty(rowCount, colCount));
						break;
					case WOOD:
						if (!(matrix.getElement(rowCount, colCount) instanceof Wood)) {
							matrix.setElement(new Wood(rowCount, colCount));
						}
						break;
					case SMOKE:
						if (!(matrix.getElement(rowCount, colCount) instanceof Smoke)) {
							matrix.setElement(new Smoke(rowCount, colCount));
						}
						break;
					case FIRE:
						if (!(matrix.getElement(rowCount, colCount) instanceof Fire)) {
							matrix.setElement(new Fire(rowCount, colCount));
						}
						break;
					case WATER:
						if (!(matrix.getElement(rowCount, colCount) instanceof Water)) {
							matrix.setElement(new Water(rowCount, colCount));
						}
						break;
					case LEAF:
						if (!(matrix.getElement(rowCount, colCount) instanceof Leaf)) {
							matrix.setElement(new Leaf(rowCount, colCount));
						}
						break;
					case DIRT:
						if (!(matrix.getElement(rowCount, colCount) instanceof Dirt)) {
							matrix.setElement(new Dirt(rowCount, colCount));
						}
						break;
					case STONE:
						if (!(matrix.getElement(rowCount, colCount) instanceof Stone)) {
							matrix.setElement(new Stone(rowCount, colCount));
						}
						break;
					case LAVA:
						if (!(matrix.getElement(rowCount, colCount) instanceof Lava)) {
							matrix.setElement(new Lava(rowCount, colCount));
						}
						break;
					case OBSIDIAN:
						if (!(matrix.getElement(rowCount, colCount) instanceof Obsidian)) {
							matrix.setElement(new Obsidian(rowCount, colCount));
						}
						break;
					case STEEL:
						if (!(matrix.getElement(rowCount, colCount) instanceof Steel)) {
							matrix.setElement(new Steel(rowCount, colCount));
						}
						break;
					case VOID:
						if (!(matrix.getElement(rowCount, colCount) instanceof Void)) {
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

	private void detectAndDrawRectangle(ShapeRenderer sr, float x, float y) {
		sr.rect(x, y, Gdx.input.getX() - x, pixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY() - y);
	}

	/**
	 * Draws a square of the brush's element type and size to the matrix.
	 * 
	 * @param row    row of the center of the square
	 * @param column column of the center of the square
	 * @param width  width of the square
	 * @param p      probability of each pixel in the square being drawn
	 * @param type   element type to be drawn
	 */
	public void drawRectangle(int row, int column, int width, int length, double p) {

		int rowDifference;
		int colDifference;
		int mod;
		if (width == 1) {
			rowDifference = 0;
			colDifference = 0;
			mod = 0;
		} else {
			rowDifference = width / 2;
			colDifference = length / 2;
			mod = 2;
		}

		for (int rowCount = row - rowDifference; rowCount <= row + rowDifference - mod; rowCount++) {
			for (int colCount = column - colDifference; colCount <= column + colDifference; colCount++) {

				if (!CellularMatrix.isWithinBounds(rowCount, colCount) || (Math.random() > p)) {
					continue;
				}

				switch (this.elementType) {
				case SAND:
					if (!(matrix.getElement(rowCount, colCount) instanceof Sand)) {
						matrix.setElement(new Sand(rowCount, colCount));
					}
					break;
				case EMPTY:
					if (!(matrix.getElement(rowCount, colCount) instanceof Empty)) {
						matrix.setElement(new Empty(rowCount, colCount));
					}
					break;
				case WOOD:
					if (!(matrix.getElement(rowCount, colCount) instanceof Wood)) {
						matrix.setElement(new Wood(rowCount, colCount));
					}
					break;
				case FIRE:
					if (!(matrix.getElement(rowCount, colCount) instanceof Fire)) {
						matrix.setElement(new Fire(rowCount, colCount));
					}
					break;
				case LEAF:
					if (!(matrix.getElement(rowCount, colCount) instanceof Leaf)) {
						matrix.setElement(new Leaf(rowCount, colCount));
					}
					break;
				case WATER:
					if (!(matrix.getElement(rowCount, colCount) instanceof Water)) {
						matrix.setElement(new Water(rowCount, colCount));
					}
					break;
				case DIRT:
					if (!(matrix.getElement(rowCount, colCount) instanceof Dirt)) {
						matrix.setElement(new Dirt(rowCount, colCount));
					}
					break;
				case STONE:
					if (!(matrix.getElement(rowCount, colCount) instanceof Stone)) {
						matrix.setElement(new Stone(rowCount, colCount));
					}
					break;
				case LAVA:
					if (!(matrix.getElement(rowCount, colCount) instanceof Lava)) {
						matrix.setElement(new Lava(rowCount, colCount));
					}
					break;
				case OBSIDIAN:
					if (!(matrix.getElement(rowCount, colCount) instanceof Obsidian)) {
						matrix.setElement(new Obsidian(rowCount, colCount));
					}
					break;
				case STEEL:
					if (!(matrix.getElement(rowCount, colCount) instanceof Steel)) {
						matrix.setElement(new Steel(rowCount, colCount));
					}
					break;
				case VOID:
					if (!(matrix.getElement(rowCount, colCount) instanceof Void)) {
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

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (brushType == BrushTypes.RECTANGLE) {
			int row = 0;
			int col = 0;
			int width = (int) Math.abs((mousePos.x - rectOriginX) / pixelPhysicsGame.pixelSizeModifier);
			int height = (int) Math.abs((mousePos.y - rectOriginY) / pixelPhysicsGame.pixelSizeModifier);
			boolean drawnUp = mousePos.y - rectOriginY > 0;
			boolean drawnRight = mousePos.x - rectOriginX > 0;
			
			if (drawnUp) {
				row = (int) (rectOriginY / pixelPhysicsGame.pixelSizeModifier) + height / 2;
			} else {
				row = (int) (rectOriginY / pixelPhysicsGame.pixelSizeModifier) - height / 2;
			}
			
			if (drawnRight) {
				col = (int) (rectOriginX / pixelPhysicsGame.pixelSizeModifier) + width / 2;
			} else {
				col = (int) (rectOriginX / pixelPhysicsGame.pixelSizeModifier) - width / 2;
			}
			
			this.drawRectangle(row, col, height, width, 1);
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
		// TODO Auto-generated method stub
		return false;
	}

}
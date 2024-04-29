package com.aidenx11.JavaPixelPhysics.input;

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
		sr.setColor(Color.WHITE);
		if (getBrushType() == BrushTypes.CIRCLE) {
			sr.circle(Gdx.input.getX(), PixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY(),
					getCursorSize() * pixelSizeModifier / 2);
		} else if (getBrushType() == BrushTypes.SQUARE) {
			sr.rect(Gdx.input.getX() - getBrushSize() * pixelSizeModifier / 2,
					PixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY() - getBrushSize() * pixelSizeModifier / 2
							- pixelSizeModifier,
					getCursorSize() * pixelSizeModifier, getCursorSize() * pixelSizeModifier);
		} else if (getBrushType() == BrushTypes.RECTANGLE && Gdx.input.isTouched()) {
			sr.setColor(Color.RED);
			this.detectAndDrawRectangleBoundingBox(sr, rectOriginX, rectOriginY);
		}
		sr.set(ShapeType.Filled);
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
					this.rectOriginY = PixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY();
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
					drawRectangle(points[i][0], points[i][1], getBrushSize(), getBrushSize(), probability);
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
	 * Detects user input for the rectangle BrushType and draws a bounding box
	 * starting at where the user clicked, and following the cursor.
	 * 
	 * @param sr shapeRenderer to draw the box with
	 * @param x  x location of the start of the box
	 * @param y  y location of the start of the box
	 */
	private void detectAndDrawRectangleBoundingBox(ShapeRenderer sr, float x, float y) {
		sr.rect(x, y, Gdx.input.getX() - x, PixelPhysicsGame.SCREEN_HEIGHT - Gdx.input.getY() - y);
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
			mod = 1;
		}

		for (int rowCount = row - rowDifference; rowCount <= row + rowDifference - mod; rowCount++) {
			for (int colCount = column - colDifference; colCount <= column + colDifference; colCount++) {

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
	 * Detects when the the input on the screen is released. If the brush type is
	 * rectangle, this is where the brush actually draws to the screen using the
	 * bounding box generated by the user's input.
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (brushType == BrushTypes.RECTANGLE) {
			int row = 0;
			int col = 0;
			int height = (int) Math.ceil(Math.abs((mousePos.x - rectOriginX) / PixelPhysicsGame.pixelSizeModifier));
			int width = (int) Math.ceil(Math.abs((mousePos.y - rectOriginY) / PixelPhysicsGame.pixelSizeModifier));

			if (width < 2) {
				width = 2;
			}

			if (height < 1) {
				height = 1;
			}

			boolean drawnUp = mousePos.y - rectOriginY > 0;
			boolean drawnRight = mousePos.x - rectOriginX > 0;

			if (drawnUp) {
				row = (int) Math.round(rectOriginY / PixelPhysicsGame.pixelSizeModifier) + width / 2;
			} else {
				row = (int) Math.round(rectOriginY / PixelPhysicsGame.pixelSizeModifier) - width / 2;
			}

			if (drawnRight) {
				col = (int) (rectOriginX / PixelPhysicsGame.pixelSizeModifier) + height / 2;
			} else {
				col = (int) (rectOriginX / PixelPhysicsGame.pixelSizeModifier) - height / 2;
			}

			if (drawnUp && drawnRight) {
				width += 1;
				row++;
			} else if (!drawnUp) {
				width += 2;
			}

			this.drawRectangle(row, col, width, height, 1);
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
		UIStage.brushSizeSlider.setValue(UIStage.brushSizeSlider.getValue() - amountY);
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
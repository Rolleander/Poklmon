package com.broll.poklmon.model;

import com.broll.pokllib.object.ObjectDirection;
import com.broll.poklmon.PoklmonGame;
import com.broll.poklmon.data.DataContainer;
import com.broll.poklmon.data.basics.Graphics;
import com.broll.poklmon.data.basics.Image;
import com.broll.poklmon.data.basics.SpriteSheet;
import com.broll.poklmon.map.MapContainer;
import com.broll.poklmon.map.Viewport;
import com.broll.poklmon.model.movement.MoveCommandListener;
import com.broll.poklmon.model.movement.MovementListener;
import com.broll.poklmon.model.movement.MovementUtil;
import com.broll.poklmon.model.movement.WalkingPath;
import com.broll.poklmon.model.movement.WalkingPathListener;

public abstract class OverworldCharacter {

	protected float xpos, ypos;
	protected ObjectDirection direction = ObjectDirection.DOWN;
	protected MapContainer map;
	protected CharacterGraphic characterGraphic = new CharacterGraphic();
	private boolean isMoving = false;
	private boolean isBlocking;
	private boolean ledgeJump = false;
	private float moveX, moveY;
	private float moveLength;
	private float movementSpeed;
	private MovementListener movementListener;
	private MoveCommandListener moveCommandListener;
	private boolean couldNotMove = false;
	private boolean movementAllowed = true;
	private int todoSteps;
	private boolean forceSuccess = false;
	private WalkingPath walkPath;
	private int renderLevel;
	private float jumpY, jumpSpeed;
	private DataContainer data;
	protected CharacterWorldState worldState=CharacterWorldState.STANDARD;
	
	public OverworldCharacter(DataContainer data,MapContainer map) {
		this.map = map;
		this.data=data;
	}

	public void setRenderLevel(int renderLevel) {
		this.renderLevel = renderLevel;
	}

	public int getRenderLevel() {
		return renderLevel;
	}

	public boolean isMovementAllowed() {
		return movementAllowed;
	}

	public void setMovementAllowed(boolean movementAllowed) {
		this.movementAllowed = movementAllowed;
	}

	public void setBlocking(boolean isBlocking) {
		System.out.println(xpos+" "+ypos+ " block " +isBlocking);
		map.blockTile((int) xpos, (int) ypos, isBlocking);
		this.isBlocking = isBlocking;
	}

	public void setMovementListener(MovementListener movementListener) {
		this.movementListener = movementListener;
	}

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public void setFixedChar(int x, int y) {
		characterGraphic.setFixed(x, y);
	}

	public void setGraphic(SpriteSheet sheet) {
		characterGraphic.setSpriteSheet(sheet);
	}

	public void setFixGraphic(Image image) {
		characterGraphic.setFixSprite(image);
	}

	public void update(float delta) {

		if (walkPath != null) {
		//	float time = delta / ((float) PoklmonGame.FPS);
			walkPath.update(delta);
		}

		if (isMoving) {
			characterGraphic.updateMovementAnimation(direction, delta);
			// update movement
			float xs=moveX*delta*PoklmonGame.FPS;
			float ys=moveY*delta*PoklmonGame.FPS;
			xpos += xs;
			ypos += ys;
			if (ledgeJump) {
				jumpY += jumpSpeed;
				jumpSpeed -= 0.02f;
			}
			moveLength += Math.abs(xs) + Math.abs(ys);
			if (moveLength >= 1) {
				// stop movement
				isMoving = false;
				ledgeJump = false;
				jumpY = 0;
				xpos = Math.round(xpos);
				ypos = Math.round(ypos);
				if (movementListener != null) {
					movementListener.movedToTile((int) xpos, (int) ypos);
				}
				checkMoveCommandListener(true);
			}
		} else {
			characterGraphic.setMovementAnimationStanding(direction);
		}
		if (couldNotMove) {
			couldNotMove = false;
			checkMoveCommandListener(false);
		}
	}

	public void stopMoving() {
		todoSteps = 0;
		forceSuccess = false;
	}

	private void checkMoveCommandListener(boolean success) {
		if (moveCommandListener != null) {
			if (forceSuccess && !success) {
				// retry
				couldNotMove = move(direction) == false;
				return;
			}
			if (todoSteps > 1) {
				todoSteps--;
				// do missing steps
				couldNotMove = move(direction) == false;
			} else {
				moveCommandListener.stoppedMoving(success);
				moveCommandListener = null;
			}
		}
	}

	public boolean move(ObjectDirection direction) {
		if (!isMoving) {
			int movex = MovementUtil.getDirectionX(direction);
			int movey = MovementUtil.getDirectionY(direction);
			int newx = (int) (xpos + movex);
			int newy = (int) (ypos + movey);
			// check if passable
			setDirection(direction);
			if (map.isPassable(newx, newy, direction,worldState)) {
				if (map.isLedge((int)xpos,(int) ypos, direction)) {
					ledgeJump = true;
					data.getSounds().playSound("ledge_jump");
					jumpSpeed = 1.3f;
				}
				isMoving = true;
				moveLength = 0;
				moveX = movementSpeed * MovementUtil.getDirectionX(direction);
				moveY = movementSpeed * MovementUtil.getDirectionY(direction);

				// release old block
				map.blockTile((int) xpos, (int) ypos, false);
				if (isBlocking) {
					// set new block
					map.blockTile(newx, newy, true);
				}
				return true;
			}
		}
		return false;
	}

	public void move(ObjectDirection direction, boolean forceSuccess, int steps, MoveCommandListener listener) {
		todoSteps = steps;
		this.forceSuccess = forceSuccess;
		this.moveCommandListener = listener;
		couldNotMove = move(direction) == false;
	}

	public void move(ObjectDirection direction, MoveCommandListener listener) {
		todoSteps = 1;
		forceSuccess = false;
		this.moveCommandListener = listener;
		couldNotMove = move(direction) == false;
	}

	public void render(Graphics g, Viewport viewport) {
		if (characterGraphic != null) {
			float x = viewport.getScreenX(xpos);
			float y = viewport.getScreenY(ypos);
			characterGraphic.render(g, x, y - jumpY);
		}
	}

	public void walkPath(WalkingPath walkingPath, WalkingPathListener listener) {
		walkPath = walkingPath;
		walkPath.setLoop(false);
		walkPath.setPathListener(listener);
		walkPath.init(this);
	}

	public void stopWalkingPath() {
		walkPath = null;
	}

	public void teleport(float x, float y) {
		xpos = x;
		ypos = y;
	}

	public void setDirection(ObjectDirection direction) {
		this.direction = direction;
		if (characterGraphic != null) {
			characterGraphic.viewDirection(direction);
		}
	}

	public boolean isMoving() {
		return isMoving;
	}

	public float getXpos() {
		return xpos;
	}

	public float getYpos() {
		return ypos;
	}

	public boolean isBlocking() {
		return isBlocking;
	}

	public ObjectDirection getDirection() {
		return direction;
	}
	
	public CharacterWorldState getWorldState() {
		return worldState;
	}
	
	public void setWorldState(CharacterWorldState worldState) {
		this.worldState = worldState;
	}
}

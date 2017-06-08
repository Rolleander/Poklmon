package com.broll.pokllib.object;

public class MapObject {

	private String graphic;
	private String name;
	private ObjectDirection direction;
	private int xpos,ypos;
	private int objectID;
	private String attributes;
	private String triggerScript;
	
	public String getGraphic() {
		return graphic;
	}
	public void setGraphic(String graphic) {
		this.graphic = graphic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ObjectDirection getDirection() {
		return direction;
	}
	public void setDirection(ObjectDirection direction) {
		this.direction = direction;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getTriggerScript() {
		return triggerScript;
	}
	public void setTriggerScript(String triggerScript) {
		this.triggerScript = triggerScript;
	}
    /**
     * Returns the {@link #xpos}.
     * @return The xpos.
     */
    public int getXpos()
    {
        return xpos;
    }
    /**
     * Sets the {@link #xpos}.
     * @param xpos The new xpos to set.
     */
    public void setXpos(int xpos)
    {
        this.xpos = xpos;
    }
    /**
     * Returns the {@link #ypos}.
     * @return The ypos.
     */
    public int getYpos()
    {
        return ypos;
    }
    /**
     * Sets the {@link #ypos}.
     * @param ypos The new ypos to set.
     */
    public void setYpos(int ypos)
    {
        this.ypos = ypos;
    }
	
	
	public int getObjectID()
    {
        return objectID;
    }
	public void setObjectID(int objectID)
    {
        this.objectID = objectID;
    }
	
}

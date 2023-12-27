package Gameplay.Link;

/** This class contains the information of what link is doing
 */
@Deprecated
public class ActionObject {
    private DIRECTION direction;
    private Action action;

    /* Constructor
     * 
     */
    public ActionObject(DIRECTION direction, Action action) {
        this.direction = direction;
        this.action = action;
    }

    public ActionObject(DIRECTION direction) {
        this.direction = direction;
        this.action = Action.STOP;
    }

    /*  Getters and setters
     * 
     */
    public DIRECTION getDirection() {return direction;}
    public void setDirection(DIRECTION direction) {this.direction = direction;}
    public Action getAction() {return action;}
    public void setAction(Action action) {this.action = action;}


    /* Hascode and equals
     * 
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((direction == null) ? 0 : direction.hashCode());
        result = prime * result + ((action == null) ? 0 : action.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActionObject other = (ActionObject) obj;
        if (direction != other.direction)
            return false;
        if (action != other.action)
            return false;
        return true;
    }
    public boolean actionEquals(DIRECTION otherDirection, Action otherAction ){
        return this.direction==otherDirection && this.action==otherAction;
    }

    @Override
    public String toString() {
        return "ActionObject [direction=" + direction + ", action=" + action + "]";
    }
}

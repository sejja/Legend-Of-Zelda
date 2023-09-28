package Gameplay.Link;

public class DirectionObject {
    private DIRECTION currentDirection;

    public DirectionObject (DIRECTION dir){
        currentDirection = dir;
    }

    public DirectionObject (boolean up, boolean left, boolean right, boolean down){
        if (up){
            currentDirection = DIRECTION.UP;
        }
        else if (left)
        {
            currentDirection = DIRECTION.LEFT;
        }
        else if (down)
        {
            currentDirection = DIRECTION.DOWN;
        }
        else
        {
            currentDirection = DIRECTION.RIGHT;
        }
    }

    public void setDirection(DIRECTION dir){
        currentDirection = dir;
    }

    public void setDirection (boolean up, boolean left, boolean right, boolean down){
        if (up){
            currentDirection = DIRECTION.UP;
        }
        else if (left)
        {
            currentDirection = DIRECTION.LEFT;
        }
        else if (down)
        {
            currentDirection = DIRECTION.DOWN;
        }
        else
        {
            currentDirection = DIRECTION.RIGHT;
        }
    }

    public DIRECTION getDirection (){
        return currentDirection;
    }
}


package Gameplay.Link;

enum DIRECTION{
    UP,
    LEFT,
    RIGHT,
    DOWN;
}

public class Direction {
    private DIRECTION currentDirection;

    public Direction (DIRECTION dir){
        currentDirection = dir;
    }

    public Direction (boolean up, boolean left, boolean right, boolean down){
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
            currentDirection = DIRECTION.LEFT;
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
            currentDirection = DIRECTION.LEFT;
        }
    }

    public DIRECTION getDirection (){
        return currentDirection;
    }
}

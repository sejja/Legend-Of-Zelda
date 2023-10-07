package Gameplay.AnimatedObject;

import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Spritesheet;
import Engine.Math.Vector2D;

public class DeadAnimation extends AnimatedObject{

    Actor actor;

    public DeadAnimation(Vector2D<Float> position, Spritesheet spritesheet, int delay, int animationIndex,
            boolean deleteAtTheEnd) {
        super(position, spritesheet, delay, animationIndex, deleteAtTheEnd);
    }

    public DeadAnimation(Actor actor){
        super(actor.GetPosition());
        deleteAtTheEnd = true;
        
    }
}

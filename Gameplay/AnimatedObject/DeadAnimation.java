package Gameplay.AnimatedObject;

import java.util.Vector;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;

public class DeadAnimation extends AnimatedObject{

    Actor actor;

    public DeadAnimation(Vector2D<Float> position, Spritesheet spritesheet, int delay, int animationIndex,
            boolean deleteAtTheEnd) {
        super(position, spritesheet, delay, animationIndex);
    }

    public DeadAnimation(Actor actor){
        super(actor.GetPosition());
        //super(new Vector2D<Float>(actor.GetPosition().x + offset.x, actor.GetPosition().x + offset.y));
        delay = 5;
        this.actor = actor;
        setAnimationMachine(new Spritesheet("Content/Animations/DeadAnimation.png", 29 , 36));
        SetScale(actor.GetScale());
        ObjectManager.GetObjectManager().AddEntity(this);
        defaultAnimationIndex = 0;
        animationMachine.setMustComplete(true);
        Animate(0);
    }

    @Override
    public void Update (){
        super.Update();
       if(animationMachine.GetAnimation().GetFrame() == 3){
            actor.SetScale(new Vector2D<>(0f, 0f));
            ObjectManager.GetObjectManager().RemoveEntity(actor);
       }
       if(animationMachine.GetAnimation().GetFrame() == 5){
            ObjectManager.GetObjectManager().RemoveEntity(this);
            SetScale(new Vector2D<>(0f, 0f));
       }
    }
}

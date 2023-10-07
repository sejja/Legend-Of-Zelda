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

    public DeadAnimation(Actor actor, Vector2D<Float> scale){
        super(actor.GetPosition());
        this.actor = actor;
        setAnimationMachine(new Spritesheet("Content/Animations/DeadAnimation.png", 17 , 36));
        SetScale(actor.GetScale());
        ObjectManager.GetObjectManager().AddEntity(this);
        defaultAnimationIndex = 0;
        animationMachine.setMust_Complete();
        Animate(0);
    }

    @Override
    public void Update (){
        super.Update();
        System.out.println(animationMachine.GetAnimation().GetDelay());
    }
}

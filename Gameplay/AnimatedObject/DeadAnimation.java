package Gameplay.AnimatedObject;

import java.util.Vector;

import Engine.Assets.AssetManager;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Animations.AnimationEvent;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;
/** Doc Generated by ChatGPT
 * The {@code DeadAnimation} class extends {@code AnimatedObject} to manage death animations of actors in the game.
 * It handles the animation when an actor is defeated or dies in the game.
 * 
 * @author Lingfeng
 */
public class DeadAnimation extends AnimatedObject{

    Actor actor;

    public DeadAnimation(Vector2D<Float> position, Spritesheet spritesheet, int delay, int animationIndex,
            boolean deleteAtTheEnd) {
        super(position, spritesheet, delay, animationIndex);
    }

    public DeadAnimation(Actor actor){
        super(actor.GetPosition());
        delay = 5;
        this.actor = actor;
        setAnimationMachine(new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/DeadAnimation.png"), new Vector2D<>(29, 36)));
        SetScale(actor.GetScale());
        ObjectManager.GetObjectManager().AddEntity(this);
        defaultAnimationIndex = 0;
        animationMachine.setMustComplete(true);
        animationMachine.AddFinishedListener(new AnimationEvent() {

            @Override
            public void OnTrigger() {
                Kill();
            }
            
        });
        Animate(0);
    }

    private void Kill() {
        actor.SetScale(new Vector2D<>(0f, 0f));
        ObjectManager.GetObjectManager().RemoveEntity(actor);
        this.SetScale(new Vector2D<>(0f, 0f));
        ObjectManager.GetObjectManager().RemoveEntity(this);
    }
}

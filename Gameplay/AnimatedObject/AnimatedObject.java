package Gameplay.AnimatedObject;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ListIterator;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.CollisionResult;
import Engine.Physics.Components.BoxCollider;
import Gameplay.States.PlayState;
import Gameplay.Enemies.Enemy;
import Gameplay.LifeBar.LifeBar;
import Gameplay.NPC.Npc;

public class AnimatedObject extends Actor{
    protected AnimationMachine animationMachine;
    protected BufferedImage[] animation;
    protected int delay = 5;
    protected int defaultAnimationIndex;

    public AnimatedObject (Vector2D<Float> position, Spritesheet spritesheet, int delay,int defaultAnimationIndex){
        super(position);

        setAnimationMachine(spritesheet);

        animation = spritesheet.GetSpriteArray2D()[defaultAnimationIndex];
        this.defaultAnimationIndex = defaultAnimationIndex;

        ObjectManager.GetObjectManager().AddEntity(this);
    }
    public AnimatedObject(Vector2D<Float> position) {
        super(position);
    }

    @Override
    public void Update(){
        super.Update();
        Animate(defaultAnimationIndex);
    }

    public void Animate(int i){
        animationMachine.SetFrames(animation);
        this.defaultAnimationIndex = i;
    }

    public void setAnimationMachine(Spritesheet spritesheet) {
        spritesheet.flip();
        animationMachine = AddComponent(new AnimationMachine(this, spritesheet));
        animationMachine.GetAnimation().SetDelay(delay);
        animation = spritesheet.GetSpriteArray2D()[defaultAnimationIndex];
        System.out.println(animation.length);
    }
}
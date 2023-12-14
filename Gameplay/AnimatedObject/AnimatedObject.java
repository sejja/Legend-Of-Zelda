package Gameplay.AnimatedObject;

import java.awt.image.BufferedImage;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;

/* This is abstract class thats create a basic object that have no movement and a simple animation
 *      -> It has a default animation delay = 5
 *      -> It has to have a position a SpriteSheet
 *          -> if you provide a actor position it will fixed in the actor
 */
public abstract class AnimatedObject extends Actor{
    protected AnimationMachine animationMachine;
    protected BufferedImage[] animation;
    protected int delay = 5;
    protected int defaultAnimationIndex = 0;

    public AnimatedObject (Vector2D<Float> position, Spritesheet spritesheet, int delay,int defaultAnimationIndex){
        super(position);
        setAnimationMachine(spritesheet);
        this.delay = delay;
        animation = spritesheet.GetSpriteArray2D()[defaultAnimationIndex];
        this.defaultAnimationIndex = defaultAnimationIndex;
        spawn();
    }

    public AnimatedObject (Vector2D<Float> position, Spritesheet spritesheet){
        super(position);
        setAnimationMachine(spritesheet);
        animation = spritesheet.GetSpriteArray2D()[defaultAnimationIndex];
        spawn();
    }

    public AnimatedObject(Vector2D<Float> position) {
        super(position);
    }

    @Override
    public void Update(){
        super.Update();
    }

    public void Animate(int i){
        animationMachine.SetFrameTrack(defaultAnimationIndex);
        animationMachine.GetAnimation().SetDelay(delay);
        this.defaultAnimationIndex = i;
    }

    public void setAnimationMachine(Spritesheet spritesheet) {
        spritesheet.Transpose();
        animationMachine = AddComponent(new AnimationMachine(this, spritesheet));
        animationMachine.GetAnimation().SetDelay(delay);
        animation = spritesheet.GetSpriteArray2D()[defaultAnimationIndex];
    }
}
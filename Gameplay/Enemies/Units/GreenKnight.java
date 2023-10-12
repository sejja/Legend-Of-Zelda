package Gameplay.Enemies.Units;

import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Enemies.*;

public class GreenKnight extends Enemy{
    
    protected Vector2D<Float> size = new Vector2D<Float>(75f, 100f);

    //animation
    protected int xoffset = 8;
    protected int yoffset = 32;
    protected Spritesheet sprite=new Spritesheet("Content/Animations/gknight.png", 24,28);
    

    public GreenKnight(Vector2D<Float> position) {
        super(position);
        setPseudoPosition(50f,50f);
        SetScale(size);
        setDamage(2);
        setHp(4);
        setSpeed(0);

        // TRANSPOSE SPRITE MATRIX
        sprite.flip();

        // ADD ANIMATION COMPONENT
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        
        // ADD COLLIDER COMPONENT
        mCollision = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<Float>(size.x, size.y)));
        SetAnimation(UP, sprite.GetSpriteArray(UP), 2);

        setPseudoPositionVisible();
    }
}

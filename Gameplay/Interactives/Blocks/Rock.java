package Gameplay.Interactives.Blocks;

import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Interactives.Interactive;

public class Rock extends Interactive{
    protected Vector2D<Float> size = new Vector2D<Float>(75f, 100f);

    //animation
    protected int xoffset = 8;
    protected int yoffset = 32;
    protected Spritesheet sprite=new Spritesheet("Content/Animations/gknight.png", 24,28);
    

    public Rock(Vector2D<Float> position) {
        super(position);
        SetScale(size);

        // TRANSPOSE SPRITE MATRIX
        sprite.flip();

        // ADD ANIMATION COMPONENT
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        
        // ADD COLLIDER COMPONENT
        mCollision = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<Float>((size.x-25f)*2, size.y*2)));
        SetAnimation(0, sprite.GetSpriteArray(0), 2);

    }
    
}

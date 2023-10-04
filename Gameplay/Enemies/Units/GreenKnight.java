package Gameplay.Enemies.Units;

import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Enemies.*;

public class GreenKnight extends Enemy{
    Vector2D size = new Vector2D<Float>(50.f, 100.f);
    

    public GreenKnight(Vector2D<Float> position, Vector2D<Float> size) {
        super(position);
        SetScale(size);
        Spritesheet sprite=new Spritesheet("Content/Animations/gknight.png", 24,28);
        

        // TRANSPOSE SPRITE MATRIX
        sprite.flip();
        // ADD ANIMATION COMPONENT
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        SetScale(new Vector2D<Float>(size.x+25, size.y));
        
        // ADD COLLIDER COMPONENT
        mCollision = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<Float>(size.x*2, size.y*2)));
        SetAnimation(UP, sprite.GetSpriteArray(UP), 2);

    }
    
}

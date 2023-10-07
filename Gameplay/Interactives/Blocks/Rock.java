package Gameplay.Interactives.Blocks;

import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Interactives.Interactive;

public class Rock extends Interactive{
    protected Vector2D<Float> size = new Vector2D<Float>(64f, 64f);

    //animation
    protected Spritesheet sprite=new Spritesheet("Content/Animations/rock.png", 16,16);
    

    public Rock(Vector2D<Float> position) {
        super(position);
        SetScale(size);

        // ADD ANIMATION COMPONENT
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        
        // ADD COLLIDER COMPONENT
        mCollision = (BoxCollider)AddComponent(new BoxCollider(this));
        SetAnimation(0, sprite.GetSpriteArray(0), 2);

    }
    
}

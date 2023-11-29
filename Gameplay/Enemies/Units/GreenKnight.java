package Gameplay.Enemies.Units;

import Engine.Assets.AssetManager;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Enemies.*;

public class GreenKnight extends Enemy{
    
    protected Vector2D<Float> size = new Vector2D<Float>(50f, 100f);

    //animation
    protected int xoffset = 8;
    protected int yoffset = 32;
    protected Spritesheet sprite=new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/gknight.png"), new Vector2D<>(16, 28));
    

    public GreenKnight(Vector2D<Float> position) {
        super(position);
        setPseudoPosition(25f, 50f);
        SetScale(size);
        setDamage(2);
        setHp(4);
        setSpeed(0f);

        // TRANSPOSE SPRITE MATRIX
        sprite.Transpose();

        // ADD ANIMATION COMPONENT
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        
        // ADD COLLIDER COMPONENT
        mCollision = (BoxCollider)AddComponent(new BoxCollider(this, size, true));
        SetAnimation(UP, sprite.GetSpriteArray(UP), 2);
        setPseudoPositionVisible();
    }
}

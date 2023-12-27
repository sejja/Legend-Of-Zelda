package Gameplay.AnimatedObject;

import Engine.Assets.AssetManager;
import Engine.Graphics.Spritesheet;
import Engine.Math.Vector2D;
import Engine.Physics.StaticPlayerCollision;
import Engine.Physics.Components.BoxCollider;
public class Lava extends AnimatedObject implements StaticPlayerCollision {
    private BoxCollider hitbox;
    private Vector2D<Float> pos;

    public Lava(Vector2D<Float> position) {
        super(position, new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/lava.png"), 2,2));
        this.pos = position;
        delay = 70;
        this.SetScale(new Vector2D<>(64f,64f));
        Animate(1);
        this.setDefaultPseudoPosition();
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, GetScale(), true));
        animationMachine.SetFrameTrack(1);
    }
    @Override
    public void Update(){
        super.Update();
        hitbox.Update();
        playerCollision(hitbox);
        SetPosition(pos);
    }

}

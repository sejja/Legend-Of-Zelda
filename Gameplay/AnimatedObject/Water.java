package Gameplay.AnimatedObject;

import Engine.Assets.AssetManager;
import Engine.Audio.Audio;
import Engine.Audio.Sound;
import Engine.ECSystem.World;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Animations.AnimationEvent;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Math.Vector2D;
import Engine.Physics.StaticPlayerCollision;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Interaction;
import Gameplay.NPC.Npc;

public class Water extends AnimatedObject implements StaticPlayerCollision {

    private BoxCollider hitbox;
    private Vector2D<Float> pos;

    public Water(Vector2D<Float> position) {
        super(position, new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/agua.png"), 1,3));
        this.pos = position;
        delay = -1;
        this.SetScale(new Vector2D<>(50f,100f));
        Animate(1);
        
        this.setDefaultPseudoPosition();
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, GetScale(), true));
        animationMachine.SetFrameTrack(1);

        ;
    }
    

}

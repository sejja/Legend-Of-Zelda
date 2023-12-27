package Gameplay.AnimatedObject;

import java.awt.image.BufferedImage;

import Engine.Assets.AssetManager;
import Engine.Audio.Audio;
import Engine.Audio.Sound;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.World;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Animations.AnimationEvent;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Math.Vector2D;
import Engine.Physics.ColliderManager;
import Engine.Physics.StaticPlayerCollision;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Interaction;
import Gameplay.NPC.Npc;

public class Water extends AnimatedObject implements StaticPlayerCollision {
    private BoxCollider hitbox;
    private int previusFrameCount = 0;
    private Vector2D<Float> pos;

    public Water(Vector2D<Float> position) {
        super(position, new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/agua2.png"), 3,2));
        this.pos = position;
        delay = -1;
        this.SetScale(new Vector2D<>(64f,32f));
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
        if (this.animationMachine.MustComplete()){
            if(this.animationMachine.GetAnimation().GetFrame() != previusFrameCount){
                previusFrameCount = this.animationMachine.GetAnimation().GetFrame();
                
            }
        }
        SetPosition(pos);
    }

}

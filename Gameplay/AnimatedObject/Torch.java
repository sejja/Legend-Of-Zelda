package Gameplay.AnimatedObject;

import Engine.ECSystem.ObjectManager;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Animations.AnimationEvent;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Math.Vector2D;
import Engine.Physics.StaticPlayerCollision;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Interaction;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

public class Torch extends AnimatedObject implements Interaction, StaticPlayerCollision {
    
    private BoxCollider hitbox;
    private boolean isIluminating = false;

    public Torch(Vector2D<Float> position) {
        super(position, new Spritesheet( "Content/Animations/Torch.png", 5,2, true));
        delay = -1;
        this.SetScale(new Vector2D<>(50f,100f));
        Animate(1);
        this.setDefaultPseudoPosition();
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, GetScale(), true));

        animationMachine.AddFinishedListener(new AnimationEvent() {

            @Override
            public void OnTrigger() {
                iluminate();
            }
            
        });
    }

    public void Update(){
        super.Update();
        hitbox.Update();
        playerCollision(hitbox);
    }

    @Override
    public void interaction() {
        if(!animationMachine.MustComplete()){
           if (isIluminating){
                turnOff();
            }else{
                turnON();
            }
        }
        shutDownInteraction();
    }
    
    private void turnON(){
        animationMachine.setMustComplete(true);
        delay = 8;
        Animate(1);
    }

    private void iluminate(){
        delay = 18;
        isIluminating = true;
        Animate(0);
        System.out.println("Sun bless you");

        Vector2D<Integer> tilePosition = this.GetPosition().getTilePosition();

        ShadowLayer.getShadowLayer().illuminate(tilePosition, 255);;

    }

    private void turnOff(){
        delay = -1;
        Animate(1);
        isIluminating = false;
    }

    @Override
    public Class GetSuperClass(){return Npc.class;}
}

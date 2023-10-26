package Gameplay.AnimatedObject;

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
    private int radius;
    private int previusFrameCount = 0;
    final int defaultRadius = 1;

    public Torch(Vector2D<Float> position) {
        super(position, new Spritesheet( "Content/Animations/Torch.png", 5,2, true));
        delay = -1;
        this.SetScale(new Vector2D<>(50f,100f));
        radius = defaultRadius;
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
        if (this.animationMachine.MustComplete()){
            if(this.animationMachine.GetAnimation().GetFrame() != previusFrameCount){
                previusFrameCount = this.animationMachine.GetAnimation().GetFrame();
                removeIlumination();
                radius++;
                //System.out.println("Aumentando radio de iluminacion a : " + radius);
                addIlumination();
                //System.out.println("____________________________________________________________________");
            }
        }
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
        //System.out.println("Se enciende con radio: "+ radius);
        animationMachine.setMustComplete(true);
        delay = 8;
        Animate(1);
        addIlumination();
    }

    private void iluminate(){
        removeIlumination();
        radius++;
        //System.out.println("Final radius: " + radius);
        delay = 18;
        isIluminating = true;
        Animate(0);
        //System.out.println("Sun bless you");
        addIlumination();
    }

    private void turnOff(){
        removeIlumination();
        delay = -1;
        Animate(1);
        isIluminating = false;
        previusFrameCount = 0;
        radius = defaultRadius;
    }

    @Override
    public Class GetSuperClass(){return Npc.class;}

    private void addIlumination(){
        //System.out.println("remove opacity in radius = " + radius);
        Vector2D<Integer> tilePosition = this.getPseudoPosition().getTilePosition();
        final int maxDisctance = (int)Math.round(Math.sqrt(2)*radius);
        for (int i = tilePosition.x - maxDisctance; i <= tilePosition.x + maxDisctance; i++){
            for(int j = tilePosition.y - maxDisctance; j <= tilePosition.y+maxDisctance; j++){
                int difference = luminosityDiffereceFunction(new Vector2D<Integer>(i,j), tilePosition);
                ShadowLayer.getShadowLayer().illuminate(new Vector2D<Integer>(i, j), -difference);
            }
        }
    }

    private void removeIlumination(){
        //System.out.println("Adding opacity in radius = " + radius);
        Vector2D<Integer> tilePosition = this.getPseudoPosition().getTilePosition();
        final int maxDisctance = (int)Math.round(Math.sqrt(2)*radius);
        for (int i = tilePosition.x - maxDisctance; i <= tilePosition.x + maxDisctance; i++){
            for(int j = tilePosition.y - maxDisctance; j <= tilePosition.y + maxDisctance; j++){
                int difference = luminosityDiffereceFunction(new Vector2D<Integer>(i,j), tilePosition);
                ShadowLayer.getShadowLayer().illuminate(new Vector2D<Integer>(i, j), difference);
            }
        }
    }

    private int luminosityDiffereceFunction(Vector2D<Integer> tilePosition, Vector2D<Integer> origin){
        double distance = Math.sqrt( Math.pow(tilePosition.x-origin.x,2) + Math.pow(tilePosition.y-origin.y,2) );
        if (distance>radius){return 0;}
        final double coeficient = ShadowLayer.getShadowLayer().opacity;
        double difference = Math.round( (-( ( coeficient*distance ) / radius ) + coeficient)); //Esos espacios NO SE TOCA
        return (int) difference;
    }
}

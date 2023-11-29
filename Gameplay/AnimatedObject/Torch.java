package Gameplay.AnimatedObject;

import Engine.Assets.AssetManager;
import Engine.Audio.Audio;
import Engine.Audio.Sound;
import Engine.ECSystem.World;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Animations.AnimationEvent;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Math.EuclideanCoordinates;
import Engine.Math.Vector2D;
import Engine.Math.TileCoordinates;
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
    private Vector2D<Float> pos;
    final int defaultRadius = 1;

    public Torch(Vector2D<Float> position) {
        super(position, new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/Torch.png"), 5,2));
        this.pos = position;
        delay = -1;
        this.SetScale(new Vector2D<>(50f,100f));
        radius = defaultRadius;
        Animate(1);
        
        this.setDefaultPseudoPosition();
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, GetScale(), true));
        animationMachine.SetFrameTrack(1);

        animationMachine.AddFinishedListener(new AnimationEvent() {
            @Override
            public void OnTrigger() {
                iluminate();
            }
        });
    }
    @Override
    public void Update(){
        super.Update();
        hitbox.Update();
        playerCollision(hitbox);
        if (this.animationMachine.MustComplete()){
            //System.out.println("Animacion que debe terminal");
            if(this.animationMachine.GetAnimation().GetFrame() != previusFrameCount){
                //System.out.println("sigue iluminando");
                previusFrameCount = this.animationMachine.GetAnimation().GetFrame();
                removeIlumination();
                radius++;
                addIlumination();
            }
        }
        SetPosition(pos);
    }

    @Override
    public void interaction() {
        if(!animationMachine.MustComplete()){
           if (isIluminating){
                turnOff();
            }else{
                //System.out.println("Se ilumina");
                turnON();
            }
        }
        shutDownInteraction();
    }
    
    private void turnON(){
        animationMachine.setMustComplete(true);

        //System.out.println("Esta en el ObjectManager -> " + ObjectManager.GetObjectManager().containsInstance(this.GetSuperClass(), this));
        delay = 8;
        Animate(0);
        Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/fire.wav"));
        Audio.Instance().Play(sound);
        addIlumination();
    }

    private void iluminate(){
        removeIlumination();
        radius++;
        delay = 18;
        isIluminating = true;
        Animate(0);
        addIlumination();
    }

    private void turnOff(){
        removeIlumination();
        delay = -1;
        Animate(1);
        this.animationMachine.SetFrameTrack(1);
        isIluminating = false;
        previusFrameCount = 0;
        radius = defaultRadius;
    }

    @Override
    public Class GetSuperClass(){return Npc.class;}

    private void addIlumination(){
        Vector2D<Integer> tilePosition = new TileCoordinates(getPseudoPosition()).getTilePosition();
        final int maxDisctance = (int)Math.round(Math.sqrt(2)*radius);
        for (int i = tilePosition.x - maxDisctance; i <= tilePosition.x + maxDisctance; i++){
            for(int j = tilePosition.y - maxDisctance; j <= tilePosition.y+maxDisctance; j++){
                int difference = luminosityDiffereceFunction(new Vector2D<Integer>(i,j), tilePosition);
                ShadowLayer.getShadowLayer().illuminate(new Vector2D<Integer>(i, j), -difference);
            }
        }
    }

    private void removeIlumination(){
        Vector2D<Integer> tilePosition = new TileCoordinates(getPseudoPosition()).getTilePosition();
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
        double difference = Math.round( (-( ( coeficient*distance ) / radius ) + coeficient)); //Esos espacios NO SE TOCA <-----------------------------------------
        return (int) difference;
    }

    public Vector2D<Float> getWorldPseudoPosition(){
        return World.GetLevelSpaceCoordinates(super.getPseudoPosition());
    }

    @Override
    public void RemoveAllComponent(){
        super.RemoveAllComponent();
        this.removeIlumination();
    }
    /*
    @Override
    public String toString() {
        return this.getPseudoPosition().toString();
    }
    */
}

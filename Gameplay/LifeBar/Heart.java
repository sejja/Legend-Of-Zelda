package Gameplay.LifeBar;

import Engine.Assets.AssetManager;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;

public class Heart extends Actor {
    private AnimationMachine animationMachine;
    private int healthPoints = 2;

    public Heart (Vector2D<Float> position){
        super(position);
        this.animationMachine = AddComponent(new AnimationMachine(this ,new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/HeartSpriteSheet.png"), new Vector2D<>(19, 14))));
        animationMachine.SetFrameTrack(0);
        SetScale( new Vector2D<Float>(15f,11f));
        animationMachine.GetAnimation().SetDelay(1);
        Animate();
    }
    public void Animate(){
        this.animationMachine.SetFrameTrack((int)Math.abs(healthPoints-2));
    }
    public void setHealthPoints(int life){
        if (life < 0){System.err.println("Error");return;}
        this.healthPoints = life;
    }
    @Override
    public void Update(){
        Animate();
    }

    public int getHealthPoints() {
        return healthPoints;
    }
    public void addToObjectManager(){
        ObjectManager.GetObjectManager().AddEntity(this);
    }
    public void popFromObjectManager(){
        despawn();
    }
    public boolean isInObjectManager(){
        return ObjectManager.GetObjectManager().GetAllObjectsOfType(GetSuperClass()).contains(this);
    }
}

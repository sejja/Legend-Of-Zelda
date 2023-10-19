package Gameplay.LifeBar;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.management.MonitorInfo;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Enemies.Enemy;

public class Heart extends Actor {
    private AnimationMachine animationMachine;
    private BufferedImage[][] allAnimations;
    private int healthPoints = 2;

    public Heart (Vector2D<Float> position){
        super(position);
        this.animationMachine = AddComponent(new AnimationMachine(this ,new Spritesheet("Content/Animations/HeartSpriteSheet.png", 19 , 14)));
        allAnimations = animationMachine.GetSpriteSheet().GetSpriteArray2D();
        animationMachine.SetFrames(allAnimations[0]);
        SetScale( new Vector2D<Float>(15f,11f));
        animationMachine.GetAnimation().SetDelay(1);
        Animate();
    }
    public void Animate(){
        this.animationMachine.SetFrames(allAnimations[(int)Math.abs(healthPoints-2)]);
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

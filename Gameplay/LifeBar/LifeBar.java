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
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Enemies.Enemy;

public class LifeBar extends Actor{
    private int healthPoints;
    private Heart[] hearts;
    private AnimationMachine animationMachine;

    public LifeBar (Actor actor, int healthPoints){
        super(new Vector2D<Float>(actor.GetPosition().x - actor.GetScale().x/2, actor.GetPosition().y - actor.GetScale().y/2));
        this.healthPoints = healthPoints;
        createHearts();
        this.animationMachine = AddComponent(new AnimationMachine(this, new Spritesheet("Content/Animations/HeartSpriteSheet.png", 19 , 14)));
    }

    public void setVisible(boolean b){
        if (b){
            for (int i = 0; i < hearts.length; i++){
                hearts[i].SetScale(new Vector2D<>(0f, 0f));
            }
        }else{
            for (int i = 0; i < hearts.length; i++){
                hearts[i].SetScale(new Vector2D<>(20f, 20f));
            }
        }
    }
    
    public void setHealthPoints(int healthPoints) {this.healthPoints = healthPoints;} 

    public void setHearts(){
        int completeHearts = 0;
        int incompleteHearts = 0;
        if (healthPoints % 2 == 0){
            completeHearts = healthPoints/2;
        } else {
            completeHearts = healthPoints-1/2;
            incompleteHearts = 1;
        }

        //Full hearts
        for (int i = 0; i < completeHearts; i++){
            hearts[i].setHealthPoints(2);
        }
        //Half heart
        hearts[completeHearts].setHealthPoints(incompleteHearts);
        //Empty Hearts
        for (int i = completeHearts+1; i < hearts.length; i++){
            hearts[i].setHealthPoints(0);
        }
    }

    private void createHearts (){
        Float counter = 0f;
        hearts = new Heart[healthPoints];
        for (int i = 0; i < healthPoints/2; i++){
            Vector2D<Float> position = new Vector2D<>(GetPosition().x + counter, GetPosition().y);
            hearts[i] = new Heart(position);
            counter += hearts[0].GetScale().x;
        }
    }

    public void Update(){
        setHearts();
        for (int i = 0; i < healthPoints; i++){
            hearts[i].Animate();
        }
    }
}

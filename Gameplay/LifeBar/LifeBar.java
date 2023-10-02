package Gameplay.LifeBar;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.Visibility;
import java.lang.management.MonitorInfo;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.text.Position;

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

/*  This is a class that allow to show a LifeBar
 *      FEATURE:
 *              -> It does NOT add any object to the ObjectManager thats are permanent, so this object it is not accesible from the ObjectManager
 *              -> The actor must have healthPoints
 * 
 *      HOW TO add A LIFEBAR TO A ACTOR:
 *              -> Call LifeBar.Update() in the Actor.Update()
 *              -> Use setVisible() to show lifeBar or not
 *              -> Use setHealthpoints() to change the healthpoints of the actor in the lifebar
 */
public class LifeBar extends Entity{
    private int healthPoints;
    private Heart[] hearts;
    private AnimationMachine animationMachine;
    private Vector2D<Float> position;
    private boolean VISIBLE = false;
    private Actor actor;

    public LifeBar (Actor actor, int healthPoints){
        this.actor = actor;
        this.position = actor.GetPosition();
        this.healthPoints = healthPoints;
        createHearts();
        setVisible(VISIBLE);
    }

    public void setVisible(boolean b){
        this.VISIBLE = b;
        if (b)
        {
            for (int i = 0; i < hearts.length; i++)
            {
                hearts[i].SetScale(new Vector2D<>(15f, 14f));
            }
            ObjectManager.GetObjectManager().AddEntity(this);
        }
        else
        {
            for (int i = 0; i < hearts.length; i++)
            {
                hearts[i].SetScale(new Vector2D<>(0f, 0f));
            }
            ObjectManager.GetObjectManager().RemoveEntity(this);
        }
        Update();
    }
    
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
        addToObjectManager();
        setHearts();
        System.out.println(heartsInfoToString());
        clearHeartsObjectManager();
    } 

    public void Update(){
        if(VISIBLE){
            setHeartsPosition();
            for (int i = 0; i < healthPoints/2; i++){
                hearts[i].Update();
            }
        }
    }

    private void setHearts(){
        int completeHearts = 0;
        int incompleteHearts = 0;
        if (healthPoints % 2 == 0){
            completeHearts = healthPoints/2;
        } else {
            completeHearts = (healthPoints-1)/2;
            incompleteHearts = 1;
        }

        //Full hearts
        for (int i = 0; i < completeHearts; i++){
            hearts[i].setHealthPoints(2);
        }
        //Half heart
        if(incompleteHearts != 0){
            hearts[completeHearts].setHealthPoints(incompleteHearts);
        }else{
            hearts[completeHearts].setHealthPoints(0);
        }
        //Empty Hearts
        for (int i = completeHearts+1; i < hearts.length; i++){
            hearts[i].setHealthPoints(0);
        }
    }

    private void createHearts (){
        Float counter = 0f;
        hearts = new Heart[healthPoints/2];
        for (int i = 0; i < healthPoints/2; i++){
            Vector2D<Float> position = new Vector2D<>(this.position.x + counter, this.position.y);
            hearts[i] = new Heart(position);
            //ObjectManager.GetObjectManager().AddEntity(hearts[i]);
            counter += hearts[0].GetScale().x;
        }
    }
    private void setHeartsPosition(){
        Vector2D<Float> actorPosition = actor.GetPosition();
        Float counter = 0f;
        for (int i = 0; i < hearts.length; i++){
            Vector2D<Float> heartPosition = new Vector2D<>(actorPosition.x-10 + counter, actorPosition.y-10);
            hearts[i].SetPosition(heartPosition);
            counter += hearts[0].GetScale().x;
        }
    }
    private String heartsInfoToString (){
        String str = "";
        for (int i = 0; i<hearts.length; i++){
            str = str + hearts[i].getHealthPoints() + ";"; 
        }
        return str;
    }
    private void addToObjectManager(){
        for (int i = 0; i < hearts.length; i++){
            hearts[i].addToObjectManager();
        }
    }
    private void clearHeartsObjectManager (){
        for (int i = 0; i < hearts.length; i++){
            hearts[i].popFromObjectManager();
        }
    }
}

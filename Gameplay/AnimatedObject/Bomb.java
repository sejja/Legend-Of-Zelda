package Gameplay.AnimatedObject;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Animations.AnimationEvent;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Engine.Physics.Components.ColliderManager;
import Gameplay.Enemies.Enemy;
import Gameplay.Interactives.Interactive;
import Gameplay.Interactives.Blocks.Rock;

public class Bomb extends AnimatedObject {
    
    private BufferedImage[][] allAnimtion;

    private int counter = 0;
    final private int limit = 90;
    final private int damage = 1;

    private BoxCollider hitbox;

    public Bomb(Vector2D<Float> position) {
        super(position);
        Spritesheet spritesheet = new Spritesheet("Content/Animations/bomb.png", 10,1, true);
        delay = 5;
        setAnimationMachine(spritesheet);
        this.allAnimtion  = spritesheet.GetSpriteArray2D();
        createChargeAnimation();
        animationMachine.SetFrames(allAnimtion[1]);
        this.SetScale(new Vector2D<Float>(100f,100f));
        ObjectManager.GetObjectManager().AddEntity(this);

        var reference = this;
        animationMachine.AddFinishedListener(new AnimationEvent() {

            @Override
            public void OnTrigger() {
                //System.out.println("Ha terminado");
                ObjectManager.GetObjectManager().RemoveEntity(reference);
                reference.SetScale(new Vector2D<>(0f,0f));
                
            }
            
        });
        setDefaultPseudoPosition();
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, this.GetScale(), false));
    }

    private void createChargeAnimation(){
        for ( int i = 0; i < allAnimtion[0].length; i++){
            if (i%2 == 0){
                allAnimtion[1][i] = allAnimtion[0][0];
            }else{
                allAnimtion[1][i] = allAnimtion[0][1];
            }
        }
    }

    @Override
    public void Update(){
        super.Update();
        countDown();
    }

    public void countDown(){
        if(counter == limit){
            animationMachine.setMustComplete(true);
            animationMachine.SetFrames(allAnimtion[0]);
            delay = 3;
            explode();
            counter = 0;
        }else{
            counter++;
        }
    }

    public void explode(){
        ArrayList<Actor> enemies = ColliderManager.GetColliderManager().getCollision(hitbox, Enemy.class, true);
        ArrayList<Actor> interactives = ColliderManager.GetColliderManager().getCollision(hitbox, Interactive.class, true);
        if (!enemies.isEmpty()){
            for(int i = 0; i<enemies.size(); i++){
                Enemy enemy = (Enemy)enemies.get(i);
                enemy.setHealthPoints(damage);
                enemy.knockBack();
            }
        }
        if(!interactives.isEmpty()){
            for(int i = 0; i<interactives.size(); i++){
                Rock rock = (Rock)interactives.get(i);
                rock.setHealthPoints(damage);
            }
        }
    }
}

package Gameplay.AnimatedObject;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.Spritesheet;
import Engine.Math.Vector2D;
import Gameplay.Enemies.Enemy;
import Gameplay.Interactives.Blocks.Rock;

public class Bomb extends AnimatedObject {
    
    private BufferedImage[][] allAnimtion;

    private int counter = 0;
    final private int limit = 90;

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
        if (animationMachine.finised_Animation){
            ObjectManager.GetObjectManager().RemoveEntity(this);
            this.SetScale(new Vector2D<>(0f, 0f));
        }
    }

    public void countDown(){
        if(counter == limit){
            animationMachine.SetFrames(allAnimtion[0]);
            animationMachine.setMust_Complete();
            delay = 3;
            explode();
            counter = 0;
        }else{
            counter++;
        }
    }

    public void explode(){
        System.out.println("Ha explotado");
        ArrayList<Entity> allEntities = ObjectManager.GetObjectManager().getmAliveEntities();
        for (int i = 0; i < allEntities.size(); i++){
            if (allEntities.get(i) instanceof Enemy){
                Enemy enemy = (Enemy) allEntities.get(i);
                Vector2D<Float> enemyPosition = enemy.GetPosition();
                //System.out.println(enemyPosition.getModuleDistance(this.GetPosition()));
                if (enemyPosition.getModuleDistance(this.GetPosition()) < this.GetScale().getModule()){ //Each enemy thats can be attacked
                    System.out.println("Le da");
                    enemy.setHealthPoints(4);
                    enemy.KnockBack(this.GetPosition());
                }
            }
            if (allEntities.get(i) instanceof Rock){
                Rock rock = (Rock) allEntities.get(i);
                Vector2D<Float> rockPosition = rock.GetPosition();
                //System.out.println(enemyPosition.getModuleDistance(this.GetPosition()));
                if (rockPosition.getModuleDistance(this.GetPosition()) < this.GetScale().getModule()){
                    System.out.println("Peta");
                    rock.setHealthPoints(1);
                    
                }
            }
        }
        return;
    }
}

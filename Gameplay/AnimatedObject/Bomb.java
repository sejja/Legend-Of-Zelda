package Gameplay.AnimatedObject;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Spritesheet;
import Engine.Math.Vector2D;

public class Bomb extends AnimatedObject {
    
    private BufferedImage[][] allAnimtion;

    private int counter = 0;
    final private int limit = 100;

    public Bomb(Vector2D<Float> position) {
        super(position);
        Spritesheet spritesheet = new Spritesheet("Content/Animations/bomb.png", 10,1, true);
        delay = 10;
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
        }else{
            counter++;
        }
    }

    public void explode(){
        System.out.println("Ha explotado");
    }
}

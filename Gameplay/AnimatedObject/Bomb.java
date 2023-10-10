package Gameplay.AnimatedObject;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

import Engine.Graphics.Spritesheet;
import Engine.Math.Vector2D;

public class Bomb extends AnimatedObject {
    private BufferedImage[][] allAnimtion;
    public Bomb(Vector2D<Float> position) {
        super(new Vector2D<>(position));
        Spritesheet spritesheet = new Spritesheet("Content/Animations/bomb.png", 10,1, true);
        setAnimationMachine(spritesheet);
        this.allAnimtion  = spritesheet.GetSpriteArray2D();
        createChargeAnimation();
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
    }
}

package Gameplay.AnimatedObject;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

import Engine.Assets.AssetManager;
import Engine.Audio.Audio;
import Engine.Audio.Sound;
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
    final private int damage = 5;

    private BoxCollider hitbox;

    public Bomb(Vector2D<Float> position) {
        super(position);
        Spritesheet spritesheet = new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/bomb.png"), 10,1);
        delay = 5;
        setAnimationMachine(spritesheet);
        createChargeAnimation(spritesheet.GetSpriteArray2D());
        animationMachine.SetFrameTrack(0);
        animationMachine.GetAnimation().SetFrames(allAnimtion[0]);
        animationMachine.GetSpriteSheet().ChangeSpriteFrames(allAnimtion);
        this.SetScale(new Vector2D<Float>(100f,100f));
        ObjectManager.GetObjectManager().AddEntity(this);

        var reference = this;
        animationMachine.AddFinishedListener(new AnimationEvent() {
            @Override
            public void OnTrigger() {
                despawn();
            }
        });
        setDefaultPseudoPosition();
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, this.GetScale(), false));
    }

    private void createChargeAnimation(BufferedImage[][] imgs) {
        allAnimtion = new BufferedImage[2][];
        allAnimtion[0] = new BufferedImage[2];
        allAnimtion[1] = new BufferedImage[imgs[0].length - 2];
        for ( int i = 0; i < imgs[0].length; i++){
            if (i < 2){
                allAnimtion[0][i] = imgs[0][i];
            }else{
                allAnimtion[1][i - 2] = imgs[0][i];
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
            animationMachine.SetFrameTrack(1);
            delay = 3;
            counter = 0;
            explode();
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

        Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/bomb.wav"));
        Audio.Instance().Play(sound);
    }
}

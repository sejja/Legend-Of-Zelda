package Gameplay.Link;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle.Control;
import java.util.concurrent.atomic.AtomicInteger;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.FontComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;

public class Arrow extends Entity{

    final private int damage = 2;
    private AnimationMachine animationMachine;
    final private int speed = 18;
    final private int range = 200;

    boolean up = false;
    boolean left = false;
    boolean right = true;
    boolean down = false;

    public Arrow(Actor Link){
        this.animationMachine = new AnimationMachine(Link, new Spritesheet("Content/Animations/Arrow.png"));
        SetPosition(Link.GetPosition());
    }
    public void Move(){
        Vector2D<Float> position = GetPosition();
    }
    @Override
    public void Update() {
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }
    public void Animate(){

    }
}

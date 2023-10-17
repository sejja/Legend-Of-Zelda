package Gameplay.AnimatedObject;

import Engine.Graphics.Spritesheet;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Interactive;
import Gameplay.NPC.Npc;

public class Torch extends AnimatedObject implements Interactive{
    
    private BoxCollider hitbox;

    public Torch(Vector2D<Float> position) {
        super(position, new Spritesheet( "Content/Animations/Torch.png", 5,2, true));
        delay = 8;
        this.SetScale(new Vector2D<>(50f,100f));
        Animate(0);
        this.setDefaultPseudoPosition();
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, GetScale(), false));
    }

    public void Update(){
        super.Update();
        hitbox.Update();
        pseudoPositionUpdate();
    }

    @Override
    public void interaction() {
        System.out.println("Ha interactuado");
    }

    @Override
    public Class GetSuperClass(){return Npc.class;};
}

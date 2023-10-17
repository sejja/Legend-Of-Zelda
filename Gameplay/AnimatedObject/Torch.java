package Gameplay.AnimatedObject;

import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Spritesheet;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Interaction;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

public class Torch extends AnimatedObject implements Interaction{
    
    private BoxCollider hitbox;
    private boolean isIluminating = false;

    public Torch(Vector2D<Float> position) {
        super(position, new Spritesheet( "Content/Animations/Torch.png", 5,2, true));
        delay = -1;
        this.SetScale(new Vector2D<>(50f,100f));
        Animate(1);
        this.setDefaultPseudoPosition();
        setPseudoPositionVisible();
        hitbox = (BoxCollider)AddComponent(new BoxCollider(this, GetScale(), true));
    }

    public void Update(){
        super.Update();
        hitbox.Update();
        pseudoPositionUpdate();
    }

    @Override
    public void interaction() {
        System.out.println("Ha interactuado");

        if (isIluminating){
            turnOff();
        }else{

        }

        ((Player)ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).getFirst()).removeInteraction();
    }
    
    private void iluminate(){
        System.out.println("SUN BLESS YOU");
    }


    private void turnOff(){
        delay = -1;
        Animate(1);
        isIluminating = false;
    }

    @Override
    public Class GetSuperClass(){return Npc.class;}
}

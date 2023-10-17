package Gameplay.Interactives.Blocks;

import javax.xml.catalog.CatalogFeatures.Feature;

import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Tile.Block;
import Engine.Graphics.Tile.Normblock;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Engine.Physics.StaticPlayerCollision;
import Engine.Physics.Components.BoxCollider;
import Engine.Physics.Components.ColliderManager;
import Gameplay.Interactives.Interactive;
import Gameplay.Link.Player;

public class Rock extends Interactive implements StaticPlayerCollision{
    protected Vector2D<Float> size = new Vector2D<Float>(64f, 64f);

    //animation
    protected Spritesheet sprite=new Spritesheet("Content/Animations/rock.png", 16,16);
    

    public Rock(Vector2D<Float> position) {
        super(position);
        SetScale(size);

        // ADD ANIMATION COMPONENT
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        
        // ADD COLLIDER COMPONENT
        mCollision = (BoxCollider)AddComponent(new BoxCollider(this,size,true));
        ColliderManager.GetColliderManager().addCollider(mCollision, true);
        SetAnimation(0, sprite.GetSpriteArray(0), 2);

        setPseudoPosition(GetScale().x/2, GetScale().y/2);
        setPseudoPositionVisible();

        mPositionPair = PositionToPair(getPseudoPosition());
        Block block = TileManager.sLevelObjects.GetBlockAt(mPositionPair.getFirst(),mPositionPair.getSecond());
        System.out.println(block);
        if (block instanceof Normblock){
            ((Normblock) block).setBlocked(true);
            System.out.println(((Normblock) block).isBlocked());
        }
    }
    @Override

    public void Update(){
        super.Update();
        playerCollision();
    }
}

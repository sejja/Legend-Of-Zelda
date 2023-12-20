package Gameplay.Interactives.Blocks;

import javax.xml.catalog.CatalogFeatures.Feature;

import Engine.Assets.AssetManager;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Tile.Block;
import Engine.Graphics.Tile.Normblock;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Engine.Physics.ColliderManager;
import Engine.Physics.StaticPlayerCollision;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Interactives.Interactive;
import Gameplay.Link.Player;

/**
 * Represents a Rock interactive object in the game.
 * Extends the Interactive class and implements StaticPlayerCollision.
 */
public class Rock extends Interactive implements StaticPlayerCollision{
    protected Vector2D<Float> size = new Vector2D<Float>(64f, 64f);

    //animation
    protected Spritesheet sprite=new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/rock.png"), new Vector2D<>(16, 16));

    /**
     * Constructs a Rock object at the specified position.
     *
     * @param position The initial position of the rock.
     */
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
    }
    @Override

    /**
     * Updates the rock.
     * Checks for player collision.
     */
    public void Update(){
        super.Update();
        playerCollision(this.getmCollision());
    }

    /**
     * Sets the health points of the rock and triggers its destruction if health reaches zero.
     *
     * @param damage The amount of damage to be applied.
     */
    public void setHealthPoints(int damage){
        this.healthPoints -= damage;
        if (healthPoints <= 0){
            die();
        }      
    }

    /**
     * Handles the destruction of the rock.
     * Shuts down the collider, removes the block from the level, and despawns the rock.
     */
    public void die(){
        mCollision.ShutDown();
        Vector2D<Integer> pos = new Vector2D<>(mPositionPair.getFirst(), mPositionPair.getSecond());
        TileManager.sLevelObjects.RemoveBlockAt(pos);
        this.SetScale(new Vector2D<Float>(0f,0f));
        despawn();
    }
}

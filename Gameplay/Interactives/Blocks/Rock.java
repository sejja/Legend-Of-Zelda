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

public class Rock extends Interactive implements StaticPlayerCollision{
    protected Vector2D<Float> size = new Vector2D<Float>(64f, 64f);

    //animation
    protected Spritesheet sprite=new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/rock.png"), new Vector2D<>(16, 16));

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
        Vector2D<Integer> pos = new Vector2D<>(mPositionPair.getFirst(), mPositionPair.getSecond());
        block = TileManager.sLevelObjects.GetBlockAt(pos);
        if(block == null) {
            TileManager.sLevelObjects.PlaceBlockAt(pos);
        }
    }
    @Override

    public void Update(){
        super.Update();
        playerCollision(this.getmCollision());
    }

    public void setHealthPoints(int damage){
        this.healthPoints -= damage;
        if (healthPoints <= 0){
            die();
        }      
    }

    public void die(){
        mCollision.ShutDown();
        Vector2D<Integer> pos = new Vector2D<>(mPositionPair.getFirst(), mPositionPair.getSecond());
        TileManager.sLevelObjects.RemoveBlockAt(pos);
        this.SetScale(new Vector2D<Float>(0f,0f));
        despawn();
    }
}

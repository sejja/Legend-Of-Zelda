package Gameplay.Interactives.Blocks;

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
import Engine.Window.GameLoop;
import Gameplay.Interactives.Interactive;
import Gameplay.Link.Player;
import Gameplay.NPC.DialogueWindow;


public class Chest extends Interactive implements StaticPlayerCollision{
    protected Vector2D<Float> size = new Vector2D<Float>(64f, 64f);
    static boolean interact = false;
    private static DialogueWindow dialogueWindow;

    //animation
    protected Spritesheet sprite=new Spritesheet(AssetManager.Instance().GetResource("Content/Animations/chest.png"), new Vector2D<>(16, 16));

    private void Pause(){
        GameLoop.SetPaused(!GameLoop.IsPaused());
    }
    
    public Chest(Vector2D<Float> position) {
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
    public void setHealthPoints(boolean interact){
        if (interact == true){
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
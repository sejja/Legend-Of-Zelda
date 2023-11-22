//
//	GraphicsPipeline.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import Engine.Developer.Logger.Logger;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.World;
import Engine.ECSystem.Types.Component;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Gameplay.LifeBar.Heart;
import Gameplay.LifeBar.LifeBar;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

public class GraphicsPipeline {
    private ArrayList<Renderable> mRenderables;
    private ArrayList<Renderable> mNewRenderables = new ArrayList<>();
    private ArrayList<Renderable> mOldRenderables = new ArrayList<>();
    private ArrayList<Renderable> unremovableRenderables;

    static private GraphicsPipeline sPipe = new GraphicsPipeline();
    private CameraComponent mCamera;
    private Vector2D<Integer> mDimensions;

    private ShadowLayer shadowLayer;

    // ------------------------------------------------------------------------
    /*! Get Graphics Pipeline
    *
    *   Returns a singleton instance of the graphics pipeline
    */ //----------------------------------------------------------------------
    static public GraphicsPipeline GetGraphicsPipeline() {
        return sPipe;
    }

    public void SetDimensions(Vector2D<Integer> dim) {
        mDimensions = dim;
    }

    public Vector2D<Integer> GetDimensions() {
        return mDimensions;
    }

    // ------------------------------------------------------------------------
    /*! Default constructor
    *
    *   creates an arraylist of renderables
    */ //----------------------------------------------------------------------
    private GraphicsPipeline() {
        mDimensions = new Vector2D<>(0, 0);
        mRenderables = new ArrayList<>();
        mCamera = null;
        shadowLayer = new ShadowLayer(170);
    }

    public void BindCamera(CameraComponent c) {
        mCamera = c;
    }

    // ------------------------------------------------------------------------
    /*! Add Renderable
    *
    *   Adds a Renderable to the pipeline
    */ //----------------------------------------------------------------------
    public void AddRenderable(Renderable r) {
        mNewRenderables.add(r);
    }

    public void AddRenderableBottom(Renderable r) {
        mRenderables.add(0, r);
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders every component
    */ //----------------------------------------------------------------------
    public void Render(Graphics2D g) {
        //Renderable
        for (Renderable r: mRenderables) 
            r.Render(g, mCamera);

        for(Renderable r: mOldRenderables)
            mRenderables.remove(r);

        mOldRenderables.clear();
        mRenderables.addAll(mNewRenderables);
        mNewRenderables.clear();

        shadowLayer.Render(g, mCamera);
        Logger.Instance().Render(g);
        //renderableInfo();
        /*for(Renderable x : mRenderables){
            if(unremovableRenderables == null){
                break;
            }
            if(x instanceof AnimationMachine && !unremovableRenderables.contains(x)){
                System.out.println(((AnimationMachine)x).GetParent());
            }
        }*/
    }

    // ------------------------------------------------------------------------
    /*! Remove Renderable
    *
    *   Removes one renderable
    */ //----------------------------------------------------------------------
    public void RemoveRenderable(Renderable r) {
        mOldRenderables.add(r);
        
    }

    public CameraComponent GetCamera() {
        return mCamera;
    }

    public void UnbindCamera(CameraComponent c) {
        mCamera = (mCamera == c) ? null : mCamera;
    }

    public void renderableInfo(){
        System.out.println(mRenderables.size());;
    }

    /** This function remove all renderable of the previus level expept Zelda's components and NPC
     * 
     */
    public void flush(){
        if(unremovableRenderables == null){
            Player player = (Player) ObjectManager.GetObjectManager().GetAllObjectsOfType(Player.class).get(0);
            ArrayList<Entity> npcArraylist = ObjectManager.GetObjectManager().GetAllObjectsOfType(Npc.class);
            ArrayList<Component> playerComponents = new ArrayList<>(player.getmComponents());
            LifeBar lifeBar = player.getLifebar();
            Heart[] hearts = lifeBar.getHearts();
            ArrayList<AnimationMachine> animation = new ArrayList<AnimationMachine>();
            for(Heart heart : hearts){
                animation.add((AnimationMachine)heart.getmComponents().get(0));
            }
            //System.out.println(playerComponents);
            //System.out.println(animation.size());
            TileManager managerDeTiles = World.mCurrentLevel.mTilemap;
            for(int i=0; i< playerComponents.size(); i++){
                if(playerComponents.get(i) instanceof ZeldaCameraComponent){
                    playerComponents.remove(i);
                }
            }
            unremovableRenderables = new ArrayList<>();
            unremovableRenderables.add(managerDeTiles);
            unremovableRenderables.addAll(animation);
            unremovableRenderables.addAll((Collection<? extends Renderable>)playerComponents);
            for( Entity npc : npcArraylist){
                if(npc instanceof Npc){
                    unremovableRenderables.addAll((Collection<? extends Renderable>)((Npc)npc).getmComponents()); //getAnimationMachine from Npc
                }
            }
        }
        mRenderables.clear();
        
        mRenderables.addAll(unremovableRenderables);
    }
}

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
import java.util.Collections;
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
import Engine.Window.GameLoop;
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

    // ------------------------------------------------------------------------
    /*! Set Dimensions
    *
    *   Sets the rendering dimensions
    */ //----------------------------------------------------------------------
    public void SetDimensions(final Vector2D<Integer> dim) {
        mDimensions = dim;
    }

    // ------------------------------------------------------------------------
    /*! Get Dimensions
    *
    *   Retrieve the Rendering Dimensions
    */ //----------------------------------------------------------------------
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

    // ------------------------------------------------------------------------
    /*! Bind Camera
    *
    *   Binds the rendering camera
    */ //----------------------------------------------------------------------
    public void BindCamera(final CameraComponent c) {
        mCamera = c;
    }

    // ------------------------------------------------------------------------
    /*! Add Renderable
    *
    *   Adds a Renderable to the pipeline
    */ //----------------------------------------------------------------------
    public void AddRenderable(final Renderable r) {
        mNewRenderables.add(r);
    }

    // ------------------------------------------------------------------------
    /*! Add Renderable Bottom
    *
    *   Adds a Renderable to the botttom of the rendering pipeline
    */ //----------------------------------------------------------------------
    public void AddRenderableBottom(final Renderable r) {
        mRenderables.add(0, r);
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders every component
    */ //----------------------------------------------------------------------
    public void Render(Graphics2D g) {
        //System.out.println("SortPrevius: ");
        //ySortInfo();
        int i = 0;
        try {
            while (mRenderables.get(i) instanceof TileManager) {
                i++;
            }
            insertionSort(i);
        } catch (java.lang.IndexOutOfBoundsException indexError) {
            System.err.println("Init");
        } catch (java.lang.ClassCastException castError){
            System.err.println("Contenido de los renders");
            System.err.println(mRenderables);
            System.err.println("Se ha añadido algo que no deberia ser ordenado : ");
            for(int k = i; k < mRenderables.size(); k++){
                if( !(mRenderables.get(k) instanceof Component) ){
                    System.out.println(mRenderables.get(i).getClass());
                }
            }
            GameLoop.Quit();
        }
        //System.out.println("SortPos: ");
        //ySortInfo();


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
        //System.out.println(mRenderables);
    }

    // ------------------------------------------------------------------------
    /*! Remove Renderable
    *
    *   Removes one renderable
    */ //----------------------------------------------------------------------
    public void RemoveRenderable(final Renderable r) {
        mOldRenderables.add(r);
        
    }

    // ------------------------------------------------------------------------
    /*! Remove All Renderables
    *
    *   Removes all renderables from the renderable list
    */ //----------------------------------------------------------------------
    public void RemoveAllRenderables() {
        mRenderables.forEach(x -> mOldRenderables.add(x));
    }

    // ------------------------------------------------------------------------
    /*! Get Camera
    *
    *   Returns the Binded Camera
    */ //----------------------------------------------------------------------
    public CameraComponent GetBindedCamera() {
        return mCamera;
    }

    // ------------------------------------------------------------------------
    /*! Unbind Camera
    *
    *   Unbinds the current rendering camera, if there is any
    */ //----------------------------------------------------------------------
    public void UnbindCamera(final CameraComponent c) {
        mCamera = (mCamera == c) ? null : mCamera;
    }

    public void renderableInfo(){
        System.out.println(mRenderables.size());;
    }

    public void ySortInfo(){
        for(int i = 1; i < mRenderables.size(); i++){
            System.out.print( ((Component)mRenderables.get(i)).GetParent().GetPosition().y + " -> " );
        }
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
    private void insertionSort(int i){
        for(int j = i; j < mRenderables.size()-1; j++){
            if(((Component)mRenderables.get(j)).compareTo((Component)mRenderables.get(j+1)) > 0){ //component.y > nextComponent.y
                int a = j; 
                int b = j+1;
                while (a >= i) {
                    //System.out.println("ytd");
                    Component componentA = (Component)mRenderables.get(a);
                    Component componentB = (Component)mRenderables.get(b);
                    if(componentA.compareTo(componentB) > 0){ //A.y > B.y
                        Collections.swap(mRenderables, a, b);
                        a--;
                        b--;
                    }else{
                        a = -1; //End loop
                    }
                }
            }
        }
    }
}

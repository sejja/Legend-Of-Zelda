//
//	ObjectManager.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 09/10/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Math.Vector2D;
import Gameplay.AnimatedObject.Torch;
import Gameplay.Enemies.Enemy;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

//This is a Singleton
public class ObjectManager {
    private ConcurrentHashMap<Class<?>, ArrayList<Entity>> mAliveEntities = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Class<?>, ArrayList<Entity>> mDeadEntities = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Class<?>, ArrayList<Entity>> mNewEntities = new ConcurrentHashMap<>();
    private Actor mPawn = null;
    static private ObjectManager sManager = new ObjectManager();

    // ------------------------------------------------------------------------
    /*! Get Object Manager
    *
    *   As a Singleton, get the only instance
    */ //----------------------------------------------------------------------
    static public synchronized ObjectManager GetObjectManager() {
        return sManager;
    }

    public void Clear() {
        for(var x : mAliveEntities.keySet()) {
            for(Entity y : mAliveEntities.get(x)) {
                RemoveEntity(y);
            }
        }
    }

    // ------------------------------------------------------------------------
    /*! Get Object By Name
    *
    *   Given the type of the actor that we want, retrieve an object by name
    */ //----------------------------------------------------------------------
    public <T> Entity GetObjectByName(Class<T> type, String name) {
        //Checks on the chunk of the desired type
        if(mAliveEntities.containsKey(type))
            return mAliveEntities.get(type).stream()
                .filter(x -> x.GetName().equals(name))
                .findFirst()
                .orElse(null);
        return null;
    }

    /** Add a entity, if it is an actor and it already contains the same actor but different instance it will remove the previus instance and add the new instance
     * 
     * @param e entity to add
     * @return
     */
    public Entity AddEntity(Entity e) {
        final Class<?> type = e.GetSuperClass();

        //If we already contain the key, perfect
        if(mAliveEntities.containsKey(type)) {
            //System.out.println(type);
            if(!mAliveEntities.get(e.GetSuperClass()).contains(e)){
                 mNewEntities.get(type).add(e);
            }
            else{
                if(e instanceof Actor){
                    for(int i = 0; i < mAliveEntities.get(type).size(); i++){
                        if( (mAliveEntities.get(type).get(i) != e ) &&  ((Actor)mAliveEntities.get(type).get(i)).equals((Actor)e) ){ //If it is the same Actor but different instance
                            mAliveEntities.get(type).set(i, e);
                        }
                    }
                }
            }

        //else, create a new chunk
        } else {
            ArrayList<Entity> chunk = new ArrayList<>();
            chunk.add(e);
            mNewEntities.put(type, chunk);
            mAliveEntities.put(type, new ArrayList<>());
        }
        return e;
    }

    // ------------------------------------------------------------------------
    /*! Remove Entity
    *
    *   Removes an entity from the object manager
    */ //----------------------------------------------------------------------
    public void RemoveEntity(Entity e) {
        final Class<?> type = e.GetSuperClass();

        if (e instanceof Actor){((Actor)e).RemoveAllComponent();}

        //If we already contain the key, perfect
        if(mDeadEntities.containsKey(type)) {
            mDeadEntities.get(type).add(e);
            e.SetScale(new Vector2D<>(0f,0f));
        //else
        } else {
            ArrayList<Entity> chunk = new ArrayList<>();
            chunk.add(e);
            mDeadEntities.put(type, chunk);
        }
    }

    // ------------------------------------------------------------------------
    /*! Set Pawn 
    *
    *   Gets the Pawn that the Object Manager observes
    */ //----------------------------------------------------------------------
    public void SetPawn(Actor a) {
        mPawn = a;
    }

    // ------------------------------------------------------------------------
    /*! Get Pawn
    *
    *   Retrieves the pawn
    */ //----------------------------------------------------------------------
    public Actor GetPawn() {
        return mPawn;
    }

    // ------------------------------------------------------------------------
    /*! Get All Objects Of Type
    *
    *   Returns all the objects of an specific type
    */ //----------------------------------------------------------------------
    public ArrayList<Entity> GetAllObjectsOfType(Class<?> type) {
        return mAliveEntities.get(type);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates every object on the object manager
    */ //----------------------------------------------------------------------
    public synchronized void Update() {
        mAliveEntities.values()            
            .stream().forEach(x -> x
            .stream().forEach(y -> y.Update()));

        mDeadEntities.keySet().stream().forEach( x -> {
            final ArrayList<Entity> chunk = mAliveEntities.get(x);
            mDeadEntities.get(x).stream().forEach(y -> chunk.remove(y));
        });

        mDeadEntities.clear();

        mNewEntities.keySet().stream().forEach( x -> {
            final ArrayList<Entity> chunk = mAliveEntities.get(x);

            mNewEntities.get(x).stream().forEach( y -> chunk.add(y));
        });

        //mNewEntities.clear();
        mNewEntities.values().stream().forEach(x -> x.clear());
        //managerInfo();
    }

    /** Inform aboout the content of the ObjectManager
     * 
     */
    public void managerInfo(){
        System.out.println("------------------------------------------------------------------------------------------");
        for (Class code: mAliveEntities.keySet()){
            System.out.println(code.descriptorString() + "Num: " + mAliveEntities.get(code).size());
            //System.out.println("Contend : \n" + mAliveEntities.get(code));
        }
    }
    public void mNewEntitiesInfo(){
        System.out.println("------------------------------------------------------------------------------------------");
        for (Class code: mNewEntities.keySet()){
            System.out.println(code.descriptorString() + "Num: " + mAliveEntities.get(code).size());
            System.out.println("Contend : \n" + mAliveEntities.get(code));
        }
    }
    /** Remove all aliveEntities except NPC,Actor
     * 
     */
    public void flush(){
        ShadowLayer.getShadowLayer().resetMatrix();
        ArrayList<Entity> player = mAliveEntities.get(Player.class);
        ArrayList<Entity> NPCs = mAliveEntities.get(Npc.class);
        for(Entity enemy: mAliveEntities.get(Enemy.class)){ //Kill all enemy
            ((Enemy)enemy).superDie();
        }
        GraphicsPipeline.GetGraphicsPipeline().flush();
        mAliveEntities.clear();
        mAliveEntities.put(Player.class, player);
        mAliveEntities.put(Npc.class, NPCs);
    }
    /** This function checks if the objectManager contais the INSTANCE of the entity, not the entity. So it does not call compareTo or equals.
     * 
     * @param type the superclass of the entity
     * @param entity entity to search
     * @return if the object Manager has the instance ? True : False
     */
    public boolean containsInstance(Class type, Entity entity){
        boolean result = false;
        for(Entity e: mAliveEntities.get(type)){
            result = (result || (e == entity));
        }
        return result;
    }
}

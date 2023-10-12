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
import Engine.Math.Vector2D;
import Gameplay.Enemies.Enemy;
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

    // ------------------------------------------------------------------------
    /*! Add Entity
    *
    *   Adds an entity to the Object Manager, classifying it
    */ //----------------------------------------------------------------------
    public Entity AddEntity(Entity e) {
        final Class<?> type = e.GetSuperClass();

        //If we already contain the key, perfect
        if(mAliveEntities.containsKey(type)) {
            //System.out.println(type);
            mNewEntities.get(type).add(e);

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

        //If we already contain the key, perfect
        if(mDeadEntities.containsKey(type)) {
            mDeadEntities.get(type).add(e);

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
    public void Update() {
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
    }
}

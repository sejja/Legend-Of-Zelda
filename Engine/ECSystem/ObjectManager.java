package Engine.ECSystem;

import java.lang.reflect.ClassFileFormatVersion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Gameplay.NPC.Npc;

public class ObjectManager {
    private ArrayList<Entity> mAliveEntities;
    private ArrayList<Entity> mDeadEntities;
    private ArrayList<Entity> mNewEntities;
    static private ObjectManager sManager = new ObjectManager();

    private HashMap<Class,TreeSet<Actor>> mapAliveActors;

    static public ObjectManager GetObjectManager() {
        return sManager;
    }

    public Entity GetObjectByName(String name) {
        for(Entity x : mAliveEntities)
            if(x.GetName().equals(name))
                return x;
        return null;
    }

    private ObjectManager() {
        mAliveEntities = new ArrayList<>();
        mDeadEntities = new ArrayList<>();
        mNewEntities = new ArrayList<>();
        
        mapAliveActors = new HashMap<>();
    }

    public Entity AddEntity(Entity e) {
        mNewEntities.add(e);

        //Map----------------------------------------------------------
        
        if ((e instanceof Actor)){
            if ( !mapAliveActors.containsKey(e.getClass()) ){
                mapAliveActors.put(e.getClass(), new TreeSet<Actor>());
            }
            mapAliveActors.get(e.getClass()).add((Actor)e);
        }
        System.out.println(mapAliveActors);
        //-------------------------------------------------------------
        return e;
    }

    public void RemoveEntity(Entity e) {
        mDeadEntities.add(e);
    }

    public void Update() {
        for(Entity x : mAliveEntities)
            x.Update();
            
        for(Entity x : mDeadEntities)
            mAliveEntities.remove(x);

        mDeadEntities.clear();
        mAliveEntities.addAll(mNewEntities);
        mNewEntities.clear();

        System.out.println(mapAliveActors);
    }

    public ArrayList<Entity> getmAliveEntities() {return mAliveEntities;}
    public HashMap<Class, TreeSet<Actor>> getMapAliveActors() {return mapAliveActors;}
}

package Engine.ECSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Math.Vector2D;
import Gameplay.Enemies.Enemy;
import Gameplay.NPC.Npc;

public class ObjectManager {
    private ArrayList<Entity> mAliveEntities;
    private ArrayList<Entity> mDeadEntities;
    private ArrayList<Entity> mNewEntities;
    static private ObjectManager sManager = new ObjectManager();

    private HashMap<Class,LinkedList<Actor>> mapAliveActors;

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
            if (!mapAliveActors.containsKey(e.getClass())){
                mapAliveActors.put(e.getClass(), new LinkedList<Actor>());
            }
            mapAliveActors.get(e.getClass()).add((Actor)e);
        }
        //System.out.println(e.getClass());
        //-------------------------------------------------------------
        return e;
    }

    public Entity AddEntity(Enemy e) {
        mNewEntities.add(e);
        System.out.println("pataa");
        //Map----------------------------------------------------------
        if (!mapAliveActors.containsKey(Enemy.class)){
            mapAliveActors.put(Enemy.class, new LinkedList<Actor>());
        }
        mapAliveActors.get(Enemy.class).add((Actor)e);
        //System.out.println(e.getClass());
        //-------------------------------------------------------------
        return e;
    }

    public void RemoveEntity(Entity e) {
        mDeadEntities.add(e);

        e.SetScale(new Vector2D<>(0f,0f));

        if(e instanceof Actor){
            if(e instanceof Enemy){
                    try {
                        mapAliveActors.get(Enemy.class).remove(e);
                        System.out.println("Se quita");
                    } catch (java.lang.NullPointerException a) {
                        //System.err.println(e.getClass() + " Already removed or it does not exist");
                    }
                return;
            }
            try {
                mapAliveActors.get(e.getClass()).remove(e);
            } catch (java.lang.NullPointerException a) {
                //System.err.println(e.getClass() + " Already removed or it does not exist");
            }
        }
    }

    public void Update() {
        for(Entity x : mAliveEntities)
            x.Update();
            
        for(Entity x : mDeadEntities)
            mAliveEntities.remove(x);

        mDeadEntities.clear();
        mAliveEntities.addAll(mNewEntities);
        mNewEntities.clear();
    }

    public ArrayList<Entity> getmAliveEntities() {return mAliveEntities;}
    public HashMap<Class, LinkedList<Actor>> getMapAliveActors() {return mapAliveActors;}
}

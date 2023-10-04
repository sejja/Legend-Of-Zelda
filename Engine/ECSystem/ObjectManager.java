package Engine.ECSystem;

import java.util.ArrayList;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;

public class ObjectManager {
    private ArrayList<Entity> mAliveEntities;
    private ArrayList<Entity> mDeadEntities;
    private ArrayList<Entity> mNewEntities;
    private Actor mPawn;
    static private ObjectManager sManager = new ObjectManager();

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
        mPawn = null;
    }

    public Entity AddEntity(Entity e) {
        mNewEntities.add(e);
        return e;
    }

    public void RemoveEntity(Entity e) {
        mDeadEntities.add(e);
    }

    public void SetPawn(Actor a) {
        mPawn = a;
    }

    public Actor GetPawn() {
        return mPawn;
    }

    public void Update() {
        for(Entity x : mAliveEntities){
            //System.out.println(mAliveEntities.size());;
            x.Update();
        }

        for(Entity x : mDeadEntities)
            mAliveEntities.remove(x);

        mDeadEntities.clear();
        mAliveEntities.addAll(mNewEntities);
        mNewEntities.clear();
    }

    public ArrayList<Entity> getmAliveEntities() {return mAliveEntities;}
    
}

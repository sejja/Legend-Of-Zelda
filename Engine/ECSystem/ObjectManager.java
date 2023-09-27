package Engine.ECSystem;

import java.util.ArrayList;

import Engine.ECSystem.Types.Entity;

public class ObjectManager {
    private ArrayList<Entity> mAliveEntities;
    private ArrayList<Entity> mDeadEntities;
    private ArrayList<Entity> mNewEntities;
    static private ObjectManager sManager = new ObjectManager();

    static public ObjectManager GetObjectManager() {
        return sManager;
    }

    private ObjectManager() {
        mAliveEntities = new ArrayList<>();
        mDeadEntities = new ArrayList<>();
        mNewEntities = new ArrayList<>();
    }

    public Entity AddEntity(Entity e) {
        mNewEntities.add(e);
        return e;
    }

    public void RemoveEntity(Entity e) {
        mDeadEntities.add(e);
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
}

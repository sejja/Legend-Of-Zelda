package Engine.ECSystem;

import java.util.ArrayList;

import Engine.ECSystem.Types.Entity;

public class ObjectManager {
    private ArrayList<Entity> mAliveEntities;
    private ArrayList<Entity> mDeadEntities;
    static private ObjectManager sManager = new ObjectManager();

    static public ObjectManager GetObjectManager() {
        return sManager;
    }

    private ObjectManager() {
        mAliveEntities = new ArrayList<>();
        mDeadEntities = new ArrayList<>();
    }

    public Entity AddEntity(Entity e) {
        mAliveEntities.add(e);
        return e;
    }

    public void RemoveEntity(Entity e) {
        mDeadEntities.add(e);
    }

    public void Update() {
        for(int i = 0; i < mAliveEntities.size(); i++)
        {
            //System.out.println(mAliveEntities.get(i).getClass());
            mAliveEntities.get(i).Update();
        }

        for(Entity x : mDeadEntities)
            mAliveEntities.remove(x);

        mDeadEntities.clear();
    }
}

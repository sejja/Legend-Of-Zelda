package Engine.Physics.Components;

import java.util.HashMap;
import java.util.LinkedList;

import Gameplay.Enemies.Enemy;

public class ColliderManager {
    private static ColliderManager colliderManager;
    private HashMap<Class, LinkedList<BoxCollider>> mapAllCollider;
    private HashMap<Class, LinkedList<BoxCollider>> mapAllCollision;
    private HashMap<Class, LinkedList<BoxCollider>> mapAllNonCollision;

    public ColliderManager (){
        mapAllCollision = new HashMap<>();
        mapAllNonCollision = new HashMap<>();
        mapAllCollider = new HashMap<>();
    }

    public ColliderManager getColliderManager (){return colliderManager;}

    private void addCollider(BoxCollider collider, boolean itCollides){
        if(!mapAllCollider.containsKey(collider.GetParent().GetSuperClass())){
            mapAllCollider.put(collider.GetParent().GetSuperClass(), new LinkedList<BoxCollider>());
            mapAllCollision.put(collider.GetParent().GetSuperClass(), new LinkedList<BoxCollider>());
            mapAllNonCollision.put(collider.GetParent().GetSuperClass(), new LinkedList<BoxCollider>());
        }else{
            if(!mapAllCollider.get(collider.GetParent().GetSuperClass()).contains(collider)){
                mapAllCollider.get(collider.GetParent().GetSuperClass()).add(collider);
                if(itCollides){
                    mapAllCollision.get(collider.GetParent().GetSuperClass()).add(collider);
                }else{
                    mapAllNonCollision.get(collider.GetParent().GetSuperClass()).add(collider);
                }
            }
            else{return;}
        }
    }

        private void removeCollider(BoxCollider collider){
        if(!mapAllCollider.containsKey(collider.GetParent().GetSuperClass())){
            return;
        }else{
            if(!mapAllCollider.get(collider.GetParent().GetSuperClass()).contains(collider)){return;}
            else{
                mapAllCollider.get(collider.GetParent().GetSuperClass()).remove(collider);
                mapAllCollision.get(collider.GetParent().GetSuperClass()).remove(collider);
                mapAllNonCollision.get(collider.GetParent().GetSuperClass()).remove(collider);
            }
        }
    }
}

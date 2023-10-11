package Engine.Physics.Components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.Box;

import Engine.ECSystem.Types.Actor;

public class ColliderManager {
    private static ColliderManager colliderManager = new ColliderManager();
    private HashMap<Class, LinkedList<BoxCollider>> mapAllCollider;
    private HashMap<Class, LinkedList<BoxCollider>> mapAllCollision;
    private HashMap<Class, LinkedList<BoxCollider>> mapAllNonCollision;

    public ColliderManager (){
        mapAllCollision = new HashMap<>();
        mapAllNonCollision = new HashMap<>();
        mapAllCollider = new HashMap<>();
    }

    public static ColliderManager GetColliderManager (){return colliderManager;}

    public void addCollider(BoxCollider collider, boolean itCollides){
        Class CLASS = collider.GetParent().GetSuperClass();
        if(!mapAllCollider.containsKey(CLASS)){
            mapAllCollider.put(CLASS, new LinkedList<BoxCollider>());
            mapAllCollision.put(CLASS, new LinkedList<BoxCollider>());
            mapAllNonCollision.put(CLASS, new LinkedList<BoxCollider>());
            addCollider(collider, itCollides);
        }else{
            if(!mapAllCollider.get(CLASS).contains(collider)){
                mapAllCollider.get(CLASS).add(collider);
                if(itCollides){
                    mapAllCollision.get(CLASS).add(collider);
                }else{
                    mapAllNonCollision.get(CLASS).add(collider);
                }
            }
            else{return;}
        }
        //System.out.println(mapAllCollider);
    }

    public void removeCollider(BoxCollider collider){
        Class CLASS = collider.GetParent().GetSuperClass();
        if(!mapAllCollider.containsKey(CLASS)){
            return;
        }else{
            if(!mapAllCollider.get(CLASS).contains(collider)){return;}
            else{
                mapAllCollider.get(CLASS).remove(collider);
                mapAllCollision.get(CLASS).remove(collider);
                mapAllNonCollision.get(CLASS).remove(collider);
            }
        }
    }

    public HashMap<Class, LinkedList<BoxCollider>> getMapAllCollider() {return mapAllCollider;}
    public HashMap<Class, LinkedList<BoxCollider>> getMapAllCollision() {return mapAllCollision;}
    public HashMap<Class, LinkedList<BoxCollider>> getMapAllNonCollision() {return mapAllNonCollision;}

    public ArrayList<Actor> getCollision(BoxCollider collider, Class objective, boolean hasCollision){
        if(hasCollision){
            
        }else{

        }
        return null;
    }
}

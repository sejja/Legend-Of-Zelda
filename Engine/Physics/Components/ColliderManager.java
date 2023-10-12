package Engine.Physics.Components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.lang.model.element.ModuleElement.DirectiveKind;
import javax.swing.Box;
import javax.swing.text.html.HTMLDocument.Iterator;

import Engine.ECSystem.Types.Actor;
import Engine.Math.Vector2D;
import Gameplay.Link.DIRECTION;

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
            System.out.println(mapAllCollider.get(objective));
            return (searchCollisionActors(collider, mapAllCollision.get(objective)));
        }else{
            return (searchCollisionActors(collider, mapAllNonCollision.get(objective)));
        }
    }

    private ArrayList<Actor> searchCollisionActors (BoxCollider collider, LinkedList<BoxCollider> listActors){
        ArrayList<Actor> result = new ArrayList<Actor>();
        if(listActors == null){return result;}
        else{
            ListIterator<BoxCollider> iterator = listActors.listIterator();
            while(iterator.hasNext()){
                BoxCollider otherCollider = iterator.next();
                Float pseudoPositionDistance = collider.GetParent().getPSeudoPosition().getModuleDistance(otherCollider.GetParent().getPSeudoPosition()); //This is the distance between the 2 pseudopositions
                Float limit = (collider.GetBounds().GetScale().getModule() + otherCollider.GetBounds().GetScale().getModule())/2;
                if (pseudoPositionDistance < limit){
                    if(hasCollided(collider, otherCollider)){
                        result.add(otherCollider.GetParent());
                    }
                }
            }
        }
        return result;
    }

    private boolean hasCollided(BoxCollider colliderA, BoxCollider colliderB){
        DIRECTION direction = colliderA.GetParent().getPSeudoPosition().getObjectiveDirection(colliderB.GetParent().getPSeudoPosition());
        if(direction == DIRECTION.UP || direction == DIRECTION.DOWN){
            Float distanceY = Math.abs(colliderA.GetParent().getPSeudoPosition().getVectorToAnotherActor(colliderB.GetParent().getPSeudoPosition()).y); //Horizontal limits
            Float limit = (colliderA.GetBounds().GetScale().y + colliderB.GetBounds().GetScale().y)/2;
            return distanceY < limit;
        }else{                                                                                                                                          //Vertical limits
            Float distanceX = Math.abs(colliderA.GetParent().getPSeudoPosition().getVectorToAnotherActor(colliderB.GetParent().getPSeudoPosition()).x);
            Float limit = (colliderA.GetBounds().GetScale().x + colliderB.GetBounds().GetScale().x)/2;
            return distanceX < limit;
        }
    }
}

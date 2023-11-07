package Engine.Physics.Components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import Engine.ECSystem.Types.Actor;
import Gameplay.Link.Player;
/** ColliderManager is a static object thats manage the colision between objects
 *      -> All BoxCollider thats hasCollision it is added automatically to the colliderManager
 *      -> Use ColliderManager.GetColliderManager() to the the ColliderManager
 *      -> Use getCollision(BoxCollider collider, Class objective, boolean hasCollision) to get the objects thats has a collision with the currentCollider 
 */
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
        //System.out.println(mapAllCollision);
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

    /* getCollision
     *  @Param
     *      -> collider <- The currentCollider
     *      -> objective <- Tha class that are searched to chech if some object of that class has collisioned with the currend collider
     *      -> hasCollision <-True if the target thats is being searching has collision mecanics (For exameple, torchs or bats have colliders but not collision )
     */
    public ArrayList<Actor> getCollision(BoxCollider collider, Class targerClass, boolean targetHasCollision){
        if(targetHasCollision){
            return (searchCollisionActors(collider, mapAllCollision.get(targerClass)));
        }else{
            return (searchCollisionActors(collider, mapAllNonCollision.get(targerClass)));
        }
    }

    private ArrayList<Actor> searchCollisionActors (BoxCollider collider, LinkedList<BoxCollider> listActors){
        ArrayList<Actor> result = new ArrayList<Actor>();
        if(listActors == null){return result;}
        else{
            ListIterator<BoxCollider> iterator = listActors.listIterator();
            while(iterator.hasNext()){
                BoxCollider otherCollider = iterator.next();
                Float pseudoPositionDistance = collider.GetParent().getPseudoPosition().getModuleDistance(otherCollider.GetParent().getPseudoPosition()); //This is the distance between the 2 pseudopositions
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
        Float distanceY = Math.abs(colliderA.GetParent().getPseudoPosition().getVectorToAnotherActor(colliderB.GetParent().getPseudoPosition()).y);
        Float limitY = (colliderA.GetBounds().GetScale().y + colliderB.GetBounds().GetScale().y)/2;
        Float distanceX = Math.abs(colliderA.GetParent().getPseudoPosition().getVectorToAnotherActor(colliderB.GetParent().getPseudoPosition()).x);
        Float limitX = (colliderA.GetBounds().GetScale().x + colliderB.GetBounds().GetScale().x)/2;
        return distanceX < limitX && distanceY < limitY;
    }

    public boolean playerCollision(BoxCollider collider){ 
        try{return(hasCollided(collider, this.mapAllCollision.get(Player.class).get(0)));}
        catch(java.lang.NullPointerException e){
            System.err.println("No player in game");
        }
        return false;
    }
}

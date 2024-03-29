package Engine.Physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import Engine.ECSystem.Types.Actor;
import Engine.Math.EuclideanCoordinates;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Link.Player;
/** ColliderManager is a static object thats manage the colision between objects, All BoxCollider thats hasCollision it is added automatically to the colliderManager 
 *  <p> Use ColliderManager.GetColliderManager() to the the ColliderManager </p>
 *  <p> Use getCollision(BoxCollider collider, Class objective, boolean hasCollision) to get the objects thats has a collision with the currentCollider </p>
 *  @author Lingfeng
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

    public void Clear() {
        mapAllCollision.clear();
        mapAllNonCollision.clear();
        mapAllCollider.clear();
    } 

    public static ColliderManager GetColliderManager (){return colliderManager;}

    /** Adds a collider; if it has the same collider but different instance it will remove the previus instance and add the next one
     * 
     * @param collider collider to add
     * @param itCollides if it has collision
     */
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
            else{
                removeCollider(collider);
                addCollider(collider, itCollides);
            }
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

     /** Get collision of the collider input
      * 
      * @param collider The currentCollider
      * @param targerClass Tha class that are searched to chech if some object of that class has collisioned with the currend collider
      * @param targetHasCollision True if the target thats is being searching has collision mecanics (For exameple, torchs or bats have colliders but not collision )
      * @return NonSorted Arraylist of all actors that collider has collided
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
                EuclideanCoordinates cords = new EuclideanCoordinates(collider.GetParent().getPseudoPosition());
                Float pseudoPositionDistance = cords.GetModulusDistance(otherCollider.GetParent().getPseudoPosition()); //This is the distance between the 2 pseudopositions
                EuclideanCoordinates desc = new EuclideanCoordinates(collider.GetBounds().GetScale());
                EuclideanCoordinates desc2 = new EuclideanCoordinates(otherCollider.GetBounds().GetScale());
                Float limit = (desc.getModule() + desc2.getModule())/2;
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
        EuclideanCoordinates coords = new EuclideanCoordinates(colliderA.GetParent().getPseudoPosition());
        Float distanceY = Math.abs(coords.getVectorToAnotherActor(colliderB.GetParent().getPseudoPosition()).y);
        Float limitY = (colliderA.GetBounds().GetScale().y + colliderB.GetBounds().GetScale().y)/2;
        Float distanceX = Math.abs(coords.getVectorToAnotherActor(colliderB.GetParent().getPseudoPosition()).x);
        Float limitX = (colliderA.GetBounds().GetScale().x + colliderB.GetBounds().GetScale().x)/2;
        return (distanceX < limitX) && (distanceY < limitY);
    }

    /** Determine if it has collided with the player
     * 
     * @param collider
     * @return bool
     */
    public boolean playerCollision(BoxCollider collider){ 
        try{return(hasCollided(collider, this.mapAllCollision.get(Player.class).get(0)));}
        catch(java.lang.NullPointerException e){
            System.err.println("No player in game");
        }
        return false;
    }
}

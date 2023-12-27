package Gameplay.Link;

import java.util.HashSet;
import java.util.LinkedList;

/** This is a deprecated class which purpose was manage the action of the link in order to substitute the statemachine
 *  @author Lingfeng
 */
@Deprecated
public class StackActioner {

    private Player link;
    private LinkedList<ActionObject> stack;
    private ActionObject floorAction;
    private HashSet<ActionObject> register;


    public StackActioner(Player player){
        link = player;
        stack = new LinkedList<>();
        floorAction = new ActionObject(link.getDirection());
        register = new HashSet<>();
        stack.addLast(floorAction);
        register.add(floorAction);
    }

    public ActionObject readCurrentAction(){return stack.getLast();}

    public boolean push(DIRECTION direction, Action action){
        return false;
        /* 
        if(stack.getLast().actionEquals(direction, action)){return false;}
        ActionObject newActionObject = new ActionObject(direction, action);
        if(!register.contains(newActionObject)){
            register.add(newActionObject);
            stack.addLast(newActionObject);
        }
        System.out.println("Push: " + toString());
        return false;
        */
    }

    public boolean pop(DIRECTION direction, Action action){
        return false;
        /*
        ActionObject actionToRemove = new ActionObject(direction, action);
        if(actionToRemove.equals(floorAction)){return false;}
        if (!register.contains(actionToRemove)) {
            System.err.println("ERROR ALGO NO VA BIEN :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("ConcheSumadre");
            return false;
        }
        floorAction.setDirection(actionToRemove.getDirection());
        register.remove(actionToRemove);
        stack.remove(actionToRemove);
        System.out.println("Pop: " + toString());
        return true;
        */
    }

    public void Update(){}

    @Override
    public String toString(){return (this.readCurrentAction().toString() + "| StackSize = " + stack.size() + "| RegisterSize = " + register.size() + "| floor = " + floorAction.getDirection());}
}

package Gameplay.Link;

import java.util.HashSet;
import java.util.LinkedList;

import Engine.ECSystem.ObjectManager;

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
        stack.push(floorAction);
    }

    public ActionObject readCurrentAction(){return stack.getLast();}

    public boolean push(DIRECTION direction, Action action){
        if(stack.getLast().actionEquals(direction, action)){return false;}
        ActionObject newActionObject = new ActionObject(direction, action);
        if(!register.contains(newActionObject)){
            //System.out.println("Chorizo");
            //System.out.println("quien unos loles");
            register.add(newActionObject);
            stack.addLast(newActionObject);
        }
        return false;
    }

    public boolean pop(ActionObject actionToRemove){
        if(actionToRemove.equals(floorAction)){return false;}
        if(register.contains(actionToRemove)){return false;}
        register.remove(actionToRemove);
        stack.remove(actionToRemove);
        return true;
    }

    @Override
    public String toString(){return this.readCurrentAction().toString();}
}

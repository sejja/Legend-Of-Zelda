package Gameplay.Link;

public class ActionException extends Exception{
    private static String  str = "ERROR DELETING ACRION";
    public ActionException(){
        super(str);
    }
}
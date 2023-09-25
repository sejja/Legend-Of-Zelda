package Gameplay.Link;
public enum Action{
    RUN(0),
    ATTACK(5),
    STOP(9),
    BOW(13);

    private int id;

    Action(int id){
        this.id = id;
    }
    public int getID (){
        return id;
    }
}
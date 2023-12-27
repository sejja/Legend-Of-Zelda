package Gameplay.LifeBar;

import Engine.ECSystem.Types.Actor;
import Engine.Math.Vector2D;

/*  This is a class that allow to show a LifeBar
 *      FEATURE:
 *              -> It does NOT add any object to the ObjectManager thats are permanent, so this object it is not accesible from the ObjectManager
 *              -> The actor must have healthPoints
 * 
 *      HOW TO add A LIFEBAR TO A ACTOR:
 *              -> Add LifeBarUpdate() at the end of Actor.Update()
 *              -> Use setVisible() to show lifeBar or not
 *              -> Use setHealthpoints() to change the healthpoints of the actor in the lifebar
 */

/** Lifebar properties
 *      Add LifeBarUpdate() at the end of Actor.Update() ->  Use setVisible() to show lifeBar or not -> Use setHealthpoints() to change the healthpoints of the actor in the lifebar
 *      @author
 */
public class LifeBar {
    private int healthPoints;
    private Heart[] hearts;
    private Vector2D<Float> position;
    private boolean VISIBLE = false;
    private Actor actor;


    public LifeBar (Actor actor, int healthPoints){
        this.actor = actor;
        this.position = actor.getPseudoPosition();
        this.healthPoints = healthPoints;
        createHearts();
        setVisible(VISIBLE);
    }

    public void setVisible(boolean b){
        this.VISIBLE = b;
        if (b)
        {
            for (int i = 0; i < hearts.length; i++)
            {
                hearts[i].SetScale(new Vector2D<>(15f, 14f));
            }
        }
        else
        {
            for (int i = 0; i < hearts.length; i++)
            {
                hearts[i].SetScale(new Vector2D<>(0f, 0f));
            }
        }
        Update();
    }
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
        setHearts();
        for (int i = 0; i < healthPoints/2; i++){
                hearts[i].Update();
        }
        addToObjectManager();
        //System.out.println(heartsInfoToString());
    } 
    public void Update(){
        if(VISIBLE){
            setHeartsPosition();
        }
        this.position = actor.getPseudoPosition();
    }
    private void setHearts(){
        int completeHearts = 0;
        int incompleteHearts = 0;
        if (healthPoints % 2 == 0){
            completeHearts = healthPoints/2;
        } else {
            completeHearts = (healthPoints-1)/2;
            incompleteHearts = 1;
        }
        //Half heart
        if(incompleteHearts != 0){
            hearts[completeHearts].setHealthPoints(incompleteHearts);
        }else{
            hearts[completeHearts].setHealthPoints(0);
        }
        //Empty Hearts
        for (int i = completeHearts+1; i < hearts.length; i++){
            hearts[i].setHealthPoints(0);
        }
    }
    private void createHearts (){
        Float counter = 0f;
        hearts = new Heart[healthPoints/2];
        for (int i = 0; i < healthPoints/2; i++){
            Vector2D<Float> position = new Vector2D<>(this.position.x + counter, this.position.y);
            hearts[i] = new Heart(position);
            counter += hearts[0].GetScale().x;
        }
    }
    private void setHeartsPosition(){
        Vector2D<Float> actorPosition = new Vector2D<>(actor.GetPosition().x + 25, actor.GetPosition().y+10);
        Float counter = 0f;
        for (int i = 0; i < hearts.length; i++){
            Vector2D<Float> heartPosition = new Vector2D<>(actorPosition.x-14 + counter, actorPosition.y-10);
            hearts[i].SetPosition(heartPosition);
            counter += hearts[0].GetScale().x;
        }
    }
    private String heartsInfoToString (){
        String str = "";
        for (int i = 0; i<hearts.length; i++){
            str = str + hearts[i].getHealthPoints() + ";"; 
        }
        return str;
    }
    private void addToObjectManager(){
        for (int i = hearts.length-1; i >= 0; i--){
            if(hearts[i].getHealthPoints() == 2){
                return;
            }else{
                hearts[i].addToObjectManager();
            }
        }
    }

    public Heart[] getHearts(){
        return hearts;
    }
}

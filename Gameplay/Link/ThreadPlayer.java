package Gameplay.Link;
import java.lang.Thread;

import Engine.Input.InputFunction;

public class ThreadPlayer extends Thread {
    private Player player;
    private int action;
    public boolean execute = false;
    public boolean kill = false;

    public ThreadPlayer (Player player){
        this.player = player;
    }
    
    public void setAction (int i){
        this.action = i;
    }
    @Override
    public void run (){
        while (!kill){
            if(execute){
                if (action < 4){
                    switch(action){
                        case(0):
                            player.setUp(false);
                            player.setDown(false);
                            player.setLeft(false);
                            player.setRight(true);
                            player.setVelocity(1);
                            break;
                        case(1):
                            player.setUp(false);
                            player.setDown(false);
                            player.setLeft(true);
                            player.setRight(false);
                            player.setVelocity(1);
                            break;
                        case(2):
                            player.setUp(false);
                            player.setDown(true);
                            player.setLeft(false);
                            player.setRight(false);
                            player.setVelocity(1);
                            break;
                        case(3):
                            player.setUp(true);
                            player.setDown(false);
                            player.setLeft(false);
                            player.setRight(false);
                            player.setVelocity(1);
                            break;
                    }
                }else if (action >= 5 && action <= 8){
                    player.setVelocity(0);
                    player.setAttack(true);
                }
                execute = false;
            }
        }
    }
}

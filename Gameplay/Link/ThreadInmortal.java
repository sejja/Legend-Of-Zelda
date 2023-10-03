package Gameplay.Link;

import Engine.Math.Vector2D;

public class ThreadInmortal extends Thread {
    Player Link;
    public ThreadInmortal(Player link){
        Link = link;
    }

    public void run (){
        double CoolDowns = 2; // 2Seconds = 2000milisecond
        for (double counter = 0; counter <= CoolDowns; counter += 0.4){
            Link.SetScale(new Vector2D<Float>(100f, 100f));
            try {
                this.sleep(100); //5times
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Link.SetScale(new Vector2D<>(0f, 0f));
            try {
                this.sleep(100); //5times
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Link.SetScale(new Vector2D<Float>(100f, 100f));
        Link.setAble_to_takeDamage(true);
    }
}

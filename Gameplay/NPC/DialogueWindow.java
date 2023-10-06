package Gameplay.NPC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.Font;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Graphics.Objects.FontObject;
import Engine.Graphics.Tile.Block;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Window.PresentBuffer;
import Engine.Window.Window;
import Gameplay.Link.Player;
import Gameplay.States.PlayState;

public class DialogueWindow extends Component implements Renderable{
    
    private DialogueWindow g;
    private int y;
    private int x;
    private int width;
    private int height;
    private static int j = 0; //Index del String del arraylist del dialogo
    //private int yFinal;
    private Npc npc;

    protected DialogueWindow(Npc npc) {
        super(npc);
        //TODO Auto-generated constructor stub
        this.npc = npc;
        this.g = this;
    }
    
    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        x = Block.getWidth()*2;
        y = Block.getHeight()*8;
        width = 1280  - (Block.getWidth()*4);;
        height = Block.getWidth()*3;
        Font mFont = new Font("Content/Fonts/ZeldaFont.png", 16, 16);
        drawSubWindow(x,y,width,height, g);
        x += 20;
        y += 20;
        for(String line: npc.getDialoguesArrayList().get(j).split("\n")){
            mFont.Render(g, line, new Vector2D<Float>((float)x, (float)y), 32, 32, 15, 0);
            y += 40;
        }
    }
    @Override
    public void Init() {
        // TODO Auto-generated method stub
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
    }
    @Override
    public void Update() {
        // TODO Auto-generated method stub
    }
    @Override
    public void ShutDown() {
        System.out.println("oefhoiefh");
        // TODO Auto-generated method stub
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);
    }

    public static void drawSubWindow(int x, int y, int width, int height, Graphics2D g) {
        Color c = new Color(0,0,0, 160);
        g.setColor(c);
        g.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g.setColor(c);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    
    public DialogueWindow getDialgueWindow(){return this;}
    public int getJ(){return j;}
    public void setJ(int index){j = index;}
    public void setSiguiente(){
        j++;
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
    }

}

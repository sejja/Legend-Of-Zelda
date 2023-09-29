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

public class DialogueWindow extends Component implements Renderable{

    private static String dialogue;
    private static DialogueWindow g;
    private int y = 0;
    private int x = 0;
    private static int i = 0;
    private static int j = 0;
    private int yFinal;
    private int code;
    private Npc npc;
    private static boolean siguiente = false;

    protected DialogueWindow(Actor parent, Npc npc) {
        super(parent);
        //TODO Auto-generated constructor stub
        this.g = this;
        this.npc = npc;
    }
    
    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        // TODO Auto-generated method stub
        //Window
        x = Block.getWidth()*2;
        y = Block.getHeight()*8;
        int width = 1280  - (Block.getWidth()*4);;
        int height = Block.getWidth()*3;

        Font mFont = new Font("Content/Fonts/ZeldaFont.png", 16, 16);

        drawSubWindow(x,y,width,height, g);

        //g.setFont(mFont);
        x += 20;
        y += 20;
        yFinal = y;
        if(siguiente) {
            j++;
        } else{   }
        for(String line: Npc.getNpcArrayList().get(0).getDialoguesArrayList().get(j).split("\n")){
            mFont.Render(g, line, new Vector2D<Float>((float)x, (float)y), 32, 32, 15, 0);
            y += 40;
        }




/*
        InputManager.SubscribeReleased(KeyEvent.VK_O, new InputFunction() {
            @Override
            public void Execute() {
                y = yFinal;
                if( i++ > Npc.getDialoguesArrayList().size()){
                    i = Npc.getDialoguesArrayList().size();
                }
                else{i++;}
            }
        });

            for(String line: Npc.getDialoguesArrayList().get(i).split("\n")){
                mFont.Render(g, line, new Vector2D<Float>((float)x, (float)y), 32, 32, 15, 0);
                y += 40;
            }
            //g.drawString("hadoken", x, y);
            */
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
    
    public static DialogueWindow getDialgueWindow(){
        return g;
    }

    public static void setI(int index){
        i = index;
    }
    public int getI(){
        return i;
    }
    public static int getJ(){
        return j;
    }

    public static void setJ(int index){
        j = index;
    }
    public static void setSiguiente(boolean next){
        siguiente = next;
    }
}

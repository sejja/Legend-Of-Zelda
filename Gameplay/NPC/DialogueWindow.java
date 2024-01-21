package Gameplay.NPC;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Engine.Assets.AssetManager;
import Engine.ECSystem.Types.Actor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Engine.ECSystem.Types.Component;
import Engine.Graphics.Font;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Graphics.Tile.Block;
import Engine.Math.Vector2D;

/**
 * DialogueWindow class represents the window that displays dialogue during NPC interactions.
 * It extends Component and implements the Renderable interface.
 */
public class DialogueWindow extends Component implements Renderable {

    // Fields for DialogueWindow properties
    private int x; // the x position of the DialogueWindow
    private int y; // the y position of the DialogueWindow
    private int width; // the width of the DialogueWindow
    private int height; // the height of the DialogueWindow
    private static int j = 0; // Index of the String in the ArrayList of dialogue
    private Npc npc; // the Npc we are interacting with
    private String linea;
    private File f = new File("Gameplay/NPC/Dialogues.txt");
    private int l = 0;
    protected ArrayList<String> dialogue = new ArrayList<>();

    /**
     * Constructor for DialogueWindow.
     * @param npc The NPC associated with the dialogue window.
     */
    protected DialogueWindow(Npc npc) {
        super(npc);
        this.npc = npc;
    }

    /**
     * Renders the DialogueWindow.
     * @param g The Graphics2D object for rendering.
     * @param camerapos The CameraComponent for camera position.
     */
    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        // Set position and size of the DialogueWindow
        x = Block.GetWidth() * 2;
        y = Block.GetHeight() * 8;
        width = 1280 - (Block.GetWidth() * 4);
        height = Block.GetWidth() * 3;

        // Set font and draw subwindow
        Font mFont = new Font(AssetManager.Instance().GetResource("Content/Fonts/ZeldaFont.png"), new Vector2D<>(16, 16));
        drawSubWindow(x, y, width, height, g);
        x += 20;
        y += 20;

        // Render each line of dialogue
        for (String line : dialogue.get(j).split("\n")) {
            mFont.Render(g, line, new Vector2D<Float>((float) x, (float) y), new Vector2D<>(32, 32), new Vector2D<>(15, 0));
            y += 40;
        }
    }

    /**
     * Initializes the DialogueWindow.
     */
    @Override
    public void Init() {
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
        readText();
        textModification();
    }

    /**
     * Updates the DialogueWindow.
     */
    @Override
    public void Update() {
        // Empty method, no update needed
    }

    /**
     * Shuts down the DialogueWindow.
     */
    @Override
    public void ShutDown() {
        GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(this);
    }

    /**
     * Draws the subwindow for the dialogue.
     * @param x The x position of the subwindow.
     * @param y The y position of the subwindow.
     * @param width The width of the subwindow.
     * @param height The height of the subwindow.
     * @param g The Graphics2D object for rendering.
     */
    public static void drawSubWindow(int x, int y, int width, int height, Graphics2D g) {
        // Set background color and draw rounded rectangle
        Color c = new Color(0, 0, 0, 160); // Set background color to black with some opacity
        g.setColor(c);
        g.fillRoundRect(x, y, width, height, 35, 35);

        // Set outside line color to white and draw rounded rectangle
        c = new Color(255, 255, 255);
        g.setColor(c);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
        g.setStroke(new BasicStroke(1));
    }

    /**
     * Gets the index of the dialogue line.
     * @return The index of the dialogue line.
     */
    public static int getJ() {
        return j;
    }

    /**
     * Sets the index of the dialogue line.
     * @param index The new index of the dialogue line.
     */
    public void setJ(int index) {
        j = index;
    }

    /**
     * Moves to the next dialogue line.
     */
    public void setSiguiente() {
        j++;
    }

    /**
     * Gets the ArrayList containing the dialogue.
     * @return The ArrayList containing the dialogue.
     */
    public ArrayList<String> getDialogue() {
        return dialogue;
    }

    /**
     * Sets the associated NPC for the dialogue.
     * @param npc The new associated NPC.
     */
    public void setNpc(Npc npc) {
        this.npc = npc;
    }

    /**
     * Modifies the text for rendering.
     */
    public void textModification() {
        // TODO: Implement text modification if needed
    }

    /**
     * Reads the dialogue text from the file and processes it.
     */
    public void readText() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            linea = br.readLine();
            while (linea != null) {
                if (linea.startsWith(npc.getName())) {
                    dialogue.clear();
                    linea = br.readLine();
                    while (linea.length() != 0) {
                        // Process dialogue text (replace characters, format lines, etc.)
                        linea = linea.replaceAll(",", "]");
                        linea = linea.replaceAll("!", "_");
                        String modifiedText = "";
                        l = 0;
                        int space = 0;
                        for (int i = 0; i < linea.length(); i++) {
                            if (linea.charAt(i) == ' ') {
                                space = i;
                            }
                            if ((i - 60) % 60 == 0 && i != 0) {
                                if (linea.charAt(i - 60) == ' ') {
                                    l = l + 1;
                                }
                                if (linea.charAt(i - 1) != ' ' && linea.charAt(i) != ' ') {
                                    modifiedText = modifiedText + linea.substring(l, space) + "\n";
                                    l = space + 1;
                                } else {
                                    modifiedText = modifiedText + linea.substring(l, i) + "\n";
                                    l = i;
                                }
                            }
                        }
                        if (linea.charAt(linea.length() - (linea.length() - l)) == ' ') {
                            modifiedText = modifiedText + linea.substring(l + 1, linea.length());
                        } else {
                            modifiedText = modifiedText + linea.substring(l, linea.length());
                        }
                        dialogue.add(modifiedText);
                        linea = br.readLine();
                    }
                }
                linea = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
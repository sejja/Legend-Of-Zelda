package Gameplay.NPC;

import java.awt.event.KeyEvent;

import Engine.Assets.AssetManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Components.FontComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;

public class SelectionArrow extends Actor{

    private boolean iscontinue = true;
    private Vector2D<Float> pos0, pos1;

    public SelectionArrow(Vector2D<Float> position, Vector2D<Float> pos1) {
        super(position);
        FontComponent f = new FontComponent(this, AssetManager.Instance().GetResource("Content/Fonts/ZeldaFont.png"));
        f.SetString("^");
        AddComponent(f);
        this.pos0 = position;
        this.pos1 = pos1;

        InputManager.SubscribePressed(KeyEvent.VK_W, new InputFunction() {

            @Override
            public void Execute() {
                SetIsContinue(true);
            }
        });

        InputManager.SubscribePressed(KeyEvent.VK_S, new InputFunction() {

            @Override
            public void Execute() {
                SetIsContinue(false);
            }
        });
    }

    @Override
    public void Update() {
        if(iscontinue)
            SetPosition(pos0);
        else
            SetPosition(pos1);

    }

    public void SetIsContinue(boolean b) {
        iscontinue = b;
    }
}

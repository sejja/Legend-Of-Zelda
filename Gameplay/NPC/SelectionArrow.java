package Gameplay.NPC;

import java.awt.event.KeyEvent;

import Engine.Assets.AssetManager;
import Engine.Audio.Audio;
import Engine.Audio.Sound;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Components.FontComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.StateMachine;
import Engine.Window.GameLoop;

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

        InputManager.SubscribePressed(KeyEvent.VK_ENTER, new InputFunction() {
            @Override
            public void Execute() {
                Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/finish.wav"));
                Audio.Instance().Play(sound);
                sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/crystal.wav"));
                Audio.Instance().Stop(sound);

                if(iscontinue) {
                    GameLoop.Restart();
                } else {
                    GameLoop.Quit();
                }
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
        Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/message.wav"));
        Audio.Instance().Play(sound);
    }
}

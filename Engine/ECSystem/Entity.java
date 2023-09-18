//
//	Entity.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import Engine.Graphics.Animation;
import Engine.Graphics.Sprite;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public abstract class Entity {
    protected Sprite mSprite;
    protected Vector2D mPosition;
    protected int mSize;
    protected Animation mAnimation;
    protected int mCurrentAnimation;
    protected AABB mBounds;

    private final int UP = 0;
    private final int DOWN = 1;
    private final int RIGHT = 2;
    private final int LEFT = 3;
    protected boolean up = false;
    protected boolean down = false;
    protected boolean right = true;
    protected boolean left = false;

    public Entity(Sprite sprite, Vector2D position, int size) {
        mSprite = sprite;
        mPosition = position;
        mSize = size;
        mAnimation = new Animation();
        mBounds = new AABB(mPosition, size, size);
        SetAnimation(RIGHT, sprite.GetSpriteArray(RIGHT), 10);
    }

    public void SetAnimation(int i, BufferedImage[] frames, int delay) {
        mCurrentAnimation = i;
        mAnimation.SetFrames(frames);
        mAnimation.SetDelay(delay);
    }

    public void Update() {
        Animate();
        mAnimation.update();
    }

    public abstract void Render(Graphics2D g);
    public void Input(KeyHandler key, MouseHandler mouse) {

    }

    public Animation GetAnimation() {
        return mAnimation;
    }

    public void SetSprite(Sprite sp) {
        mSprite = sp;
    }

    public void SetSize(int i) {
        mSize = i;
    }

    public AABB GetBounds() {
        return mBounds;
    }

    public void Animate() {
        if(up) {
            if(mCurrentAnimation != UP || mAnimation.GetDelay() == -1) {
                SetAnimation(UP, mSprite.GetSpriteArray(UP), 5);
            }
        } else if(down) {
            if(mCurrentAnimation != DOWN || mAnimation.GetDelay() == -1) {
                SetAnimation(DOWN, mSprite.GetSpriteArray(DOWN), 5);
            }
        } else if(right) {
            if(mCurrentAnimation != RIGHT || mAnimation.GetDelay() == -1) {
                SetAnimation(RIGHT, mSprite.GetSpriteArray(RIGHT), 5);
            }
        } else {
            if(mCurrentAnimation != DOWN || mAnimation.GetDelay() == -1) {
                SetAnimation(DOWN, mSprite.GetSpriteArray(DOWN), 5);
            }
        }
    }
}

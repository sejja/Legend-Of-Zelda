package Engine.Graphics.Tile;

import java.awt.Graphics2D;

import Engine.ECSystem.Level;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;

public class ShadowLayer {
    float[][] mGradient; //Array de los gradients (width x height)

    public void illuminate(Vector2D<Float> at, int r, float intensity) {

    }

    public static void Render(Graphics2D g, CameraComponent mCamera) {
        final int scaleX = 16;
        final int scaleY = 16;
        var cameracoord = mCamera.GetCoordinates();
        Vector2D<Float> tilemapPos = Level.mCurrentLevel.GetBounds().GetPosition();
        int x = (int) ((cameracoord.x - tilemapPos.x) / scaleX);
        int y = (int) ((cameracoord.y - tilemapPos.y) / scaleY);
        /*
        for(int i = x; i < x + (mCamera.GetDimensions().x) + 1; i++) {
            for(int j = y; j < y + (mCamera.GetDimensions().y) + 2; j++) {
                g.drawRect(i, j, scaleX, scaleY);
            }
        }
        */
    }
}
package Engine.Math;

import Gameplay.Link.DIRECTION;

public class EuclideanCoordinates extends Vector2D<Float> {
    public EuclideanCoordinates(float x, float y) {
        super(x, y);
    }

    public EuclideanCoordinates(Vector2D<Float> vector2d) {
        super(vector2d.x, vector2d.y);
    }

    // ------------------------------------------------------------------------
    /*! Get Modulus
    *
    *   Retuns the Modulus operator for two sets of vectors
    */ //----------------------------------------------------------------------
    public Float GetModulusDistance(final Vector2D<Float> a){
        Float xf = Math.abs(this.x - a.x);
        Float yf = Math.abs(this.y - a.y);
        return (Float)(float) Math.sqrt(Math.pow(xf,2) + Math.pow(yf, 2));
    }
    public Float getModule (){
        return (Float)(float) Math.sqrt(Math.pow((Float)x,2) + Math.pow((Float)y, 2));
    }

    public Vector2D<Float> getVectorToAnotherActor(Vector2D<Float> enemyPosition){
        Float xf = -((Float)this.x - enemyPosition.x);
        Float yf = -((Float)this.y - enemyPosition.y);
        return new Vector2D<>(xf, yf);
    }

    public DIRECTION getTargetDirection(Vector2D<Float> targetVector) { 
        Vector2D<Float> vector = getVectorToAnotherActor(targetVector);
        if (Math.abs(vector.x) > Math.abs(vector.y)) {
            if (vector.x > 0) {return DIRECTION.RIGHT;} 
            else {return DIRECTION.LEFT;}
        } else {
            if (vector.y > 0) {return DIRECTION.DOWN;} 
            else {return DIRECTION.UP;}
        }
    }

    public Vector2D<Float> getNormalVector (){
        return new Vector2D<Float>((Float)this.x/getModule(), (Float)this.y/getModule());
    }

    public Vector2D<Float> sumVector (Vector2D<Float> sum){
        return new Vector2D<Float>((Float)this.x + sum.x, (Float)this.y + sum.x);
    }
    public Vector2D<Float> diffVector (Vector2D<Float> sum){
        return new Vector2D<Float>((Float)this.x - sum.x, (Float)this.y - sum.x);
    }
}

//
//	Vector2D.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Math;

import Gameplay.Link.DIRECTION;

public class Vector2D<T> {
    public T x;
    public T y;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Initializes the values to 0
    */ //----------------------------------------------------------------------
    public Vector2D() {
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Initializes the values to the given values
    */ //----------------------------------------------------------------------
    public Vector2D(T x, T y) {
        this.x = x;
        this.y = y;
    }

    // ------------------------------------------------------------------------
    /*! Copy Constructor
    *
    *   Copies another vector
    */ //----------------------------------------------------------------------
    public Vector2D(Vector2D<T> rhs) {
         new Vector2D<T>(rhs.x, rhs.y);
    } 

    // ------------------------------------------------------------------------
    /*! To String
    *
    *   Converts the vector to a string to ease debugging
    */ //----------------------------------------------------------------------
    @Override
    public String toString() {
        return x + ", " + y;
    }

    public Float getModuleDistance(Vector2D<Float> a){
        Float xf = Math.abs((Float)this.x - a.x);
        Float yf = Math.abs((Float)this.y - a.y);
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
        Vector2D<Float> vector = this.getVectorToAnotherActor(targetVector);
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

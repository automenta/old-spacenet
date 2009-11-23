/*
 * Copyright (c) 2003-2009 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package automenta.spacenet.space.jme.geom.scalar;

import automenta.spacenet.space.geom3.MetaballBox;
import automenta.spacenet.space.geom3.Sphere;
import automenta.spacenet.space.jme.video.Jme;
import automenta.spacenet.space.surface.ColorSurface;
import automenta.spacenet.var.list.ListVar;
import automenta.spacenet.var.vector.Vector3;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.TriMesh;



public class MetaballMesh extends TriMesh {

    private MetaBallScalarField field;
    private final ScalarFieldPolygonisator polygonisator;
	private ListVar<Sphere> balls;

    public MetaballMesh(MetaballBox box) {
    	super();
    	
    	this.balls = box.getPoints();

    	Vector3 fieldBoxSize = new Vector3(box.getSize()).multiply(2.0);
    	float maxWeight = 0.5f;
    	float maxSpeed = 0.1f; 
    	float cellSize = (float) box.getCellSize().d();

        field = new MetaBallScalarField( fieldBoxSize, maxWeight, maxSpeed);
        polygonisator = new ScalarFieldPolygonisator( fieldBoxSize.multiply( 2 ), cellSize, field );
        
    }

    
    @Override public void updateGeometricState( final float tpf, final boolean initiator ) {
   		polygonisator.calculate( this, 1f );
   		
        super.updateGeometricState( 0, true );
    }


    // This does not have to be an internal class ;-)
    private class MetaBallScalarField implements ScalarField {

        private final Vector3 boxSize = new Vector3();
        private final Vector3f calcVector = new Vector3f();

        private MetaBallScalarField( final Vector3 fieldBoxSize,
                final float maxWeight, final float maxSpeed) {

            boxSize.set( fieldBoxSize );
        }

        Vector3 v = new Vector3();
        
        public float calculate( final Vector3f point ) {
        	v.set(point.getX(), point.getY(), point.getZ());
        	
            double sum = 0;
            for( Sphere ball : balls ){
                double part = ball.getRadius().d() / ( ball.getPosition().getDistanceSqr( v ) + 0.001f );
                sum += part;
            }
            return (float)sum;
        }

        // VERY IMPORTANT! Do NOT create new Vector3f, method may be called 1000/sec
        public void normal( final Vector3f point, final Vector3f result ) {
            result.zero();
            for( Sphere ball : balls ){
            	float px = (float) ball.getPosition().x();
            	float py = (float) ball.getPosition().y();
            	float pz = (float) ball.getPosition().z();
            	
                calcVector.set( point ).subtractLocal( px, py, pz );
                float lengthSquared = calcVector.lengthSquared() + 0.001f;
                result.add( calcVector.multLocal( 1.0f / (lengthSquared * lengthSquared )) );
            }
            result.normalize();
        }

        public void textureCoords( final Vector3f normal, final Vector2f result ) {
            result.zero();
            // little trick: we know that normals have been computed before
            result.x = /*point.x/20 +*/ normal.x;
            result.y = /*point.x/20 +*/ normal.y;
        }

        final ColorRGBA white = new ColorRGBA(1f,1f,1f,1f);
        ColorRGBA t = new ColorRGBA();
        
        // VERY IMPORTANT! Do NOT create new ColorRGBA, method may be called 1000/sec
        public void color( final Vector3f point, final ColorRGBA color ) {
            color.set( 0f, 0f, 0f, 0f );
            for( Sphere ball : balls ){
            	v.set(point.getX(), point.getY(), point.getZ());
                double part = ball.getRadius().d() / ( ball.getPosition().getDistanceSqr( v ) + 0.001f ) * 2;
                if( part > 1 ){
                    part = 1;
                }
                
                if (ball.getSurface().get() instanceof ColorSurface) {
                	ColorSurface cs = (ColorSurface)ball.getSurface().get();
                	Jme.toJMEColor(cs.getColor(), t);
	                color.interpolate( t, (float)part );
                }
                else {
	                color.interpolate( white, (float)part );
                }
            }
        }

//        public void updateBallLocations() {
//            for( Ball ball : balls ){
//                ball.getPosition().addLocal( ball.getSpeed() );
//                if( ball.getPosition().x < -boxSize.x || ball.getPosition().x > boxSize.x ){
//                    ball.getSpeed().x = -ball.getSpeed().x;
//                }
//                if( ball.getPosition().y < -boxSize.y || ball.getPosition().y > boxSize.y ){
//                    ball.getSpeed().y = -ball.getSpeed().y;
//                }
//                if( ball.getPosition().z < -boxSize.z || ball.getPosition().z > boxSize.z ){
//                    ball.getSpeed().z = -ball.getSpeed().z;
//                }
//            }
//        }
//


//        private class Ball {
//
//            private final Vector3f position;
//            private final Vector3f speed;
//            private final ColorRGBA color;
//            private float weight;
//
//            public Ball( Vector3f position, float weight, Vector3f speed, ColorRGBA color ) {
//                this.position = position;
//                this.weight = weight;
//                this.speed = speed;
//                this.color = color;
//            }
//
//            public Vector3f getPosition() {
//                return position;
//            }
//
//            public void setPosition( Vector3f position ) {
//                this.position.set( position );
//            }
//
//            public float getWeight() {
//                return weight;
//            }
//
//            public void setWeight( float weight ) {
//                this.weight = weight;
//            }
//
//            public Vector3f getSpeed() {
//                return speed;
//            }
//
//            public void setSpeed( Vector3f speed ) {
//                this.speed.set( speed );
//            }
//
//            public ColorRGBA getColor() {
//                return color;
//            }
//        }
    }
}
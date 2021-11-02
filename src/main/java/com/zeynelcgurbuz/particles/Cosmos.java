package com.zeynelcgurbuz.particles;

import com.zeynelcgurbuz.particles.animation.Animatable;
import com.zeynelcgurbuz.particles.animation.Animator;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.redux.Subscriber;
import com.zeynelcgurbuz.particles.redux.Subscription;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Cosmos implements Animatable, Subscriber<ParticlesState> {

    private static final int MAX_ATOMS = 1000;
    private static final int MIN_RADIUS = 10;
    private static final int VSCALE = 3;
    private static final int G_TIME_SCALE = 6;
    private static final double G = 6.67398 * 0.00000000001;

    private static final Random random = new Random();
    private int startX;
    private int startY;
    private int width;
    private int height;
    private Vector initialPositionVector = new Vector();
    private Vector attractionVector = new Vector();
    private Vector positionVector1 = new Vector(); // position
    private Vector positionVector2 = new Vector();
    private Vector velocityVector1 = new Vector(); // velocity
    private Vector velocityVector2 = new Vector();
    private Vector normalVector = new Vector(); // normal vector
    private Vector unitNormalVector = new Vector(); // unit normal
    private Vector unitTangentVector = new Vector(); // unit tangent

    private double attractionMean;
    private double attractionStd;
    private double attractionMax;
    private double attractionMin;
    private double minRLower;
    private double minRUpper;
    private double maxRLower;
    private double maxRUpper;
    private double friction;
    boolean flat_force;

    private final ArrayList<Particle> particles;
    private final ArrayList<Color> colors;

    private GraphicsContext graphics;

    private int particleCount;
    private int colorCount;
    private ParticlesInfo info;


    private Store<ParticlesState> store;
    private ParticlesState state;
    private boolean updated;
    private Subscription storeSubscription;
    private double g;

    public Cosmos(Store<ParticlesState> store, int particleCount, int colorCount, int width, int height) {
        this.store = store;
        storeSubscription = store.subscribe(this);
        //this.particleCount = particleCount;
        //this.colorCount = colorCount;
        particles = new ArrayList<>();
        colors = new ArrayList<>();
        //**************************
        this.particleCount = 100;
        this.colorCount = 5;

        attractionMean = 0.0;
        attractionStd = 1.2;
        minRLower = 10;
        minRUpper = 15;
        attractionMin = -5.0;
        attractionMax = 5.5;
        maxRLower = 20.0;
        maxRUpper = 80.0;
        friction = 0.01;
        g = 10000.0;
        flat_force = true;
        //**************************
        setBoundaries(0, 0, width, height);
        generateColors();
        info = new ParticlesInfo();
        info.setSizes(this.colorCount);
        setRandomTypes();
        //**************************
        setRandomParticles();
    }

    @Override
    public void update(double timePassedFromLastFrame) {
        //applyAttractionMolecular();
       /* for (int thisIdx = 0; thisIdx < particles.size(); thisIdx++) {


            Particle particle1 = particles.get(thisIdx);

            //applyAttraction(thisIdx, timePassedFromLastFrame);
            //applyAttractionMolecular(thisIdx);
         *//*   positionVector1 = particle1.getPosition(positionVector1);
            velocityVector1 = particle1.getVelocity(velocityVector1);
            positionVector1.add(velocityVector1.scale(VSCALE));


            particle1.setPosition(positionVector1);*//*
            //applyFriction(thisIdx);
            //applyAttraction(thisIdx, timePassedFromLastFrame / 1000000000.0);

*//*            for (int i = 0; i < particles.size(); i++) {
                //Particle particle2 = particles.get(i);
                Particle particle1 = particles.get(i);
                //collideParticles(particle1, particle2);

            }*//*
            //collideWalls(particle1);
        }*/
        for (int thisIdx = 0; thisIdx < particles.size(); thisIdx++) {
            Particle particle1 = particles.get(thisIdx);

            applyAttractionMolecular(thisIdx);
            //applyAttraction(thisIdx, timePassedFromLastFrame);
            collideWalls(particle1);
            //position update
            for(int otherIdx = 0; otherIdx < particles.size(); otherIdx++){
                if(thisIdx != otherIdx){
                    Particle particle2 = particles.get(otherIdx);
                    collideParticles(particle1, particle2);
                }
            }
            particle1.setPosition(particle1.getPosition().add(particle1.getVelocity()));

            //particle1.setPosition(particle1.getPosition().add(particle1.getVelocity()));
            //friction
            particle1.setVelocity(particle1.getVelocity().scale(1.0 - friction));

        }

        drawCosmos();
    }

    private void applyAttractionMolecular(int indexOfParticle) {
        //for (int indexOfParticle = 0; indexOfParticle < particles.size(); indexOfParticle++) {
       /* Particle particle1 = particles.get(indexOfParticle);
        for (int idx = 0; idx < particles.size(); idx++) {
            Particle particle2 = particles.get(idx);
            Vector delta = Vector.subtract(particle2.getPosition(), particle1.getPosition());
            double r = delta.norm();

            double minR = info.getMinDistance(particle1.getType(), particle2.getType());
            double maxR = info.getMaxDistance(particle1.getType(), particle2.getType());
            if (r > maxR || r < 0.1) {
                continue;
            }
            delta.divide(r);


            double force = 0.0;
            if (r > minR) {
                if (flat_force) {
                    force = info.getAttraction(particle1.getType(), particle2.getType());
                } else {
*//*                    double att1 = info.getAttraction(particle1.getType(), particle2.getType());
                    double att2 = info.getAttraction(particle2.getType(), particle1.getType());
                    force = ((att1 * att2) / (r * r));*//*

                    double top = 2.0 * Math.abs(r - 0.5 * (maxR + minR));
                    double bottom = maxR - minR;
                    force = info.getAttraction(particle1.getType(), particle2.getType()) * (1.0 - (top / bottom));
                }
            } else {
                double factor = 3.0;
                force = factor * minR * ((1.0 / (minR + factor)) - (1.0 / (r + factor)));

                //force = minR * ((1.0 / (minR)) - (1.0 / (r)));
                //double diff = minR - r;
                //force = (diff * diff)/(r+2);
            }


            delta.scale(force);
            particle1.setVelocity(particle1.getVelocity().add(delta));


*//*           particle1.setPosition(particle1.getPosition().add(particle1.getVelocity()));
            //friction
            particle1.setVelocity(particle1.getVelocity().scale(1.0 - friction));*//*
        }*/
        Particle particle1 = particles.get(indexOfParticle);
        for (int idx = 0; idx < particles.size(); idx++) {
            Particle particle2 = particles.get(idx);
            double particle1MaxAttractionRadius = info.getMaxDistance(particle1.getType(), particle2.getType());
            double particle1MinAttractionRadius = info.getMinDistance(particle1.getType(), particle2.getType());
            double particle2MaxAttractionRadius = info.getMaxDistance(particle2.getType(), particle1.getType());
            double particle2MinAttractionRadius = info.getMinDistance(particle2.getType(), particle1.getType());
            Vector delta = Vector.subtract(particle2.getPosition(), particle1.getPosition());
            double distance = delta.norm();
            delta.unitVector();


            double force = info.getAttraction(particle1.getType(), particle2.getType());
            double force2 = info.getAttraction(particle2.getType(), particle1.getType());
            if (indexOfParticle != idx
                    && distance <= particle1MaxAttractionRadius
                    && distance <= particle2MaxAttractionRadius
                    && distance >= particle1MinAttractionRadius
                    && distance >= particle2MinAttractionRadius) {
                //Vector vector = new  Vector(1,1);
                //vector.scale(force * force).divide(distance * distance);
                delta.scale((force * force2 / distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(delta));

            } else if(indexOfParticle != idx && distance < particle1MinAttractionRadius
                    && distance < particle2MinAttractionRadius) {
              /* force = info.getAttraction(particle1.getType(), particle2.getType());
                  force2 = info.getAttraction(particle2.getType(), particle1.getType());*/
                delta.scale((-(force * force2) / distance * distance)*0.1);
                particle1.setVelocity(particle1.getVelocity().add(delta));
            }


            collideParticles(particle1,particle2);
        }
    }




    // Check for collision between atom1 & atom2
    //https://www.vobarian.com/collisions/2dcollisions2.pdf
    private boolean collideParticles(Particle particle1, Particle particle2) {
        double radius = particle1.getRadius() + particle2.getRadius();
        //Step 1: n=〈 x2−x1, y2−y1〉
        Vector normalVector =  Vector.subtract(particle1.getPosition(), particle2.getPosition());
        //if true, we have a collision.
        if (normalVector.norm() < radius) {
            // Move the particles to collision point, because they intersect at this point.
            double rDifference = (radius - normalVector.norm()) / 2;
            Vector unitNormalVector =  new Vector(normalVector).unitVector();
            particle1.setPosition(particle1.getPosition().add(unitNormalVector.scale(rDifference)));
            particle2.setPosition(particle2.getPosition().add(unitNormalVector.scale(-rDifference)));

            //Step 1:  find the unit vector of n, which we will call, also normal vector get recalculated,
            //since we moved the particles to the collision point
            normalVector =  Vector.subtract(particle1.getPosition(), particle2.getPosition());
            unitNormalVector =  new Vector(normalVector).unitVector();
            //Next we need the unit tangent vector. Just make the x component of the unit tangent vector equal
            // to the negative of the y component of the unit normal vector, and make the y component of
            // the unit tangent vector equal to the x component of the unit normal vector: ut = 〈−un y, un x〉
            Vector unitTangentVector = new Vector(-unitNormalVector.y, unitNormalVector.x);
            //Step:2 is skipped since we have velocity vectors.
            //Step 3: we need to resolve the velocity vectors, v1 and v2, into normal and tangential components
            //To do this, project the velocity vectors onto the unit normal and  unit tangent vectors by computing the dot product
            //v1n= un⋅• v1, v1t= ut⋅• v1, v2n= un⋅• v2,  v2t= ut • v2
            Vector velocityVector1 = particle1.getVelocity();
            Vector velocityVector2 = particle2.getVelocity();
            double v1n = unitNormalVector.dot(velocityVector1);
            double v1t = unitTangentVector.dot(velocityVector1);
            double v2n = unitNormalVector.dot(velocityVector2);
            double v2t = unitTangentVector.dot(velocityVector2);
            //Step 4:  Find the new tangential velocities (after the collision). The new tangential
            //velocities are simply equal to the old ones, v1t = v1tFinal, v2t = v2tFinal

            // Step 5, Find the final normal velocities. This is where we use the one-dimensional collision formulas
            double m1 = particle1.getMass();
            double m2 = particle2.getMass();
            double v1nFinal = (v1n * (m1 - m2) + 2.0 * m2 * v2n) / (m1 + m2);
            double v2nFinal = (v2n * (m2 - m1) + 2.0 * m1 * v1n) / (m1 + m2);

            // Step 6: Convert the scalar normal and tangential velocities into vectors
            Vector v1nVector = new Vector(unitNormalVector).scale(v1nFinal);
            Vector v1tVector = new Vector(unitTangentVector).scale(v1t);
            particle1.setVelocity(v1nVector.add(v1tVector));

            Vector v2nVector = new Vector(unitNormalVector).scale(v2nFinal);
            Vector v2tVector = new Vector(unitTangentVector).scale(v2t);
            particle2.setVelocity(v2nVector.add(v2tVector));
            return true;
        }
        return false;
    }

    private void applyAttraction(int indexOfParticle, double timePassedFromLastUpdate) {
        //attractionVector.reset();
        Particle particle1 = particles.get(indexOfParticle);
        Particle particle2;
        Vector attractionForceVector = new Vector();
        for (int idx = 0; idx < indexOfParticle; idx++) {
            particle2 = particles.get(idx);
            attractionForceVector.add(calculateAttractionVector(particle1, particle2));
        }
        for (int idx = indexOfParticle + 1; idx < particles.size(); idx++) {
            particle2 = particles.get(idx);
            attractionForceVector.add(calculateAttractionVector(particle1, particle2));
        }
        //Vector attractionForceVector = attractionVector.scale(1 / particle1.getMass());
        attractionForceVector.scale(1 / particle1.getMass()); // convert force vector to acc. vector
        //v_f = v_i + (a * t), and acceleration is opposing, so it is negative.

        particle1.setVelocity(particle1.getVelocity()
                .add(attractionForceVector));

        //some friction
        //particle1.setVelocity(particle1.getVelocity().scale(0.98));

    }

    private Vector calculateAttractionVector(Particle particle1, Particle particle2) {
        Vector distance = particle2.getPosition().subtract(particle1.getPosition());
        double r = distance.norm();
        //double force = (6.7 * g * particle1.getMass() * particle2.getMass()) / (r * r);
        double force = (6.7 * g * 1) / (r * r);
        //Vector gravitationalForceVector = distance.unitVector().scale(force);
        //if(particle1.getType() == ParticleType.ATTRACTION) {
        return distance.unitVector().scale(force);
        //} else {
        //    attractionVector.subtract(gravitationalForceVector);
        //}

    }

    private void applyFriction(int indexOfParticle) {

        /*Particle particle = particles.get(indexOfParticle);

        particle.setVelocity(particle.getVelocity().scale(0.98));*/

    }


    // Check for collision with wall
    private void collideWalls(Particle particle) {
        double radius = particle.getRadius();
        Vector positionVector1 = particle.getPosition();
        Vector velocityVector1 = particle.getVelocity();
        if (positionVector1.x < startX + radius) {
            positionVector1.x = startX + radius;
            velocityVector1.x = -velocityVector1.x;
        }
        if (positionVector1.y < startY + radius) {
            positionVector1.y = startY + radius;
            velocityVector1.y = -velocityVector1.y;
        }
        if (positionVector1.x > width - radius) {
            positionVector1.x = width - radius;
            velocityVector1.x = -velocityVector1.x;
        }
        if (positionVector1.y > height - radius) {
            positionVector1.y = height - radius;
            velocityVector1.y = -velocityVector1.y;
        }
        particle.setPosition(positionVector1);
        particle.setVelocity(velocityVector1);
    }

    private void setRandomTypes() {

        for (int i = 0; i < info.size(); i++) {
            info.setColor(i, colors.get(i));

            for (int j = 0; j < info.size(); j++) {
/*                if (i == j) {
                    info.setAttraction(i, j, -Math.abs(random.nextGaussian() * attractionStd + attractionMean));
                    info.setMinDistance(i, j, MIN_RADIUS);
                } else {
                    info.setAttraction(i, j, (random.nextGaussian() * attractionStd + attractionMean));
                    info.setMinDistance(i, j, Math.max(minRLower
                            + (minRUpper - minRLower) * random.nextDouble(), MIN_RADIUS));
                }*/
                //info.setMaxDistance(i, j, Math.max(maxRLower + (maxRUpper - maxRLower) * random.nextDouble(),
                //        info.getMinDistance(i, j)));
                //info.setAttraction(i, j, (random.nextGaussian() * attractionStd + attractionMean));
                info.setAttraction(i, j, (attractionMin + (attractionMax - attractionMin) * random.nextDouble()));
                info.setMaxDistance(i, j, (maxRLower + (maxRUpper - maxRLower) * random.nextDouble()));
                info.setMinDistance(i, j, (minRLower + (minRUpper - minRLower) * random.nextDouble()));

                //info.setMaxDistance(j, i, info.getMaxDistance(i, j));
                //info.setMinDistance(j, i, info.getMinDistance(i, j));

            }
        }
    }

    private void generateColors() {
        for (int idx = 0; idx < colorCount; idx++) {
            colors.add(ColorManager.next());
        }
    }

    public void setBoundaries(int startX, int startY, int width, int height) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    public void setRandomParticles() {
        if (particles.size() >= MAX_ATOMS) return;
        for (int i = 0; i < particleCount; i++) {
            Particle particle = new Particle();
            particle.setPosition(
                    random.nextDouble() * (width - startX) + startX,
                    random.nextDouble() * (height - startY) + startY);
            //int id = i % colors.size();
            //int type = i % colors.size();
            int type = random.nextInt(info.size());
            //double attractionConstant = 0.5 + id * 0.1;
/*            if (i % 2 != 0) {
                particle.setType(ParticleType.ATTRACTION);
                //attractionConstant *= -1;
            } else {
                particle.setType(ParticleType.REPULSION);
            }*/
            //particle.setAttractionConstant(attractionConstant);
            //particle.setColor(colors.get(id));
            particle.setColor(info.getColor(type));
            //particle.setRadius(id + MIN_RADIUS);
            particle.setRadius(MIN_RADIUS);
            double vx = random.nextDouble() * 5;
            if (random.nextBoolean()) vx = -vx;
            double vy = random.nextDouble() * 5;
            if (random.nextBoolean()) vy = -vy;
            particle.setVelocity(vx, vy);
            //particle.setVelocity(0, 0);
            particles.add(particle);
        }


        //add a big particle
        /*Particle particle = new Particle();
        particle.setPosition(600,400);
        particle.setType(ParticleType.ATTRACTION);
        particle.setRadius(50);
        particle.setColor(Color.YELLOW);
        particle.setVelocity(0, 0);
        particles.add(particle);*/
    }

    public void setAnimator(Animator animator) {
        animator.attach(this);
    }

    public void setGraphics(GraphicsContext graphics) {
        this.graphics = graphics;
    }

    private void drawCosmos() {

        Vector initialPosition = store.getState().getMouseDragPosition();

        graphics.clearRect(0, 0,
                graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());
        for (Particle particle : particles) {
            graphics.setFill(particle.getColor());
            double radius = particle.getRadius();
            double diameter = radius + radius;

            graphics.fillOval(particle.getPosition().x - radius + initialPosition.x,
                    particle.getPosition().y - radius + initialPosition.y,
                    diameter, diameter);

        }
    }

    private void updatePositions() {
        if (particles != null && particles.size() > 0) {
            for (Particle particle : particles) {
                particle.setPosition(particle.getPosition().add(initialPositionVector));
            }
        }
    }

    @Override
    public void onChange(ParticlesState state) {
        //this.state = state;
/*        if(!Arrays.equals(state.getMouseDragCoordinates(), new double[2])){
            this.initialPositionVector.x = state.getMouseDragCoordinates()[0];
            this.initialPositionVector.y = state.getMouseDragCoordinates()[1];
            updatePositions();
            store.dispatch(new MouseDragStopAction());
        }*/
    }
}

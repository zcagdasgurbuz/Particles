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
    private static final int MIN_RADIUS = 3;
    private static final int VSCALE = 3;
    private static final int G_TIME_SCALE = 3;
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
    private double minRLower;
    private double minRUpper;
    private double maxRLower;
    private double maxRUpper;
    private double friction;

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

    public Cosmos(Store<ParticlesState> store, int particleCount, int colorCount, int width, int height) {
        this.store = store;
        storeSubscription = store.subscribe(this);
        this.particleCount = particleCount;
        this.colorCount = colorCount;
        particles = new ArrayList<>();
        colors = new ArrayList<>();
        //**************************
        attractionMean = -0.02;
        attractionStd = 0.06;
        minRLower = 0.0;
        minRUpper = 20.0;
        maxRLower = 20.0;
        maxRUpper = 70.0;
        friction = 0.01;
        //**************************
        setBoundaries(0, 0, width, height);
        generateColors();
        info = new ParticlesInfo();
        info.setSizes(colorCount);
        setRandomTypes();
        //**************************
        setRandomParticles();


    }

    @Override
    public void update(double timePassedFromLastFrame) {
        for (int thisIdx = 0; thisIdx < particles.size(); thisIdx++) {


            Particle particle1 = particles.get(thisIdx);
            applyAttractionMolecular(thisIdx);

         /*   positionVector1 = particle1.getPosition(positionVector1);
            velocityVector1 = particle1.getVelocity(velocityVector1);
            positionVector1.add(velocityVector1.scale(VSCALE));


            particle1.setPosition(positionVector1);*/
            //applyFriction(thisIdx);
            //applyAttraction(thisIdx, timePassedFromLastFrame / 1000000000.0);

/*            for (int i = thisIdx + 1; i < particles.size(); i++) {
                //Particle particle2 = particles.get(i);
                //collideParticles(particle1, particle2);

            }*/
            collideWalls(particle1);
        }
        drawCosmos();
    }

    // Check for collision between atom1 & atom2
    private void collideParticles(Particle a1, Particle a2) {
        double radius = a1.getRadius() + a2.getRadius();
        positionVector1 = a1.getPosition(positionVector1);
        positionVector2 = a2.getPosition(positionVector2);
        normalVector = normalVector.set(positionVector1).subtract(positionVector2);
        if (normalVector.norm() < radius) {
            // Move to start of collision
            double dr = (radius - normalVector.norm()) / 2;
            unitNormalVector = unitNormalVector.set(normalVector).unitVector();
            positionVector1.add(unitNormalVector.scale(dr));
            unitNormalVector = unitNormalVector.set(normalVector).unitVector();
            positionVector2.add(unitNormalVector.scale(-dr));
            a1.setPosition(positionVector1);
            a2.setPosition(positionVector2);
            // Find normal and tangential components of v1/v2
            normalVector = normalVector.set(positionVector1).subtract(positionVector2);
            unitNormalVector = unitNormalVector.set(normalVector).unitVector();
            unitTangentVector = unitTangentVector.set(-unitNormalVector.y, unitNormalVector.x);
            velocityVector1 = a1.getVelocity(velocityVector1);
            velocityVector2 = a2.getVelocity(velocityVector2);
            double v1n = unitNormalVector.dot(velocityVector1);
            double v1t = unitTangentVector.dot(velocityVector1);
            double v2n = unitNormalVector.dot(velocityVector2);
            double v2t = unitTangentVector.dot(velocityVector2);
            // Calculate new v1/v2 in normal direction
            double m1 = a1.getMass();
            double m2 = a2.getMass();
            double v1nNew = (v1n * (m1 - m2) + 2d * m2 * v2n) / (m1 + m2);
            double v2nNew = (v2n * (m2 - m1) + 2d * m1 * v1n) / (m1 + m2);
            // Update velocities with sum of normal & tangential components
            velocityVector1 = velocityVector1.set(unitNormalVector).scale(v1nNew);
            velocityVector2 = velocityVector2.set(unitTangentVector).scale(v1t);
            a1.setVelocity(velocityVector1.add(velocityVector2));
            velocityVector1 = velocityVector1.set(unitNormalVector).scale(v2nNew);
            velocityVector2 = velocityVector2.set(unitTangentVector).scale(v2t);
            a2.setVelocity(velocityVector1.add(velocityVector2));
        }
    }

    private void applyAttraction(int indexOfParticle, double timePassedFromLastUpdate) {
        attractionVector.reset();
        Particle particle1 = particles.get(indexOfParticle);
        Particle particle2;
        for (int idx = 0; idx < indexOfParticle; idx++) {
            particle2 = particles.get(idx);
            calculateAndAddToAttractionVector(particle1, particle2);
        }
        for (int idx = indexOfParticle + 1; idx < particles.size(); idx++) {
            particle2 = particles.get(idx);
            calculateAndAddToAttractionVector(particle1, particle2);
        }
        Vector accelerationVector = attractionVector.scale(1 / particle1.getMass());
        //v_f = v_i + (a * t), and acceleration is opposing, so it is negative.

        particle1.setVelocity(particle1.getVelocity()
                .add(accelerationVector
                        .scale(timePassedFromLastUpdate * G_TIME_SCALE /** particle1.getAttractionConstant()*/)));

        //some friction
        //particle1.setVelocity(particle1.getVelocity().scale(0.98));

    }

    private void applyAttractionMolecular(int indexOfParticle){
        Particle particle1 = particles.get(indexOfParticle);
        for(int idx = 0; idx < particles.size(); idx++){
            Particle particle2 = particles.get(idx);

            Vector delta = Vector.subtract(particle2.getPosition(), particle1.getPosition());
            double r = delta.norm();
            double minR = info.getMinDistance(particle1.getType(), particle2.getType());
            double maxR = info.getMaxDistance(particle1.getType(), particle2.getType());

            if(indexOfParticle == idx || r > maxR  || r < 0.1){
                continue;
            }

            delta.divide(r);

            boolean flat_force = false;

            double force = 0.0;
            if(r > minR){
                if(flat_force) {
                    force = info.getAttraction(particle1.getType(), particle2.getType());
                } else {
                    double top = 2.0 * Math.abs(r - 0.5 * (maxR + minR));
                    double bottom = maxR - minR;
                    force =  info.getAttraction(particle1.getType(), particle2.getType()) * (1.0 - top / bottom);
                }
            } else {
                force = 2.0 * minR * (1.0 / (minR + 2.0) - 1.0 / (r + 2.0));
            }
            particle1.setVelocity(particle1.getVelocity().add(delta.scale(force)));

            particle1.setPosition(particle1.getPosition().add(particle1.getVelocity()));
            //friction
            particle1.setVelocity(particle1.getVelocity().scale(1.0 - friction));
        }
    }

    private void calculateAndAddToAttractionVector(Particle particle1, Particle particle2) {
        Vector distance = particle2.getPosition().subtract(particle1.getPosition());
        double r = distance.norm();
        double force = G * particle1.getMass() * particle2.getMass() * (r * r);
        Vector gravitationalForceVector = distance.unitVector().scale(force);
        //if(particle1.getType() == ParticleType.ATTRACTION) {
            attractionVector.add(gravitationalForceVector);
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

    private void setRandomTypes(){

        for(int i = 0; i < info.size(); i++){
            info.setColor(i, colors.get(i));

            for(int j = 0; j < info.size(); j++){
                if(i == j){
                    info.setAttraction(i,j, -Math.abs(random.nextGaussian()*attractionStd+attractionMean));
                    info.setMinDistance(i,j,MIN_RADIUS);
                } else {
                    info.setAttraction(i,j, random.nextGaussian()*attractionStd+attractionMean);
                    info.setMinDistance(i,j,Math.max(minRLower
                            + (minRUpper - minRLower) * random.nextDouble(), MIN_RADIUS));
                }
                info.setMaxDistance(i,j,Math.max(maxRLower + (maxRUpper - maxRLower) * random.nextDouble(),
                        info.getMinDistance(i,j)));

                info.setMaxDistance(j,i,info.getMaxDistance(i,j));
                info.setMinDistance(j,i,info.getMinDistance(i,j));

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
             int type = random.nextInt(colors.size());
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
            double vx = random.nextDouble() * 50;
            if (random.nextBoolean()) vx = -vx;
            double vy = random.nextDouble()  * 50;
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

package com.example.simulator;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Redshoot {
    private static final double wait = 1;

    /*
Red Goal.  Set up to goal side of centerline tile
-62,15,0  ->  -12,32,30


Red Far.  Set up to goal side of centerline tile
62,15,180 ->  42,18,180


Blue Goal.  Set up to goal side of centerline tile
-62,-14,0 ->  0,-20,-30

Blue Far.
62,-14,180  ->  44,-18,200

    */


    //==============================================
    public static RoadRunnerBotEntity examplePath( MeepMeep meepMeep)
    {
        RoadRunnerBotEntity path = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(



                        new Pose2d(-55.5, 41, Math.toRadians(90)))
                        //Shooter on
                        .waitSeconds( 2 )
                        .lineToLinearHeading(new Pose2d(-29.29, 13.36,Math.toRadians(130)))
                        .waitSeconds((3))
                        .lineToLinearHeading(new Pose2d(-11,22,Math.toRadians(90)))
                        .waitSeconds((3))
                        //Start intake
                        .lineToLinearHeading(new Pose2d(-11,38,Math.toRadians(90)))
                        .waitSeconds((.3))
                        .lineToLinearHeading(new Pose2d(-11,46,Math.toRadians(90)))
                        .waitSeconds((.5))
                        //Stop Intake
                        //Go back to shooting position
                        .lineToLinearHeading(new Pose2d(-29.29, 13.36,Math.toRadians(130)))

                        //Shoot again

                        .waitSeconds(2)
                        .build());

        return path;
    }
    //==============================================
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        meepMeep.setMouseCoordinateDisplayPosition( 20, 200 );
        meepMeep.setShowFPS( false );

        RoadRunnerBotEntity path = examplePath( meepMeep );

        File file = new File("Simulator/decodeField.png");
        try
        {
            BufferedImage image = ImageIO.read( file );
            meepMeep.setBackground( image )
                    .setDarkMode( true )
                    .setBackgroundAlpha( 0.7f )
                    .addEntity( path )
                    .start();
        }
        catch(Exception e)
        {
            System.out.println( e.getMessage() );
        }
    }
}
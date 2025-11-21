package com.example.simulator;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class BlueFarShoot3 {
    private static final double wait = 1;



    //==============================================
    public static RoadRunnerBotEntity examplePath( MeepMeep meepMeep)
    {
        RoadRunnerBotEntity path = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(



                        new Pose2d(52, -20, Math.toRadians(180)))
                        //Shooter on
                        .waitSeconds( wait )
                        .lineToLinearHeading(new Pose2d(49,-13.36,Math.toRadians(210)))
                        .waitSeconds((3))
                        //Shoot here


                        //Then go park
                        .lineToLinearHeading(new Pose2d(44,-18,Math.toRadians(200)))
                        //Shoot here
                        /*
                        .lineToLinearHeading(new Pose2d(-11,-25,Math.toRadians(270)))
                        .waitSeconds(.5)
                        //Turn intake on
                        .forward(7)
                        .waitSeconds(.1)
                        .forward(7)
                        .waitSeconds(.1)
                        .forward(9)
                        .waitSeconds(1)
                        //Intake off
                        .lineToLinearHeading(new Pose2d(-33,-14,Math.toRadians(230)))
                        .waitSeconds(3)
                        //Shoot again

                        .lineToLinearHeading(new Pose2d(12,-25,Math.toRadians(270)))
                        //Intake on
                        .forward(7)
                        .waitSeconds(.1)
                        .forward(7)
                        .waitSeconds(.1)
                        .forward(9)
                        .waitSeconds(1)
                        .lineToLinearHeading(new Pose2d(-33,-14,Math.toRadians(230)))
                        .waitSeconds(3)
                        //Shoot
                        .strafeLeft(25)*/
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
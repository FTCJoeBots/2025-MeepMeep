package com.example.simulator;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Simulator11855Park {
  private static final double wait = 1;

  //==============================================
  public static RoadRunnerBotEntity examplePath( MeepMeep meepMeep)
  {
    RoadRunnerBotEntity path = new DefaultBotBuilder(meepMeep)
      // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
      .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
      .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(
          new Pose2d(62, -22, Math.toRadians(180)))
        .waitSeconds( wait )
        .splineTo(new Vector2d(-52,-35),Math.toRadians(240))
        .waitSeconds( wait )
              .lineToLinearHeading(new Pose2d(-12,-18,Math.toRadians(270)))
              .waitSeconds( wait )
              .lineTo(new Vector2d(-12,-50))
              .waitSeconds( wait )
              .lineToLinearHeading(new Pose2d(-52,-35,Math.toRadians(240)))
              .waitSeconds(wait)
              .lineToLinearHeading(new Pose2d(12,-18,Math.toRadians(270)))
              .waitSeconds( wait )
              .lineTo(new Vector2d(12,-50))
              .waitSeconds( wait )
              .lineToLinearHeading(new Pose2d(-52,-35,Math.toRadians(240)))
              .waitSeconds( wait )
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
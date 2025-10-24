package com.example.simulator;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Simulator {
  private static final double wait = 1;
  static Pose2d BLUE_CENTER_RIGHT_INI = new Pose2d(62, -22, Math.toRadians(180));
  static Pose2d BLUE_CENTER_LEFT_INI = new Pose2d(-62, -22, Math.toRadians(0));
  static Pose2d BLUE_SHOOT_POSE = new Pose2d(-52, -35, Math.toRadians(240));
  static Pose2d BLUE_BALL_LEFT_TOP = new Pose2d(-12, -18, Math.toRadians(270));
  static Pose2d BLUE_BALL_LEFT_BOT = new Pose2d(-12, -50, Math.toRadians(270));
  static Pose2d BLUE_BALL_MID_TOP = new Pose2d(12, -18, Math.toRadians(270));
  static Pose2d BLUE_BALL_MID_BOT = new Pose2d(12, -50, Math.toRadians(270));
  static Pose2d BLUE_BALL_RIGHT_TOP = new Pose2d(36, -18, Math.toRadians(270));
  static Pose2d BLUE_BALL_RIGHT_BOT = new Pose2d(36, -50, Math.toRadians(270));
  static Pose2d RED_CENTER_RIGHT_INI = new Pose2d(62, 22, Math.toRadians(180));
  static Pose2d RED_CENTER_LEFT_INI = new Pose2d(-62, 22, Math.toRadians(0));
  static Pose2d RED_SHOOT_POSE = new Pose2d(-52, 35, Math.toRadians(120));
  static Pose2d RED_BALL_LEFT_TOP = new Pose2d(-12, 50, Math.toRadians(90));
  static Pose2d RED_BALL_LEFT_BOT = new Pose2d(-12, 18, Math.toRadians(90));
  static Pose2d RED_BALL_MID_TOP = new Pose2d(12, 50, Math.toRadians(90));
  static Pose2d RED_BALL_MID_BOT = new Pose2d(12, 18, Math.toRadians(90));
  static Pose2d RED_BALL_RIGHT_TOP = new Pose2d(36, 50, Math.toRadians(90));
  static Pose2d RED_BALL_RIGHT_BOT = new Pose2d(36, 18, Math.toRadians(90));



  //==============================================
  public static RoadRunnerBotEntity blueCenterRightPickAll( MeepMeep meepMeep)
  {
    RoadRunnerBotEntity path = new DefaultBotBuilder(meepMeep)
      // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
      .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
      .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(BLUE_CENTER_RIGHT_INI)
        .waitSeconds( wait )
        .splineTo(BLUE_SHOOT_POSE.vec(), BLUE_SHOOT_POSE.getHeading())
        .waitSeconds( wait )
              .lineToLinearHeading(BLUE_BALL_LEFT_TOP)
              .waitSeconds( wait )
              .lineTo(BLUE_BALL_LEFT_BOT.vec())
              .waitSeconds( wait )
              .lineToLinearHeading(BLUE_SHOOT_POSE)
              .waitSeconds(wait)
              .lineToLinearHeading(BLUE_BALL_MID_TOP)
              .waitSeconds( wait )
              .lineTo(BLUE_BALL_MID_BOT.vec())
              .waitSeconds( wait )
              .lineToLinearHeading(BLUE_SHOOT_POSE)
              .waitSeconds(wait)
              .lineToLinearHeading(BLUE_BALL_RIGHT_TOP)
              .waitSeconds( wait )
              .lineTo(BLUE_BALL_RIGHT_BOT.vec())
              .waitSeconds( wait )
              .lineToLinearHeading(BLUE_SHOOT_POSE)
              .waitSeconds(wait)
        .build());

    return path;
  }
  public static RoadRunnerBotEntity blueCenterLeftPickOut( MeepMeep meepMeep)
  {
    RoadRunnerBotEntity path = new DefaultBotBuilder(meepMeep)
            // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
            .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(BLUE_CENTER_LEFT_INI)
                    .waitSeconds( wait )
                    .splineTo(BLUE_SHOOT_POSE.vec(), BLUE_SHOOT_POSE.getHeading())
                    .waitSeconds( wait )
                    .lineToLinearHeading(BLUE_BALL_LEFT_TOP)
                    .waitSeconds( wait )
                    .lineTo(BLUE_BALL_LEFT_BOT.vec())
                    .waitSeconds( wait )
                    .lineToLinearHeading(BLUE_SHOOT_POSE)
                    .waitSeconds(wait)
                    .lineToLinearHeading(BLUE_BALL_MID_TOP)
                    .waitSeconds( wait )
                    .lineTo(BLUE_BALL_MID_BOT.vec())
                    .waitSeconds( wait )
                    .lineToLinearHeading(BLUE_SHOOT_POSE)
                    .waitSeconds(wait)
                    .lineToLinearHeading(BLUE_BALL_RIGHT_TOP)
                    .waitSeconds( wait )
                    .lineTo(BLUE_BALL_RIGHT_BOT.vec())
                    .waitSeconds( wait )
                    .lineToLinearHeading(BLUE_SHOOT_POSE)
                    .waitSeconds(wait)
                    .build());

    return path;
  }
  public static RoadRunnerBotEntity examplePath( MeepMeep meepMeep)
  {
    RoadRunnerBotEntity path = new DefaultBotBuilder(meepMeep)
            // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
            .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
            .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(RED_CENTER_LEFT_INI)
                    .waitSeconds( wait )
                    .splineTo(RED_SHOOT_POSE.vec(), RED_SHOOT_POSE.getHeading())
                    .waitSeconds( wait )
                      .lineToLinearHeading(RED_BALL_LEFT_BOT)
                      .waitSeconds( wait )
                    .lineTo(RED_BALL_LEFT_TOP.vec())
                    .waitSeconds( wait )
                    .lineToLinearHeading(RED_SHOOT_POSE)
                    .waitSeconds(wait)
                    .lineToLinearHeading(RED_BALL_MID_BOT)
                    .waitSeconds( wait )
                    .lineTo(RED_BALL_MID_TOP.vec())
                    .waitSeconds( wait )
                    .lineToLinearHeading(RED_SHOOT_POSE)
                    .waitSeconds(wait)
                    .lineToLinearHeading(RED_BALL_RIGHT_BOT)
                    .waitSeconds( wait )
                    .lineTo(RED_BALL_RIGHT_TOP.vec())
                    .waitSeconds( wait )
                    .lineToLinearHeading(RED_SHOOT_POSE)
                    .waitSeconds(wait)
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
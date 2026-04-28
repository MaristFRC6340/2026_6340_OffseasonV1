// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class OperatorConstants
  {
    //
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 1;
    // Joystick Deadband
    public static final double DEADBAND = 0.1;
    public static final double LEFT_Y_DEADBAND = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT = 6;

    // speed multiplier :D
    public static final double SPEED_CONTROL = 0.5;
  }

  public static class SwerveConstants {
    public static final double kPX = 2.75;
    public static final double kPY = 2.75;
    public static final double kPTheta = 2.5;
    public static final double kXTolerance = 0;
    public static final double kYTolerance = 0;
    public static final double kThetaTolerance = 0;
    public static double kStoredRadius = 3.9527559/2; // to be configured later??
    public static double kDrivebaseRadius = .409;
  }

  // Constants for Swerve Drive
  public static final double MAX_SPEED = Units.feetToMeters(14.5);

  // Not Sure if we need these
  public static final double leftAlignmentX = .2435737274077523; //meters
    public static final double leftAlignmentY = 0.275;
    public static final double rightAlignmentX = .2435737274077523;
    public static final double rightAlignmentY = 0.623;
    public static final double troughAlignmentTheta = -1.860;
    public static final double troughAlignmentX = .379;
    public static final double troughAlignmentY = .748;
    public static final double thetaAlignment = -Math.PI/2; //degrees
    public static double maxAlignmentDistance = 1.5;

    public static final double xTolerance = .05;
    public static final double yTolerance = .05;
    public static final double thetaTolerance = .05;
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LauncherSubsystem extends SubsystemBase {

    TalonFX leftShooter;
    TalonFX rightShooter;

    SparkMax feederMotor;
    SparkFlex rearIndexer;
    SparkFlex activeFloor;

    private final VelocityVoltage shooter_request = new VelocityVoltage(0).withSlot(0);

    //private final RelativeEncoder m_leftLaunchEncoder;
    //private final RelativeEncoder m_rightLaunchEncoder;
  /** Creates a new ExampleSubsystem. */
  public LauncherSubsystem() {
    leftShooter = new TalonFX(Constants.LauncherConstants.leftShooterID);

    rightShooter = new TalonFX(Constants.LauncherConstants.rightShooterID);
    rightShooter.setNeutralMode(NeutralModeValue.Coast);

    MotorOutputConfigs shooterConfigs = new MotorOutputConfigs();
    shooterConfigs.Inverted=InvertedValue.Clockwise_Positive;
    rightShooter.getConfigurator().apply(shooterConfigs);


    feederMotor = new SparkMax(Constants.LauncherConstants.indexerID, MotorType.kBrushless);
    rearIndexer = new SparkFlex(Constants.LauncherConstants.rearIndexerID, MotorType.kBrushless);
    activeFloor = new SparkFlex(Constants.LauncherConstants.activeFloorID, MotorType.kBrushless);// change from null to something else later

    SparkMaxConfig launcherConfig = new SparkMaxConfig();
    launcherConfig.smartCurrentLimit(Constants.LauncherConstants.launcherCurrentLimit);

            launcherConfig.closedLoop
        .p(0.00015)
        .i(0)
        .d(0)
        .outputRange(0, 0.95)
        .feedForward.kV( 12.0 / 6271 ); // 12 Volts divided by Maximum RPM of KrakenX60 (12.0 / 6271)
    
        //Configfor for feeder and active floor
        SparkMaxConfig feederConfig = new SparkMaxConfig();
        feederConfig.smartCurrentLimit(Constants.LauncherConstants.launcherCurrentLimit);
        feederConfig.idleMode(IdleMode.kBrake);
        feederMotor.configure(feederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        activeFloor.configure(feederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // rightShooter.configure(launcherConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        //rightShooter.setDirection(InvertedValue.Clockwise_Positive);

        //launcherConfig.disableFollowerMode();

      // Invert Left
      launcherConfig.inverted(true);
      //launcherConfig.follow(launcherRight); // Trying to have left follow the right
      leftShooter.getConfigurator().apply(Constants.LauncherConstants.launcherConfig);

      // Encoders for Launching Motors
       //m_leftLaunchEncoder = leftShooter.getEncoder();
       //m_rightLaunchEncoder =  rightShooter.getEncoder();

    //   m_leftLaunchEncoder.setPosition(0);
    //   m_rightLaunchEncoder.setPosition(0);

      // Smart Dashboard
      SmartDashboard.putNumber("Left Launcher RPM", 0);
      SmartDashboard.putNumber("Right Launcher RPM:", 0);
      SmartDashboard.putNumber("Left Launch Amps", 0);
      SmartDashboard.putNumber("Right Launch Amps", 0);
      SmartDashboard.putNumber("Shoot Velocity Set", 0);
      
      // binding camera

  }

  public void setShooterRPM(double rpm) {
    leftShooter.set(rpm);
    rightShooter.set(rpm);
  }

  public void setShooterVelocity(double velocity){
    rightShooter.setControl(shooter_request.withVelocity(velocity).withFeedForward(0.5));
  }
  public void setFeederSpeed(double power) {
    feederMotor.set(power);
  }

    public void setRearIndexerSpeed(double power) {
    rearIndexer.set(power);
  }

  public void setIndexerBothSpeed(double power) {
    rearIndexer.set(-power);
    feederMotor.set(power);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double shooterVelocity = rightShooter.getVelocity().getValueAsDouble()*60;
    SmartDashboard.putNumber("Shooter Velocity", shooterVelocity);
  }

  public Command setShooterVelocityCommand(double speed){
    return Commands.run(()-> setShooterVelocity(speed));
  }

  public Command stopShooterCommand(){
    return this.run(()-> setShooterVelocity(0));
  }

  public Command feederSpeedCommand(double speed) {
    return Commands.run(() -> setFeederSpeed(speed));
  }

    public Command stopFeederCommand()  {
    return Commands.run(() -> setFeederSpeed(0));
  }

  public Command startRearIndexerCommand(){
     return Commands.run(() -> setRearIndexerSpeed(-1));
  }

      public Command stopRearIndexerCommand()  {
    return Commands.run(() -> setRearIndexerSpeed(0));
  }

    public Command startIndexerBothCommand(){
     return Commands.run(() -> setIndexerBothSpeed(.75));
  }

      public Command stopIndexerBothCommand()  {
    return Commands.run(() -> setIndexerBothSpeed(0));


  }

  public Command startFeederCommand(){
    return Commands.run(()-> setFeederSpeed(0.8));
  }
  
    public Command reverseFeederCommand(){
    return Commands.run(()-> setFeederSpeed(-0.8));
  }
}

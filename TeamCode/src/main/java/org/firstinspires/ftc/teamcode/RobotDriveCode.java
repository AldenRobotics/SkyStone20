package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
Alden Robotics Code
 *Made for X3 AWD and X4 AWD robot system and configuration
 *System not compatible with other robot systems    */

@TeleOp(name = "Silver Side Robot", group = "Just testing")
public class RobotDriveCode extends OpMode {
    //declaration for two wheels
    DcMotor back_left;
    DcMotor back_right;
    DcMotor front_left;
    DcMotor front_right;

    /*
    //Servos
    Servo Plow1;
    Servo Plow2;
     */

    //input from controller
    double leftWheelPower;
    double rightWheelPower;

    @Override
    public void init() {
        //Motors
        back_left = hardwareMap.dcMotor.get("back_left");
        back_right = hardwareMap.dcMotor.get("back_right");
        front_left = hardwareMap.dcMotor.get("front_left");
        front_right = hardwareMap.dcMotor.get("front_right");

        back_left.setDirection(DcMotorSimple.Direction.REVERSE);
        front_left.setDirection(DcMotorSimple.Direction.REVERSE);

        /*
        //---------------------------------------------------
        //Servos
        Plow1 = hardwareMap.servo.get("Plow1");
        Plow2 = hardwareMap.servo.get("Plow2");
         */

        //Telemetry Garbage
        telemetry.addLine("Okay we are ready to go guys ");


    }

    @Override
    public void loop() {
        //Start A for gamepad 1
        telemetry.addLine(/*This is a */"Tank Drive");
        //Motor Control
        leftWheelPower = gamepad1.right_stick_y;
        rightWheelPower = gamepad1.left_stick_y;


        //Speed control for Motors

        if (gamepad1.right_bumper) {
            back_left.setPower(leftWheelPower * .5);
            back_right.setPower(rightWheelPower * .5);
            front_left.setPower(leftWheelPower * .5);
            front_right.setPower(rightWheelPower * .5);
            telemetry.addLine("Robot Slowing...1/2 speed");

        } else {
            back_left.setPower(leftWheelPower);
            back_right.setPower(rightWheelPower);
            front_left.setPower(leftWheelPower);
            front_right.setPower(rightWheelPower);
            telemetry.clear();
        }

        /*
        //No Servos added yet
        //Servo Control
        if (gamepad1.x) {
            Plow1.setPosition(0);
            Plow2.setPosition(0);
        } else {
            Plow1.setPosition(1);
            Plow2.setPosition(1);
        }
        */

    }


}

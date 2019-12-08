package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

/**
Alden Robotics Code
 *Made for X3 AWD and X4 AWD robot system and configuration
 *System not compatible with other robot systems    */

@TeleOp(name = "Rochester Comp.", group = "just")
public class RobotDriveCode extends OpMode {
    //declaration for 4 wheels
    DcMotor back_left;
    DcMotor back_right;
    DcMotor front_left;
    DcMotor front_right;

    //declaration for front grabber wheels
    DcMotor left;
    DcMotor right;

    //input from controller
    double leftWheelPower;
    double rightWheelPower;

    //touch sensor
    DigitalChannel digitalTouch;  // Hardware Device Object

    //input from controller
    double grabPower;

    @Override
    public void init() {
        //Motors
        back_left = hardwareMap.dcMotor.get("back_left");
        back_right = hardwareMap.dcMotor.get("back_right");
        front_left = hardwareMap.dcMotor.get("front_left");
        front_right = hardwareMap.dcMotor.get("front_right");

        //to fix wheel direction
        back_left.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right.setDirection(DcMotorSimple.Direction.REVERSE);

        //front grabber

        left = hardwareMap.dcMotor.get("left_grab");
        right = hardwareMap.dcMotor.get("right_grab");

        left.setDirection(DcMotorSimple.Direction.REVERSE);

        //Touch sensor


        //Telemetry Garbage
        telemetry.addLine("Okay we are ready to go guys ");
        grabPower = -0.05;

        // get a reference to our digitalTouch object.
        digitalTouch = hardwareMap.get(DigitalChannel.class, "sensor_digital");

        // set the digital channel to input.
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        // wait for the start button to be pressed.
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

        left.setPower(grabPower);
        right.setPower(grabPower);


        //Speed control for Motors

        if (gamepad2.a) {

            grabPower = -0.5;

        }
        else {
            grabPower = -0.05;
        }


        if (gamepad2.b) {

            grabPower = 1.0;

        }

        // send the info back to driver station using telemetry function.
        // if the digital channel returns true it's HIGH and the button is unpressed.
        if (digitalTouch.getState() == true) {
            telemetry.addData("Digital Touch", "Is Not Pressed");
        } else {
            telemetry.addData("Digital Touch", "Is Pressed");

            grabPower = 0.0;
        }


        telemetry.update();


    }


}

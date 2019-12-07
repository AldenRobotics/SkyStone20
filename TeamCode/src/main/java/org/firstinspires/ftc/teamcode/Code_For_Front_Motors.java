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

@TeleOp(name = "Front_Grabber_Test", group = "Just testing")
public class Code_For_Front_Motors extends OpMode {
    //declaration for two wheels
    DcMotor left;
    DcMotor right;


    DigitalChannel digitalTouch;  // Hardware Device Object

    /*
    //Servos
    Servo Plow1;
    Servo Plow2;
     */

    //input from controller
    double grabPower;



    @Override
    public void init() {
        //Motors
        left = hardwareMap.dcMotor.get("left_grab");
        right = hardwareMap.dcMotor.get("right_grab");


        left.setDirection(DcMotorSimple.Direction.REVERSE);

        /*
        //---------------------------------------------------
        //Servos
        Plow1 = hardwareMap.servo.get("Plow1");
        Plow2 = hardwareMap.servo.get("Plow2");
         */

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

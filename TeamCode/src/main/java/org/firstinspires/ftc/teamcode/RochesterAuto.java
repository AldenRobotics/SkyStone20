/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;


@Autonomous(name="Rochester-Auto", group="COMP")
//@Disabled
public class RochesterAuto extends LinearOpMode {


    //declaration for 4 wheels
    DcMotor back_left;
    DcMotor back_right;
    DcMotor front_left;
    DcMotor front_right;

    //declaration for front grabber wheels
    DcMotor left;
    DcMotor right;

        /* Declare OpMode members. */
    HardwarePushbot robot   = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

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


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        back_left.setPower(FORWARD_SPEED);
        back_right.setPower(FORWARD_SPEED);
        front_left.setPower(FORWARD_SPEED);
        front_right.setPower(FORWARD_SPEED);

        sleep(2500);

        back_left.setPower(TURN_SPEED);
        back_right.setPower(TURN_SPEED * -1);
        front_left.setPower(TURN_SPEED);
        front_right.setPower(TURN_SPEED * -1);

        sleep(2500);

        back_left.setPower(FORWARD_SPEED);
        back_right.setPower(FORWARD_SPEED);
        front_left.setPower(FORWARD_SPEED);
        front_right.setPower(FORWARD_SPEED);

        sleep(8500);

        stop();
    }
}

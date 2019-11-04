
package org.firstinspires.ftc.teamcode;
import android.media.AudioManager;
import android.media.SoundPool;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/**
 * This 2018-2019 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the gold and silver minerals.
 * <p>
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 * <p>
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */
@TeleOp(name = "Concept: TensorFlow Object Detection", group = "Concept")
//@Disabled
public class Vuforia_Tensor_flow extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    DcMotor FleftWheel;
    DcMotor FrightWheel;
    DcMotor BleftWheel;
    DcMotor BrightWheel;
    Servo Plow1;
    Servo Plow2;

    // Sound variables
    SoundPool mySoundL;
    int beepIDL;

    SoundPool mySoundC;
    int beepIDC;

    SoundPool mySoundR;
    int beepIDR;




    /*
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "AT4UbXz/////AAABmZNGJN4S9EuViS5xKITvJVQg5HZQZe9jF0yRV7rbNk0WtJrTmYIVS2ULbwgzQZ4G6WHbe2pXnULSXlU50/vzjgVJ3ULt4nYrt03hNNM9WI5tMmadtjW8k4iSQvbbiXlflge/3rdqKhzPiSFfkMHeY2qYomEcklG41Jo4AiKH35PlZwYZF8x7H9pJhOPh5y1zjwvefTf9FIBos1k7WCNF38mT+AVfuHGKKm0lduy23epCZ7wUYeSPDtAJUm9uZNvfNaOP3FemJ56gE4bCUsBABPTq4w1R44RvFswm580NEGkJ6cku0CzG9L2Q4w+snhLzpOYl1Mje3USp/how7gee+/H47ipt/nW+BHymuZd78RQi";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();

        FrightWheel = hardwareMap.dcMotor.get("front_right");
        FleftWheel = hardwareMap.dcMotor.get("front_left");
        BleftWheel = hardwareMap.dcMotor.get("back_left");
        BrightWheel = hardwareMap.dcMotor.get("back_right");
        BleftWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        //FleftWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        FrightWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        Plow1 = hardwareMap.servo.get("Plow1");
        Plow2 = hardwareMap.servo.get("Plow2");


        //For the Turn Time of the robot
        int turnTime = 350;
        double drivePower = -.2;
        double up = .5;
        int bTurn = 398;

        //For INT position


        /*
        //NEEDS FILES FOR MUSIC
        //Music Stuff :)
        mySoundL = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // PSM
        beepIDL = mySoundL.load(hardwareMap.appContext, R.raw.left, 1); // PSM

        mySoundC = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // PSM
        beepIDC = mySoundL.load(hardwareMap.appContext, R.raw.center, 1); // PSM

        mySoundR = new SoundPool(1, AudioManager.STREAM_MUSIC, 0); // PSM
        beepIDR = mySoundL.load(hardwareMap.appContext, R.raw.right, 1); // PSM
        */




        waitForStart();
        if (opModeIsActive()) {

            /** Activate TensorFlow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "left");
                                    telemetry.update();

                                    //To turn off camera
                                    if (tfod != null) {
                                        tfod.shutdown();
                                    }
                                    mySoundL.play(beepIDL, 1, 1, 1, 0, 1);
                                    BleftWheel.setPower(.3);
                                    BrightWheel.setPower(-.3);
                                    FleftWheel.setPower(.3);
                                    FrightWheel.setPower(-.3);
                                    telemetry.addLine("Going left");
                                    telemetry.update();
                                    telemetry.update();


                                    sleep(turnTime);

                                    BleftWheel.setPower(drivePower);
                                    BrightWheel.setPower(drivePower);
                                    FleftWheel.setPower(drivePower);
                                    FrightWheel.setPower(drivePower);
                                    telemetry.addLine("Going Foward");


                                    sleep(4500);


                                    BleftWheel.setPower(-.3);
                                    BrightWheel.setPower(.3);
                                    FleftWheel.setPower(-.3);
                                    FrightWheel.setPower(.3);


                                    sleep(bTurn);



                                    BleftWheel.setPower(drivePower);
                                    BrightWheel.setPower(drivePower);
                                    FleftWheel.setPower(drivePower);
                                    FrightWheel.setPower(drivePower);
                                    telemetry.addLine("Going Foward");
                                    telemetry.update();



                                    sleep(1000);

                                    Plow1.setPosition(up);
                                    Plow2.setPosition(up);
                                    BleftWheel.setPower(drivePower);
                                    BrightWheel.setPower(drivePower);
                                    FleftWheel.setPower(drivePower);
                                    FrightWheel.setPower(drivePower);
                                    telemetry.addLine("Going Foward");
                                    telemetry.update();
                                    sleep(2000);


                                    stop();


                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "right");
                                    telemetry.update();

                                    mySoundL.play(beepIDR, 1, 1, 1, 0, 1);

                                    //To turn off camera
                                    if (tfod != null) {
                                        tfod.shutdown();
                                    }

                                    BleftWheel.setPower(-.3);
                                    BrightWheel.setPower(.3);
                                    FleftWheel.setPower(-.3);
                                    FrightWheel.setPower(.3);
                                    telemetry.addLine("Going right");
                                    telemetry.update();


                                    sleep(turnTime);

                                    BleftWheel.setPower(drivePower);
                                    BrightWheel.setPower(drivePower);
                                    FleftWheel.setPower(drivePower);
                                    FrightWheel.setPower(drivePower);
                                    telemetry.addLine("Going Foward");
                                    telemetry.update();


                                    sleep(4500);

                                    BleftWheel.setPower(.3);
                                    BrightWheel.setPower(-.3);
                                    FleftWheel.setPower(.3);
                                    FrightWheel.setPower(-.3);
                                    telemetry.addLine("Going right");

                                    sleep(bTurn);

                                    BleftWheel.setPower(drivePower);
                                    BrightWheel.setPower(drivePower);
                                    FleftWheel.setPower(drivePower);
                                    FrightWheel.setPower(drivePower);
                                    telemetry.addLine("Going Foward");
                                    telemetry.update();


                                    sleep(1000);

                                    Plow1.setPosition(up);
                                    Plow2.setPosition(up);
                                    BleftWheel.setPower(drivePower);
                                    BrightWheel.setPower(drivePower);
                                    FleftWheel.setPower(drivePower);
                                    FrightWheel.setPower(drivePower);
                                    telemetry.addLine("Going Foward");
                                    telemetry.update();
                                    sleep(2000);


                                    stop();


                                } else {
                                    telemetry.addData("Gold Mineral Position", "center");
                                    telemetry.update();

                                    mySoundL.play(beepIDC, 1, 1, 1, 0, 1);

                                    //To turn off camera
                                    if (tfod != null) {
                                        tfod.shutdown();
                                    }

                                    BleftWheel.setPower(drivePower);
                                    BrightWheel.setPower(drivePower);
                                    FleftWheel.setPower(drivePower);
                                    FrightWheel.setPower(drivePower);
                                    telemetry.addLine("Going Foward");
                                    telemetry.update();

                                    sleep(4500);

                                    BleftWheel.setPower(drivePower);
                                    BrightWheel.setPower(drivePower);
                                    FleftWheel.setPower(drivePower);
                                    FrightWheel.setPower(drivePower);
                                    telemetry.addLine("Going Foward");
                                    telemetry.update();

                                    Plow1.setPosition(up);
                                    Plow2.setPosition(up);



                                    sleep(1000);


                                    stop();


                                }
                            }
                        }
                    }
                }
            }
        }


    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
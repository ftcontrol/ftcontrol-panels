package org.firstinspires.ftc.teamcode.camerastream

import android.graphics.Bitmap
import android.graphics.Canvas
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import java.util.concurrent.atomic.AtomicReference
import org.firstinspires.ftc.robotcore.external.function.Consumer
import org.firstinspires.ftc.robotcore.external.function.Continuation
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.VisionProcessor
import org.opencv.android.Utils
import org.opencv.core.Mat
import androidx.core.graphics.createBitmap
import com.bylazar.camerastream.PanelsCameraStream
import com.qualcomm.robotcore.eventloop.opmode.OpMode

@TeleOp(name = "Test Camera Stream", group = "Dev")
class TestCameraStream : OpMode() {

    class Processor : VisionProcessor, CameraStreamSource {

        private val lastFrame =
            AtomicReference(createBitmap(1, 1, Bitmap.Config.RGB_565))

        override fun init(width: Int, height: Int, calibration: CameraCalibration?) {
            lastFrame.set(createBitmap(width, height, Bitmap.Config.RGB_565))
        }

        override fun processFrame(frame: Mat, captureTimeNanos: Long): Any? {
            val b = createBitmap(frame.width(), frame.height(), Bitmap.Config.RGB_565)
            Utils.matToBitmap(frame, b)

            lastFrame.set(b)

            return null
        }

        override fun onDrawFrame(
            canvas: Canvas,
            onscreenWidth: Int,
            onscreenHeight: Int,
            scaleBmpPxToCanvasPx: Float,
            scaleCanvasDensity: Float,
            userContext: Any?
        ) {
        }

        override fun getFrameBitmap(continuation: Continuation<out Consumer<Bitmap>>) {
            continuation.dispatch { bitmapConsumer ->
                bitmapConsumer.accept(lastFrame.get())
            }
        }
    }

    val processor = Processor()

    override fun init() {
        VisionPortal.Builder()
            .addProcessor(processor)
            .setCamera(BuiltinCameraDirection.BACK)
            .build()

        PanelsCameraStream.startStream(processor)
    }

    override fun loop() {

    }

    override fun stop() {
        PanelsCameraStream.stopStream()
    }
}

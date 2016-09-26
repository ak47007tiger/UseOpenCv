package test;

import java.util.ArrayList;
import java.util.Random;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

//  
// Detects faces in an image, draws boxes around them, and writes the results  
// to "faceDetection.png".  
//  
public class DetectFaceDemo {
	public void lk(Mat image){
		Mat dst = new Mat(image.size(), image.type());
		//灰度化
		Imgproc.cvtColor(image, dst, Imgproc.COLOR_BGR2GRAY);
		Imgcodecs.imwrite("1gray.png", dst);
		//模糊
		Mat dst2 = new Mat(image.size(), image.type());
		Size ksize = new Size(3, 3);
		Imgproc.blur(dst, dst2, ksize);
		Imgcodecs.imwrite("2blur.png", dst2);
		
		double threshold1 = 100;
		double threshold2 = threshold1 * 2;
		//轮廓检测
		Mat cannyOutput = new Mat(image.size(), image.type());;
		Imgproc.Canny(dst2, cannyOutput, threshold1 , threshold2 );
		Imgcodecs.imwrite("3cannyOutput.png", cannyOutput);
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat(image.size(), image.type());
		Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		Imgcodecs.imwrite("4hierarchy.png", hierarchy);
		
		//绘制轮廓
		Random random = new Random(System.currentTimeMillis());
		Mat drawing = new Mat(image.size(), image.type());
		for(int i = 0; i < contours.size(); i++){
			Scalar color = new Scalar(random.nextInt(256), random.nextInt(256), random.nextInt(256));
			Imgproc.drawContours(drawing, contours, i, color, 2, 8, hierarchy, 0, new Point());
		}
		Imgcodecs.imwrite("5drawing.png", drawing);
		
		//二值化
//		Imgproc.threshold(dst, dst2, 100, 255, Imgproc.THRESH_BINARY);
	}
	public void run() {
		String filesDir = System.getProperty("user.dir") + "/files";

		System.out.println("\nRunning DetectFaceDemo");

		// Create a face detector from the cascade file in the resources
		// directory.
		String xmlPath = filesDir + "/lbpcascade_frontalface.xml";
		CascadeClassifier faceDetector = new CascadeClassifier(xmlPath);
		String imgPath = filesDir + "/lena.png";
		Mat image = Imgcodecs.imread(imgPath);
		lk(image);
		// Detect faces in the image.
		// MatOfRect is a special container class for Rect.
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		System.out.println(String.format("Detected %s faces",
				faceDetections.toArray().length));

		// Draw a bounding box around each face.
		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(
					rect.x + rect.width, rect.y + rect.height), new Scalar(0,
					255, 0));
		}
		
		// Save the visualized detection.
		String filename = "faceDetection.png";
		System.out.println(String.format("Writing %s", filename));
		Imgcodecs.imwrite(filename, image);
	}

	public static void main(String[] args) {
		System.out.println("Hello, OpenCV");
		// Load the native library.
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		new DetectFaceDemo().run();
	}
	
}
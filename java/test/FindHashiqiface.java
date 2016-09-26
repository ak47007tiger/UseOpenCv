package test;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FindHashiqiface {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String filesDir = System.getProperty("user.dir") + "/files";

		System.out.println("\nRunning DetectFaceDemo");

		// Create a face detector from the cascade file in the resources
		// directory.
		String xmlPath = filesDir + "/hashiqi_dagface_cascade.xml";
		CascadeClassifier faceDetector = new CascadeClassifier(xmlPath);
		String imgPath = filesDir + "/hashiqi3.jpg";
		Mat image = Imgcodecs.imread(imgPath);
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
		String filename = "findHashiqi.png";
		System.out.println(String.format("Writing %s", filename));
		Imgcodecs.imwrite(filename, image);
	}
}

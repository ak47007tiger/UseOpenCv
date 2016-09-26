package test;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;


public class TestFuncOk {
	public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        System.loadLibrary("opencv_java310");  
        Mat m  = Mat.eye(3, 3, CvType.CV_8UC1);  
        System.out.println("m = " + m.dump());  
        String path = System.getProperty("user.dir");
        String filename = path + "/files/img.jpg";
        Mat imgMat = Imgcodecs.imread(filename);
    }  
}

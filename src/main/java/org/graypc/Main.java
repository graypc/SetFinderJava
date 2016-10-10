package org.graypc;

/*
 * HelloWorldSwing.java requires no other files.
 */


import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_calib3d.cvRodrigues2;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_DO_ROUGH_SEARCH;
import static org.bytedeco.javacpp.opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT;

/*
import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.indexer.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_calib3d.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;
*/

public class Main
{
    private static List<List<Card>> mCards;

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        //Create and set up the window.
        JFrame frame = new JFrame("SetFinder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        frame.getContentPane().add(mainPanel);

        mCards = new ArrayList<List<Card>>();

        // For each row
        for (int row = 0; row < 3; ++row)
        {
            // Add a new row of Cards
            mCards.add(new ArrayList<Card>());

            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            rowPanel.add(Box.createRigidArea(new Dimension(5, 0)));

            // For each column
            for (int col = 0; col < 3; ++col)
            {
                // Create a new card
                Card card = new Card(1, Card.Shade.CARD_SHADE_FULL,
                        Card.Color.CARD_COLOR_GREEN, Card.Shape.CHARD_SHAPE_DIAMOND);

                mCards.get(row).add(card);

                // Add this card to the GUI
                rowPanel.add(card);
                rowPanel.add(Box.createRigidArea(new Dimension(5, 0)));
            }
            mainPanel.add(rowPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                findSolution();
            }
        });

        mainPanel.add(solveButton);

        //frame.setPreferredSize(new Dimension(105 * 3, 155 * 3));
        /*
        container.add(new Card(1,
                Card.Shade.CARD_SHADE_FULL,
                Card.Color.CARD_COLOR_GREEN,
                Card.Shape.CHARD_SHAPE_DIAMOND));

        container.add(new Card(1,
                Card.Shade.CARD_SHADE_FULL,
                Card.Color.CARD_COLOR_GREEN,
                Card.Shape.CHARD_SHAPE_DIAMOND));

        container.add(new Card(1,
                Card.Shade.CARD_SHADE_FULL,
                Card.Color.CARD_COLOR_GREEN,
                Card.Shape.CHARD_SHAPE_DIAMOND));

        container.add(new Card(1,
                Card.Shade.CARD_SHADE_FULL,
                Card.Color.CARD_COLOR_GREEN,
                Card.Shape.CHARD_SHAPE_DIAMOND));
                */

        //frame.setSize(new Dimension(500, 500));

//        frame.getContentPane().add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void findSolution()
    {

    }

    /*
    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }
    */
//}

    /**/
//public class Main {
	public static void main(String[] args) throws Exception {
		String classifierName = null;
		if (args.length > 0) {
			classifierName = args[0];
		} else {
			URL url = new URL("https://raw.github.com/Itseez/opencv/2.4.0/data/haarcascades/haarcascade_frontalface_alt.xml");
			File file = Loader.extractResource(url, null, "classifier", ".xml");
			file.deleteOnExit();
			classifierName = file.getAbsolutePath();
		}

		// Preload the opencv_objdetect module to work around a known bug.
		Loader.load(opencv_objdetect.class);

		// We can "cast" Pointer objects by instantiating a new object of the desired class.
		opencv_objdetect.CvHaarClassifierCascade classifier = new opencv_objdetect.CvHaarClassifierCascade(cvLoad(classifierName));
		if (classifier.isNull()) {
			System.err.println("Error loading classifier file \"" + classifierName + "\".");
			System.exit(1);
		}

		// The available FrameGrabber classes include OpenCVFrameGrabber (opencv_videoio),
		// DC1394FrameGrabber, FlyCaptureFrameGrabber, OpenKinectFrameGrabber,
		// PS3EyeFrameGrabber, VideoInputFrameGrabber, and FFmpegFrameGrabber.
		FrameGrabber grabber = FrameGrabber.createDefault(0);
		grabber.start();

		// CanvasFrame, FrameGrabber, and FrameRecorder use Frame objects to communicate image data.
		// We need a FrameConverter to interface with other APIs (Android, Java 2D, or OpenCV).
		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

		// FAQ about IplImage and Mat objects from OpenCV:
		// - For custom raw processing of data, createBuffer() returns an NIO direct
		//   buffer wrapped around the memory pointed by imageData, and under Android we can
		//   also use that Buffer with Bitmap.copyPixelsFromBuffer() and copyPixelsToBuffer().
		// - To get a BufferedImage from an IplImage, or vice versa, we can chain calls to
		//   Java2DFrameConverter and OpenCVFrameConverter, one after the other.
		// - Java2DFrameConverter also has static copy() methods that we can use to transfer
		//   data more directly between BufferedImage and IplImage or Mat via Frame objects.
		opencv_core.IplImage grabbedImage = converter.convert(grabber.grab());
		int width  = grabbedImage.width();
		int height = grabbedImage.height();
		opencv_core.IplImage grayImage    = opencv_core.IplImage.create(width, height, IPL_DEPTH_8U, 1);
		opencv_core.IplImage rotatedImage = grabbedImage.clone();

		// Objects allocated with a create*() or clone() factory method are automatically released
		// by the garbage collector, but may still be explicitly released by calling release().
		// You shall NOT call cvReleaseImage(), cvReleaseMemStorage(), etc. on objects allocated this way.
		opencv_core.CvMemStorage storage = opencv_core.CvMemStorage.create();

		// The OpenCVFrameRecorder class simply uses the CvVideoWriter of opencv_videoio,
		// but FFmpegFrameRecorder also exists as a more versatile alternative.
		FrameRecorder recorder = FrameRecorder.createDefault("output.avi", width, height);
		recorder.start();

		// CanvasFrame is a JFrame containing a Canvas component, which is hardware accelerated.
		// It can also switch into full-screen mode when called with a screenNumber.
		// We should also specify the relative monitor/camera response for proper gamma correction.
		CanvasFrame frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma()/grabber.getGamma());

		// Let's create some random 3D rotation...
		opencv_core.CvMat randomR = opencv_core.CvMat.create(3, 3), randomAxis = opencv_core.CvMat.create(3, 1);
		// We can easily and efficiently access the elements of matrices and images
		// through an Indexer object with the set of get() and put() methods.
		DoubleIndexer Ridx = randomR.createIndexer(), axisIdx = randomAxis.createIndexer();
		axisIdx.put(0, (Math.random()-0.5)/4, (Math.random()-0.5)/4, (Math.random()-0.5)/4);
		cvRodrigues2(randomAxis, randomR, null);
		double f = (width + height)/2.0;  Ridx.put(0, 2, Ridx.get(0, 2)*f);
		Ridx.put(1, 2, Ridx.get(1, 2)*f);
		Ridx.put(2, 0, Ridx.get(2, 0)/f); Ridx.put(2, 1, Ridx.get(2, 1)/f);
		System.out.println(Ridx);

		// We can allocate native arrays using constructors taking an integer as argument.
		opencv_core.CvPoint hatPoints = new opencv_core.CvPoint(3);

		while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {
			cvClearMemStorage(storage);

			// Let's try to detect some faces! but we need a grayscale image...
			cvCvtColor(grabbedImage, grayImage, CV_BGR2GRAY);
			opencv_core.CvSeq faces = cvHaarDetectObjects(grayImage, classifier, storage,
					1.1, 3, CV_HAAR_FIND_BIGGEST_OBJECT | CV_HAAR_DO_ROUGH_SEARCH);
			int total = faces.total();
			for (int i = 0; i < total; i++) {
				opencv_core.CvRect r = new opencv_core.CvRect(cvGetSeqElem(faces, i));
				int x = r.x(), y = r.y(), w = r.width(), h = r.height();
				cvRectangle(grabbedImage, cvPoint(x, y), cvPoint(x+w, y+h), CvScalar.RED, 1, CV_AA, 0);

				// To access or pass as argument the elements of a native array, call position() before.
				hatPoints.position(0).x(x-w/10)   .y(y-h/10);
				hatPoints.position(1).x(x+w*11/10).y(y-h/10);
				hatPoints.position(2).x(x+w/2)    .y(y-h/2);
				cvFillConvexPoly(grabbedImage, hatPoints.position(0), 3, CvScalar.GREEN, CV_AA, 0);
			}

			// Let's find some contours! but first some thresholding...
			cvThreshold(grayImage, grayImage, 64, 255, CV_THRESH_BINARY);

			// To check if an output argument is null we may call either isNull() or equals(null).
			CvSeq contour = new CvSeq(null);
			cvFindContours(grayImage, storage, contour, Loader.sizeof(CvContour.class),
					CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
			while (contour != null && !contour.isNull()) {
				if (contour.elem_size() > 0) {
					CvSeq points = cvApproxPoly(contour, Loader.sizeof(CvContour.class),
							storage, CV_POLY_APPROX_DP, cvContourPerimeter(contour)*0.02, 0);
					cvDrawContours(grabbedImage, points, CvScalar.BLUE, CvScalar.BLUE, -1, 1, CV_AA);
				}
				contour = contour.h_next();
			}

			cvWarpPerspective(grabbedImage, rotatedImage, randomR);

			org.bytedeco.javacv.Frame rotatedFrame = converter.convert(rotatedImage);
			frame.showImage(rotatedFrame);
			recorder.record(rotatedFrame);
		}
		frame.dispose();
		recorder.stop();
		grabber.stop();
	}
}
/**/


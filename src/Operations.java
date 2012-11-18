import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;
import javax.media.jai.ROI;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

public class Operations {
	
	public static void main(String[] args) {
		Operations main = new Operations();
		PlanarImage image = main.loadPicture("loetstellen.jpg");
		image = main.defineROI(image, 0,60, 420, 50);
		image = main.threshold(image, 0, 30, 255);
		image = main.median(image, MedianFilterDescriptor.MEDIAN_MASK_SQUARE, 5);
		image = main.erode(image);
		image = main.dilate(image);
		image = main.calculateBalls(image);
		main.showPicture(main.convertToDisplayJAI(image));
		
	}

	private PlanarImage loadPicture(String filename){
		PlanarImage image = JAI.create("fileload", "/Users/Shady/Downloads/" + filename);
		return image;
		
	}
	
	private DisplayJAI convertToDisplayJAI(PlanarImage image){
		DisplayJAI dj = new DisplayJAI(image);
		return dj;
	}
	
	private void showPicture(DisplayJAI dj){
		JFrame frame = new JFrame();
		frame.setTitle("DisplayJAI: lena512.jpg");
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new JScrollPane(dj),BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,200); // adjust the frame size.
		frame.setVisible(true); // show the frame.
	}
	
	private PlanarImage defineROI(PlanarImage image, int x, int y, int width, int height){
		
		Rectangle rectangle = new Rectangle(x, y, width, height);
		image = PlanarImage.wrapRenderedImage((RenderedImage)image.getAsBufferedImage
				(rectangle, image.getColorModel()));
		return image;
		
	}
	
	private PlanarImage threshold(PlanarImage image, double low, double high, double map){
		double[] arrayLow = {low};
		double[] arrayHigh = {high};
		double[] arrayMap = {map};
		
		ParameterBlock parameterBlock = new ParameterBlock();
		parameterBlock.addSource(image);
		parameterBlock.add(arrayLow);
		parameterBlock.add(arrayHigh);
		parameterBlock.add(arrayMap);
		return JAI.create("threshold", parameterBlock);
	}
	
	private PlanarImage median(PlanarImage image, MedianFilterShape shape, int size){
		
		ParameterBlock parameterBlock = new ParameterBlock();
		parameterBlock.addSource(image);
		parameterBlock.add(shape);
		parameterBlock.add(size);
		return JAI.create("MedianFilter", parameterBlock);
	}
	
	private PlanarImage erode(PlanarImage image){
        float[] kernelData = {
                0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,};
        
        KernelJAI kernel = new KernelJAI(11, 11, kernelData);

        ParameterBlock parameterBlock = new ParameterBlock();
        parameterBlock.addSource(image);
        parameterBlock.add(kernel);
        return JAI.create("erode", parameterBlock);
	}
	
	private PlanarImage dilate(PlanarImage image){
        float[] kernelData = {
                0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,};

            KernelJAI kernel = new KernelJAI(11, 11, kernelData);

            ParameterBlock parameterBlock = new ParameterBlock();
            parameterBlock.addSource(image);
            parameterBlock.add(kernel);

            return JAI.create("dilate", parameterBlock);
	}

	
	private PlanarImage calculateBalls(PlanarImage _image) {
		Raster pixelRaster = _image.getData();
		LinkedList<LinkedList<Point>> allBalls = new LinkedList<LinkedList<Point>>();
		LinkedList<Point> ball = new LinkedList<Point>();

		boolean isBall = false;

		StringBuffer results = new StringBuffer();

		// Go horizontal through the raster
		for (int x = 0; x < pixelRaster.getWidth(); x++) {

			isBall = false;

			// Go vertical through the raster
			for (int y = 0; y < pixelRaster.getHeight(); y++) {

				int[] dArray = new int[3];
				pixelRaster.getPixel(x, y, dArray);

				// Check the color of the pixel and when it falls in the defined
				// range add it to the set
				if (dArray[0] == 255 && dArray[1] == 255 && dArray[2] == 255
						|| dArray[0] > 240 && dArray[1] > 240
						&& dArray[2] > 240) {
					ball.add(new Point(x, y));
					isBall = true;
				}
			}
			// After some Pixels have been added to a ball and the actual pixel
			// is not anymore white, the ball is added to the array of balls
			if (isBall == false && ball.size() > 0) {
				allBalls.add(ball);
				ball = new LinkedList<Point>();
			}
		}

		// Mark the center of the white pixel set with an blue spot
		// And print out the coordinates
		BufferedImage buf = _image.getAsBufferedImage();
		for (LinkedList<Point> currentBall : allBalls) {
			Point middlePoint = getCentralPointOfBall(currentBall);
			Graphics g = buf.createGraphics();

			g.setColor(Color.RED);
			g.fillOval(middlePoint.x - 2, middlePoint.y - 2, 4, 4);
			System.out.println("");
			System.out.println("Center coordinates");
			System.out.println("X: " + middlePoint.x);
			System.out.println("Y: " + middlePoint.y);

			results.append("Center coordinates\n");
			results.append("X: " + middlePoint.x + "\n");
			results.append("Y: " + middlePoint.y + "\n");
			results.append("\n");
		}

		Writer out = null;
		File file = new File("write.txt");
		// WRITE THE MIDDLEPOINTS INTO A TEXT FILE
		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(results.toString());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		PlanarImage output = PlanarImage.wrapRenderedImage(buf);

		return output;
	}
	
	private Point getCentralPointOfBall(LinkedList<Point> points){
		int countX = 0;
		int valueX = 0;
		int centerX = 0;
		
		int countY = 0;
		int valueY = 0;
		int centerY = 0;
		
		for(Point point : points){
			valueX += point.getX();
			valueY += point.getY();
			countX++;
			countY++;
			
		}
		centerX = valueX/countX;
		centerY = valueY/countY;
		return new Point(centerX, centerY);
		
	}
	
}



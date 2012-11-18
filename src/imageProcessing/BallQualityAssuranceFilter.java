package imageProcessing;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;
import itb04.sa.teamb.pipesfilters.filter.AbstractFilter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.LinkedList;

import javax.media.jai.PlanarImage;

//This Filter finds all the "white" balls on the defined picture and puts them into a LinkedList
//After this step, every ball is measured and a red dot is set onto the picture.
public class BallQualityAssuranceFilter extends
		AbstractFilter<PlanarImage, PlanarImage> {

	private PlanarImage _image;
	private Writeable<PlanarImage> _output;

	public BallQualityAssuranceFilter(Readable<PlanarImage> input)
			throws InvalidParameterException, StreamCorruptedException {
		super(input);
		_image = input.read();
	}
	
	public BallQualityAssuranceFilter(Writeable<PlanarImage> output)
			throws InvalidParameterException, StreamCorruptedException {
		super(output);
		_output = output;
	}

	@Override
	public PlanarImage read() throws StreamCorruptedException {
		return process();
	}

	@Override
	public void write(PlanarImage value) throws StreamCorruptedException {
		_image = value;
		_output.write(process());
	}

	private PlanarImage process() {
		Raster ras = _image.getData();
		return calculateBalls(ras);
	}

	
	@Override
	public void run() {

	}

	private PlanarImage calculateBalls(Raster pixelRaster) {
		LinkedList<LinkedList<Point>> allBalls = new LinkedList<LinkedList<Point>>();
		LinkedList<Point> ball = new LinkedList<Point>();

		boolean isBall = false;

		StringBuffer results = new StringBuffer();

		//Iterate through all the pixels on the pictures
		for (int x = 0; x < pixelRaster.getWidth(); x++) {

			isBall = false;

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
		// And print out the coordinates in the console
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

		return PlanarImage.wrapRenderedImage(buf);
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

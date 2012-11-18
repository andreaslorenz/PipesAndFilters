package imageProcessing;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;

import java.awt.Rectangle;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;

public class Main {

	public static void main(String[] args) throws InvalidParameterException, StreamCorruptedException {
		Main main = new Main();
		//How do you want the image processing to be done?
		
		main.pullFilters();
		main.pushFilters();
	}
	
	private void pullFilters() throws InvalidParameterException, StreamCorruptedException{
		Rectangle rectangle = new Rectangle(0,60, 420, 50);
		//PULL AUFRUF
		Readable<PlanarImage> source = new ImageSource("/Users/Shady/Downloads/loetstellen.jpg");
		Readable<PlanarImage> roi = new DefineROIFilter(source, rectangle);
		Readable<PlanarImage> threshold = new ThresholdFilter(roi, 0.0, 30.0, 250.0);
		Readable<PlanarImage> median = new MedianFilter(threshold, MedianFilterDescriptor.MEDIAN_MASK_SQUARE, 5);
		Readable<PlanarImage> erode = new ErodeFilter(median);
		Readable<PlanarImage> dilate = new DilateFilter(erode);
		Readable<PlanarImage> quality = new BallQualityAssuranceFilter(dilate);
		ImageSink sink = new ImageSink(quality);
		
		Thread thread = new Thread(sink);
		thread.start();
	}
	
	private void pushFilters() throws InvalidParameterException, StreamCorruptedException{
		Rectangle rectangle = new Rectangle(0,60, 420, 50);

		//PUSH AUFRUF
		Writeable<PlanarImage> source = new ImageSource("/Users/Shady/Downloads/loetstellen.jpg",
				(Writeable<PlanarImage>) new DefineROIFilter((Writeable<PlanarImage>)new ThresholdFilter(
						(Writeable<PlanarImage>) new MedianFilter((Writeable<PlanarImage>)new ErodeFilter(
								(Writeable<PlanarImage>)new DilateFilter(
										(Writeable<PlanarImage>)new BallQualityAssuranceFilter(
												(Writeable<PlanarImage>)new ImageSink()))), 
												MedianFilterDescriptor.MEDIAN_MASK_SQUARE, 5), 0.0, 30.0, 250.0), rectangle) );		
		Thread thread = new Thread((ImageSource)source);
		thread.start();
	}
}

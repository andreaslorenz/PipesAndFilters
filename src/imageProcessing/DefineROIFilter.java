package imageProcessing;

import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import javax.media.jai.PlanarImage;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;
import itb04.sa.teamb.pipesfilters.filter.AbstractFilter;

//This filter defines the Region of Interest. The image is being
//cut with the coordinates from a user-defined  awt.rectangle.
public class DefineROIFilter extends AbstractFilter<PlanarImage, PlanarImage>{

	PlanarImage _image;
	Rectangle _rectangle;
	Writeable<PlanarImage> _output;
	
	public DefineROIFilter(Readable<PlanarImage> input, Rectangle rectangle)
			throws InvalidParameterException, StreamCorruptedException {
		super(input);
		
		_image = input.read();
		_rectangle = rectangle;
	}
	
	public DefineROIFilter(Writeable<PlanarImage> output, Rectangle rectangle)
			throws InvalidParameterException, StreamCorruptedException {
		super(output);
		
		_rectangle = rectangle;
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

	private PlanarImage process(){
		_image = PlanarImage.wrapRenderedImage((RenderedImage)_image.getAsBufferedImage
				(_rectangle, _image.getColorModel()));
		return _image;
	}
	@Override
	public void run() {
		
	}

}

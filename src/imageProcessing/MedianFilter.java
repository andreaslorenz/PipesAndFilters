package imageProcessing;

import java.awt.image.renderable.ParameterBlock;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterShape;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;
import itb04.sa.teamb.pipesfilters.filter.AbstractFilter;

//The median filter
public class MedianFilter extends AbstractFilter<PlanarImage, PlanarImage>{

	private MedianFilterShape _shape;
	private int _size;
	private PlanarImage _image;
	private Writeable<PlanarImage> _output;
	
	public MedianFilter(Readable<PlanarImage> input, MedianFilterShape shape, int size)
			throws InvalidParameterException, StreamCorruptedException {
		super(input);
		_image = input.read();
		_shape = shape;
		_size = size;
	}
	
	public MedianFilter(Writeable<PlanarImage> output, MedianFilterShape shape, int size)
			throws InvalidParameterException, StreamCorruptedException {
		super(output);
		
		_shape = shape;
		_size = size;
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
		ParameterBlock parameterBlock = new ParameterBlock();
		parameterBlock.addSource(_image);
		parameterBlock.add(_shape);
		parameterBlock.add(_size);
		return JAI.create("MedianFilter", parameterBlock);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

package imageProcessing;

import java.awt.image.renderable.ParameterBlock;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;
import itb04.sa.teamb.pipesfilters.filter.AbstractFilter;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

//The threshold filter
public class ThresholdFilter extends AbstractFilter<PlanarImage, PlanarImage> {
	
	Double _low;
	Double _high;
	Double _map;
	PlanarImage _image;
	Writeable<PlanarImage> _output;
	
	public ThresholdFilter(Readable<PlanarImage> input, Double low, Double high, Double map)
			throws InvalidParameterException, StreamCorruptedException {
		super(input);
		
		_low = low;
		_high = high;
		_map = map;
		
		_image = input.read();
	}
	
	public ThresholdFilter(Writeable<PlanarImage> output, Double low, Double high, Double map)
			throws InvalidParameterException, StreamCorruptedException {
		super(output);
		
		_low = low;
		_high = high;
		_map = map;
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
		double[] arrayLow = {_low};
		double[] arrayHigh = {_high};
		double[] arrayMap = {_map};
		
		ParameterBlock parameterBlock = new ParameterBlock();
		parameterBlock.addSource(_image);
		parameterBlock.add(arrayLow);
		parameterBlock.add(arrayHigh);
		parameterBlock.add(arrayMap);
		
		return JAI.create("threshold", parameterBlock);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}

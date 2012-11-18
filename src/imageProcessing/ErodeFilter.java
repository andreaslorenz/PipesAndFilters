package imageProcessing;

import java.awt.image.renderable.ParameterBlock;
import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;
import itb04.sa.teamb.pipesfilters.filter.AbstractFilter;

//The erode filter
public class ErodeFilter extends AbstractFilter<PlanarImage, PlanarImage>{

	private PlanarImage _image;
	private Writeable<PlanarImage> _output;
	
	public ErodeFilter(Readable<PlanarImage> input)
			throws InvalidParameterException, StreamCorruptedException {
		super(input);
		_image = input.read();
	}

	public ErodeFilter(Writeable<PlanarImage> output)
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

	private PlanarImage process(){
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
        parameterBlock.addSource(_image);
        parameterBlock.add(kernel);
        return JAI.create("erode", parameterBlock);
	}
	@Override
	public void run() {
		
	}
	

}

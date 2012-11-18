package imageProcessing;

import java.io.StreamCorruptedException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;

//The image source loads the picture from a defined path
public class ImageSource implements Readable<PlanarImage>, Writeable<PlanarImage>, Runnable{

	String _filename;
	Writeable<PlanarImage> _output;
	
	public ImageSource(String filename) {
		_filename = filename;
	}
	
	public ImageSource (String filename, Writeable<PlanarImage> output) throws StreamCorruptedException{
		_filename = filename;
		_output = output;
	}

	@Override
	public PlanarImage read() throws StreamCorruptedException {
		return process();
	}

	@Override
	public void write(PlanarImage value) throws StreamCorruptedException {
		_output.write(process());
	}
	
	public PlanarImage process(){
		PlanarImage image = JAI.create("fileload",_filename);
		return image;
	}

	@Override
	public void run() {
		try {
			_output.write(process());
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		}
	}
}

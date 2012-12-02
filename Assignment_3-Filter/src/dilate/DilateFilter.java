package dilate;

import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;

import com.sun.media.jai.widget.DisplayJAI;

/**
 * Dilatefilter saves the customized image
 */
public class DilateFilter extends DisplayJAI implements PropertyChangeListener {
	
	private PlanarImage _image;
	private String _path;
	private PropertyChangeSupport _support = new PropertyChangeSupport(this);	
	
	public DilateFilter(){
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		System.out.println("ROI addListener:" + listener.getClass());
		_support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		System.out.println("ROI removeListener:" + listener.getClass());
		_support.removePropertyChangeListener(listener);
	}
	
	private PlanarImage process() {
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

            return JAI.create("dilate", parameterBlock);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		_path = (String) evt.getNewValue();
		_image = (PlanarImage) evt.getOldValue();
		updateImage();
	}
	
	private void updateImage(){
		_image = process();
		this.setVisible(false);
		this.set(_image);
		this.setVisible(true);
		
		try {
		    // creates a Buffered Image to save this file
		    BufferedImage bi = _image.getAsBufferedImage();
		    File outputfile = new File("dilate.png");
		    ImageIO.write(bi, "jpg", outputfile);
		    _path = outputfile.getPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

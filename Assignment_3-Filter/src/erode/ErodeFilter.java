package erode;

import java.awt.image.renderable.ParameterBlock;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;

import com.sun.media.jai.widget.DisplayJAI;

public class ErodeFilter extends DisplayJAI implements PropertyChangeListener{
	
	private PropertyChangeSupport _support = new PropertyChangeSupport(this);
	private PlanarImage _image;
	private String _path;
	private int _loop;

	public ErodeFilter(){
		_loop = 1;
	}
	
	public int getLoop() {
		return _loop;
	}

	public void setLoop(int loop) {
		this._loop = loop;
		updateImage();
		_support.firePropertyChange("ERODE.loop", _image, _path);
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		System.out.println("ROI addListener:" + listener.getClass());
		_support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		System.out.println("ROI removeListener:" + listener.getClass());
		_support.removePropertyChangeListener(listener);
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
	public void propertyChange(PropertyChangeEvent evt) {
		_path = (String) evt.getNewValue();
		_image = (PlanarImage) evt.getOldValue();
		_image = process();
		updateImage();
		if(_support.hasListeners("_loop")){
			System.out.println("E _support true");
			_support.firePropertyChange("ERODE.update", _image, _path);
		}
	}
	
	private void updateImage(){
		this.setVisible(false);
		this.set(_image);
		this.setVisible(true);
	}

}

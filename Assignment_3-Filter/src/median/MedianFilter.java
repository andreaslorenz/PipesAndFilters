package median;

import java.awt.image.renderable.ParameterBlock;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;

import com.sun.media.jai.widget.DisplayJAI;

public class MedianFilter  extends DisplayJAI implements PropertyChangeListener {
	
	private MedianFilterShape _shape;
	private int _width;
	private PlanarImage _image;
	private String _path;
	
	private PropertyChangeSupport _support = new PropertyChangeSupport(this);
	
	public MedianFilter(){
		_shape = MedianFilterDescriptor.MEDIAN_MASK_SQUARE;
		_width = 5;
	}
	
	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		this._width = width;
		updateImage();
		_support.firePropertyChange("MEDIAN.width", _image,_path);
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
		ParameterBlock parameterBlock = new ParameterBlock();
		parameterBlock.addSource(_image);
		parameterBlock.add(_shape);
		parameterBlock.add(_width);
		return JAI.create("MedianFilter", parameterBlock);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		_path = (String) evt.getNewValue();
		_image = (PlanarImage) evt.getOldValue();
		updateImage();
		if(_support.hasListeners("_shape")){
			System.out.println("M _support true");
			_support.firePropertyChange("MEDIAN.update", _image, _path);
		}
	}
	
	private void updateImage(){
		_image = process();
		this.setVisible(false);
		this.set(_image);
		this.setVisible(true);
	}
	
}

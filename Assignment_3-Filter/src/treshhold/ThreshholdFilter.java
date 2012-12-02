package treshhold;

import java.awt.image.renderable.ParameterBlock;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import com.sun.media.jai.widget.DisplayJAI;

public class ThreshholdFilter extends DisplayJAI implements PropertyChangeListener{
	
	private Double _low = 0.0;
	private Double _high = 30.0;
	private Double _map = 250.0;
	PlanarImage _image;
	String _path;
	
	private PropertyChangeSupport _support = new PropertyChangeSupport(this);
	
	public ThreshholdFilter(){
		
	}
	
	public Double getLow() {
		return _low;
	}

	public void setLow(Double low) {
		this._low = low;
		updateImage();
		_support.firePropertyChange("TRESHHOLD.low", _image, _path);
	}

	public Double getHigh() {
		return _high;
	}

	public void setHigh(Double high) {
		this._high = high;
		updateImage();
		_support.firePropertyChange("TRESHHOLD.high", _image, _path);
	}

	public Double getMap() {
		return _map;
	}

	public void setMap(Double map) {
		this._map = map;
		updateImage();
		_support.firePropertyChange("TRESHHOLD.map", _image, _path);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		System.out.println("THRESSHOLD addListener:" + listener.getClass());
		_support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		System.out.println("THRESSHOLD removeListener:" + listener.getClass());
		_support.removePropertyChangeListener(listener);
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
	public void propertyChange(PropertyChangeEvent evt) {
		_path = (String) evt.getNewValue();
		_image = (PlanarImage) evt.getOldValue();
		updateImage();
		if(_support.hasListeners("_low")){
			System.out.println("TH _support true");
			_support.firePropertyChange("TRESHHOLD.update", _image, _path);
		}
	}
	
	private void updateImage(){
		_image = process();
		this.setVisible(false);
		this.set(_image);
		this.setVisible(true);
	}

}

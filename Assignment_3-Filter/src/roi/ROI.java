package roi;

import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import java.beans.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import com.sun.media.jai.widget.DisplayJAI;

public class ROI extends DisplayJAI implements PropertyChangeListener{
	
	//Gößenangaben fürs Rechteck
	private int _height;
	private int _width;
	private Rectangle _rectangle;
	
	private PlanarImage _image;
	String _path;
	
	private PropertyChangeSupport _support = new PropertyChangeSupport(this);
	
	public ROI(){
		this._height = 50;
		this._width = 420;
		_rectangle = new Rectangle(0,60, getWidth(), getHeight());
		this.setSize(_width, _height);		
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		System.out.println("h:" + height);
		this._height = height;
		_rectangle.setSize(_width, _height);
		updateImage();
		_support.firePropertyChange("ROI.height", _image, _path);
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		this._width = width;
		_rectangle.setSize(_width, _height);
		updateImage();
		_support.firePropertyChange("ROI.width", _image, _path);
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
		_image = PlanarImage.wrapRenderedImage((RenderedImage)_image.getAsBufferedImage
				(_rectangle, _image.getColorModel()));
		return _image;
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		_path = (String) evt.getNewValue();
		//_image = (PlanarImage) evt.getOldValue();
		updateImage();
		if(_support.hasListeners("_height")){
			System.out.println("ROI _support true");
			_support.firePropertyChange("ROI.update", _image, _path);
		}
	}
	
	private void updateImage(){
		_image = null;
		_image = JAI.create("fileload", _path);
		process();
		this.setVisible(false);
		this.set(_image);
		this.setVisible(true);
	}
}

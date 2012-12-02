package load;

import java.beans.*;
import java.io.Serializable;

import javax.media.jai.*;

import com.sun.media.jai.widget.DisplayJAI;

public class LoadPicture extends DisplayJAI implements PropertyChangeListener{
	
	private int _height;
	private int _width;
	private String _path;
	private PlanarImage _image;
	
	private PropertyChangeSupport _support = new PropertyChangeSupport(this);
	
	public LoadPicture(){
		this.setHeight(300);
		this.setWidth(300);
		this._path = "C:\\Users\\Andreas\\Downloads\\loetstellen.jpg";
		_image = JAI.create("fileload",_path);
		this.set(_image);
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		this._height = height;
		_support.firePropertyChange("LOAD.height", _image, _path);
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		this._width = width;
		_support.firePropertyChange("LOAD.width", _image, _path);
	}

	public String getPath() {
		return _path;
	}

	public void setPath(String path) {
		this._path = path;
		_image = JAI.create("fileload",path);
		System.out.println("LOAD EVENT P fired");
		this.setVisible(false);
		this.set(_image);
		this.setVisible(true);
		_support.firePropertyChange("LOAD.path", _image, _path);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		System.out.println("LOAD addListener:" + listener.getClass());
		_support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		System.out.println("LOAD removeListener:" + listener.getClass());
		_support.removePropertyChangeListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}
}

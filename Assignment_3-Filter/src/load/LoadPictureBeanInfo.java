package load;

import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class LoadPictureBeanInfo extends SimpleBeanInfo {
	
	private int _width;
	private int _height;
	private String _path;
	
	private static final Class beanClass = LoadPicture.class;
	
	//Getter and Setter for the properties
	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		this._width = width;
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		this._height = height;
	}

	public String getPath() {
		return _path;
	}

	public void setPath(String path) {
		this._path = path;
	}
	
	@Override
	public BeanDescriptor getBeanDescriptor(){
		return new BeanDescriptor(beanClass);
	}
	
	public PropertyDescriptor[] getPropertyDescriptors(){
		try{
			Class<LoadPicture> cls = LoadPicture.class;
			PropertyDescriptor pd = new PropertyDescriptor("width", cls);
			PropertyDescriptor pd1 = new PropertyDescriptor("height", cls);
			PropertyDescriptor pd2 = new PropertyDescriptor("path", cls);
			PropertyDescriptor pds[] = {pd, pd1, pd2};
			return pds;
		}catch(Exception e){
			throw new Error(e.toString());
		}
	}
}

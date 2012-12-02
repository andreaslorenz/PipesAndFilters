package roi;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

import load.LoadPicture;

public class ROIBeanInfo extends SimpleBeanInfo {
	
	private int _width;
	private int _height;
	
	private static final Class beanClass = ROI.class;

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
	
	@Override
	public BeanDescriptor getBeanDescriptor(){
		return new BeanDescriptor(beanClass);
	}
	
	public PropertyDescriptor[] getPropertyDescriptors(){
		try{
			Class<ROI> cls = ROI.class;
			PropertyDescriptor pd = new PropertyDescriptor("width", cls);
			PropertyDescriptor pd1 = new PropertyDescriptor("height", cls);
			PropertyDescriptor pds[] = {pd, pd1};
			return pds;
		}catch(Exception e){
			throw new Error(e.toString());
		}
	}

}

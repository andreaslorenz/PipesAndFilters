package median;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

import javax.media.jai.operator.MedianFilterShape;

import treshhold.ThreshholdFilter;

public class MedianFilterBeanInfo extends SimpleBeanInfo {

	private static final Class beanClass = MedianFilter.class;
	
	private int width;
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	public BeanDescriptor getBeanDescriptor(){
		return new BeanDescriptor(beanClass);
	}
	
	public PropertyDescriptor[] getPropertyDescriptors(){
		try{
			Class<MedianFilter> cls = MedianFilter.class;
			PropertyDescriptor pd = new PropertyDescriptor("width", cls);
			PropertyDescriptor pds[] = {pd};
			return pds;
		}catch(Exception e){
			throw new Error(e.toString());
		}
	}
	
}

package erode;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

import median.MedianFilter;

public class ErodeFilterBeanInfo extends SimpleBeanInfo {

	private static final Class beanClass = ErodeFilter.class;
	
	private int loop;

	public int getLoop() {
		return loop;
	}

	public void setLoop(int loop) {
		this.loop = loop;
	}

	@Override
	public BeanDescriptor getBeanDescriptor(){
		return new BeanDescriptor(beanClass);
	}
	
	public PropertyDescriptor[] getPropertyDescriptors(){
		try{
			Class<ErodeFilter> cls = ErodeFilter.class;
			PropertyDescriptor pd = new PropertyDescriptor("loop", cls);
			PropertyDescriptor pds[] = {pd};
			return pds;
		}catch(Exception e){
			throw new Error(e.toString());
		}
	}
	
}

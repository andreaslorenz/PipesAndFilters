package treshhold;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;


public class ThreshholdFilterBeanInfo extends SimpleBeanInfo{
	
	private static final Class beanClass = ThreshholdFilter.class;
	
	private Double low;
	private Double high;
	private Double map;
	
	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getMap() {
		return map;
	}

	public void setMap(Double map) {
		this.map = map;
	}

	@Override
	public BeanDescriptor getBeanDescriptor(){
		return new BeanDescriptor(beanClass);
	}
	
	public PropertyDescriptor[] getPropertyDescriptors(){
		try{
			Class<ThreshholdFilter> cls = ThreshholdFilter.class;
			PropertyDescriptor pd = new PropertyDescriptor("low", cls);
			PropertyDescriptor pd1 = new PropertyDescriptor("high", cls);
			PropertyDescriptor pd2 = new PropertyDescriptor("map", cls);
			PropertyDescriptor pds[] = {pd, pd1, pd2};
			return pds;
		}catch(Exception e){
			throw new Error(e.toString());
		}
	}
}

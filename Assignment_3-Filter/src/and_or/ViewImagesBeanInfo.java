package and_or;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

import erode.ErodeFilter;

public class ViewImagesBeanInfo extends SimpleBeanInfo {

	private static final Class beanClass = ViewImages.class;
	
	private String path1;
	private String path2;
	private String action;

	public String getPath1() {
		return path1;
	}

	public void setPath1(String path1) {
		this.path1 = path1;
	}

	public String getPath2() {
		return path2;
	}

	public void setPath2(String path2) {
		this.path2 = path2;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public BeanDescriptor getBeanDescriptor(){
		return new BeanDescriptor(beanClass);
	}
	
	public PropertyDescriptor[] getPropertyDescriptors(){
		try{
			Class<ViewImages> cls = ViewImages.class;
			PropertyDescriptor pd = new PropertyDescriptor("action", cls);
			PropertyDescriptor pd2 = new PropertyDescriptor("path1", cls);
			PropertyDescriptor pd3 = new PropertyDescriptor("path2", cls);
			PropertyDescriptor pds[] = {pd, pd2, pd3};
			return pds;
		}catch(Exception e){
			throw new Error(e.toString());
		}
	}
	
}

package and_or;

import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import com.sun.media.jai.widget.DisplayJAI;

/**
 * ViewImages is used to connect two Pictures with OR or AND
 */
public class ViewImages extends DisplayJAI implements PropertyChangeListener{
	
	private PropertyChangeSupport _support = new PropertyChangeSupport(this);
	
	private PlanarImage _image;
	private String _path1 = "C:\\Users\\Andreas\\Downloads\\loetstellen.jpg";
	private String _path2 = "C:\\Users\\Andreas\\Downloads\\apple.jpg";
	private String _action = "and";
	private String _path;
			
	public ViewImages(){
		this.setSize(400, 400);
	}
	
	public String getPath1() {
		return _path1;
	}
	
	public void setPath1(String _path1) {
		this._path1 = _path1;
		updateImage();
		_support.firePropertyChange("AND-OR.path1", _image, _path);
	}
	
	public String getPath2() {
		return _path2;
	}
	
	public void setPath2(String _path2) {
		this._path2 = _path2;
		updateImage();
		_support.firePropertyChange("AND-OR.path2", _image, _path);
	}
	
	public String getAction() {
		return _action;
	}

	public void setAction(String action) {
		this._action = action;
		updateImage();
		_support.firePropertyChange("AND-OR.action", _image, _path);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener){
		System.out.println("LOAD addListener:" + listener.getClass());
		_support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		System.out.println("LOAD removeListener:" + listener.getClass());
		_support.removePropertyChangeListener(listener);
	}
	
	//divides between AND or OR
	public void process(){
		if(_action.equalsIgnoreCase("and")){
			connectWithAnd();
		}else if(_action.equalsIgnoreCase("or")){
			connectWithOr();
		}
	}
	
	//Startup of the programm so does not need to listen to events
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}
	
	public void connectWithAnd() {
		if (!_path1.equals("") && !_path2.equals("")) {
			// Set up the parameter block and add the two source images to it.
			ParameterBlock pb = new ParameterBlock();
			pb.addSource((PlanarImage) JAI.create("fileload", _path1));
			pb.addSource((PlanarImage) JAI.create("fileload", _path2));
			
			_image = (PlanarImage) JAI.create("and", pb, null);
			try {
			    // creates a Buffered Image to save this file
			    BufferedImage bi = _image.getAsBufferedImage();
			    File outputfile = new File("saved.png");
			    ImageIO.write(bi, "jpg", outputfile);
			    _path = outputfile.getPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void connectWithOr() {
		
		if (!_path1.equals("") && !_path2.equals("")) {
			// Set up the parameter block and add the two source images to it.
			ParameterBlock pb = new ParameterBlock();
			pb.addSource((PlanarImage) JAI.create("fileload", _path1));
			pb.addSource((PlanarImage) JAI.create("fileload", _path2));
			
			_image = (PlanarImage) JAI.create("or", pb, null);
			try {
			    // creates a Buffered Image to save this file
			    BufferedImage bi = _image.getAsBufferedImage();
			    File outputfile = new File("saved.png");
			    ImageIO.write(bi, "jpg", outputfile);
			    _path = outputfile.getPath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void updateImage(){
		//update Image just needs to be executed, if both file paths are set
		if (!_path1.equals("") && !_path2.equals("")) {
			process();
			this.setVisible(false);
			this.set(_image);
			this.setVisible(true);
		}
	}
}

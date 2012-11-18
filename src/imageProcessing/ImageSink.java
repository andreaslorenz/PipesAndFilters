package imageProcessing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.StreamCorruptedException;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;

import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

//This sink converts the edited picture into a DisplayJAI
//and shows it in a JFrame
public class ImageSink implements Readable<PlanarImage>, Writeable<PlanarImage>, Runnable{

	Readable<PlanarImage> _readable;
	Writeable<PlanarImage> _writeable;
	PlanarImage _image;
	
	public ImageSink(Readable<PlanarImage> readable){
		_readable = readable;
	}
	
	public ImageSink(){
	}
	
	@Override
	public PlanarImage read() throws StreamCorruptedException {
		return process();
	}

	@Override
	public void write(PlanarImage value) throws StreamCorruptedException {
		_image = value;
		process();
	}
	
	private PlanarImage process(){
		//Convert into DisplayJAI 
		DisplayJAI dj = new DisplayJAI(_image);
		JFrame frame = new JFrame();		
		frame.setTitle("DisplayJAI: loetstellen.jpg");
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new JScrollPane(dj),BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,200); // adjust the frame size.
		frame.setVisible(true); // show the frame.
		return _image;
	}
	@Override
	public void run() {
		try {
			_image = _readable.read();
			read();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		}
	}


}

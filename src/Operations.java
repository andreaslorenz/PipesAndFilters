import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;
import javax.media.jai.ROI;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

public class Operations {
	
	public static void main(String[] args) {
		Operations main = new Operations();
		PlanarImage image = main.loadPicture("loetstellen.jpg");
		image = main.defineROI(image, 0,60, 420, 50);
		image = main.threshold(image, 0, 30, 255);
		image = main.median(image, MedianFilterDescriptor.MEDIAN_MASK_SQUARE, 5);
		image = main.erode(image);
		image = main.dilate(image);
		image = main.centroid(image);
		main.showPicture(main.convertToDisplayJAI(image));
		
	}

	private PlanarImage loadPicture(String filename){
		PlanarImage image = JAI.create("fileload", "/Users/Shady/Downloads/" + filename);
		return image;
		
	}
	
	private DisplayJAI convertToDisplayJAI(PlanarImage image){
		DisplayJAI dj = new DisplayJAI(image);
		return dj;
	}
	
	private void showPicture(DisplayJAI dj){
		JFrame frame = new JFrame();
		frame.setTitle("DisplayJAI: lena512.jpg");
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new JScrollPane(dj),BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,200); // adjust the frame size.
		frame.setVisible(true); // show the frame.
	}
	
	private PlanarImage defineROI(PlanarImage image, int x, int y, int width, int height){
		
		Rectangle rectangle = new Rectangle(x, y, width, height);
		image = PlanarImage.wrapRenderedImage((RenderedImage)image.getAsBufferedImage
				(rectangle, image.getColorModel()));
		return image;
		
	}
	
	private PlanarImage threshold(PlanarImage image, double low, double high, double map){
		double[] arrayLow = {low};
		double[] arrayHigh = {high};
		double[] arrayMap = {map};
		
		ParameterBlock parameterBlock = new ParameterBlock();
		parameterBlock.addSource(image);
		parameterBlock.add(arrayLow);
		parameterBlock.add(arrayHigh);
		parameterBlock.add(arrayMap);
		return JAI.create("threshold", parameterBlock);
	}
	
	private PlanarImage median(PlanarImage image, MedianFilterShape shape, int size){
		
		ParameterBlock parameterBlock = new ParameterBlock();
		parameterBlock.addSource(image);
		parameterBlock.add(shape);
		parameterBlock.add(size);
		return JAI.create("MedianFilter", parameterBlock);
	}
	
	private PlanarImage erode(PlanarImage image){
        float[] kernelData = {
                0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,};
        
        KernelJAI kernel = new KernelJAI(11, 11, kernelData);

        ParameterBlock parameterBlock = new ParameterBlock();
        parameterBlock.addSource(image);
        parameterBlock.add(kernel);
        return JAI.create("erode", parameterBlock);
	}
	
	private PlanarImage dilate(PlanarImage image){
        float[] kernelData = {
                0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,};

            KernelJAI kernel = new KernelJAI(11, 11, kernelData);

            ParameterBlock parameterBlock = new ParameterBlock();
            parameterBlock.addSource(image);
            parameterBlock.add(kernel);

            return JAI.create("dilate", parameterBlock);
	}
	
	private PlanarImage centroid(PlanarImage image){
	       ParameterBlock pb = new ParameterBlock();
           ROI roi = new ROI(JAI.create("bandselect", image, new int[] { 0 }));
           pb.add(roi);
           pb.addSource(image); 
           return JAI.create("centroid", pb, RenderingHints.KEY_ANTIALIASING);
	}
	
}



package pipes;

/**
 * Unter Pullpipe has two mehtods
 * flush()
 * write( in input)
 * 
 * 
 * @author Ramon Lopez, Andreas Lorenz
 */
public interface IPushPipe<in> extends IPipe {

	public void write(in input);
	public void flush();
}

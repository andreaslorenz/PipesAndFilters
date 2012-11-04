package pipes;

/**
 * 
 * @author Stefan Laesser, Dennis Kurszlaukis ITB5_2
 *
 * @param <in> the input type
 * @param <out> the output type
 */
public interface IPushPipe<in> extends IPipe {
	/**
	 * Writes the input to the next filter
	 * @param input
	 */
	public void write(in input);
	
	/**
	 * signals the next filter to flush the data
	 */
	public void flush();
}

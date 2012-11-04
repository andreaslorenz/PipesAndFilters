package pipes;

/**
 * 
 * @author Stefan Laesser, Dennis Kurszlaukis ITB5_2
 *
 * @param <in> the input type
 * @param <out> the output type
 */
public interface IPullPipe<out> extends IPipe {
	/**
	 * Start the reading process at the next filter
	 * @return
	 */
	public out read();
	
	/**
	 * Passes through the is Available state
	 * @return the is available value of the filter
	 */
	public boolean isAvailable();
}

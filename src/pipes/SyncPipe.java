/**
 * 
 */
package pipes;

import filters.IPullFilter;
import filters.IPushFilter;

/**
 * 
 * @author Stefan Laesser, Dennis Kurszlaukis ITB5_2
 *
 * Synchronized Pipes were used between two active filters.
 *
 * @param <in> the input type
 * @param <out> the output type
 */
public abstract class SyncPipe<in, out> implements IPushPipe<in>, IPullPipe<out> {
	
	protected IPushFilter<in, ?> _pushFilter;
	protected IPullFilter<?, out> _pullFilter;
	
	public SyncPipe(IPushFilter<in, ?> pushFilter, IPullFilter<?, out> pullFilter) {
		_pullFilter = pullFilter;
		_pushFilter = pushFilter;
	}
	
	@Override
	public out read() {
		return _pullFilter.read();
	}

	@Override
	public void write(in input) {
		_pushFilter.write(input);
	}
}

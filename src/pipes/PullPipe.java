/**
 * 
 */
package pipes;

import filters.IPullFilter;

/**
 * Implements interface IPullpipe
 * Overrides read and isAvailable
 * knows the next _pullFiletr
 * 
 * 
 * @author Ramon Lopez, Andreas Lorenz
 */
public class PullPipe<out> implements IPullPipe<out> {
	protected IPullFilter<?, out> _pullFilter;
	
	public PullPipe(IPullFilter<?, out> pullFilter) {
		_pullFilter = pullFilter;
	}
	
	@Override
	public out read() {
		return _pullFilter.read();
	}

	@Override
	public boolean isAvailable() {
		return _pullFilter.isAvailable();
	}
}

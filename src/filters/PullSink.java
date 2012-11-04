package filters;

import pipes.IPullPipe;

/**
 * The datasink for Pullfilters
 * 
 * 
 * @author Ramon Lopez, Andreas Lorenz
 */
public abstract class PullSink<in, out> implements IPullFilter<in, out> {
	protected out _output;
	protected boolean _isAvailable;
	protected IPullPipe<in> _pullPipe;
	
	public PullSink(out output, IPullPipe<in> pipe) {
		_output = output;
		_pullPipe = pipe;
		_isAvailable = true;
	}
	
	@Override
	public boolean isAvailable() {
		return _isAvailable;
	}
}

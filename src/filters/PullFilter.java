/**
 * 
 */
package filters;

import pipes.IPullPipe;

/**
 * 
 * @author Stefan Laesser, Dennis Kurszlaukis ITB5_2
 *
 * @param <in> the input type
 * @param <out> the output type
 */
public abstract class PullFilter<in, out> implements IPullFilter<in, out> {
	protected out _output;
	protected IPullPipe<in> _pullPipe;
	protected boolean _isAvailable;
	
	public PullFilter(IPullPipe<in> pipe) {
		_pullPipe = pipe;
		_isAvailable = false;
	}
	
	@Override
	public out read() {
		boolean finished = false;
		
		do {
			finished = process(_pullPipe.read());
			//if isAvailable is false, break here - nothing will follow
			if((_isAvailable = _pullPipe.isAvailable()) == false) {
				flushData();
				break;
			}
		} while(!finished);
		
		out tempOut = _output;
		
		clear();
		
		return tempOut;
	}
	
	@Override
	public boolean isAvailable() {
		return _isAvailable;
	}
	
	/**
	 * This method is called by the flush method if there is the need of
	 * flushing all data, even unfinished ones. 
	 */
	protected abstract void flushData();
	
	/**
	 * This method is called by the write method. The logic of the filter should be
	 * coded in this method.
	 * @param input the received data
	 * @return true if the currently received data were enough to push them on, or false otherwise
	 */
	protected abstract boolean process(in input);
}

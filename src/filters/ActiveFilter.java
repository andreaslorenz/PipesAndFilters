package filters;

import pipes.PullPipe;

/**
 * Active Filter implements Runnable and IFilter
 * Knows the previous pipe and flushes the data if no input
 * (output of the previous pipe) is no longe available
 * 
 * 
 * @author Ramon Lopez, Andreas Lorenz
 */
public abstract class ActiveFilter<in, out> implements IFilter, Runnable {
	protected boolean _isAvailable;
	protected out _output;
	protected in _input;
	protected PullPipe<in> _pullPipe;
	
	public ActiveFilter(PullPipe<in> pullPipe) {
		_pullPipe = pullPipe;
		_isAvailable = true;
	}

	@Override
	public void run() {
		do {
			
			boolean finished = false;
			
			do {
				finished = process(_pullPipe.read());
				
				//if nothing more isAvailable stop here and flush the data
				if((_isAvailable = _pullPipe.isAvailable()) == false) {
					flush();
					break;
				}
				
			} while(!finished);
			
		} while(_isAvailable);
	}
	
	protected abstract void flush();
	protected abstract boolean process(in input);
}

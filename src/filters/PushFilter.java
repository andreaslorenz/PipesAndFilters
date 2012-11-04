/**
 * 
 */
package filters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pipes.IPushPipe;

/**
 * 
 * @author Stefan Laesser, Dennis Kurszlaukis ITB5_2
 *
 * @param <in> the input type
 * @param <out> the output type
 */
public abstract class PushFilter<in, out> implements IPushFilter<in, out> {
	protected out _output;
	protected List<IPushPipe<out>> _pushPipes;
	
	public PushFilter(IPushPipe<out> pushPipe) {
		_pushPipes = new ArrayList<IPushPipe<out>>();
		_pushPipes.add(pushPipe);
	}
	
	@Override
	public void write(in input) {		
		if(process(input)) {
			for(IPushPipe<out> pipe : _pushPipes) {
				pipe.write(_output);
			}
			clear();
		}
	}
	
	@Override
	public void flush() {
		flushData();
		
		for(IPushPipe<out> pipe : _pushPipes) {
			pipe.write(_output);
			pipe.flush();
		}
	}
	
	@Override
	public void addPushPipe(IPushPipe<out> pipe) {
		_pushPipes.add(pipe);
	}
	
	@Override
	public void removePushPipe(IPushPipe<out> pipe) {
		_pushPipes.remove(pipe);
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

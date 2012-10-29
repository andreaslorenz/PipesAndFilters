package pipes;

import filters.PushFilter;

public interface IPushPipe<in, out> extends IPipe {
	
	public PushFilter pushFilter;
	public void write(in input);
	
	
	
}

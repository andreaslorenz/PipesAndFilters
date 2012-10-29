package pipes;

import filters.PushFilter;

public interface IPushPipe<in, out> extends IPipe {
	
	PushFilter <out, in> pushFilter;

}

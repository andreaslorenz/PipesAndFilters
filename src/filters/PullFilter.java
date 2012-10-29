package filters;

import pipes.IPullPipe;

public abstract class PullFilter<out, in> implements IFilter {

	boolean outputFinished;
	out outPut;
	IPullPipe<in, out> pullPipe;
	
	public out read(){
		
		return null;
	}
	
	public void process(in input){
		
		
	}
	
}

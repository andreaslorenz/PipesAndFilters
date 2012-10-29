package filters;

import pipes.IPushPipe;

public abstract class PushFilter<out, in> implements IFilter{

	boolean outputFinished;
	out output;
	IPushPipe pushPipe;
	
	public void write(in input){
		
	}
	
	public void process(in input){
		
	}
}

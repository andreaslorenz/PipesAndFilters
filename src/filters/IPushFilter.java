package filters;

import pipes.IPushPipe;

/**
 * Interface IPushFilter
 * two methods:
 * write(in input)
 * flush();
 * 
 * 
 * @author Ramon Lopez, Andreas Lorenz
 */
public interface IPushFilter<in, out> extends IFilter {

	public void write(in input);
	public void flush();
}

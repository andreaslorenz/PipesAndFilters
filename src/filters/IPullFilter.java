package filters;

/**
 * Interface IPullfilter
 * two methods:
 * read():out
 * isAvailable(): boolean
 * 
 * 
 * @author Ramon Lopez, Andreas Lorenz
 */
public interface IPullFilter<in, out> extends IFilter {	
	
	public out read();
	public boolean isAvailable();
}

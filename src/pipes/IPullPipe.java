package pipes;

/**
 * Unter Pullpipe has two mehtods
 * isAvailable:
 * read() :out
 * 
 * 
 * @author Ramon Lopez, Andreas Lorenz
 */
public interface IPullPipe<out> extends IPipe {
	
	public out read();
	public boolean isAvailable();
}

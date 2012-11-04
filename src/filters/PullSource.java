package filters;

/**
 * Overrides isAvailable
 * 
 * 
 * @author Ramon Lopez, Andreas Lorenz
 */
public abstract class PullSource<in, out> implements IPullFilter<in, out> {

	protected boolean _isAvailable;
	
	public PullSource() {
		_isAvailable = true;
	}
	
	@Override
	public boolean isAvailable() {
		return _isAvailable;
	}
}

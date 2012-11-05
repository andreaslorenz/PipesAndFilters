package itb04.sa.teamb.pipesfilters;

import java.io.StreamCorruptedException;

public interface Readable<T>  {
	public T read() throws StreamCorruptedException;
}

package itb04.sa.teamb.pipesfilters.Adapter;

import java.io.StreamCorruptedException;

import itb04.sa.teamb.pipesfilters.Writeable;

public class StdOutAdapter implements Writeable<Object> {

	public void write(Object value) throws StreamCorruptedException {
		if (value != null){
			System.out.println(value.toString()); 
		}else {
		    System.out.println("NULL passed to " + StdOutAdapter.class.getName());
        }
	}

}

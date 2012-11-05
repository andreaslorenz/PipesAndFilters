package itb04.sa.teamb.pipesfilters.Adapter;

import java.io.StreamCorruptedException;

import itb04.sa.teamb.pipesfilters.Writeable;


public class StdOutPageAdapter extends StdOutAdapter{

    @Override
    public void write(Object value) throws StreamCorruptedException {
            super.write(value);
            System.out.println("--------------page---------------");
        if (value == null ) System.out.println("################### END OF STREAM ###################");
    }
}

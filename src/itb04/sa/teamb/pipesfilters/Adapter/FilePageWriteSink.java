package itb04.sa.teamb.pipesfilters.Adapter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;
import itb04.sa.teamb.pipesfilters.data.Page;

public class FilePageWriteSink implements Writeable<Page>, Runnable{
    int counter = 0;
    private String m_Dir = null;
    private String m_FilePrefix = null;
    private static final String m_FileSuffix = ".txt";
    private Readable<Page> m_input = null;
    
    
    
    
    public FilePageWriteSink(String dir, String filePrefix) {
        super();
        // TODO Automatisch erstellter Konstruktoren-Stub
        m_Dir = dir;
        m_FilePrefix = filePrefix;
    }
    
    public FilePageWriteSink(String dir, String filePrefix, Readable<Page> input) {
        super();
        // TODO Automatisch erstellter Konstruktoren-Stub
        m_Dir = dir;
        m_FilePrefix = filePrefix;
        m_input = input;
    }


    public void write(Page value) throws StreamCorruptedException {
        try {
            if (value != null) {
                FileWriter fw = new FileWriter(getNewFileName());
                fw.write(value.toString());
                fw.close();
            }
        } catch (IOException e) {
            // TODO Automatisch erstellter Catch-Block
            e.printStackTrace();
            throw new StreamCorruptedException(e.getMessage());
        }
    }
    
    
    private String getNewFileName() {
        String filename = m_Dir + m_FilePrefix + counter + m_FileSuffix;
        counter++;
        return filename;
    }

    public void run() {
        Page entity = null;
        try {
            do {
                entity = m_input.read();
                write(entity);
            } while (entity != null);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

}

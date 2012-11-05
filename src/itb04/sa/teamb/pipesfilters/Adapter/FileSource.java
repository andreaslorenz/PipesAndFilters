package itb04.sa.teamb.pipesfilters.Adapter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamCorruptedException;



import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;

public class FileSource implements Readable<Character>, Runnable {
    
    
    private BufferedReader m_FI;
    private boolean m_StreamAlive = true;
    private String m_Filename; 
    private char[] m_Buffer = null;
    private static final int m_DefaultBufLen = 100; 
    private int m_CurrentOffset = 0;
    private int m_FillLevel = 0;
    private Writeable<Character> m_output = null; 
    
    public FileSource(String filename, int bufLen) {
        super();
        m_Filename = filename;
        bufLen = Math.max(bufLen, m_DefaultBufLen);
        m_Buffer = new char[bufLen];
    }
    
    public FileSource(String filename, int bufLen, Writeable<Character> successor) {
        m_Filename = filename;
        bufLen = Math.max(bufLen, m_DefaultBufLen);
        m_Buffer = new char[bufLen];
        m_output = successor; 
    }

    private BufferedReader getFI() throws FileNotFoundException {
        if (m_FI == null) {
            m_FI = new BufferedReader(new FileReader(m_Filename));
        }
        return m_FI;
    }
    
    private Character getNextCharacter() throws FileNotFoundException, IOException {
        Character c = null;
        if (m_StreamAlive) {
             // is there still anything in the buffer?
            if (m_FillLevel == 0) {
                // if not, refill the buffer
                m_FillLevel = fillBuffer();
            }
             if (m_CurrentOffset < m_FillLevel) {
                 c = m_Buffer[m_CurrentOffset];
                   
             }else {
                 m_FillLevel =  fillBuffer();
                 if (m_FillLevel > 0) {
                     m_CurrentOffset = 0;
                     c = m_Buffer[m_CurrentOffset];
                 }else {
                     m_StreamAlive = false;
                 }
             }
             m_CurrentOffset++;
        }
        return c;
    }
    
    private int fillBuffer() throws FileNotFoundException, IOException {
        if (m_StreamAlive) {
            int charCount = getFI().read(m_Buffer); 
            //m_StreamAlive = (charCount <= m_Buffer.length);
            return charCount;
        }else {
            return 0;
        }
    }


    public Character read() throws StreamCorruptedException {
        try {
            Character c = getNextCharacter();
            /*if (c != null && c.equals('\n')){
                System.out.println("GOT CRLF");
            }else {
                System.out.println("got character: '" + c + "'");
            }*/
            return c;//getNextCharacter();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }

    public void run() {
        Character entity = null;
        try {
            do {
                entity = read();
                m_output.write(entity);
            } while (entity != null);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
    }

}

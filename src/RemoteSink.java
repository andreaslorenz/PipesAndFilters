import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;
import itb04.sa.teamb.pipesfilters.Adapter.FilePageWriteSink;
import itb04.sa.teamb.pipesfilters.Adapter.FileSource;
import itb04.sa.teamb.pipesfilters.data.Line;
import itb04.sa.teamb.pipesfilters.data.Word;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.DublicationFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.LineFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.PageFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.ReplaceFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.RightAlignLineFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.WordFilter;
import itb04.sa.teamb.pipesfilters.pipes.BufferedSyncPipe;
import itb04.sa.teamb.pipesfilters.pipes.RemoteIOable;
import itb04.sa.teamb.pipesfilters.pipes.RemotePipe;
import itb04.sa.teamb.pipesfilters.pipes.RemotePipeProxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.LinkedList;

public class RemoteSink {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int bufSize = 50;
        int maxLineLenght = 60;
        // ignore crlf 
        char[] ignoring = "\n".toCharArray();
        Character[] dublicat = {' '};      
        java.io.File f = new java.io.File("./pages");
        if (!f.exists()) f.mkdir();
        
        try {
            registerRemotePipe("pipe", new RemotePipe(512));
            
            //first register the remote pipe
            
            Writeable sink = new FilePageWriteSink("./pages/", "alice"); 
            
            RemotePipeProxy<Word> remotepipe = new RemotePipeProxy<Word>("localhost", "pipe");
            
            
            LineFilter lineFilter       = new LineFilter (maxLineLenght, (Readable<Word>)remotepipe);
            Writeable<Line> alignFilter = new RightAlignLineFilter (maxLineLenght,(Readable<Line>) lineFilter);
            PageFilter pageFilter       = new PageFilter (15,(Readable<Line>) alignFilter, sink);       
        
            getInThread(pageFilter).start();
        
        } catch (Exception e) {
            // TODO Automatisch erstellter Catch-Block
            e.printStackTrace();
        }
    }
    
    private static Thread getInThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(r.getClass().getName());
        return t;
    }
    
    private static void registerRemotePipe(String name, RemoteIOable pipe) throws RemoteException, MalformedURLException {
        Naming.rebind(name, pipe);  
    }

}

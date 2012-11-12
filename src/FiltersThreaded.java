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

import java.util.Collections;
import java.util.LinkedList;

public class FiltersThreaded {

    public static void main(String[] args) {
        
        int bufSize = 50;
        int maxLineLenght = 60;
        java.io.File f = new java.io.File("./pages");
        if (!f.exists()) f.mkdir();
        
        // create source & sink
        FileSource fsource = new FileSource("./alice30.txt", 512);
        Writeable sink = new FilePageWriteSink("./pages/", "alice"); 
        
        // create 2 synchronizing pipes
        // .. <-- wordFilter <--> wordPipe <--> LineFilter <--> linePipe <--> PageFilter --> ... 
        BufferedSyncPipe<Word> wordPipe = new BufferedSyncPipe<Word>(bufSize);
        BufferedSyncPipe<Line> LinePipe = new BufferedSyncPipe<Line>(bufSize);

        // create the filters and connect them
        /*
         * source <-- ReplaceFilter <-- DublicationFilter <-- WordFilter <--> 
         *     wordPipe <--> LineFilter <--> linePipe <--> PageFilter --> sink
         */
        PageFilter pageFilter                   = new PageFilter                    (15, LinePipe, sink);
        Writeable<Line> alignFilter             = new RightAlignLineFilter          (maxLineLenght, (Writeable<Line>)LinePipe);
        LineFilter lineFilter                   = new LineFilter                    (maxLineLenght, wordPipe, alignFilter );
        Readable<Character> replaceFilter       = new ReplaceFilter<Character>      (getReplacingList(), ' ', fsource);
        DublicationFilter<Character> dubFilter  = new DublicationFilter<Character>  (getDuplicateList(), replaceFilter);
        WordFilter wordFilter                   = new WordFilter                    (dubFilter, wordPipe);
        
        // start pageFilter, lineFilter & wordFilter in seperate threads
        getInThread(pageFilter).start();
        getInThread(lineFilter).start();
        getInThread(wordFilter).start();
    }
    
    private static LinkedList<Character> getDuplicateList(){
        LinkedList<Character> dubplicates = new LinkedList<Character>();
        Character[] dublicat = {' '};  
        
        Collections.addAll(dubplicates, dublicat);
        return dubplicates;
    }
    
    public static LinkedList<Character> getReplacingList(){
        LinkedList<Character> replacingList = new LinkedList<Character>();
        char[] ignoring = "\n".toCharArray();
    
        for(char c: ignoring) {
            replacingList.add(c);
        }
        char c = Character.LINE_SEPARATOR;
        replacingList.add(c);
        
        return replacingList;
    }
    
    private static Thread getInThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(r.getClass().getName());
        return t;
    }

}

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Writeable;
import itb04.sa.teamb.pipesfilters.Adapter.FilePageWriteSink;
import itb04.sa.teamb.pipesfilters.Adapter.FileSource;
import itb04.sa.teamb.pipesfilters.data.Line;
import itb04.sa.teamb.pipesfilters.data.Page;
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

public class FiltersPull {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int bufSize = 50;
        int maxLineLenght = 60;

        /*
         * source <-- ReplaceFilter <-- DublicationFilter <-- WordFilter 
         *     <-- LineFilter <-- linePipe <-- PageFilter <-- sink
         */
        
        java.io.File f = new java.io.File("./pages");
        if (!f.exists()) f.mkdir();
        
        Readable<Character> fsource = new FileSource("./alice30.txt", 512);
        Readable<Character>             replFilter  = new ReplaceFilter<Character>      (getReplacingList(), ' ', fsource);
        Readable<Character>             dubFilter   = new DublicationFilter<Character>  (getDuplicateList(), replFilter);
        Readable<Word>                  wordFilter  = new WordFilter                    (dubFilter);
        Readable<Line>                  lineFilter  = new LineFilter                    (maxLineLenght, wordFilter );
        Readable<Line>                  alignFilter = new RightAlignLineFilter          (maxLineLenght, lineFilter);
        Readable<Page>                  pageFilter  = new PageFilter                    (15, alignFilter);
        Runnable                        sink        = new FilePageWriteSink             ("./pages/", "alice", pageFilter);
        
        getInThread(sink).start();
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

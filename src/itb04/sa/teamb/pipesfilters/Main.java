package itb04.sa.teamb.pipesfilters;

import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.omg.PortableServer.AdapterActivator;

import itb04.sa.teamb.pipesfilters.Adapter.CharSourceAdapter;
import itb04.sa.teamb.pipesfilters.Adapter.FilePageWriteSink;
import itb04.sa.teamb.pipesfilters.Adapter.FileSource;
import itb04.sa.teamb.pipesfilters.Adapter.StdOutAdapter;
import itb04.sa.teamb.pipesfilters.Adapter.StdOutPageAdapter;
import itb04.sa.teamb.pipesfilters.data.Line;
import itb04.sa.teamb.pipesfilters.data.Page;
import itb04.sa.teamb.pipesfilters.data.Word;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.CenterAlignLineFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.DublicationFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.LineFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.PageFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.RightAlignLineFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.ReplaceFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.WordFilter;
import itb04.sa.teamb.pipesfilters.pipes.BufferedSyncPipe;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        int bufSize = 50;
        int maxLineLenght = 60;
        // ignore crlf 
        char[] ignoring = "\n".toCharArray();
        Character[] dublicat = {' '};      
        
        LinkedList<Character> replacingList = new LinkedList<Character>();
        LinkedList<Character> dubplicates = new LinkedList<Character>();
        
        Collections.addAll(dubplicates, dublicat);
        
        for(char c: ignoring) {
            replacingList.add(c);
        }
        
        FileSource fsource = new FileSource("/tmp/alice30.txt", 512);
        //Readable<Character> fsource = new CharSourceAdapter();
        //Writeable sink = new StdOutPageAdapter();
        Writeable sink = new FilePageWriteSink("/tmp/pages/", "alice"); 
        
        BufferedSyncPipe<Word> wordPipe = new BufferedSyncPipe<Word>(bufSize);
        BufferedSyncPipe<Line> LinePipe = new BufferedSyncPipe<Line>(bufSize);
        //BufferedSyncPipe pagePipe = new BufferedSyncPipe<Line>(bufSize);
        
        //Writeable<Line> alignFilter = new CenterAlignLineFilter(maxLineLenght, (Writeable<Line>)LinePipe);
        
        PageFilter pageFilter                   = new PageFilter                    (15, LinePipe, sink);
        Writeable<Line> alignFilter             = new RightAlignLineFilter          (maxLineLenght, (Writeable<Line>)LinePipe);
        LineFilter lineFilter                   = new LineFilter                    (maxLineLenght, wordPipe, alignFilter );
        
        Readable<Character> replaceFilter       = new ReplaceFilter<Character>      (replacingList, ' ', fsource);
        DublicationFilter<Character> dubFilter  = new DublicationFilter<Character>  (dubplicates, replaceFilter);
        WordFilter wordFilter                   = new WordFilter                    (dubFilter, wordPipe);
        
        // set replacing-entities in replace-filter 
        char c = Character.LINE_SEPARATOR;
        ((ReplaceFilter<Character>)replaceFilter).addReplaceEntity(c);
        
        getInThread(pageFilter).start();
        getInThread(lineFilter).start();
        getInThread(wordFilter).start();
    }
    
    private static Thread getInThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(r.getClass().getName());
        return t;
    }
}

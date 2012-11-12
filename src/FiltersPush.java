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

import java.util.Collections;
import java.util.LinkedList;

public class FiltersPush {

    public static void main(String[] args) {
        int bufSize = 50;
        int maxLineLenght = 60;
        java.io.File f = new java.io.File("./pages");
        if (!f.exists()) f.mkdir();
        /*
         * source --> ReplaceFilter --> DublicationFilter --> WordFilter 
         *     --> LineFilter --> AlignFilter --> PageFilter --> sink
         */
        
        // !!! read bottom - up !!!
        Writeable<Page>                  sink        = new FilePageWriteSink             ("./pages/", "alice");
        Writeable<Line>                  pageFilter  = new PageFilter                    (15, sink);
        Writeable<Line>                  alignFilter = new RightAlignLineFilter          (maxLineLenght, pageFilter);
        Writeable<Word>                  lineFilter  = new LineFilter                    (maxLineLenght, alignFilter);
        Writeable<Character>             wordFilter  = new WordFilter                    (lineFilter);
        Writeable<Character>             dubFilter   = new DublicationFilter<Character>  (getDuplicateList(), wordFilter);
        Writeable<Character>             replFilter  = new ReplaceFilter<Character>      (getReplacingList(), ' ', dubFilter);
        Runnable                         fsource = new FileSource("./alice30.txt", 512, replFilter);
        
        getInThread(fsource).start();
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

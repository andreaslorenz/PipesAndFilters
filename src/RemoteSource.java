import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.Adapter.FileSource;
import itb04.sa.teamb.pipesfilters.data.Word;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.DublicationFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.ReplaceFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.WordFilter;
import itb04.sa.teamb.pipesfilters.pipes.RemotePipeProxy;

public class RemoteSource {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        
        // ignore crlf 
        char[] ignoring = "\n".toCharArray();
        Character[] dublicat = {' '};      
        
        System.setSecurityManager(new SecurityManager());
        
        LinkedList<Character> replacingList = new LinkedList<Character>();
        LinkedList<Character> dubplicates = new LinkedList<Character>();
        
        Collections.addAll(dubplicates, dublicat);
        
        for(char c: ignoring) {
            replacingList.add(c);
        }
        
        FileSource fsource = new FileSource("./alice30.txt", 512);

        Readable<Character> replaceFilter       = new ReplaceFilter<Character>      (replacingList, ' ', fsource);
        // set replacing-entities in replace-filter 
        char c = Character.LINE_SEPARATOR;
        ((ReplaceFilter<Character>)replaceFilter).addReplaceEntity(c);
 
        
        DublicationFilter<Character> dubFilter  = new DublicationFilter<Character>  (dubplicates, replaceFilter);
        
        WordFilter wordFilter;
        try {
            wordFilter = new WordFilter(dubFilter, new RemotePipeProxy<Word>("123.123.123.1", "pipe"));
            long start = GregorianCalendar.getInstance().getTimeInMillis();
            wordFilter.start = start;
            getInThread(wordFilter).start();
        } catch (InvalidParameterException e) {
            // TODO Automatisch erstellter Catch-Block
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            // TODO Automatisch erstellter Catch-Block
            e.printStackTrace();
        }
        
 
 
    }
    
    private static Thread getInThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(r.getClass().getName());
        return t;
    }
}

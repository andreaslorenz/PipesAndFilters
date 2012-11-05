package itb04.sa.teamb.pipesfilters;

import itb04.sa.teamb.pipesfilters.Adapter.CharSourceAdapter;
import itb04.sa.teamb.pipesfilters.Adapter.StdOutAdapter;
import itb04.sa.teamb.pipesfilters.data.Word;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.LineFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.WordFilter;
import itb04.sa.teamb.pipesfilters.pipes.BufferedSyncPipe;

public class MainWordFilter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CharSourceAdapter source = new CharSourceAdapter();
		Writeable sink = new StdOutAdapter();
		
		
		WordFilter wf = new WordFilter(source, sink);
		//LineFilter lf = new LineFilter(maxLineLength, pipe, sink);
				
		Thread tWord = new Thread(wf);
		//Thread tLine = new Thread(lf);
		
		System.out.println("Testprogram for WordFilter");
		System.out.println("--------------------------");
		System.out.println("Text for testing is: '" + source.getText() + "'");
		System.out.println("This text will be passed to the filter, character by character");
		System.out.println("now starting the filter ... ");
		System.out.println();
		
		tWord.start();
		

	}

}

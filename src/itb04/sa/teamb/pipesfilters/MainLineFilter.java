package itb04.sa.teamb.pipesfilters;

import itb04.sa.teamb.pipesfilters.Adapter.CharSourceAdapter;
import itb04.sa.teamb.pipesfilters.Adapter.StdOutAdapter;
import itb04.sa.teamb.pipesfilters.Adapter.WordSourceAdapter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.LineFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.WordFilter;

public class MainLineFilter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WordSourceAdapter source = new WordSourceAdapter();
		Writeable sink = new StdOutAdapter();
		
		
		int maxCharsPerLine = 10;
		LineFilter lf = new LineFilter(maxCharsPerLine, source, sink);
		//LineFilter lf = new LineFilter(maxLineLength, pipe, sink);
				
		Thread tWord = new Thread(lf);
		//Thread tLine = new Thread(lf);
		
		System.out.println("Testprogram for LineFilter");
		System.out.println("--------------------------");
		System.out.println("Text for testing is: '" + source.getText() + "'");
		System.out.println("This text will be passed to the filter, word by word");
		System.out.println("now starting the filter ... ");
		System.out.println();
		
		tWord.start();
	}

}

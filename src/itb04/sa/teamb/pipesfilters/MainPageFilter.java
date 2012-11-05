package itb04.sa.teamb.pipesfilters;

import itb04.sa.teamb.pipesfilters.Adapter.LineSourceAdapter;
import itb04.sa.teamb.pipesfilters.Adapter.StdOutAdapter;
import itb04.sa.teamb.pipesfilters.Adapter.StdOutPageAdapter;
import itb04.sa.teamb.pipesfilters.Adapter.WordSourceAdapter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.LineFilter;
import itb04.sa.teamb.pipesfilters.filter.TextFilter.PageFilter;

public class MainPageFilter  {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LineSourceAdapter source = new LineSourceAdapter();
		Writeable sink = new StdOutPageAdapter();
		
		
		int maxLinesPerPage = 5;
		PageFilter lf = new PageFilter(maxLinesPerPage, source, sink);
	
		Thread tWord = new Thread(lf);
		
		System.out.println("Testprogram for PageFilter");
		System.out.println("--------------------------");
		System.out.println("The test-programm will pass you 20 lines like  'this is line#1' ...");
		System.out.println("This text will be passed to the filter, line by line");
		System.out.println("max lines per page = " + maxLinesPerPage);
		System.out.println("now starting the filter ... ");
		System.out.println();
		
		tWord.start();
	}

}

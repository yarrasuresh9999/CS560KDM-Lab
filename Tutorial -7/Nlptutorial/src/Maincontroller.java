

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;








import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;
import opennlp.uima.tokenize.Tokenizer;
import rita.wordnet.RiWordnet;
/**
 * Servlet implementation class Maincontroller
 */
@WebServlet("/Maincontroller")
public class Maincontroller extends HttpServlet {
	//private static final RiWordnet wordnet = new RiWordnet(null);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Maincontroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("dsdfsdfsdfsd");
		String sentence1=request.getParameter("sentence");
		System.out.println("sentence:"+sentence1);

	    try {
	    	StemmingandNGramPartition sngp=new StemmingandNGramPartition();
	    	String sentence0 = "Tom is playing basketball with other kids";
	    	System.out.println("Before Processing:");
	    	System.out.println(sentence1);
	    	System.out.println("==============================================================");
	    	System.out.println("After Processing:");
	    	
	    	String answer  = sngp.stem(sentence1);

	    	System.out.println(answer);
	    	
	    	System.out.println();
	    	
	        System.out.println("Before NGram:");
	        //    ClassLoader classLoader=getClass().getClassLoader();
	            TokenizerModel tm = new TokenizerModel(new FileInputStream("en-token.bin"));
	            TokenizerME wordBreaker = new TokenizerME(tm);
	            POSModel pm = new POSModel(new FileInputStream("en-pos-maxent.bin"));
	            POSTaggerME posme = new POSTaggerME(pm);
	            InputStream modelIn = new FileInputStream("en-chunker.bin");
	           ChunkerModel chunkerModel = new ChunkerModel(modelIn);
	            ChunkerME chunkerME = new ChunkerME(chunkerModel);
	            //this is your sentence
	            String sentence = "Barack Hussein Obama II is the 44th and current President of the United States, and the first African American to hold the office.";
	            System.out.println(sentence);
	            System.out.println("==============================================================");
	        	System.out.println("After Processing:");
	            //words is the tokenized sentence
	            String[] words = wordBreaker.tokenize(sentence1);
	            //posTags are the parts of speech of every word in the sentence (The chunker needs this info of course)
	            String[] posTags = posme.tag(words);
	            //chunks are the start end "spans" indices to the chunks in the words array
	            Span[] chunks = chunkerME.chunkAsSpans(words, posTags);
	            //chunkStrings are the actual chunks
	            String[] chunkStrings = Span.spansToStrings(chunks, words);
	            for (int i = 0; i < chunks.length; i++) {
	              if (chunks[i].getType().equals("NP")) {
	                System.out.println("NP: \n\t" + chunkStrings[i]);
	                String[] split = chunkStrings[i].split(" ");

	                List<String> ngrams = sngp.ngram(Arrays.asList(split), 2, " ");
	                System.out.println("ngrams:");
	                for (String gram : ngrams) {
	                  System.out.println("\t" + gram);
	                }
	    	    	request.setAttribute("ngrams",ngrams);
	              }
	            }


	    	
	    	request.setAttribute("answer", answer);
	    	request.setAttribute("chunkStrings", chunkStrings);

	    	request.getRequestDispatcher("output.jsp").forward(request, response);

	    } catch (IOException e) {
	    }
}
}

package com;


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
	    	
	    	request.setAttribute("answer", answer);
	    	request.getRequestDispatcher("jsp/output.jsp").forward(request, response);

	    } catch (IOException e) {
	    }
}
}

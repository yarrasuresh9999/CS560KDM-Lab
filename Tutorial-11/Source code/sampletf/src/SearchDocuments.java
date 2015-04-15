import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.store.FSDirectory;

public class SearchDocuments {

	public static void main(String[] args) throws Exception
	{
		IndexReader r = IndexReader.open(FSDirectory.open(new File("index")));
		
		Scanner scanner = new Scanner(System.in);
		double nf[] = new double[r.maxDoc()];					// an array to store document norms
		Map<Integer, Map<String,Double>> documentTermMatrix = new HashMap<Integer, Map<String,Double>>(); // Document Term Matrix
		Map<String,Double> idf = new HashMap<String,Double>();  // map to store idf of terms
		
		TermEnum t = r.terms();
		
		System.out.print("Enter your choice to search \n 1. Using TF 2. Using TF/IDF");  
		int option = scanner.nextInt();   // providing choice to user; Whether to search with TF weights or TF/IDF weights
		
		switch(option)
		{
		case 1: System.out.println("building repository using TF.. ");  
				while(t.next())											//Building Document term matrix using TF weights
				{					
					Term te = new Term("contents", t.term().text());		
					TermDocs td = r.termDocs(te);						    // retrieves all the documents with a term
					
					while(td.next()) 
						{
							if(documentTermMatrix.containsKey(td.doc())) 	// if that document already exists in the document term matrix
								{
									documentTermMatrix.get(td.doc()).put(t.term().text(), (double)td.freq());
								}
							else											 // if that document doesn't exists in matrix, build new entry in map with that doc id
								{	
									Map<String,Double> termsInDocument = new HashMap<String,Double>();
									termsInDocument.put(t.term().text(), (double)td.freq());
									documentTermMatrix.put(td.doc(),termsInDocument);
								}
						 }
				}
				
				for(int k=1;k<r.maxDoc();k++) 
				{
			 		double maxFrequencyOfDocument = Collections.max(documentTermMatrix.get(k).values()); // finding max frequency in a document
			 		double totalTermFreq=0;
					for(String key : documentTermMatrix.get(k).keySet()) 
					{  
						double termFreq = documentTermMatrix.get(k).get(key)/maxFrequencyOfDocument;          // Calculating TF of a term in document 
						documentTermMatrix.get(k).put(key, termFreq);										  // Replacing term weight with it's TF in matrix
						totalTermFreq+=Math.pow(termFreq, 2);
					}
				  nf[k]=1/Math.pow(totalTermFreq, 0.5);                   	 // building document norms
				}
				break;
			
		case 2: System.out.println("building repository using TF/IDF.. "); 
				while(t.next())												  //Building Document term matrix using TF/IDF weights												
				{					
					Term te = new Term("contents", t.term().text());
					TermDocs td = r.termDocs(te);							  // retrieves all the documents with a term
					int NumOfDocHavingTerm=0;
					
					while(td.next()) 
						{	NumOfDocHavingTerm++;
								if(documentTermMatrix.containsKey(td.doc()))   // if that document already exists in the document term matrix
									{
										documentTermMatrix.get(td.doc()).put(t.term().text(), (double)td.freq());
									}
								else										    // if that document doesn't exists in matrix, build new entry in map with that doc id
									{	
										Map<String,Double> termsInDocument = new HashMap<String,Double>();
										termsInDocument.put(t.term().text(), (double)td.freq());
										documentTermMatrix.put(td.doc(),termsInDocument);
									}
						}	
					if(NumOfDocHavingTerm!=0) idf.put(t.term().text(), Math.log(r.maxDoc()/NumOfDocHavingTerm)); //Computing IDF of a term
				 }
			
				 for(int k=1;k<r.maxDoc();k++) 
				 {
					 double maxFrequencyOfDocument = Collections.max(documentTermMatrix.get(k).values()); // finding max frequency in a document
					 double normalizedTfIDF=0;
					 	for(String key : documentTermMatrix.get(k).keySet()) 
					 	{  
					 		double tfIdf=(documentTermMatrix.get(k).get(key)/maxFrequencyOfDocument)*(idf.get(key)); // Calculating TF*IDF of a term in document
					 		documentTermMatrix.get(k).put(key, tfIdf);			// Replacing term weight with it's new normalized weight
					 		normalizedTfIDF+=Math.pow(tfIdf, 2);
					 	}
					 nf[k]=1/Math.pow(normalizedTfIDF, 0.5);                    // building document norms
				 }
			break;
		 default: System.out.println("Not a valid input");
		 			System.exit(0);
		}
		
		Scanner scan = new Scanner(System.in);
		String query = "";
		System.out.print("query> ");
		while(!(query = scan.nextLine()).equals("quit"))
		{
			long startQueryTime = System.nanoTime();
			String[] words = query.split(" ");									// Splits words in the given query and stores in an array
			HashMap<Integer,Double> similarity = new HashMap<Integer,Double>(); // Map to store similarity of documents 
			
			for(String word : words)
			{
				TermDocs tdocs = r.termDocs(new Term("contents", word));		// Retrieves docs having that word
				while(tdocs.next())																				
				{   
					if(similarity.containsKey(tdocs.doc()))						 // if a document already exists in similarity map
						similarity.put(tdocs.doc(), similarity.get(tdocs.doc()) + documentTermMatrix.get(tdocs.doc()).get(word)); //increases similarity by adding new similarity
					else														 // if a document does'nt exists in similarity map
						similarity.put(tdocs.doc(), documentTermMatrix.get(tdocs.doc()).get(word));  // creates a entry in map with doc id as key and similarity as value
				}
			}
			
			if(similarity.size()==0) {											  // if similarity map is 0 i.e. no documents matched
				
				System.out.println("Your search -"+query+"- did not match any documents \n ");
				System.out.println("Suggestion \n Try different keywords");
			}
			
			for(Integer key : similarity.keySet())								   // Computes final similarity of documents
			{
				similarity.put(key, similarity.get(key)*nf[key]);
			}
			List<Entry<Integer, Double>> sortedResults = sortHashMapByValues(similarity); //sorting documents
			
			for(Map.Entry<Integer, Double> entry:sortedResults)							  // displays results
			{
				System.out.println(entry.getKey());
	        }
			
			System.out.println("About "+sortedResults.size()+" results in "+ (double)(System.nanoTime()-startQueryTime)/1000000000+" seconds");
			System.out.print("Next query> ");
		}
		scanner.close();
		scan.close();
		System.out.println("Thanks for using the search engine");
	}
	
	public static List<Entry<Integer, Double>> sortHashMapByValues(HashMap<Integer, Double> passedMap) // code to sort results
	{
		
        Set<Entry<Integer, Double>> set = passedMap.entrySet();
        List<Entry<Integer, Double>> list = new ArrayList<Entry<Integer, Double>>(set);
       
        Collections.sort( list, new Comparator<Map.Entry<Integer, Double>>()
        		{
            		public int compare( Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2 )
            			{
            				return (o2.getValue()).compareTo( o1.getValue() );
            			}
        		} 
        );
      return list;
	}
}
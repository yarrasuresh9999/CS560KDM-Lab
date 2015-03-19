import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class Maincontroller
 */
//@WebServlet("/Maincontroller")
public class Maincontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Maincontroller() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		// TODO Auto-generated method stub
		String sentence=request.getParameter("sentence");
		System.out.println("sentence"+sentence);
		try {
			JSONParser parser = new JSONParser();
			 String data;
            data = "text=Dylan was born in Duluth. ";
            data="text="+sentence;
request.setAttribute("sentence", sentence);
            HttpURLConnection con = (HttpURLConnection) new URL("https://gate.d5.mpi-inf.mpg.de/aida/service/disambiguate").openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(data.getBytes("UTF-8"));

            // Get the inputstream
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            // .. and print it
            String tmp;
            String tmp1=null;
            while((tmp = reader.readLine()) != null) {
                System.out.println(tmp);
                request.setAttribute("tmp",(String)tmp);
                Object obj = parser.parse(tmp);
                JSONObject jsonObject = (JSONObject) obj;
                String originalFileName = (String) jsonObject.get("originalFileName");
                String originalText = (String) jsonObject.get("originalText");
                String overallTime = (String) jsonObject.get("overallTime");
                JSONArray mentions = (JSONArray) jsonObject.get("mentions");
              
                
                for(int i=0;i<mentions.size();i++)
                {
                //	System.out.println(mentions.get(i));
                	Object obj2 = parser.parse(mentions.get(i).toString());
                    JSONObject jsonObject2 = (JSONObject) obj2;
                    JSONArray allEntities = (JSONArray) jsonObject2.get("allEntities");
                    for(int j=0;j<allEntities.size();j++)
                    {
                    	Object obj3 = parser.parse(allEntities.get(j).toString());
                    	JSONObject jsonObject3 = (JSONObject) obj3;
                    	String kdId = (String) jsonObject3.get("kbIdentifier");
                    	String disambiguationScore = (String) jsonObject3.get("disambiguationScore");
                    	System.out.println("KbID: "+kdId + "===="+"score: "+disambiguationScore);
                        String kd1="KbID: "+kdId + "===="+"score: "+disambiguationScore;
                        if(j==0)
                        request.setAttribute("kd1", kd1);
                        else if(kd1!=null)
                            request.setAttribute("kd2", kd1);
                    }
                    
                }
                
                
           /*     Iterator<JSONArray> iterator2 = mentions.iterator();
                while (iterator2.hasNext()) {
        			
                	JSONArray tempArray =  (JSONArray)iterator2.next();
                	
                	for(int i=0;i<tempArray.size();i++)
                	{
                		System.out.println(tempArray.get(i));
                	}
        		}*/
                
              /*  JSONArray msg = (JSONArray) jsonObject.get("allEntities");
                Iterator<String> iterator = msg.iterator();
        		while (iterator.hasNext()) {
        			System.out.println("Entities: "+iterator.next());
        		}*/
         
                
             //  System.out.println(mentions);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String[] sentences=null;
		//request.setAttribute("sentences", sentences);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("jsp/output.jsp");
        dispatcher.forward(request, response);
	}

}

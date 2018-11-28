

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.ufc.insightlab.ror.entities.ResultQuery;
import br.ufc.insightlab.ror.entities.ResultQuerySet;
import br.ufc.insightlab.ror.implementations.OntopROR;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	OntopROR ror = new OntopROR("schema file", "mapping file");

    	String query = loadQueryFromFile("src/main/resources/query.txt");
    	ResultQuerySet results = ror.runQuery(query);
    	
    	for(ResultQuery result : results){
    		System.out.println(result);
    	}
    }
    
    private static String loadQueryFromFile(String path){
    	BufferedReader reader;
    	String query = "";
    	
    	try {
			reader = new BufferedReader(
					new FileReader(path) );
			
			String line = reader.readLine();
	    	
			while(line!=null){
	    		query += line+"\n";
	    		line = reader.readLine();
	    	}
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return query;
    	
    }
}

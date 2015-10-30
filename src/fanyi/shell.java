    package fanyi;  
      
    import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
      
    public class shell {  
        public static void main(String args[]) throws MalformedContentNameStringException, ConfigurationException, IOException {  
            Process process = null;  
            List<String> processList = new ArrayList<String>();  
            System.out.println("请输入要查询的单词");
            Scanner sc = new Scanner(System.in);
        		while(sc.hasNext()){
        			
        		
                process = Runtime.getRuntime().exec("ccncat ccnx:/fanyi/"+sc.next());  
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));  
                String rline = "";  
                while ((rline = input.readLine()) != null) {  
                    processList.add(rline);  
                }  
                input.close();  
              
      
            for (String line : processList) {  
          //      System.out.println(line);  
                JSONObject json= JSONObject.fromObject(JSONObject.fromObject(JSONObject.fromObject(line).get("retData")).get("dict_result"));
            //    System.out.println(json);
                JsonConfig jsonConfig = new JsonConfig();

                jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                String showMessage = "";
                JSONArray jsonA = JSONArray.fromObject(json.get("symbols"), jsonConfig);
                for(int i=0; i< jsonA.size(); i++){
                    JSONObject mark = (JSONObject)jsonA.get(i);
                    showMessage += mark.getString("ph_am");
                    System.out.println("查询单词为："+json.get("word_name"));
                    System.out.println("美音标为："+mark.get("ph_am"));
                    System.out.println("英音标为："+mark.get("ph_en"));
                    //System.out.println(mark);
                    JSONArray fanyiR = JSONArray.fromObject(mark.getString("parts"), jsonConfig);
                    for(int j=0; j< fanyiR.size(); j++){
                    	JSONObject dire = (JSONObject)fanyiR.get(j);
                    	System.out.print("\n词性："+dire.get("part")+" 翻译：");
                    	JSONArray result = JSONArray.fromObject(dire.getString("means"), jsonConfig);
                    	for(int k=0; k< result.size(); k++){
                    		System.out.print(result.get(k));
                    	}
                    }
                  
                    }

    			System.out.println("请输入要查询的单词");
               

                    
                
                
            }
            
        } 
    }  
        
    }
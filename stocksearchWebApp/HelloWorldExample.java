import java.io.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HelloWorldExample extends HttpServlet {
	  
/**
*		ERR_MALFORMED_STOCK = "Stock name is malformed"
		ERR_EMPTY_STOCK ="Stock is empty"
		ERR_PARSING_ERROR ="Stock data can't be parsed"
		ERR_EMPTY_NEWS_FEED ="Stock news feed is empty"    
		ERR_GENERAL_SYSTEM_ERROR ="Unknown error has occurred"   s	
**/

// from http://yohandfblog.blogspot.com/2010/01/string-replaceall-method-for-java-13.html
	public static String replaceAll(String source, String toReplace, String replacement) 
	{
			int idx = source.lastIndexOf( toReplace );
			if ( idx != -1 )
			{
				StringBuffer ret = new StringBuffer( source );
				ret.replace( idx, idx+toReplace.length(), replacement );
				while((idx=source.lastIndexOf(toReplace, idx-1)) != -1 )
				{
					ret.replace( idx, idx+toReplace.length(), replacement );
				}

				source = ret.toString();
			}

			return source;
	}

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{
	try{
		String urlstream = "";
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String company = request.getParameter("symbol");
		

		if(company == null || company == ""){
			out.println(errorResponse("ERR_EMPTY_STOCK"));
			return ;
		}
		URL urldemo = null;
		InputStream urlStream =null;
		try {
			urldemo = new URL("http://default-environment-qmv3qmez2p.elasticbeanstalk.com/?company="+company);
			URLConnection urlConnection = urldemo.openConnection();
			urlConnection.setAllowUserInteraction(false);
			urlStream = urldemo.openStream();
        
		} 
		catch (MalformedURLException e) {
			out.println(errorResponse("ERR_MALFORMED_STOCK"));
			return ;
		}catch(IOException ioe){
			out.println(errorResponse("ERR_MALFORMED_STOCK"));
			return;
		}
        
      	DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder DB = null;
            Document doc = null;
			
		try
            {
				DB = DBF.newDocumentBuilder(); 
				doc = DB.parse(urlStream);
            }
            catch (SAXException e)//add needed exceptions
				{
					out.println(errorResponse("ERR_PARSING_ERROR"));
					return;
				}
				
				
				 String ChangeType = "N/A";
				 String  Change  = "N/A";
				 String  ChangeInPercent = "N/A";
				 String  LastTradePriceOnly = "N/A";
				 String  PreviousClose = "N/A";
				 String  DaysLow = "N/A";
				 String  DaysHigh = "N/A";
				 String  Open = "N/A";
				 String  YearLow = "N/A";
				 String  YearHigh = "N/A";
				 String  Bid = "N/A";
				 String  Volume = "N/A";
				 String  Ask= "N/A";
				 String  AverageDailyVolume = "N/A";
				 String  OneYearTargetPrice = "N/A";
				 String  MarketCapitalization = "N/A";
				String Title =  "N/A";
				String Link =  "N/A";

				
				NodeList names = doc.getElementsByTagName("Name");
                Node nameNode = names.item(0); // only one exists
                Element name_element = (Element) nameNode;
                String name = name_element.getFirstChild().getNodeValue();
				//out.println(name);
               
				NodeList symbols = doc.getElementsByTagName("symbol");
                Node symbolNode = symbols.item(0); // only one exists
                Element symbol_element = (Element) symbolNode;
                String symbol = symbol_element.getFirstChild().getNodeValue(); // alternative for getFirstChild().getNodeValue();
				
				NodeList nList = doc.getElementsByTagName("Quote");
				
				Node nNode = nList.item(0);
				Element eElement = (Element) nNode;
				
   				 ChangeType = eElement.getElementsByTagName("ChangeType").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("ChangeType").item(0).getFirstChild().getNodeValue();
				 Change = eElement.getElementsByTagName("Change").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("Change").item(0).getFirstChild().getNodeValue();
				 ChangeInPercent = eElement.getElementsByTagName("ChangeInPercent").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("ChangeInPercent").item(0).getFirstChild().getNodeValue();
				 LastTradePriceOnly = eElement.getElementsByTagName("LastTradePriceOnly").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("LastTradePriceOnly").item(0).getFirstChild().getNodeValue();
				 PreviousClose= eElement.getElementsByTagName("PreviousClose").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("PreviousClose").item(0).getFirstChild().getNodeValue();
				 DaysLow =  eElement.getElementsByTagName("DaysLow").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("DaysLow").item(0).getFirstChild().getNodeValue();
				 DaysHigh = eElement.getElementsByTagName("DaysHigh").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("DaysHigh").item(0).getFirstChild().getNodeValue();
				 Open = eElement.getElementsByTagName("Open").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("Open").item(0).getFirstChild().getNodeValue();
				 YearLow = eElement.getElementsByTagName("YearLow").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("YearLow").item(0).getFirstChild().getNodeValue();
				 YearHigh = eElement.getElementsByTagName("YearHigh").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("YearHigh").item(0).getFirstChild().getNodeValue();
				 Bid = eElement.getElementsByTagName("Bid").item(0).getFirstChild() ==null? "": eElement.getElementsByTagName("Bid").item(0).getFirstChild().getNodeValue();
				 Volume = eElement.getElementsByTagName("Volume").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("Volume").item(0).getFirstChild().getNodeValue();
				 Ask = eElement.getElementsByTagName("Ask").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("Ask").item(0).getFirstChild().getNodeValue();
				 AverageDailyVolume = eElement.getElementsByTagName("AverageDailyVolume").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("AverageDailyVolume").item(0).getFirstChild().getNodeValue();
				 OneYearTargetPrice = eElement.getElementsByTagName("OneYearTargetPrice").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("OneYearTargetPrice").item(0).getFirstChild().getNodeValue();
				 MarketCapitalization = eElement.getElementsByTagName("MarketCapitalization").item(0).getFirstChild()==null? "": eElement.getElementsByTagName("MarketCapitalization").item(0).getFirstChild().getNodeValue();
				
				//out.println(ChangeType+Change+ChangeInPercent+LastTradePriceOnly+PreviousClose+DaysLow+DaysHigh+Open+YearLow+YearHigh+Bid+Volume+Ask+AverageDailyVolume+OneYearTargetPrice+MarketCapitalization);

				
				NodeList ImageList = doc.getElementsByTagName("StockChartImageURL");
				Node imageNodes = ImageList.item(0);
				Element imageListeElements = (Element) imageNodes;
                String imageName = imageListeElements.getFirstChild().getNodeValue();

 
				NodeList NewList = doc.getElementsByTagName("News");
				Node NewListNodes = NewList.item(0);
				Element NewListeElements = (Element) NewListNodes;
				String outPutArray =  "";
				outPutArray = outPutArray+ "\"News\":{" ;
				outPutArray = outPutArray+ "\"Item\":[";
				
				NodeList ItemLists = NewListeElements.getElementsByTagName("Item");

				if(ItemLists.getLength() ==0 || (ItemLists.getLength() ==1 && ((Element)ItemLists.item(0)).getElementsByTagName("Link").item(0).getFirstChild() ==null)){
					outPutArray += errorResponse("ERR_EMPTY_NEWS_FEED") ;
					
				}else{
				
					for(int temp = 0; temp < ItemLists.getLength(); temp++) {
							Node ItemListsNodes = ItemLists.item(temp);
				 
								//out.println("\n\n Current Element :" + ItemListsNodes.getNodeName()); 
								Element ItemListseElement = (Element) ItemListsNodes;
								Title = ItemListseElement.getElementsByTagName("Title").item(0).getFirstChild().getNodeValue();
								Link  = ItemListseElement.getElementsByTagName("Link").item(0).getFirstChild().getNodeValue();
								
								outPutArray = outPutArray+ "{";
								outPutArray = outPutArray+ "\"Link\": \""+Link+"\"," ;
								//Title= replaceAll(Title, "\"", "\\\"");
								Title= replaceAll(Title, "\\", "\\\\");
								Title= replaceAll(Title, "\"", "\\\"");

								outPutArray = outPutArray+ "\"Title\": \"" + Title+ "\"" ;
								
								if(temp+1 == ItemLists.getLength()){
									outPutArray = outPutArray+ "}";
								}
								else{
									outPutArray = outPutArray+ "},";
								}
								
					}
				}
 
 
 
		
				String outPut = "{";
				outPut = outPut+"\"result\":{";
				outPut = outPut+ "\"Name\":\""+name+"\"," ;
				outPut = outPut+ "\"symbol\":\""+symbol+"\"," ;
				outPut = outPut+"\"Quote\":{";
				outPut = outPut+"\"ChangeType\":\""+ChangeType+"\"," ;
				outPut = outPut+"\"Change\":\""+Change+"\"," ;
				outPut = outPut+"\"ChangeInPercent\":\""+ChangeInPercent+"\"," ;
				outPut = outPut+"\"LastTradePriceOnly\":\""+LastTradePriceOnly+"\"," ;
				outPut = outPut+"\"PreviousClose\":\""+PreviousClose+"\"," ;
				outPut = outPut+"\"DaysLow\":\""+DaysLow+"\"," ;
				outPut = outPut+"\"DaysHigh\":\""+DaysHigh+"\"," ;
				outPut = outPut+"\"Open\":\""+Open+"\"," ;
				outPut = outPut+"\"YearLow\":\""+YearLow+"\"," ;
				outPut = outPut+"\"YearHigh\":\""+YearHigh+"\"," ;
				outPut = outPut+"\"Bid\":\""+Bid+"\"," ;
				outPut = outPut+"\"Volume\":\""+Volume+"\"," ;
				outPut = outPut+"\"Ask\":\""+Ask+"\"," ;
				outPut = outPut+"\"OneYearTargetPrice\":\""+OneYearTargetPrice+"\"," ;
				outPut = outPut+"\"AverageDailyVolume\":\""+AverageDailyVolume+"\"," ;
				outPut = outPut+"\"MarketCapitalization\":\""+MarketCapitalization+"\"";
				outPut = outPut+"},";
				outPut = outPut + outPutArray ;
				outPut = outPut+ "]";
				outPut = outPut+ "},";
				outPut = outPut+"\"StockChartImageURL\":\""+imageName.trim()+"\"" ;   
				outPut = outPut+ "}";
				outPut = outPut+ "}";		
				out.println(outPut);
	}catch(Throwable t){
		 response.setContentType("application/json");
		 PrintWriter out = response.getWriter();
		 t.printStackTrace();
		 out.println(errorResponse("ERR_GENERAL_SYSTEM_ERROR"));
		return ;
	}
			
					 
    }
	
	
	private String errorResponse(String stockError){
		String outPut = "{";
		outPut = outPut+"\"Error\":\""+stockError.toString()+"\"}";			
		return outPut;
	}

}



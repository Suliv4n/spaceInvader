package fr.licencepro.spaceinvader.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.res.Resources;
import android.util.Log;
import fr.licencepro.spaceinvader.Config;
import fr.licencepro.spaceinvader.object.AI;
import fr.licencepro.spaceinvader.object.AIGroup;
import fr.licencepro.spaceinvader.object.Direction;
import fr.licencepro.spaceinvader.object.PatternFactory;
import static fr.licencepro.spaceinvader.data.DataConfig.*;

public class DataManager {
	
	private DataManager(){}
	

	
	private static Document getDocument(String id, Resources resources, String xml)
	{
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = factory.newDocumentBuilder();
			InputStream is = resources.getAssets().open(Config.DATA+xml);
			doc = db.parse(is);
		} catch (IOException e) {
			Log.e("XML - IO",e.getMessage());
		} catch (SAXException e){
			Log.e("XML - SAX",e.getMessage());
		} catch (ParserConfigurationException e){
			Log.e("XML - Parse config", e.getMessage());
		}
		
		return doc;
	}
	
	public static AIGroup generateAIs(String id, boolean miror, Resources resources)
	{
		ArrayList<AI> ai = new ArrayList<AI>();
		
		Document doc = getDocument(id, resources, FILE_FORMATIONS);
		NodeList formations = doc.getElementsByTagName(NODE_FORMATION);
		
		for(int i = 0; i<formations.getLength(); i++)
		{
			Element e = (Element) formations.item(i);
			if(e.getAttribute("id").equals(id))
			{
				ai = getAllAIs(e, miror);
				break;
			}
		}
		
		AIGroup aiGroup = new AIGroup(ai);
		PatternFactory.createPatternForAIGroup(PatternFactory.DEFAULT, aiGroup);
		return aiGroup;
	}



	private static ArrayList<AI> getAllAIs(Element formation, boolean miror) {
		ArrayList<AI> ai = new ArrayList<AI>();
		
		NodeList aiList = formation.getElementsByTagName(NODE_AI);
		int width_grid = (int) ((float) Config.CAMERA_WIDTH/ (float) Config.GRID_HORIZONTAL);
		Log.d("DIVISION" ,width_grid+" DIVISION");
		for(int i=0; i<aiList.getLength(); i++)
		{
			Element e = (Element) aiList.item(i);
			int x = Integer.parseInt(e.getAttribute("x")) * width_grid + Config.ORIGIN_GRID_X;
			int y = Integer.parseInt(e.getAttribute("y")) * width_grid + Config.ORIGIN_GRID_Y;
			
			int health = e.hasAttribute("health") ? Integer.parseInt(e.getAttribute("health")) : Config.DEFAULT_AI_HEALTH;
			int speed = e.hasAttribute("speed") ? Integer.parseInt(e.getAttribute("speed")) : Config.DEFAULT_AI_SPEED;
			float ratio = e.hasAttribute("ratio") ? Float.parseFloat(e.getAttribute("ratio")) : 1f;
			
			Direction direction;
			if(miror)
			{
				direction = Direction.DOWN;
			}
			else
			{
				direction = Direction.UP;
			}
				
			ai.add(new AI(x, y, health, speed, direction, ratio));
		}
		
		return ai;
	}


}

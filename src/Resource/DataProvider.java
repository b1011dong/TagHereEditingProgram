package Resource;

import java.awt.Image;
import java.awt.Point;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import FloorPlan.Building;
import FloorPlanObject.*;

/**
 * Handle Data from server
 * @author DongKyu
 *
 */
public class DataProvider {
	
	/** For Singleton design pattern */
	private static DataProvider singleton = null;
	/** String for user id */
	private String id;
	/** For get list of building */
	private ArrayList<String> buildingsData;
	/** For get current building */
	private Building building;
	/** For get Object list */
	private ArrayList<FPObject> Objects;
	
	/** For editor */
	/** Type of current selected object */
	private ObjectType objectType;
	/** Type of current selected icon */
	private IconType iconType;
	
	/** Icon images */
	private ArrayList<Image> iconImages;
	
	private DataProvider() {
		objectType = ObjectType.LINE;
		buildingsData = new  ArrayList<String>();
		iconImages = new ArrayList<Image>();
		Objects = new ArrayList<FPObject>();
		
		ArrayList<String> iconNames = new ArrayList<String>();
		
		iconNames.add("src/Image/door_icon.png");
		iconNames.add("src/Image/elevator_icon.png");
		iconNames.add("src/Image/stairs_icon.png");
		iconNames.add("src/Image/toilet_icon.png");
		iconNames.add("src/Image/escalator_up_icon.png");
		iconNames.add("src/Image/escalator_down_icon.png");
		iconNames.add("src/Image/escalator_up_down_icon.png");
		iconNames.add("src/Image/tag_on_icon.png");
		iconNames.add("src/Image/tag_off_icon.png");
		
		for(int i = 0; i < IconType.values().length; i++) {
			File pathToFile = new File(iconNames.get(i));
			Image img = null;
			try {
				img = ImageIO.read(pathToFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			iconImages.add(img);
		}
		
		File pathToFile = new File(iconNames.get(7));
		Image img = null;
		try {
			img = ImageIO.read(pathToFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		iconImages.add(img);
		
		pathToFile = new File(iconNames.get(8));
		img = null;
		try {
			img = ImageIO.read(pathToFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		iconImages.add(img);
		
		buildingsData.add("aaa 5類");
		buildingsData.add("bbb 3類");
		buildingsData.add("ccc 7類");
		buildingsData.add("aaa 5類");
		buildingsData.add("bbb 3類");
		buildingsData.add("ccc 7類");
		buildingsData.add("aaa 5類");
		buildingsData.add("bbb 3類");
		buildingsData.add("ccc 7類");
		buildingsData.add("aaa 5類");
		buildingsData.add("bbb 3類");
		buildingsData.add("ccc 7類");
		buildingsData.add("aaa 5類");
		buildingsData.add("bbb 3類");
		buildingsData.add("ccc 7類");
	}

	/** Get Current Icon Image */
	public Image getIconImage(int index) {
		return iconImages.get(index);
	}

	/** For Singleton design.
	 * This function provide get instance of singleton */
	public static DataProvider getInstance()
	{ 
		if(singleton == null)
		{
			singleton  = new DataProvider();
		}
		return singleton;
	}

	public ArrayList<String> getBuildingsData() {
		return buildingsData;
	}

	public void setBuildingsData(ArrayList<String> buildingsData) {
		this.buildingsData = buildingsData;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ObjectType getCurrentObjectType() {
		return objectType;
	}

	public void setCurrentObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}
	
	public IconType getCurrentIconType() {
		return iconType;
	}

	public void setCurrentIconType(IconType iconType) {
		this.iconType = iconType;
	}

	public ArrayList<FPObject> getObjects() {
		return Objects;
	}

	public void setObjects(ArrayList<FPObject> objects) {
		Objects = objects;
	}
	
	public String loadFromServer(int id) {//throws Exception {
		try{
			URL url = new URL("http://155.230.118.252/webserver/documents/loadFromFloorPlan.php");
			URLConnection urlConnection;
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setDefaultUseCaches(false);
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("id").append("=").append(id);
			OutputStream outputSream = new BufferedOutputStream(urlConnection.getOutputStream());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputSream, "UTF-8"));
		    writer.write(buffer.toString());
		    writer.flush();
		    writer.close();
		    outputSream.close();
		    
		    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		    String inputLine;
		    StringBuffer response = new StringBuffer();
		    while((inputLine = in.readLine()) != null) {
		    	response.append(inputLine);
		    }
		    in.close();
		    
		    return response.toString();
		}
		catch(Exception e){
			return null;
		}
	}
	
	public void saveToServer(int id, ArrayList<FPObject> FPObjects) { //throws Exception {
		
		try{
			for(int i = 0; i < FPObjects.size(); i++) {
				URL url = new URL("http://155.230.118.252/webserver/documents/savetoFloorPlan.php");
				URLConnection urlConnection;
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setDoOutput(true);
				urlConnection.setDefaultUseCaches(false);
				urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
				
				StringBuffer buffer = new StringBuffer();
			
				/*System.out.println(FPObjects.get(i).getType().getInt());
				System.out.println(FPObjects.get(i).getStartPosition().x);
				System.out.println(FPObjects.get(i).getStartPosition().y);
				System.out.println(FPObjects.get(i).getEndPosition().x);
				System.out.println(FPObjects.get(i).getEndPosition().y);*/
				buffer = new StringBuffer();
				
				buffer.append("id").append("=").append(id).append("&");
				buffer.append("object_type").append("=").append(FPObjects.get(i).getType().getInt()).append("&");
				buffer.append("x1").append("=").append(FPObjects.get(i).getStartPosition().x).append("&");
				buffer.append("y1").append("=").append(FPObjects.get(i).getStartPosition().y).append("&");
				buffer.append("x2").append("=").append(FPObjects.get(i).getEndPosition().x).append("&");
				buffer.append("y2").append("=").append(FPObjects.get(i).getEndPosition().y).append("&");
				if(FPObjects.get(i).getType() == ObjectType.ICON) {
					buffer.append("icon_type").append("=").append(((Icon)FPObjects.get(i)).getIconType().getInt()).append("&");
				}
				else if(FPObjects.get(i).getType() == ObjectType.TAG) {
					buffer.append("icon_type").append("=").append(((Tag)FPObjects.get(i)).getKey()).append("&");
				}
				else
					buffer.append("icon_type").append("=").append("null").append("&");
				
				System.out.println(buffer.toString());
			
				OutputStream outputSream = new BufferedOutputStream(urlConnection.getOutputStream());
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputSream, "UTF-8"));
			    writer.write(buffer.toString());
			    writer.flush();
			    writer.close();
			    outputSream.close();
			    
			    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			    String inputLine;
			    StringBuffer response = new StringBuffer();
			    while((inputLine = in.readLine()) != null) {
			    	response.append(inputLine);
			    }
			
			    in.close();
			    System.out.println(response.toString());
			}
		}
		catch(Exception e){
		}
	}
	
	public void parsingObject(String data) {
        String[] strings = data.split(":");
        int numberOfAttribute = 6;
        
        for(int i = 0; i < strings.length;) {
            FPObject newObject = null;
            switch(Integer.parseInt(strings[i])) {
                case 0:
                    newObject = new Line(
                            Integer.parseInt(strings[i + 1]),
                            Integer.parseInt(strings[i + 2])
                    );
                    break;

                case 1:
                    newObject = new Icon(
                            Integer.parseInt(strings[i + 1]),
                            Integer.parseInt(strings[i + 2])
                    );
                    ((Icon)newObject).setIconType(IconType.values()[Integer.parseInt(strings[i + 5])]);
                    break;

                case 2:
                    newObject = new Oval(
                            Integer.parseInt(strings[i + 1]),
                            Integer.parseInt(strings[i + 2])
                    );
                    break;

                case 3:
                    newObject = new Rectangle(
                            Integer.parseInt(strings[i + 1]),
                            Integer.parseInt(strings[i + 2])
                    );
                    break;

                case 4:
                    newObject = new Tag(
                            Integer.parseInt(strings[i + 1]),
                            Integer.parseInt(strings[i + 2])
                    );
                    ((Tag)newObject).setKey(Integer.parseInt(strings[i + 5]));
                    break;
            }

            if(newObject != null) {
                newObject.setEndPosition(
                        new Point(
                                Integer.parseInt(strings[i + 3]),
                                Integer.parseInt(strings[i + 4])
                        )
                );
            }

            Objects.add(newObject);

            i += numberOfAttribute;
        }
    }
}

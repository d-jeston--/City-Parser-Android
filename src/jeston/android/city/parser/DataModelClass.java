package jeston.android.city.parser;

public class DataModelClass 
{
   private int id;
   private String name;
   private String state;
   private String url;
   private float lattitude;
   private float longitude;
 
   
   public int getId()
   {
	   return id;
   };
   
   public String getName()
   {
	 return name;  
   };
   
   public String getState()
   {
	 return state;  
   };
   
   public String getURL()
   {
	   return url;
   };
   
   public float getLattitude()
   {
	 return lattitude;  
   };
   
   public float getLongitude()
   {
	 return longitude;  
   };
   
   public void setId(int id)
   {
	 this.id = id;  
   };
   
   public void setName(String name)
   {
	 this.name = name;  
   };
   
   public void setState(String state)
   {
	 this.state = state;  
   };
   
   public void setURL(String URL)
   {
	 this.url = URL;  
   };
   
   public void setLattitude(float lattitude)
   {
	 this.lattitude = lattitude;   
   };
   
   public void setLongitude(float longitude)
   {
	 this.longitude = longitude;  
   };
   
}

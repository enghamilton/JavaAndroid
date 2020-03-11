package info.androidhive.sqlite;

public class DBUsers {
	int _id;
	String _username;
	String _userphone;
	String _imageUrl;
	String _description01,_description02,_description03;
	
	//constructor
	public DBUsers(){
		
	}
	
	public DBUsers(int id, String username, String userphone){
		this._id = id;
		this._username = username;
		this._userphone = userphone;
	}
	
	//polymorphism
	// Method for MainActivity onOptionsItemSelected action_refresh ListView setAdapter
	public DBUsers(String username, String userphone, String imageUrl){
		this._username = username;
		this._userphone = userphone;
		this._imageUrl = imageUrl;
	}
	
	// polymorphism
	public DBUsers(int id, String username, String userphone, String description01, String description02, String description03){
		this._id = id;
		this._username = username;
		this._userphone = userphone;
		this._description01 = description01;
		this._description02 = description02;
		this._description03 = description03;
	}
	
	//polymorphism
	//Method for ProfileMainActivity Asynctask
	public DBUsers(String username, String userphone, String imageUrl, String description01, String description02, String description03){
		this._username = username;
		this._userphone = userphone;
		this._imageUrl = imageUrl;
		this._description01 = description01;
		this._description02 = description02;
		this._description03 = description03;
	}
	
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._username;
	}
	
	// setting name
	public void setName(String name){
		this._username = name;
	}
	
	// getting userphone
	public String getUserPhone(){
		return this._userphone;
	}
	
	// setting userphone
	public void setUserPhone(String userPhone){
		this._userphone = userPhone;
	}
	
	// getting imageUrl
	public String getImageUrl(){
		return this._imageUrl;
	}
	
	// setting imageUrl
	public void setImageUrl(String imageUrl){
		this._imageUrl = imageUrl;
	}
	
	// getting description01
	public String getDescription01(){
		return this._description01;
	}
	
	// setting description01
	public void setDescription01(String description01){
		this._description01 = description01;
	}
	
	// getting description02
	public String getDescription02(){
		return this._description02;
	}
		
	// setting description02
	public void setDescription02(String description02){
		this._description02 = description02;
	}

	// getting description03
	public String getDescription03(){
		return this._description03;
	}
	
	// setting description03
	public void setDescription03(String description03){
		this._description03 = description03;
	}
}

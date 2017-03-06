package business;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//Each Instance of this class should be a user doc in users collection when persisted to mongo database
@Document(collection = "users")
public class User {
	@Id
	private String id;//This doc identifiable by Id type string thats autogenerated by Mongo when doc is persisted 
	private String username;
	private String password;
		
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password=password;
	}
}
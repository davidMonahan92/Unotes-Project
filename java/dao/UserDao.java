package dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import business.User;

public class UserDao
{
	@Autowired
	private MongoTemplate mongoOperation;
	
	public void setMongoOperation(MongoTemplate mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
	//Creates User
	public User createAccount(User user){
		mongoOperation.save(user, "users");
		return findUserByUserNameAndPassword(user.getUsername(), user.getPassword());
	}
	//Deletes User
	public Object deleteUser(String username, String password){
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username).andOperator(Criteria.where("password").is(password)));
		WriteResult result =  mongoOperation.remove(query,User.class, "users");
		return result.getUpsertedId();
	}
	
	//Checks if users exists
	public boolean isUser(String username) {
		Query isUserQ = new Query(Criteria.where("username").is(username));
		if (mongoOperation.exists(isUserQ, User.class)) {
			return true;
		} else {
			return false;
		}
	}
	//Updates users password
	public boolean updatePassword(String username, String password,String newPassword) {
		Update update = new Update();
		update.set("password", newPassword);
		Query updatePasswordQuery = new Query(Criteria.where("username").is(username).andOperator(Criteria.where("password").is(password)));
		mongoOperation.updateFirst(updatePasswordQuery, update, User.class);
		return true;

	}
	//Logins in user
	public User login(String username, String password){
		return findUserByUserNameAndPassword(username, password);
	}
	//Checks to see if user exists 
	protected User findUserByUserNameAndPassword(String username,  String password){
		Query loginQuery = new Query();
		loginQuery.addCriteria(Criteria.where("username").is(username).andOperator(Criteria.where("password").is(password)));	
		System.out.println("*********************************************************************");
		System.out.println(loginQuery.toString());
		if(mongoOperation.findOne(loginQuery, User.class) != null){
			return mongoOperation.findOne(loginQuery, User.class);
		}else{
			return new User(null, null);
		}
	}
}
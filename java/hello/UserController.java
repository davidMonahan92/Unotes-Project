package hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.UserDao;
import business.User;

@RestController
public class UserController 
{
	@Autowired
	private UserDao userDao;
	//Creates Account
    @RequestMapping("/createAccount")
    public User createAccount(@RequestParam(value="username")String username, @RequestParam(value="password") String password)
    {
    	return userDao.createAccount(new User(username, password));
    }
    //Logs in user
    @RequestMapping("/userLogin")
    public User login(@RequestParam(value="username") String username, @RequestParam(value="password") String password) 
    {
    	return userDao.login(username, password);
    }
    //Deletes account
    @RequestMapping("/deleteUser")
    public Object delete(@RequestParam(value="username") String username, @RequestParam(value="password") String password) 
    {
    	return userDao.deleteUser(username, password);
    }
    //Update users password
 	@RequestMapping("/updatePassword")
 	public boolean updatePassword(@RequestParam(value = "username") String username,@RequestParam(value = "oldPassword") String oldPassword,
 			@RequestParam(value = "newPassword") String newPassword)
 	{
 		if(userDao.updatePassword(username, oldPassword, newPassword)) 
 		{
 			return true;
 		}
 		else{ return false; }
 	}
 	//Checks if the username is valid or not
 	@RequestMapping("/isUserNew")
 	public @ResponseBody User isUserNow(@RequestParam(value = "username") String username) 
 	{
 		if(userDao.isUser(username)) 
 		{
 			return (new User(username, ""));
 		}else {
 			return (new User("", ""));
 		}
 	}
}

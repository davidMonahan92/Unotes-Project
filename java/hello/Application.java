package hello;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;

import dao.NoteDao;
import dao.UserDao;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Bean
	public MongoClient mongo() throws Exception {
		MongoClient client = new MongoClient(new MongoClientURI(
				"mongodb://unotes:unotes@ds015713.mlab.com:15713/unotes"));
		return client;
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), "unotes");
	}

	@Bean
	public MongoCredential mongoCredential() {
		MongoCredential credential = MongoCredential.createScramSha1Credential(
				"unotes", "unotes", "unotes".toCharArray());
		return credential;
	}

	@Bean
	public UserDao userDao(){
		return new UserDao();
	}
	@Bean
	public NoteDao noteDao(){
		return new NoteDao();
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}

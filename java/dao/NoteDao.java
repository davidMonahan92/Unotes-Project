package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.WriteResult;

import business.Note;

public class NoteDao {
	@Autowired
	private MongoTemplate mongoOperation;
	
	public void setMongoOperation(MongoTemplate mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
	//Creates Note
	public Note createNote(Note note){
		mongoOperation.save(note, "notes");
		return findNoteByWeekAndByCourseAndByCourseSection(note.getWeek(), note.getCourse(), note.getCourseSection());
	}

	//Deletes Note
	public Object deleteNote(String week, String course, String courseSection){
		Query query = new Query();
		query.addCriteria(Criteria.where("week").is(week).andOperator(Criteria.where("course").is(course).andOperator(Criteria.where("courseSection").is(courseSection))));
		WriteResult result =  mongoOperation.remove(query,Note.class, "notes");
		return result.getUpsertedId();
	}
	
	//find the note
	public Note findNote(String week, String course, String courseSection){
		return findNoteByWeekAndByCourseAndByCourseSection(week,course,courseSection);
	}
	//Checks to see if the note exists
	protected Note findNoteByWeekAndByCourseAndByCourseSection(String week,  String course, String courseSection){
		Query noteQuery = new Query();
		noteQuery.addCriteria(Criteria.where("week").is(week).andOperator(Criteria.where("course").is(course).andOperator(Criteria.where("courseSection").is(courseSection))));	
		System.out.println("*********************************************************************");
		System.out.println(noteQuery.toString());
		if(mongoOperation.findOne(noteQuery, Note.class) != null){
			return mongoOperation.findOne(noteQuery, Note.class);
		}else{
			return new Note(null, null,null);
		}
	}
	public List<Note> findAllNotes(){	
		System.out.println("Doing find all");
		List<Note> notes =  mongoOperation.findAll(Note.class, "notes");
		return notes;
	}
}

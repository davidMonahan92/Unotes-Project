package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dao.NoteDao;
import business.Note;

@RestController
public class NotesController {

	@Autowired
	private NoteDao notesDao;

    @RequestMapping("/createNote")
    public Note createNote(@RequestParam(value="week") String week, @RequestParam(value="course") String course,@RequestParam(value="courseSection") String courseSection) {
    	return notesDao.createNote(new Note(week, course, courseSection));
    }
    
    @RequestMapping("/deleteNote")
    public Object deleteNote(@RequestParam(value="week") String week, @RequestParam(value="course") String course,@RequestParam(value="courseSection") String courseSection) {
    	return notesDao.deleteNote(week,course,courseSection);
    }
    
    @RequestMapping("/findBy")
    public Note findNote(@RequestParam(value="week") String week, @RequestParam(value="course") String course,@RequestParam(value="courseSection") String courseSection) {
    	return notesDao.findNote(week,course,courseSection);
    }
    
    @RequestMapping("/getAllNotes")
    public List<Note> getAllNotes() {
    	System.out.println("Starting get all notes");
    	List<Note> allNotes = notesDao.findAllNotes();
    	System.out.println(allNotes.size());
    	return allNotes;
    }
}

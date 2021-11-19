package ECSE428Project.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "question")
public class Question {

    //------------------------
    // ATTRIBUTES
    //------------------------

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String askedQuestion;

    @ElementCollection
    private List<String> possibleAnswers;
    
    private String answer;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Question() {
        askedQuestion = null;
        possibleAnswers = new ArrayList<>();
    }

    public Question(String anAskedQuestion, List<String> possibleAnswers, String answer) {
        askedQuestion = anAskedQuestion;
        this.possibleAnswers = possibleAnswers;
        this.answer = answer;
    }


    //------------------------
    // INTERFACE
    //------------------------


    public void setId(String id) { this.id = id; }

    public void setAskedQuestion(String askedQuestion) { this.askedQuestion = askedQuestion; }

    public void setPossibleAnswers(List<String> acceptedAnswers) { this.possibleAnswers = acceptedAnswers; }

    public String getId() { return id; }

    public String getAskedQuestion() { return askedQuestion; }

    public List<String> getPossibleAnswers() { return possibleAnswers; }

    public boolean removePossibleAnswer(String answer) {
    	return possibleAnswers.remove(answer);
    }
    
    public String removePossibleAnswer(int index) {
    	if(possibleAnswers.size() < index && index >= 0) {
    		return possibleAnswers.remove(index);
    	}
    	return null;
    }
    
    public boolean addPossibleAnswer(String answer) {
    	return possibleAnswers.add(answer);
    }
    
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Question && this.id != null) {
			Question q = (Question)o;
			return this.id.equals(q.id);
		}
		return false;
	}
}

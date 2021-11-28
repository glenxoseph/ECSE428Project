package ECSE428Project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name = "quiz")
public class Quiz {
	
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
	
	private String name;
	
	private QuestionTopic topic;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch (FetchMode.SELECT)
	private List<Question> questions;
	
	public Quiz() {
		topic = QuestionTopic.ALL;
		questions = new ArrayList<>();
	}
	
	public Quiz(QuestionTopic topic) {
		this.topic = topic;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public QuestionTopic getTopic() {
		return topic;
	}

	public void setTopic(QuestionTopic topic) {
		this.topic = topic;
	}
	
	public List<Question> getQuestions(){
		return Collections.unmodifiableList(questions);
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public boolean removeQuestion(Question question){
			return questions.remove(question);	
	}
	
	public boolean addQuestion(Question question) {
		if(questions.contains(question)) {
			return false;
		}
		return questions.add(question);
	}
	
	public boolean equals(Object o) {
		return o instanceof Quiz && this.id != null && this.id.equals(((Quiz)o).id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

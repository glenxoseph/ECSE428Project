package ECSE428Project.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Player {

    private String id;
    private int numberOfCorrectAnswers;
    private int numberOfWrongAnswers;

}

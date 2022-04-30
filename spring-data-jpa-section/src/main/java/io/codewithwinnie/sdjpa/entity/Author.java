package io.codewithwinnie.sdjpa.entity;

import javax.persistence.*;
import java.util.StringJoiner;


/**
 * <PRE>Created by on 04/28/22.</PRE>
 * @author Ifeanyichukwu Otiwa
 */
@NamedQuery(name = "author", query = "FROM Author")
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
    
    @Override
    public String toString() {
        return new StringJoiner("", Author.class.getSimpleName() + "= {\n", "}\n")
                .add("\t\t\t\"id\": \"" + id + "\",\n")
                .add("\t\t\t\"firstName\": \"" + firstName + "\",\n")
                .add("\t\t\t\"lastName\": \"" + lastName + "\"")
                .toString();
    }
}

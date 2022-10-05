package by.voropai.Project2SpringBoot.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
//@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @NotNull(message = "Name shouldn't be empty!")
    @Size(min = 5, max = 50, message = "Enter your full name!")
    private String fullName;

    @Column(name = "birth_year")
    @NotNull
    @Min(value = 1910, message = "Enter valid year")
    @Max(value = 2022, message = "Enter valid year")
    private int birthYear;

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private List<Book> books;


//    public Person(String fullName, int birthYear) {
//        this.fullName = fullName;
//        this.birthYear = birthYear;
//    }

//    public Person() {
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

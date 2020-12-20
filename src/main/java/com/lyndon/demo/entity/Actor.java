package com.lyndon.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "actor")
@Data
@NoArgsConstructor
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_update")
    private Date lastUpdate;

    @ManyToMany(mappedBy = "actors", cascade = CascadeType.ALL)
    private Set<Film> films;

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "films");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Actor) {
            Actor actor = (Actor) obj;
            return id.equals(actor.id);
        }
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(id).toString();
    }

}

package com.projectstarter.ProjectStarter.model;


import lombok.*;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Long id;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Project> projects;

    @Column(name = "tag_name")
    @Field
    private String tagName;

    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Tag)) {
            return false;
        }
        return this.tagName.equals(((Tag) o).tagName);
    }
}

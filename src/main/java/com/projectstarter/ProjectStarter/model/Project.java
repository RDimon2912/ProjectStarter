package com.projectstarter.ProjectStarter.model;

import com.projectstarter.ProjectStarter.model.enums.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@Indexed
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    @Field
    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    @Field
    @Type(type = "text")
    private String description;

    @Column(name = "target_amount")
    private int targetAmount;

    @Column(name = "current_amount")
    private int currentAmount;

    @Column(name = "donate_min")
    private int donateMin;

    @Column(name = "rating")
    private double rating;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexedEmbedded
    private List<News> newsList;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexedEmbedded
    private List<Comments> commentsList;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexedEmbedded
    private List<Goal> goalList;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexedEmbedded
    private List<DonateSystem> donateSystemList;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subscription> subscriptionList;

    @OneToMany(mappedBy = "project",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Donate> donateList;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "projects_tags", joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @IndexedEmbedded
    private Set<Tag> tags;
}

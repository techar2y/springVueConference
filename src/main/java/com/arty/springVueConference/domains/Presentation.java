package com.arty.springVueConference.domains;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "presentations")
public class Presentation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private String description;

    private String title;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private String startTime;

    private String lasts;

    private String date;

    @ManyToMany
    @JoinTable(name = "presentation_users",
            joinColumns = @JoinColumn(name = "presentation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> presenters;

    public Presentation()
    {

    }

    public Presentation(String subject, String description, String title, Room room,
                        String lasts, String date, String startTime, List<User> presenters) {
        this.subject = subject;
        this.description = description;
        this.title = title;
        this.room = room;
        this.lasts = lasts;
        this.date = date;
        this.startTime = startTime;
        this.presenters = presenters;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Room getRoom()
    {
        return room;
    }

    public void setRoom(Room room)
    {
        this.room = room;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLasts ()
    {
        return lasts;
    }

    public void setLasts (String lasts)
    {
        this.lasts = lasts;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public List<User> getPresenters ()
    {
        return presenters;
    }

    public void setPresenters (List<User> presenters)
    {
        this.presenters = presenters;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
}

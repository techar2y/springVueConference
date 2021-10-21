package com.arty.springVueConference.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    @OneToMany(mappedBy = "room")
    private List<Presentation> presentations;

    public Room()
    {

    }

    public Room(Long id, Integer number) {
        this.id = id;
        this.number = number;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

    public void setPresentations(List<Presentation> presentations)
    {
        this.presentations = presentations;
    }

    @JsonIgnore
    public List<Presentation> getPresentations ()
    {
        return presentations;
    }
}

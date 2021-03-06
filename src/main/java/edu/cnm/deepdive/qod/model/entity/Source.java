package edu.cnm.deepdive.qod.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.cnm.deepdive.qod.view.FlatQuote;
import edu.cnm.deepdive.qod.view.FlatSource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

@Entity
public class Source implements FlatSource {

  @Id
  @GeneratedValue(generator = "uuid2")//This colum will be gotten automaticly
  @GenericGenerator(name = "uuid2", strategy = "uuid2")//This is where uuids are coing to come from jpa doesnt understand so this annot is needed
  @Column(name = "source_id", columnDefinition = "CHAR(16) FOR BIT DATA",
     nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @Column(length = 1024, nullable = false, unique = true) //fields that store text have a length this however changes the default length what ever that may be
  private String name;

  @JsonSerialize(contentAs = FlatQuote.class) //This serializes the  contant as FlatQuote not all the things
  @OneToMany(mappedBy = "source", fetch = FetchType.EAGER)
  private List<Quote> quotes = new LinkedList<>();

  public UUID getId() {
    return id;
  }

  public Date getCreated() {
    return created;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Quote> getQuotes() {
    return quotes;
  }

  public void setQuotes(List<Quote> quotes) {
    this.quotes = quotes;
  }

}

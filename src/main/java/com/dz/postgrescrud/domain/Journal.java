package com.dz.postgrescrud.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Used for optimistic locking, which may not be needed for this project
    @Version
    private Integer version;

    private String journalEntry;

    // We'll likely want to store the image in S3 and store the URL here
    private String imageUrl;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    public Journal() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getJournalEntry() {
        return journalEntry;
    }

    public void setJournalEntry(String journalEntry) {
        this.journalEntry = journalEntry;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id=" + id +
                ", version=" + version +
                ", journalEntry='" + journalEntry + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date=" + date +
                '}';
    }
}

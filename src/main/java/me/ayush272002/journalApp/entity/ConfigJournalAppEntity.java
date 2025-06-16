package me.ayush272002.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("config_journal")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {
    @Id
    private ObjectId id;

    private String key;
    private String value;
}

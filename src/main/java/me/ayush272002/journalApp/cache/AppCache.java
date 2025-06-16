package me.ayush272002.journalApp.cache;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import me.ayush272002.journalApp.entity.ConfigJournalAppEntity;
import me.ayush272002.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class AppCache {
    private final ConfigJournalAppRepository configJournalAppRepository;

    private Map<String, String> APP_CACHE;

    public AppCache(ConfigJournalAppRepository configJournalAppRepository) {
        this.configJournalAppRepository = configJournalAppRepository;
    }

    @PostConstruct
    public void init() {
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all) {
            APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}

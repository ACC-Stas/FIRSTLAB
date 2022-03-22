package main.banksystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.banksystem.containers.Id;

import java.util.HashMap;
import java.util.Map;

public class IndexGenerator {
    private static IndexGenerator generator;
    private final StringConverter<Map<String, Long>> dictionaryConverter;
    private final ObjectMapper objectMapper;

    private IndexGenerator() {
        dictionaryConverter = new StringConverter<>();
        objectMapper = new ObjectMapper();
    }

    public static IndexGenerator GetInstance() {
        if (generator == null) {
            generator = new IndexGenerator();
        }

        return generator;
    }

    public Long GenerateUserIdx() {
        DataBase dataBase = DataBase.GetInstance();
        String str = dataBase.Download(new Id(0), DataBase.INDEXES_PART);
        Map<String, Long> idxDictionary = null;

        try {
            idxDictionary = objectMapper.readValue(str, new TypeReference<HashMap<String, Long>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (idxDictionary == null) {
            idxDictionary = new HashMap<>();
            idxDictionary.put("User", 1L);
        }
        Long userIdx = idxDictionary.get("User");
        userIdx++;
        idxDictionary.put("User", userIdx);

        dataBase.Save(new Id(0), DataBase.INDEXES_PART, dictionaryConverter.Serialize(idxDictionary));
        return userIdx;
    }
}

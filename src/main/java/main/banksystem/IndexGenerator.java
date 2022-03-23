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

    private static final String USER_IDX = "User";
    private static final String BILLS_IDX = "Bill";

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
        return GenerateIdx(IndexGenerator.USER_IDX);
    }

    public Long GenerateIdx(String idxName) {
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
            idxDictionary.put(idxName, 1L);
        }

        if (!idxDictionary.containsKey(idxName)) {
            idxDictionary.put(idxName, 1L);
        }
        Long idx = idxDictionary.get(idxName);
        idx++;
        idxDictionary.put(idxName, idx);

        dataBase.Save(new Id(0), DataBase.INDEXES_PART, dictionaryConverter.Serialize(idxDictionary));
        return idx;
    }
}

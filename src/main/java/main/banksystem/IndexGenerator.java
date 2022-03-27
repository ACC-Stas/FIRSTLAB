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

    public static final String USER_IDX = "User";
    public static final String BILLS_IDX = "Bill";

    private IndexGenerator() {
        dictionaryConverter = new StringConverter<>();
        objectMapper = new ObjectMapper();
    }

    public static IndexGenerator getInstance() {
        if (generator == null) {
            generator = new IndexGenerator();
        }

        return generator;
    }

    public Long generateIdx(String idxName) {
        DataBase dataBase = DataBase.getInstance();
        String str = dataBase.download(new Id(0), DataBase.INDEXES_PART);
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

        dataBase.save(new Id(0), DataBase.INDEXES_PART, dictionaryConverter.serialize(idxDictionary));
        return idx;
    }
}

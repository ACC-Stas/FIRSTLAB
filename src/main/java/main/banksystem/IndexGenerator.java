package main.banksystem;

import main.banksystem.containers.Id;

import java.util.Map;

public class IndexGenerator {
    private static IndexGenerator generator;
    private final StringConverter<Map<String, Long>> dictionaryConverter;

    private IndexGenerator() {
        dictionaryConverter = new StringConverter<>();
    }

    public static IndexGenerator GetInstance() {
        if (generator == null) {
            generator = new IndexGenerator();
        }

        return generator;
    }

    public Long GenerateUserIdx() {
        DataBase dataBase = DataBase.GetInstance();
        Map<String, Long> idxDictionary = dictionaryConverter.Deserialize(dataBase.Download(new Id(0), "indexes.csv"));

        Long userIdx = idxDictionary.get("User");
        userIdx++;
        idxDictionary.put("User", userIdx);

        dataBase.Save(new Id(0), "indexes.csv", dictionaryConverter.Serialize(idxDictionary));
        return userIdx;
    }
}

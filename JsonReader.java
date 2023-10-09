package main;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class JsonReader {
	
	public static HashMap<Integer, Set<Set<Integer>>> readJsonFile(String filePath) throws IOException {
        // 创建 ObjectMapper 对象
        ObjectMapper objectMapper = new ObjectMapper();

        // 读取 JSON 文件并解析为 HashMap<Integer, Set<Set<Integer>>>
        File jsonFile = new File(filePath);
        return objectMapper.readValue(jsonFile, new TypeReference<HashMap<Integer, Set<Set<Integer>>>>() {});
    }

    public static void main(String[] args) {
        try {
            // 读取 JSON 文件并存储为 HashMap<Integer, Set<Set<Integer>>>
            String filePath = "/Users/mac/Desktop/eclipse-workplace/strategyselection/OptimalRepair/conflicts.json"; // 替换为你的 JSON 文件路径
            HashMap<Integer, Set<Set<Integer>>> resultMap = readJsonFile(filePath);

            // 打印读取的数据
            /*for (HashMap.Entry<Integer, Set<Set<Integer>>> entry : resultMap.entrySet()) {
                System.out.println("Key: " + entry.getKey());
                Set<Set<Integer>> sets = entry.getValue();
                for (Set<Integer> set : sets) {
                    System.out.println("Set: " + set);
                }
            }*/
            System.out.println(resultMap.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class FindAllRepairs {
   
    public static void main(String[] args) {
        String inputFilePath = "/Users/mac/Desktop/eclipse-workplace/strategyselection/OptimalRepair/out.dat"; // 输入的 .dat 文件路径

        HashMap<Integer, int[]> dataMap = readDatFile(inputFilePath);
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 5000; i++) {
            list.add(i);
        }

        // 输出 HashMap 中的数据
        for (int value : dataMap.get(5)) {
            System.out.print(value + " ");
        }
        
        HashMap<Integer, List<Integer>> h2 = generateHashMap(dataMap, list);

        // 打印 h2 中的数据
        for (Integer key : h2.keySet()) {
            List<Integer> arrayH2 = h2.get(key);
            System.out.println("Key: " + key + ", Array: " + arrayH2);
        }
    }
  
    public static HashMap<Integer, int[]> readDatFile(String filePath) {
        HashMap<Integer, int[]> dataMap = new HashMap<>();

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            int lineNumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.trim().split(" ");
                int[] dataArray = new int[values.length];

                for (int i = 0; i < values.length; i++) {
                    dataArray[i] = Integer.parseInt(values[i]);
                }

                dataMap.put(lineNumber, dataArray);
                lineNumber++;
            }
 
            // 关闭文件流
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataMap;
    }
    
    public static HashMap<Integer, List<Integer>> generateHashMap(HashMap<Integer, int[]> h1, List<Integer> list) {
        HashMap<Integer, List<Integer>> h2 = new HashMap<>();

        for (Integer key : h1.keySet()) {
        	//System.out.println(" key : "+key);
            int[] arrayH1 = h1.get(key);
            List<Integer> arrayH2 = new ArrayList<>(list);

            for (int num : arrayH1) {
                arrayH2.remove(Integer.valueOf(num));
            }

            h2.put(key, arrayH2);
            //System.out.println(" key : "+key+" list "+arrayH2);
        }
        //System.out.println(h2);
        return h2;
    }

}

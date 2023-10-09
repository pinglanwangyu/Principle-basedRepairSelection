package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.Iterator;
import com.fasterxml.jackson.databind.ObjectMapper;
public class cardinality {
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String resultfilePath = "./cardresult.txt"; 


        //HashMap<Integer,Set<List<Integer>>> optimalrepairs = new HashMap<>();
        Set<Integer> repairnocalculate = new HashSet<>();
        HashMap<Integer, Set<Integer>> cadinalitysets = new HashMap<>();
        int sizecard =0;
        int length =0;
        try {
            // 读取 JSON 文件并存储为 HashMap<Integer, Set<Set<Integer>>>
            String ConflictPath = "/Users/mac/Desktop/eclipse-workplace/strategyselection/OptimalRepair/conflictsnonbinary.json"; // 替换为你的 JSON 文件路径
            String HittingSetPath = "/Users/mac/Desktop/eclipse-workplace/strategyselection/OptimalRepair/hittingsetnonbinary.json"; // 替换为你的 JSON 文件路径
            
            //String ConflictPath = "./conflictsnonbinary.json"; // 替换为你的 JSON 文件路径
            //String HittingSetPath = "./hittingsetnonbinary.json"; // 替换为你的 JSON 文件路径

            HashMap<Integer, Set<Set<Integer>>> ConflictGraph = JsonReader.readJsonFile(ConflictPath);
            HashMap<Integer, Set<Set<Integer>>> HittingSetGraph = JsonReader.readJsonFile(HittingSetPath);
 
            
            for(int index : HittingSetGraph.keySet()) {
            	System.out.println("index : "+index);
            	//每一个子图的repairs和conflictgraph
                HashMap<Integer,int[]> conflictgraph = new HashMap<>();
                HashMap<Integer , int[]> hittingset = new HashMap<>();
                //HashMap<Integer,Set<Integer>> optimalrepairs = new HashMap<>();
            	
            	//将每一个子图的conflict和hittingset转换为需要的形式
                int j = 0;
                int k = 0;
            	for(Set<Integer> conflicts :ConflictGraph.get(index)) {
            		int[] conf = new int[conflicts.size()];
            		int i=0;
            		
            		for(int conflict : conflicts) {
            			conf[i] = conflict;
            			i++;
            		}
            		conflictgraph.put(j, conf);
            		j++;
            	}
            	//System.out.println(conflictgraph.get(1)[0]+"   "+conflictgraph.get(1)[1]);
            	for(Set<Integer> hittingsets : HittingSetGraph.get(index)) {
            		int[] hit = new int[hittingsets.size()];
            		int i=0;
            		
            		for(int conflict : hittingsets) {
            			hit[i] = conflict;
            			i++;
            		}
            		hittingset.put(k, hit);
            		k++;
            	}
            	
            	
            	//System.out.println(conflictgraph.get(1)[0]+"   "+conflictgraph.get(1)[1]);
        		Set<Integer> s1 = Algorithm.getUnionOfIntArrayValues(conflictgraph);
                List<Integer> database = new ArrayList<>(s1);
                
                
                HashMap<Integer, List<Integer>> allrepairs = FindAllRepairs.generateHashMap(hittingset, database);
                System.out.println("allrepairsize : "+allrepairs.size());

                Set<Integer> cardset = Algorithm.cardinality(allrepairs, hittingset);
                cadinalitysets.put(index, cardset);
                Iterator<Integer> iterator = cardset.iterator();

                if (iterator.hasNext()) {
                    int element = iterator.next();
                    length += allrepairs.get(element).size();
                    System.out.println("Element from set: " + element);
                } else {
                    System.out.println("Set is empty.");
                }
                System.out.println("length allrepairsize : "+length);


                
                
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        for(int key : cadinalitysets.keySet()) {
        	sizecard += cadinalitysets.get(key).size();
        }
        
        
        //记录程序结束时间
        long endTime = System.currentTimeMillis();

        // 计算运行时间（毫秒）
        long duration = endTime - startTime;
        //System.out.println("number of repairs found : "+allrepairs.keySet().size());
        System.out.println("number of cadinalitysets repairs found : "+cadinalitysets.keySet().size());
        // 打印运行时间
        System.out.println("runtime ：" + duration + " ms");
        System.out.println("repair calculate : "+sizecard);
        ObjectMapper objectMapper = new ObjectMapper();

        

        try {
            // 创建文件写入器
            FileWriter fileWriter = new FileWriter(resultfilePath, true); // 设置为true表示追加写入
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // 写入日志消息
            //printWriter.println("number of repairs found : "+allrepairs.keySet().size());
            printWriter.println("number of cadinalitysets repairs found : "+cadinalitysets.size());
            printWriter.println("runtime ：" + duration + " ms");
            //printWriter.println("sel ：" + lengthsel + " card : "+lengthcard);
            // 关闭写入器
            printWriter.close();

            System.out.println("Log message has been written to " + resultfilePath);
            
            
            // 将 JSON 字符串写入文件
            File file = new File("./cardrepairs.json");
            objectMapper.writeValue(file, cadinalitysets);

            //System.out.println("HashMap 已保存为 JSON 文件。");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    


        
	}
	

}

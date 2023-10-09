package main;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.*;

public class compability {
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String resultfilePath = "./result.txt"; 
		ExecutorService executorService = Executors.newSingleThreadExecutor();


        HashMap<Integer,Set<List<Integer>>> optimalrepairs = new HashMap<>();
        //HashMap<Integer,Set<Integer>> repairnocalculate = new HashMap<>();
        Set<Integer> repairnocalculate = new HashSet<>();
        int lengthcard =0;
        int lengthsel = 0;
        try {
            // 读取 JSON 文件并存储为 HashMap<Integer, Set<Set<Integer>>>
            String ConflictPath = "/Users/mac/Desktop/eclipse-workplace/strategyselection/OptimalRepair/conflictsnonbinary.json"; // 替换为你的 JSON 文件路径
            String HittingSetPath = "/Users/mac/Desktop/eclipse-workplace/strategyselection/OptimalRepair/hittingsetnonbinary.json"; // 替换为你的 JSON 文件路径
            
            //String ConflictPath = "./conflictsnonbinary.json"; // 替换为你的 JSON 文件路径
            //String HittingSetPath = "./hittingsetnonbinary.json"; // 替换为你的 JSON 文件路径

            HashMap<Integer, Set<Set<Integer>>> ConflictGraph = JsonReader.readJsonFile(ConflictPath);
            HashMap<Integer, Set<Set<Integer>>> HittingSetGraph = JsonReader.readJsonFile(HittingSetPath);
            //for(int index : HittingSetGraph.keySet()) {
            	//System.out.println("hittingset : "+HittingSetGraph.get(1).size()+" conflict :"+ConflictGraph.get(1).size());
            //}
            
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
                //if(allrepairs.size()<=1000) {
                	Callable<MyResult> myCallable = new MyCallable(allrepairs,conflictgraph);

                    Future<MyResult> future = executorService.submit(myCallable);
                    //HashMap<Integer,Set<Integer>> general = new HashMap<>();
                                   
                    try {
                        // 等待线程返回结果，最多等待1分钟
                        MyResult result = future.get(10, TimeUnit.SECONDS);
                        //HashMap<Integer,Set<Integer>> general = result.getMap();
                        if (result != null) {
                        	HashMap<Integer,Set<Integer>> general = result.getMap();
                            System.out.println("generalsize : "+general.size());
                            
                            repairnocalculate.add(index);
                            System.out.println(general);
                            
                            
                            for(int index1 : general.keySet()) {
                            	Set<List<Integer>> newsets = new HashSet<>();
                        		if(optimalrepairs.containsKey(index)) {
                        			optimalrepairs.get(index).add(allrepairs.get(index1));
                        		}else {
                        			optimalrepairs.put(index, newsets);
                        			optimalrepairs.get(index).add(allrepairs.get(index1));
                        			}
                        		//System.out.println(allrepairs.get(index1));
                            }
                            //System.out.println(optimalrepairs);
                            //System.out.println("sel ：" + lengthsel + " card : "+lengthcard);
                            // ...
                        } else {
                            System.out.println("线程超时未返回结果");
                            // 在超时后中止线程
                            future.cancel(true);
                            //repairnocalculate.add(index);
                        }
                        
                        
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }
                    
                //}
             }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //记录程序结束时间
        long endTime = System.currentTimeMillis();

        // 计算运行时间（毫秒）
        long duration = endTime - startTime;
        //System.out.println("number of repairs found : "+allrepairs.keySet().size());
        System.out.println("number of optimal repairs found : "+optimalrepairs.keySet().size());
        // 打印运行时间
        System.out.println("runtime ：" + duration + " ms");
        System.out.println("repair calculate : "+repairnocalculate);
        ObjectMapper objectMapper = new ObjectMapper();

        

        try {
            // 创建文件写入器
            FileWriter fileWriter = new FileWriter(resultfilePath, true); // 设置为true表示追加写入
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // 写入日志消息
            //printWriter.println("number of repairs found : "+allrepairs.keySet().size());
            printWriter.println("number of optimal repairs found : "+optimalrepairs.size());
            printWriter.println("runtime ：" + duration + " ms");
            //printWriter.println("sel ：" + lengthsel + " card : "+lengthcard);
            // 关闭写入器
            printWriter.close();

            System.out.println("Log message has been written to " + resultfilePath);
            
            
            // 将 JSON 字符串写入文件
            File file = new File("./optimalrepairs_1min.json");
            objectMapper.writeValue(file, optimalrepairs);

            //System.out.println("HashMap 已保存为 JSON 文件。");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    


        
	}
	
	
	
	

}

class MyResult {
	HashMap<Integer,Set<Integer>> map;

    public MyResult(HashMap<Integer,Set<Integer>> map) {
        this.map = map;
    }



    public HashMap<Integer,Set<Integer>> getMap() {
        return map;
    }
}


class MyCallable implements Callable<MyResult> {
	private HashMap<Integer, List<Integer>> allrepairs;
	private HashMap<Integer,int[]> conflictgraph;

    public MyCallable(HashMap<Integer, List<Integer>> sharedMap,HashMap<Integer,int[]> conflictgraph) {
        this.allrepairs = sharedMap;
        this.conflictgraph = conflictgraph;
    }
    @Override
    public MyResult call() {
        // 在线程中执行一些操作，计算int值1，int值2和HashMap
    	HashMap<Integer,Set<Integer>> general = new HashMap<>();
    	general = Algorithm.optimize(allrepairs,conflictgraph);


        // 在适当的地方检查线程是否被中断，如果中断则终止线程执行
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("线程被中断，终止执行");
            return null;
        }

        return new MyResult(general);
    }
}

package main;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
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
        Set<Integer> repairnocalculate = new HashSet<>();
        try {
      
            String ConflictPath = "./conflictsnonbinary.json"; 
            String HittingSetPath = "./hittingsetnonbinary.json"; 

            HashMap<Integer, Set<Set<Integer>>> ConflictGraph = JsonReader.readJsonFile(ConflictPath);
            HashMap<Integer, Set<Set<Integer>>> HittingSetGraph = JsonReader.readJsonFile(HittingSetPath);

            
            for(int index : HittingSetGraph.keySet()) {
            	System.out.println("index of connected component: "+index);
                HashMap<Integer,int[]> conflictgraph = new HashMap<>();
                HashMap<Integer , int[]> hittingset = new HashMap<>();
            	
            	//Translate each connected component's conflict and hitting set into the required format.
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
            	
        		Set<Integer> s1 = Algorithm.getUnionOfIntArrayValues(conflictgraph);
                List<Integer> database = new ArrayList<>(s1);
                
                
                HashMap<Integer, List<Integer>> allrepairs = FindAllRepairs.generateHashMap(hittingset, database);
                System.out.println("allrepairsize : "+allrepairs.size());
                Callable<MyResult> myCallable = new MyCallable(allrepairs,conflictgraph);

                Future<MyResult> future = executorService.submit(myCallable);
                                   
                    try {
                        // waiting for result
                        MyResult result = future.get(40, TimeUnit.SECONDS);
                        if (result != null) {
                        	HashMap<Integer,Set<Integer>> general = result.getMap();
                            System.out.println("generalsize : "+general.size());
                            
                            repairnocalculate.add(index);                            
                            
                            for(int index1 : general.keySet()) {
                            	Set<List<Integer>> newsets = new HashSet<>();
                        		if(optimalrepairs.containsKey(index)) {
                        			optimalrepairs.get(index).add(allrepairs.get(index1));
                        		}else {
                        			optimalrepairs.put(index, newsets);
                        			optimalrepairs.get(index).add(allrepairs.get(index1));
                        			}
                            }
                        } else {
                            future.cancel(true);
                        }
                        
                        
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }
             }

        } catch (IOException e) {
            e.printStackTrace();
        }
        

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("runtime ：" + duration + " ms");
        System.out.println("repair calculate : "+repairnocalculate);
        ObjectMapper objectMapper = new ObjectMapper();

        

        try {
            FileWriter fileWriter = new FileWriter(resultfilePath, true); 
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("runtime ：" + duration + " ms");
            printWriter.println("repair calculate : "+repairnocalculate);

            printWriter.close();

            System.out.println("Log message has been written to " + resultfilePath);
                        
            File file = new File("./optimalrepairs_40s.json");
            objectMapper.writeValue(file, optimalrepairs);

            System.out.println("Repairs saved in file optimalrepairs_40s.json" );
            
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
    	HashMap<Integer,Set<Integer>> general = new HashMap<>();
    	general = Algorithm.optimize(allrepairs,conflictgraph);

        if (Thread.currentThread().isInterrupted()) {
            System.out.println("thread is interrupted");
            return null;
        }

        return new MyResult(general);
    }
}

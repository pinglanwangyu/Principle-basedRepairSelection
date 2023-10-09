package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Algorithm {
	
	//naive
	public static HashMap<Integer, Set<Integer>> naive(HashMap<Integer, List<Integer>> allrepairs, HashMap<Integer, int[]> conflictgraph) {
		HashMap<Integer, Set<Integer>> result = new HashMap<>();		
		HashMap<Integer, Set<Integer>> CompabilitySetBinary = new HashMap<>();
        HashMap<Integer, Set<Integer>> nonCompabilitySetBinary = new HashMap<>();
		
        HashMap<Integer, int[]> conflictbinaire = new HashMap<>();
        HashMap<Integer, int[]> conflictnonbinary = new HashMap<>();
		
        for (int keys : allrepairs.keySet()) {
            CompabilitySetBinary.put(keys, allrepairs.keySet());
        }

        
        for(int keysrepair1 : allrepairs.keySet()) {
        	nonCompabilitySetBinary.put(keysrepair1, new HashSet<>());
        }
		
        int i = 0;
        int j = 0;
        for (int keys : conflictgraph.keySet()) {
            int[] conflict = conflictgraph.get(keys);
            if (conflict.length == 2) {
                conflictbinaire.put(i, conflict);
                i++;
            } else {
            	conflictnonbinary.put(j, conflict);
                j++;
            }
        }

/*
        for (int keys : conflictbinaire.keySet()) {
            for (int keysrepair1 : allrepairs.keySet()) {
            	boolean containsElement11 = allrepairs.get(keysrepair1).contains(conflictbinaire.get(keys)[0]);               
            	for (int keysrepair2 : allrepairs.keySet()) {
            		if (containsElement11) {
            			boolean containsElement2 = allrepairs.get(keysrepair2).contains(conflictbinaire.get(keys)[1]);
                        if (containsElement2) {
                        	nonCompabilitySetBinary.get(keysrepair1).add(keysrepair2);
                        	nonCompabilitySetBinary.get(keysrepair2).add(keysrepair1);
                        }
            		}
            	}
            }
        }
 */       
       // HashMap<Integer,Set<Integer>> SetBinary= removeAllElements(CompabilitySetBinary, nonCompabilitySetBinary);
        
        HashMap<Integer, Set<Integer>> CompabilitySetNonBinary = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
        	CompabilitySetNonBinary.put(keys, allrepairs.keySet());
        }
        
        HashMap<Integer, Set<Integer>> nonCompabilitySetNonBinary = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
        	nonCompabilitySetNonBinary.put(keys, new HashSet<>());
        }
        
        for(int keys : conflictgraph.keySet()) {
        	//for (int keyrepairs1 =0;keyrepairs1<allrepairs.keySet().size()-1;keyrepairs1++) {
        	for (int keyrepairs1 : allrepairs.keySet()) {
            	//for(int keyrepairs2 =keyrepairs1+1;keyrepairs2<allrepairs.keySet().size();keyrepairs2++) {
        		for (int keyrepairs2 : allrepairs.keySet()) {
            		//System.out.println(keyrepairs1+" "+allrepairs.get(keyrepairs1));
            		Set<Integer> compabilityset = new HashSet<>(allrepairs.get(keyrepairs1));
            		compabilityset.addAll(allrepairs.get(keyrepairs2));
            		Set<Integer> intersection = new HashSet<>(allrepairs.get(keyrepairs1));
            		intersection.retainAll(allrepairs.get(keyrepairs2));
            		compabilityset.removeAll(intersection);
    				Set<Integer> set = new HashSet<>();
    		        for (int num : conflictgraph.get(keys)) {
    		            set.add(num);
    		        }
            		if(compabilityset.containsAll(set)) {
            			nonCompabilitySetNonBinary.get(keyrepairs1).add(keyrepairs2);
            			nonCompabilitySetNonBinary.get(keyrepairs2).add(keyrepairs1);
            		}
            	}
            }

        }
        HashMap<Integer, Set<Integer>> SetNonBinary = removeAllElements(CompabilitySetNonBinary,nonCompabilitySetNonBinary);
        /*
        for(int keys : conflictnonbinary.keySet()) {
        	for (int keyrepairs1 =0;keyrepairs1<allrepairs.keySet().size()-1;keyrepairs1++) {
            	for(int keyrepairs2 =keyrepairs1+1;keyrepairs2<allrepairs.keySet().size();keyrepairs2++) {
            		for(int element : conflictnonbinary.get(keys)) {
            			if(allrepairs.get(keyrepairs1).contains(element) && allrepairs.get(keyrepairs2).contains(element)) {
            				//做并集，并检查是否含有完整conflict
            				Set<Integer> s1 =new HashSet<>(allrepairs.get(keyrepairs1));
            				Set<Integer> s2 =new HashSet<>(allrepairs.get(keyrepairs2));
            				s1.addAll(s2);
            				//转换list为set
            				List<Integer> list = new ArrayList<>();
            		        for (int num : conflictnonbinary.get(keys)) {
            		            list.add(num);
            		        }
            				
            				
            				Set<Integer> conflict =new HashSet<>(list);
            				if(s1.containsAll(conflict)) {
            					CompabilitySetNonBinary.get(keyrepairs1).add(keyrepairs2);
            					CompabilitySetNonBinary.get(keyrepairs2).add(keyrepairs1);
            					//System.out.println("r1 : "+keyrepairs1+" r2 : "+keyrepairs2);
            					break;
            				}
            			}
            		}      		
            	}
            	//System.out.println("r1 : "+keyrepairs1);
            }
        }
        */
        //System.out.println("binary :"+SetBinary);
        //System.out.println("non binary :"+CompabilitySetNonBinary);
        
        //result = intersectionOfSets(SetBinary, SetNonBinary);

        return SetNonBinary;
    }
	
	//optimize
	public static HashMap<Integer, Set<Integer>> optimize(HashMap<Integer, List<Integer>> allrepairs, HashMap<Integer, int[]> conflictgraph) {
		HashMap<Integer, Set<Integer>> result = new HashMap<>();
		HashMap<Integer, Set<Integer>> compabilitysets = new HashMap<>();
		HashMap<Integer, Set<Integer>> optimalrepairs = new HashMap<>();
		HashMap<Integer, Set<Integer>> CompabilitySetBinary = new HashMap<>();
        HashMap<Integer, Set<Integer>> nonCompabilitySetBinary = new HashMap<>();
		
        HashMap<Integer, int[]> conflictbinaire = new HashMap<>();
        HashMap<Integer, int[]> conflictnonbinary = new HashMap<>();
        int maxard =0;
		
        for (int keys : allrepairs.keySet()) {
            CompabilitySetBinary.put(keys, allrepairs.keySet());
        }

        
        for(int keysrepair1 : allrepairs.keySet()) {
        	nonCompabilitySetBinary.put(keysrepair1, new HashSet<>());
        }
		
        int i = 0;
        int j = 0;
        for (int keys : conflictgraph.keySet()) {
            int[] conflict = conflictgraph.get(keys);
            if (conflict.length == 2) {
                conflictbinaire.put(i, conflict);
                i++;
            } else {
            	conflictnonbinary.put(j, conflict);
                j++;
            }
        }
        
        HashMap<Integer, Set<Integer>> CompabilitySet = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
            CompabilitySet.put(keys, allrepairs.keySet());
        }
        
        
        if(conflictbinaire.keySet().size()==conflictgraph.keySet().size()) {

        	//return CompabilitySet;
        }


        for (int keys : conflictbinaire.keySet()) {
            for (int keysrepair1 : allrepairs.keySet()) {
            	boolean containsElement11 = allrepairs.get(keysrepair1).contains(conflictbinaire.get(keys)[0]);               
            	for (int keysrepair2 : allrepairs.keySet()) {
            		if (containsElement11) {
            			boolean containsElement2 = allrepairs.get(keysrepair2).contains(conflictbinaire.get(keys)[1]);
                        if (containsElement2) {
                        	nonCompabilitySetBinary.get(keysrepair1).add(keysrepair2);
                        	nonCompabilitySetBinary.get(keysrepair2).add(keysrepair1);
                        }
            		}
            	}
            }
        }
        
        HashMap<Integer,Set<Integer>> SetBinary= removeAllElements(CompabilitySetBinary, nonCompabilitySetBinary);
        //System.out.println(nonCompabilitySetBinary);
        HashMap<Integer, Set<Integer>> CompabilitySetNonBinary = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
        	CompabilitySetNonBinary.put(keys, allrepairs.keySet());
        }
        
        HashMap<Integer, Set<Integer>> nonCompabilitySetNonBinary = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
        	nonCompabilitySetNonBinary.put(keys, new HashSet<>());
        }
        
        HashMap<Integer,Set<Integer>> setconflictnonbinary =new HashMap<>();
        for(int keys : conflictnonbinary.keySet()) {
        	Set<Integer> set = new HashSet<>();
	        for (int num : conflictnonbinary.get(keys)) {
	            set.add(num);
	        }
	        setconflictnonbinary.put(keys, set);
	        
        }
        
        Set<Integer> keylist = new HashSet<>();
        	for (int keyrepairs1 : allrepairs.keySet()) {
        		keylist.add(keyrepairs1);
        		for (int keyrepairs2 : SetBinary.keySet()) {
        			if(!keylist.contains(keyrepairs2)) {
        				for(int keys : conflictnonbinary.keySet()) {
            				Set<Integer> compabilityset = new HashSet<>(allrepairs.get(keyrepairs1));
                    		compabilityset.addAll(allrepairs.get(keyrepairs2));
                    		Set<Integer> intersection = new HashSet<>(allrepairs.get(keyrepairs1));
                    		intersection.retainAll(allrepairs.get(keyrepairs2));
                    		compabilityset.removeAll(intersection);
                    		if(compabilityset.containsAll(setconflictnonbinary.get(keys))) {
                    			nonCompabilitySetNonBinary.get(keyrepairs1).add(keyrepairs2);
                    			nonCompabilitySetNonBinary.get(keyrepairs2).add(keyrepairs1);
                    		}
            			}
        			}
        			
            		
            	}
            }

        
        HashMap<Integer, Set<Integer>> SetNonBinary = removeAllElements(CompabilitySetNonBinary,nonCompabilitySetNonBinary);
      
        compabilitysets = intersectionOfSets(SetBinary, SetNonBinary);
        
        //result = findOptimalRepairs(compabilitysets);
        /*
        for(int index : result.keySet()) {
        	for(int keyrepairs : result.get(index)) {
        		if(optimalrepairs.containsKey(index)) {
        			optimalrepairs.get(index).addAll(allrepairs.get(keyrepairs));
        		}else {
        			optimalrepairs.put(index, new HashSet<>(allrepairs.get(keyrepairs)));
        		}
        	}
        }
         */
        return compabilitysets;
    }
	
	public static  Set<Integer> cardinality(HashMap<Integer, List<Integer>>  allrepairs,HashMap<Integer, int[]> dataMap){
		 Set<Integer> cardinalityset = new HashSet<>();

		int maxlength=0;
		/*
		for (int keyrepairs1 : allrepairs.keySet()) {
    		if(allrepairs.get(keyrepairs1).size()>maxlength) {
    			cardinalityset.clear();
    			cardinalityset.add(keyrepairs1);
    			maxlength = allrepairs.get(keyrepairs1).size();
    		}else if(allrepairs.get(keyrepairs1).size()==maxlength) {
    			cardinalityset.add(keyrepairs1);
    		}
    	}*/
		
		for (int keyrepairs1 : allrepairs.keySet()) {
    		if(allrepairs.get(keyrepairs1).size()>maxlength) {
    			cardinalityset.clear();
    			cardinalityset.add(keyrepairs1);
    			maxlength = allrepairs.get(keyrepairs1).size();
    		}
    	}
		
		return cardinalityset;
	}

	
	//对于三元，二元求stratagy。
 	public static HashMap<Integer, Set<Integer>> ternaire(HashMap<Integer, List<Integer>>  allrepairs1,HashMap<Integer, int[]> dataMap) {
		HashMap<Integer, List<Integer>>  allrepairs = allrepairs1;
		HashMap<Integer, Set<Integer>> intersection = new HashMap<>();
        HashMap<Integer, int[]> conflictbinaire = new HashMap<>();
        HashMap<Integer, int[]> conflictternaire = new HashMap<>();
        HashMap<Integer, int[]> conflicts = new HashMap<>();

        int i = 0;
        int j = 0;
        int k = 0;
        for (int keys : dataMap.keySet()) {
            int[] conflict = dataMap.get(keys);
            if (conflict.length == 2) {
                conflictbinaire.put(i, conflict);
                i++;
            } else if (conflict.length == 3) {
                conflictternaire.put(j, conflict);
                j++;
            } else {
                conflicts.put(k, conflict);
                k++;
            }
        }

        HashMap<Integer, Set<Integer>> CompabilitySet = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
            CompabilitySet.put(keys, allrepairs.keySet());
        }
        
        
        if(conflictbinaire.keySet().size()==dataMap.keySet().size()) {

        	return CompabilitySet;
        }


        HashMap<Integer, Set<Integer>> nonCompabilitySet = new HashMap<>();
        for(int keysrepair1 : allrepairs.keySet()) {
        	nonCompabilitySet.put(keysrepair1, new HashSet<>());
        }

        for (int keys : conflictbinaire.keySet()) {
            for (int keysrepair1 : allrepairs.keySet()) {
            	boolean containsElement11 = allrepairs.get(keysrepair1).contains(conflictbinaire.get(keys)[0]);               
            	for (int keysrepair2 : allrepairs.keySet()) {
            		if (containsElement11) {
            			boolean containsElement2 = allrepairs.get(keysrepair2).contains(conflictbinaire.get(keys)[1]);
                        if (containsElement2) {
                            nonCompabilitySet.get(keysrepair1).add(keysrepair2);
                            nonCompabilitySet.get(keysrepair2).add(keysrepair1);
                        }
            		}
            	}
            }
        }
        
        HashMap<Integer,Set<Integer>> CompabilitySetBinaire= removeAllElements(CompabilitySet, nonCompabilitySet);

        HashMap<Integer, Set<Integer>> CompabilitySetForTernaire = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
            CompabilitySetForTernaire.put(keys, new HashSet<>());
        }
        
        for (int keys : conflictternaire.keySet()) {
            List<Set<Integer>> combinations = getAllCombinations(conflictternaire.get(keys));
            for(int keyrepairs1 : allrepairs.keySet()) {
            	//Set<Integer> candidateCompabilitySetForTernaire = new HashSet<>(allrepairs.keySet());
            	for(int keyrepairs2 : CompabilitySetBinaire.get(keyrepairs1)) {
            		 if (allrepairs.get(keyrepairs1).containsAll(combinations.get(0)) && (allrepairs.get(keyrepairs2).containsAll(combinations.get(1)) || allrepairs.get(keyrepairs2).containsAll(combinations.get(2))))	{
            			 CompabilitySetForTernaire.get(keyrepairs1).add(keyrepairs2);          			 
            		 }else if(allrepairs.get(keyrepairs1).containsAll(combinations.get(1))&&(allrepairs.get(keyrepairs2).containsAll(combinations.get(2)))) {
            			 CompabilitySetForTernaire.get(keyrepairs1).add(keyrepairs2);
            		 }           		             		 
            	}
            	 //System.out.println(keyrepairs1+" : "+CompabilitySetForTernaire.get(keyrepairs1).size());
            }            

        }
        
        
        HashMap<Integer, Set<Integer>> nonCompabilitySetForTernaire = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
            nonCompabilitySetForTernaire.put(keys, new HashSet<>());
        }
        
        HashMap<Integer,Set<Integer>> setconflictternaire =new HashMap<>();
        for(int keys : conflictternaire.keySet()) {
        	Set<Integer> set = new HashSet<>();
	        for (int num : conflictternaire.get(keys)) {
	            set.add(num);
	        }
	        setconflictternaire.put(keys, set);
	        
        }

        	for(int keyrepairs1 : allrepairs.keySet()) {
        		for(int keyrepairs2 : CompabilitySetForTernaire.get(keyrepairs1)) {
        			for(int keys : setconflictternaire.keySet()) {
        				Set<Integer> compabilityset = new HashSet<>(allrepairs.get(keyrepairs1));
                		compabilityset.addAll(allrepairs.get(keyrepairs2));
                		Set<Integer> intersectionrepair = new HashSet<>(allrepairs.get(keyrepairs1));
                		intersectionrepair.retainAll(allrepairs.get(keyrepairs2));
                		compabilityset.removeAll(intersectionrepair);
                		if(compabilityset.containsAll(setconflictternaire.get(keys))) {
                			nonCompabilitySetForTernaire.get(keyrepairs1).add(keyrepairs2);
                		}
        			}
        		}
        	}
        
        for(int keys : CompabilitySetForTernaire.keySet()) {
        	CompabilitySetForTernaire.get(keys).removeAll(nonCompabilitySetForTernaire.get(keys));
        }
        
        return CompabilitySetForTernaire;
    }
	
	public static Set<Integer> getUnionOfIntArrayValues(HashMap<Integer, int[]> h1) {
        Set<Integer> s1 = new HashSet<>();

        for (int[] array : h1.values()) {
            for (int num : array) {
                s1.add(num);
            }
        }

        return s1;
    }
	
	
	public static HashMap<Integer, Set<Integer>> removeAllElements(HashMap<Integer, Set<Integer>> CompabilitySet,HashMap<Integer, Set<Integer>> nonCompabilitySet) {
		HashMap<Integer, Set<Integer>> result = new HashMap<>();
        // 遍历 nonCompabilitySet 中的每个键值对
		for (int keys : CompabilitySet.keySet() ) {
			Set<Integer> set = new HashSet<>(CompabilitySet.get(keys));
			set.removeAll(nonCompabilitySet.get(keys));
			result.put(keys, set);
		}
		
		
		return result;
    }
	
	public static List<Set<Integer>> getAllCombinations(int[] arr) {
        List<Set<Integer>> combinations = new ArrayList<>();

        // 遍历数组并获取两两元素组合
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                Set<Integer> combination = new HashSet<>();
                combination.add(arr[i]);
                combination.add(arr[j]);
                combinations.add(combination);
            }
        }
      
        return combinations;
    }
	//两hashmap中的相同set求交集
	public static HashMap<Integer, Set<Integer>> intersectionOfSets(HashMap<Integer, Set<Integer>> map1, HashMap<Integer, Set<Integer>> map2) {
        HashMap<Integer, Set<Integer>> result = new HashMap<>();

        // 找出两个 HashMap 中具有相同键的键值对
        for (Integer key : map1.keySet()) {
            if (map2.containsKey(key)) {
                // 对于相同的键值对，取出对应的 Set<Integer>，计算交集，并放入 result 中
                Set<Integer> set1 = map1.get(key);
                Set<Integer> set2 = map2.get(key);
                Set<Integer> intersection = new HashSet<>(set1);
                intersection.retainAll(set2);
                result.put(key, intersection);
            }
        }
        
        for(int key : result.keySet()) {
        	result.get(key).add(key);
        }

        return result;
    }
	
	
	public static HashMap<Integer, Set<Integer>> findOptimalRepairs(HashMap<Integer, Set<Integer>> inputMap) {
        // 用于存储最大大小的 Set<Integer> 的 HashMap
        HashMap<Integer, Set<Integer>> largestSets = new HashMap<>();

        // 用于跟踪最大大小
        int maxSize = 0;

        // 遍历输入的 HashMap 条目
        for (HashMap.Entry<Integer, Set<Integer>> entry : inputMap.entrySet()) {
            int setSize = entry.getValue().size();

            // 如果当前 Set<Integer> 大小大于最大大小，清除 largestSets 并添加当前 Set
            if (setSize > maxSize) {
                maxSize = setSize;
                largestSets.clear();
                largestSets.put(entry.getKey(), entry.getValue());
            } 
            // 如果当前 Set<Integer> 大小等于最大大小，添加当前 Set
            else if (setSize == maxSize) {
                largestSets.put(entry.getKey(), entry.getValue());
            }
        }

        return largestSets;
    }
	
	public static HashMap<Integer, Set<Integer>> combineMaps(HashMap<Integer, Set<Integer>> map1, HashMap<Integer, Set<Integer>> map2) {
		HashMap<Integer, Set<Integer>> result = new HashMap<>();
        int combinedKey =0;
        if(map1.isEmpty()) {
        	result = map2;
        }
        else {
        	for (int key1:map1.keySet()) {
                for (int key2 : map2.keySet()) {
                    Set<Integer> combinedSet = new HashSet<>(map1.get(key1));
                    combinedSet.addAll(map2.get(key2));
                    result.put(combinedKey, combinedSet);
                    combinedKey++;
                }
            }
        }
        

        return result;
    } 
	
	//检查cause是否符合某种semantic
		public static void semanticcheck(Set<Integer> cause,HashMap<Integer, Set<Integer>> compabilityset, String keyword){
			boolean result = false;
			switch (keyword) {
				case "AR":
					result = true;
					for(int key : compabilityset.keySet()) {
						if(!compabilityset.get(key).containsAll(cause)) {
							result = false;
							break;
						}
					}
					break;
				case "IAR":
					Set<Integer> interset = new HashSet<>(compabilityset.get(0));
					result = true;
					for(int key : compabilityset.keySet()) {
						interset.retainAll(compabilityset.get(key));
						if(!compabilityset.get(key).containsAll(cause)) {
							result = false;
							break;
						}
					}
					if(result == true) {
						if(interset.containsAll(cause)) {
							result=true;
						}else {
							result = false;
						}
					}
					
					break;
				case "brave":
					for(int key : compabilityset.keySet()) {
						if(compabilityset.get(key).containsAll(cause)) {
							result = true;
							break;
						}
					}
					break;
			}
			if(result == true) {
				System.out.println("It fits "+ keyword+" semantic.");
			}else {
				System.out.println("It does not fit "+ keyword+" semantic.");
			}
		}

	
}

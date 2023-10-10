package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Algorithm {
	
	//naive
	public static HashMap<Integer, Set<Integer>> naive(HashMap<Integer, List<Integer>> allrepairs, HashMap<Integer, int[]> conflictgraph) {	
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

        
        HashMap<Integer, Set<Integer>> CompabilitySetNonBinary = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
        	CompabilitySetNonBinary.put(keys, allrepairs.keySet());
        }
        
        HashMap<Integer, Set<Integer>> nonCompabilitySetNonBinary = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
        	nonCompabilitySetNonBinary.put(keys, new HashSet<>());
        }
        
        for(int keys : conflictgraph.keySet()) {
        	for (int keyrepairs1 : allrepairs.keySet()) {
        		for (int keyrepairs2 : allrepairs.keySet()) {
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

        return SetNonBinary;
    }
	
	//optimize
	public static HashMap<Integer, Set<Integer>> optimize(HashMap<Integer, List<Integer>> allrepairs, HashMap<Integer, int[]> conflictgraph) {

		HashMap<Integer, Set<Integer>> compabilitysets = new HashMap<>();
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
        
        HashMap<Integer, Set<Integer>> CompabilitySet = new HashMap<>();
        for (int keys : allrepairs.keySet()) {
            CompabilitySet.put(keys, allrepairs.keySet());
        }
        
        
        if(conflictbinaire.keySet().size()==conflictgraph.keySet().size()) {

        	return CompabilitySet;
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

        return compabilitysets;
    }
	
	public static  Set<Integer> cardinality(HashMap<Integer, List<Integer>>  allrepairs,HashMap<Integer, int[]> dataMap){
		 Set<Integer> cardinalityset = new HashSet<>();

		int maxlength=0;
		
		for (int keyrepairs1 : allrepairs.keySet()) {
    		if(allrepairs.get(keyrepairs1).size()>maxlength) {
    			cardinalityset.clear();
    			cardinalityset.add(keyrepairs1);
    			maxlength = allrepairs.get(keyrepairs1).size();
    		}else if(allrepairs.get(keyrepairs1).size()==maxlength) {
    			cardinalityset.add(keyrepairs1);
    		}
    	}
		
		return cardinalityset;
	}

	
	//for conflict binary and ternary。
 	public static HashMap<Integer, Set<Integer>> ternaire(HashMap<Integer, List<Integer>>  allrepairs1,HashMap<Integer, int[]> dataMap) {
		HashMap<Integer, List<Integer>>  allrepairs = allrepairs1;
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
		for (int keys : CompabilitySet.keySet() ) {
			Set<Integer> set = new HashSet<>(CompabilitySet.get(keys));
			set.removeAll(nonCompabilitySet.get(keys));
			result.put(keys, set);
		}
		
		
		return result;
    }
	
	public static List<Set<Integer>> getAllCombinations(int[] arr) {
        List<Set<Integer>> combinations = new ArrayList<>();

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
	public static HashMap<Integer, Set<Integer>> intersectionOfSets(HashMap<Integer, Set<Integer>> map1, HashMap<Integer, Set<Integer>> map2) {
        HashMap<Integer, Set<Integer>> result = new HashMap<>();

        for (Integer key : map1.keySet()) {
            if (map2.containsKey(key)) {
            	
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
        HashMap<Integer, Set<Integer>> largestSets = new HashMap<>();

        int maxSize = 0;

        for (HashMap.Entry<Integer, Set<Integer>> entry : inputMap.entrySet()) {
            int setSize = entry.getValue().size();

            if (setSize > maxSize) {
                maxSize = setSize;
                largestSets.clear();
                largestSets.put(entry.getKey(), entry.getValue());
            } 
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

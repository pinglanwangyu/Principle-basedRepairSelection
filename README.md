# Principle-based Repair Selection

The objective of this project is to extract preferred repairs from inconsistent knowledge bases using specific strategies, with the classical cardinality-based strategy as the conventional approach. In particular, we are implementing a compatibility-based strategy that evaluates all pairs of repairs based on a criterion: a preference for repairs exhibiting higher compatibility with other repairs, implying they are 'opposed' by fewer repairs. The formal definition of the compatibility-based strategy will be provided in the near future.

To download the code to your own computer, you should clone it using Git.  
To do this, run the following in your terminal:

    git clone https://github.com/pinglanwangyu/Principle-basedRepairSelection.git

To find compatibility based optimal repairs, you should run this in your terminal:
    
    java -jar compatibility_1min.jar

Then you can find the optimal repairs in the file "optimalrepairs.json" and other information in the file "result_1mn.txt"

We have already given the conflictgraph and the minimal hitting set as input of compatibility_1min.jar:  

    conflicts.json
    hittingsets.json

You can rebuild these two files by following the steps in the notebook:
 
    pysat_for_hittingset.ipynb
     

    
##Test Dataset:

We construct a knowledge base from the
National Downloadable File provided by [the Centers for Medicare & Medicaid Services](https://data.cms.gov/provider-data). It contains information on
medical professionals and their affiliations. We use the following [provsql](https://github.com/PierreSenellart/provsql.git) query to stipulate
that the same location cannot have more than a certain number of physicians. This addition enables us to generate a substantial
number of non-binary conflicts:

Select formula(provenance(), 'assertion_mapping')  
&emsp;&emsp;    From grouppractice as g1,grouppractice as g2   
&emsp;&emsp;    Where g1.zip = g2.zip   
&emsp;&emsp;&emsp;&emsp;         And g1.cty = g2.cty   
&emsp;&emsp;&emsp;&emsp;         And g1.st = g2.st    
&emsp;&emsp;&emsp;&emsp;         And g1.org_pac_id != g2.org_pac_id ;    


We selected a set of conflicts whose sizes range from 4 to 51. In total, we obtained 35710 (9134 binary and 26576 non-binary) conflicts, i.e. medical_conflictgrpha.dat,
the input of pysat_for_hittingset.ipynb, which results in 1888 connected components. 

 

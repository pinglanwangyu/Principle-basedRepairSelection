# Principle-based Repair Selection

To download the code to your own computer, you should clone it using Git.  
To do this, run the following in your terminal:

    git clone https://github.com/pinglanwangyu/Principle-basedRepairSelection.git

To find compatibility based optimal repair, you shuold run this in your terminal:
    
    java -jar compatibility_1min

Then you can find optimal repairs in file "optimalrepairs.json" and other information in file "result_1mn.txt"

We have already given the conflictgraph and minimal hitting set as input for java.  
You can rebuild these files by following the steps in the notebook:
 
    pysat_for_hittingset.ipynb
     

    
Test Dataset:

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

 

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

import functions.generate_graphs as gg
import functions.hyphotesis_tester as ht

# Load CSV files

# global
stats = pd.read_csv("../Stats.csv")

# json

jGP = pd.read_csv("../baseline/json_get_plan_data.csv")

jGSP = pd.read_csv("../baseline/json_get_specific_plan_data.csv")

jPCP = pd.read_csv("../baseline/json_post_plan_data.csv")

jPCS = pd.read_csv("../baseline/json_post_subs_data.csv")

# protobuf

pGP = pd.read_csv("../baseline/protobuf_get_plan_data.csv")

pGSP = pd.read_csv("../baseline/protobuf_get_specific_plan_data.csv")

pPCP = pd.read_csv("../baseline/protobuf_post_plan_data.csv")

pPCS = pd.read_csv("../baseline/protobuf_post_subs_data.csv")

#gg.create_min_max_mean_graph(pGP,jGP, "Baseline GET Plan Elapsed Time", "elapsed" ,"Elapsed Time (ms)")
#gg.create_min_max_mean_graph(pGP,jGP, "Baseline GET Plan Bytes", "bytes" ,"Bytes")
#gg.create_one_pain_plot(stats,[0,12],"Baseline GET Plan Throughput", "Throughput","Throughput /sec")
#gg.create_one_pain_plot(stats,[0,12],"Baseline GET Plan Error ", "Error","Error %", percetange=True) # Error is 0 for both

ht.hyphtesis_test([pGP['elapsed'],jGP['elapsed']],"There is no significant difference between the performance of the versions of the application", "There is a significant difference between the performance of the versions of the application")


ht.hyphtesis_test([pGP['elapsed'],jGP['elapsed']],"Protobuf is not faster then JSON", "Protobuf is faster then JSON",'less')

ht.hyphtesis_test([pGP['bytes'],jGP['bytes']],"Protobuf does not result in fewer received bytes compared to JSON", "Protobuf results in fewer received bytes compared to JSON",'less')
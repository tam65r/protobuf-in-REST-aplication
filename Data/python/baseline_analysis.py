import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

import functions.generate_graphs as gg

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

gg.create_min_max_mean_graph(pGP,jGP, "Baseline GET Plan Elapsed Time", "elapsed" ,"Elapsed Time (ms)")
gg.create_min_max_mean_graph(pGP,jGP, "Baseline GET Plan Bytes", "bytes" ,"Bytes")
gg.create_one_pain_plot(stats,[0,12],"Baseline GET Plan Throughput", "Throughput","Throughput /sec")
gg.create_one_pain_plot(stats,[0,12],"Baseline GET Plan Error ", "Error","Error %", percetange=True) # Error is 0 for both
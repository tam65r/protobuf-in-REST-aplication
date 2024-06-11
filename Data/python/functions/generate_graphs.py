import matplotlib.pyplot as plt
import numpy as np
import pandas as pd


def create_min_max_mean_graph(pGP, jGP, title, column, yLabel, xLabel = "Format", labels = ["Protobuf", "JSON"], colors = ["plum","cyan"], loc = "upper left"):
    
    min_values = [pGP[column].min(), jGP[column].min()]
    mean_values = [pGP[column].mean(), jGP[column].mean()]
    max_values = [pGP[column].max(), jGP[column].max()]

    bar_labels = ['Min', 'Mean', 'Max']
    protobuf = [min_values[0], mean_values[0], max_values[0]]
    json = [min_values[1], mean_values[1], max_values[1]]

    x = np.arange(len(bar_labels)) 
    width = 0.35  

    fig, ax = plt.subplots()
    rects1 = ax.bar(x - width/2, protobuf, width, label=labels[0], color=colors[0])
    rects2 = ax.bar(x + width/2, json, width, label=labels[1], color=colors[1])

 
    ax.set_xlabel(xLabel)
    ax.set_ylabel(yLabel)
    ax.set_title(title)
    ax.set_xticks(x)
    ax.set_xticklabels(bar_labels)
    ax.legend(loc=loc)
    ax.set_ylim(0,(max(max_values)+ max(max_values)*(1/3)))


    def autolabel(rects):
        for rect in rects:
            height = rect.get_height()
            ax.annotate('{}'.format(round(height, 2)),
                        xy=(rect.get_x() + rect.get_width() / 2, height),
                        xytext=(0, 3),
                        textcoords="offset points",
                        ha='center', va='bottom')

    autolabel(rects1)
    autolabel(rects2)

    fig.tight_layout()

    plt.show()
    
    
def create_one_pain_plot(stats, indexes, title, column, yLabel, xLabel = "Format", labels = ["Protobuf", "JSON"], colors = ["plum","cyan"], loc = "upper right", percetange = False):

    
        

    data = [stats[column][indexes[0]], stats[column][indexes[1]]]  

    fig, ax = plt.subplots()
    bars = ax.bar(labels, data, color=colors)

    ax.set_xlabel(xLabel)
    ax.set_ylabel(yLabel)
    ax.set_title(title)
    if not percetange:
        ax.set_ylim(0, max(data) + max(data) / 3)
    else:
        ax.set_ylim(0, 125)

    

    for bar, value in zip(bars, data):
        yval = bar.get_height()
        ax.text(bar.get_x() + bar.get_width() / 2.0, yval + 1, value, ha='center', va='bottom', color='black')

    ax.legend(bars, labels, loc=loc)

    plt.show()
    
    
def create_one_pain_plot_v2(stats1, stats2, indexes, title, column, yLabel, xLabel = "Format", labels = ["Protobuf", "JSON"], colors = ["plum","cyan"], loc = "upper right", percetange = False):

    
    data = [float(stats1[column][indexes[0]].replace(",", ".")), float(stats2[column][indexes[1]].replace(",", "."))]  

    fig, ax = plt.subplots()
    bars = ax.bar(labels, data, color=colors)

    ax.set_xlabel(xLabel)
    ax.set_ylabel(yLabel)
    ax.set_title(title)
    if not percetange:
        ax.set_ylim(0, max(data) + max(data) / 3)
    else:
        ax.set_ylim(0, 125)

    

    for bar, value in zip(bars, data):
        yval = bar.get_height()
        ax.text(bar.get_x() + bar.get_width() / 2.0, yval + 1, value, ha='center', va='bottom', color='black')

    ax.legend(bars, labels, loc=loc)

    plt.show()
    
    
def create_min_max_mean_graph_v2(pGP, jGP, pGPv2, title, column, yLabel, xLabel="Format", labels=["Protobuf", "JSON", "ProtobufV2"], colors=["plum", "cyan", "salmon"], loc="upper left", spacing = 0.05, width  = 2):

    min_values = [pGP[column].min(), jGP[column].min(), pGPv2[column].min()]
    mean_values = [pGP[column].mean(), jGP[column].mean(), pGPv2[column].mean()]
    max_values = [pGP[column].max(), jGP[column].max(), pGPv2[column].max()]

    bar_labels = ['Min', 'Mean', 'Max']
    protobuf = [min_values[0], mean_values[0], max_values[0]]
    json = [min_values[1], mean_values[1], max_values[1]]
    protobufV2 = [min_values[2], mean_values[2], max_values[2]]

    x = np.arange(len(bar_labels))
    width = 0.2

    fig, ax = plt.subplots()
    rects1 = ax.bar(x - width - spacing, protobuf, width, label=labels[0], color=colors[0])
    rects2 = ax.bar(x, json, width, label=labels[1], color=colors[1])
    rects3 = ax.bar(x + width + spacing, protobufV2, width, label=labels[2], color=colors[2])

    ax.set_xlabel(xLabel)
    ax.set_ylabel(yLabel)
    ax.set_title(title)
    ax.set_xticks(x)
    ax.set_xticklabels(bar_labels)
    ax.legend(loc=loc)
    ax.set_ylim(0, max(max_values) + max(max_values) * (1/3))

    def autolabel(rects):
        for rect in rects:
            height = rect.get_height()
            ax.annotate('{}'.format(round(height, 2)),
                        xy=(rect.get_x() + rect.get_width() / 2, height),
                        xytext=(0, 3),
                        textcoords="offset points",
                        ha='center', va='bottom')

    autolabel(rects1)
    autolabel(rects2)
    autolabel(rects3)

    fig.tight_layout()

    plt.show()
    
    
def create_one_pain_plot_v3(stats1, stats2, stats3, indexes, title, column, yLabel, xLabel = "Format", labels = ["Protobuf", "JSON", "ProtobufV2"], colors = ["plum","cyan","salmon"], loc = "upper right", percetange = False):

    
    data = [float(stats1[column][indexes].replace(",", ".")), float(stats2[column][indexes].replace(",", ".")), float(stats3[column][indexes].replace(",", "."))]  

    fig, ax = plt.subplots()
    bars = ax.bar(labels, data, color=colors)

    ax.set_xlabel(xLabel)
    ax.set_ylabel(yLabel)
    ax.set_title(title)
    if not percetange:
        ax.set_ylim(0, max(data) + max(data) / 3)
    else:
        ax.set_ylim(0, 125)

    

    for bar, value in zip(bars, data):
        yval = bar.get_height()
        ax.text(bar.get_x() + bar.get_width() / 2.0, yval + 1, value, ha='center', va='bottom', color='black')

    ax.legend(bars, labels, loc=loc)

    plt.show()
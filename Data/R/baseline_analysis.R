library(readr)

## Global Stats

stats <- read_csv("../Stats.csv")
View(stats)

## Load JSON files

jGP <- read_csv("../baseline/json_get_plan_data.csv")
View(jGP)


jGSP <- read_csv("../baseline/json_get_specific_plan_data.csv")
View(jGSP)


jPCP <- read_csv("../baseline/json_post_plan_data.csv")
View(jPCP)


jPCS <- read_csv("../baseline/json_post_subs_data.csv")
View(jPCS)


## Load Protocol Buffers files

pGP <- read_csv("../baseline/protobuf_get_plan_data.csv")
View(pGP)


pGSP <- read_csv("../baseline/protobuf_get_specific_plan_data.csv")
View(pGSP)


pPCP <- read_csv("../baseline/protobuf_post_plan_data.csv")
View(pPCP)


pPCS <- read_csv("../baseline/protobuf_post_subs_data.csv")
View(pPCS)



#### MEAN

## MEAN Elapsed

# JSON

mTimeJGP<-mean(jGP$elapsed)
mTimeJGSP<-mean(jGSP$elapsed)
mTimeJPCP<-mean(jPCP$elapsed)
mTimeJPCS<-mean(jPCS$elapsed)


# Protobuf

mTimePGP<-mean(pGP$elapsed)
mTimePGSP<-mean(pGSP$elapsed)
mTimePPCP<-mean(pPCP$elapsed)
mTimePPCS<-mean(pPCS$elapsed)

## MEAN Bytes

# JSON

mBytesJGP<-mean(jGP$bytes)
mBytesJGSP<-mean(jGSP$bytes)
mBytesJPCP<-mean(jPCP$bytes)
mBytesJPCS<-mean(jPCS$bytes)


# Protobuf

mBytesPGP<-mean(pGP$bytes)
mBytesPGSP<-mean(pGSP$bytes)
mBytesPPCP<-mean(pPCP$bytes)
mBytesPPCS<-mean(pPCS$bytes)


#### Bar Graphs

### Get Plan

## Time

# Graph Title

title <- "Baseline GET Plan Elapsed Time"

# Graph Data

data <- round(c(mTimePGP, mTimeJGP),2)

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Elapsed Time (ms)")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)


## Bytes


# Graph Title

title <- "Baseline GET Plan Bytes"

# Graph Data
data <- round(c(mBytesPGP, mBytesJGP), 2)

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Bytes")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)

## Throughput


# Graph Title

title <- "Baseline GET Plan Throughput"

# Graph Data
data <- c(stats$Throughput[1],stats$Throughput[13])

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Throughput /sec ")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)





### Get Specific Plan

## Time

# Graph Title

title <- "Baseline GET Specific Plan Elapsed Time"

# Graph Data

data <- round(c(mTimePGSP, mTimeJGSP),2)

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Elapsed Time (ms)")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)


## Bytes


# Graph Title

title <- "Baseline GET Specific Plan Bytes"

# Graph Data
data <- round(c(mBytesPGSP, mBytesJGSP), 2)

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Bytes")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)

## Throughput


# Graph Title

title <- "Baseline GET Specific Plan Throughput"

# Graph Data
data <- c(stats$Throughput[2],stats$Throughput[14])

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Throughput /sec ")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)



### POST Subscriber

## Time

# Graph Title

title <- "Baseline POST Subscriber Elapsed Time"

# Graph Data

data <- round(c(mTimePPCS, mTimeJPCS),2)

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Elapsed Time (ms)")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)


## Bytes


# Graph Title

title <- "Baseline POST Subscriber Bytes"

# Graph Data
data <- round(c(mBytesPPCS, mBytesJPCS), 2)

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Bytes")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)

## Throughput


# Graph Title

title <- "Baseline POST Subscriber Throughput"

# Graph Data
data <- c(stats$Throughput[3],stats$Throughput[15])

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Throughput /sec ")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)




### POST Plan

## Time

# Graph Title

title <- "Baseline POST Plan Elapsed Time"

# Graph Data

data <- round(c(mTimePPCP, mTimeJPCP),2)

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Elapsed Time (ms)")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)


## Bytes


# Graph Title

title <- "Baseline POST Plan Bytes"

# Graph Data
data <- round(c(mBytesPPCP, mBytesJPCP), 2)

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Bytes")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)

## Throughput


# Graph Title

title <- "Baseline POST Plan Throughput"

# Graph Data
data <- c(stats$Throughput[4],stats$Throughput[16])

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Throughput /sec ")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)



##### Hypothesis Tests

#Null Hypothesis (H0): There is no significant difference between the performance of the two formats
#Alternative Hypothesis (H1): There is a significant difference between the performance of the two formats

### Significance Level p-value < 0.05

## Get Plan

library(nortest)

lillie.test(jGP$elapsed)
lillie.test(pGP$elapsed)

valor_p <- wilcox.test(pGP$elapsed,jGP$elapsed, paired=FALSE)$p.value
valor_p # 2.858037e-11, H1

valor_pBytes <- wilcox.test(pGP$bytes,jGP$bytes, paired=FALSE)$p.value
valor_pBytes # 0, H1

## Get Specific Plan

valor_p <- wilcox.test(pGSP$elapsed,jGSP$elapsed, paired=FALSE)$p.value
valor_p # 4.551959e-09, H1

valor_pBytes <- wilcox.test(pGSP$bytes,jGSP$bytes, paired=FALSE)$p.value
valor_pBytes # 0, H1

## Post Subscriber

valor_p <- wilcox.test(pPCS$elapsed,jPCS$elapsed, paired=FALSE)$p.value
valor_p #  0.0147173, H1

valor_pBytes <- wilcox.test(pPCS$bytes,jPCS$bytes, paired=FALSE)$p.value
valor_pBytes # 0, H1

## Post Plan

valor_p <- wilcox.test(pPCP$elapsed,jPCP$elapsed, paired=FALSE)$p.value
valor_p # 1.162059e-26, H1

valor_pBytes <- wilcox.test(pPCP$bytes,jPCP$bytes, paired=FALSE)$p.value
valor_pBytes # 0, H1



library(readr)

## Global Stats

stats <- read_csv("../Stats.csv")
View(stats)

## Stress JSON files

jGP <- read_csv("../stress/json_get_plan_data.csv")
View(jGP)


jGSP <- read_csv("../stress/json_get_specific_plan_data.csv")
View(jGSP)


jPCP <- read_csv("../stress/json_post_plan_data.csv")
View(jPCP)


jPCS <- read_csv("../stress/json_post_subs_data.csv")
View(jPCS)


## Stress Protocol Buffers files

pGP <- read_csv("../stress/protobuf_get_plan_data.csv")
View(pGP)


pGSP <- read_csv("../stress/protobuf_get_specific_plan_data.csv")
View(pGSP)


pPCP <- read_csv("../stress/protobuf_post_plan_data.csv")
View(pPCP)


pPCS <- read_csv("../stress/protobuf_post_subs_data.csv")
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

title <- "Stress GET Plan Elapsed Time"

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
# Calculate median values
medians <- c(median(pGP$elapsed),median(jGP$elapsed))

for (i in 1:length(medians)) {
  # Add a line for the current median value
  lines(x = c(graph[i] - 0.4, graph[i] + 0.4), y = c(medians[i], medians[i]), col = "black", lwd = 2)
  
  # Add text label for the current median value
  text(x = graph[i], y = medians[i] + 1, labels = medians[i], pos = 3, col = "black")
}
# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)


## Bytes


# Graph Title

title <- "Stress GET Plan Bytes"

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

title <- "Stress GET Plan Throughput"

# Graph Data
data <- c(stats$Throughput[9],stats$Throughput[21])

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


## Error


# Graph Title

title <- "Stress GET Plan Error"

# Graph Data
data <- c(stats$Error[9],stats$Error[21])

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Error %")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)




### Get Specific Plan

## Time

# Graph Title

title <- "Stress GET Specific Plan Elapsed Time"

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

title <- "Stress GET Specific Plan Bytes"

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

title <- "Stress GET Specific Plan Throughput"

# Graph Data
data <- c(stats$Throughput[10],stats$Throughput[22])

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

## Error


# Graph Title

title <- "Stress GET Specific Plan Error"

# Graph Data
data <- c(stats$Error[10],stats$Error[22])

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Error %")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)



### POST Subscriber

## Time

# Graph Title

title <- "Stress POST Subscriber Elapsed Time"

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

title <- "Stress POST Subscriber Bytes"

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

title <- "Stress POST Subscriber Throughput"

# Graph Data
data <- c(stats$Throughput[11],stats$Throughput[23])

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

## Error


# Graph Title

title <- "Stress POST Subscriber Error"

# Graph Data
data <- c(stats$Error[11],stats$Error[23])

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Error %")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data, labels = data, pos = 3, col = "black")

# Graph Color Legend
legend("topright", legend = names, fill = bar_colors)


### POST Plan

## Time

# Graph Title

title <- "Stress POST Plan Elapsed Time"

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

title <- "Stress POST Plan Bytes"

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

title <- "Stress POST Plan Throughput"

# Graph Data
data <- c(stats$Throughput[12],stats$Throughput[24])

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

## Error


# Graph Title

title <- "Stress POST Plan Error"

# Graph Data
data <- c(stats$Error[12],stats$Error[24])

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Data Format","Error %")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[1], ylab = axis[2], col = bar_colors, main = title ,ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data, labels = data, pos = 3, col = "black")

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
valor_p # 3.360227e-198, H1

valor_pBytes <- wilcox.test(pGP$bytes,jGP$bytes, paired=FALSE)$p.value
valor_pBytes # 2.416059e-55, H1

## Get Specific Plan

valor_p <- wilcox.test(pGSP$elapsed,jGSP$elapsed, paired=FALSE)$p.value
valor_p #  3.031833e-08, H1

valor_pBytes <- wilcox.test(pGSP$bytes,jGSP$bytes, paired=FALSE)$p.value
valor_pBytes # 0, H1

## Post Subscriber

valor_p <- wilcox.test(pPCS$elapsed,jPCS$elapsed, paired=FALSE)$p.value
valor_p #  9.945679e-84, H1

valor_pBytes <- wilcox.test(pPCS$bytes,jPCS$bytes, paired=FALSE)$p.value
valor_pBytes # 0, H1

## Post Plan

valor_p <- wilcox.test(pPCP$elapsed,jPCP$elapsed, paired=FALSE)$p.value
valor_p # 0, H1

valor_pBytes <- wilcox.test(pPCP$bytes,jPCP$bytes, paired=FALSE)$p.value
valor_pBytes # 0, H1



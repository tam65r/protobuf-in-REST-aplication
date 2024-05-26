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


## Bar Graphs

# Graph Data
data <- c(10, 15)  # Replace with your actual data points

# Bar Names Labels
names <- c("Protobuf", "JSON")

# Bar Axis Names

axis <- c("Elapsed Time (ms)", "Data Format")

# Custom colors for the bars
bar_colors <- c("plum2", "cyan3")

# Create Bar
graph <- barplot(data, names.arg = names, xlab = axis[0], ylab = axis[1], col = bar_colors, main = "Comparison of JSON and Protobuf",ylim = c(0, max(data) + max(data)/3))

# Add Value For Each Bar
text(x = graph, y = data + 1, labels = data, pos = 3, col = "black")

# Graph Color Lengeda
legend("topright", legend = names, fill = bar_colors)



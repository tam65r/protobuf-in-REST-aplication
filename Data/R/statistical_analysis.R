library(readr)

## Load JSON files

jsonGetPlanData <- read_csv("../json_get_plan_data.csv")
View(jsonGetPlanData)


jsonGetSpecificPlanData <- read_csv("../json_get_specific_plan_data.csv")
View(jsonGetSpecificPlanData)


jsonGetSpecificPlanDataV2 <- read_csv("../json_get_specific_plan_data_v2.csv")
View(jsonGetSpecificPlanDataV2)


jsonPostCreatePlanData <- read_csv("../json_post_create_plan_data.csv")
View(jsonPostCreatePlanData)


jsonPostCreateSubscriberData <- read_csv("../json_post_create_subscriber_data.csv")
View(jsonPostCreatePlanData)


## Load Protocol Buffers files

protobufGetPlanData <- read_csv("../protobuf_get_plan_data.csv")
View(protobufGetPlanData)


protobufGetSpecificPlanData <- read_csv("../protobuf_get_specific_plan_data.csv")
View(protobufGetSpecificPlanData)


protobufGetSpecificPlanDataV2 <- read_csv("../protobuf_get_specific_plan_data_v2.csv")
View(protobufGetSpecificPlanDataV2)


protobufPostCreatePlanData <- read_csv("../protobuf_post_create_plan_data.csv")
View(protobufPostCreatePlanData)


protobufPostCreateSubscriberData <- read_csv("../protobuf_post_create_subscriber_data.csv")
View(protobufPostCreateSubscriberData)


## Calculate MEAN for every data

# JSON

meanJsonGetPlanData<-mean(jsonGetPlanData$elapsed)
meanJsonGetSpecificPlanData<-mean(jsonGetSpecificPlanData$elapsed)

meanBytesJsonGetSpecificPlanData<-mean(jsonGetSpecificPlanData$bytes)

meanJsonGetSpecificPlanDataV2<-mean(jsonGetSpecificPlanDataV2$elapsed)
meanJsonPostCreatePlanData<-mean(jsonPostCreatePlanData$elapsed)
meanJsonPostCreateSubscriberData<-mean(jsonPostCreateSubscriberData$elapsed)


# Protobuf

meanProtobufGetPlanData<-mean(protobufGetPlanData$elapsed)
meanProtobufGetSpecificPlanData<-mean(protobufGetSpecificPlanData$elapsed)

meanBytesProtobufGetSpecificPlanData<-mean(protobufGetSpecificPlanData$bytes)

meanProtobufGetSpecificPlanDataV2<-mean(protobufGetSpecificPlanDataV2$elapsed)
meanProtobufPostCreatePlanData<-mean(protobufPostCreatePlanData$elapsed)
meanProtobufPostCreateSubscriberData<-mean(protobufPostCreateSubscriberData$elapsed)


## Calculate MAX for every data

# JSON

max(jsonGetPlanData$elapsed)
max(jsonGetSpecificPlanData$elapsed)
max(jsonGetSpecificPlanDataV2$elapsed)
max(jsonPostCreatePlanData$elapsed)
max(jsonPostCreateSubscriberData$elapsed)


# Protobuf

max(protobufGetPlanData$elapsed)
max(protobufGetSpecificPlanData$elapsed)
max(protobufGetSpecificPlanDataV2$elapsed)
max(protobufPostCreatePlanData$elapsed)
max(protobufPostCreateSubscriberData$elapsed)


## Calculate MIN for every data

# JSON

min(jsonGetPlanData$elapsed)
min(jsonGetSpecificPlanData$elapsed)
min(jsonGetSpecificPlanDataV2$elapsed)
min(jsonPostCreatePlanData$elapsed)
min(jsonPostCreateSubscriberData$elapsed)


# Protobuf

min(protobufGetPlanData$elapsed)
min(protobufGetSpecificPlanData$elapsed)
min(protobufGetSpecificPlanDataV2$elapsed)
min(protobufPostCreatePlanData$elapsed)
min(protobufPostCreateSubscriberData$elapsed)


#GRÁFICO DE BARRAS
#GetPlanData


dados <- c(meanProtobufGetPlanData, meanJsonGetPlanData)
categorias <- c("Protocol Buffers", "JSON")

# Cores para as barras
cores <- c("red", "cyan")

# Criar o gráfico de barras com cores diferentes
grafico <- barplot(dados, names.arg = categorias, xlab = "Média", ylab = "Tempo de Resposta (ms)", col = cores,ylim = c(0, max(dados) + 100))

# Adicionar números acima das barras
text(x = grafico, y = dados + 50, labels = dados, pos = 3, col = "black")
# Adicionar legenda
legend("topright", legend = categorias, fill = cores)



#GRÁFICO DE BARRAS
#get Specific Plan Data


dados <- c(meanProtobufGetSpecificPlanData, meanJsonGetSpecificPlanData)
categorias <- c("Protocol Buffers", "JSON")

# Cores para as barras
cores <- c("red", "cyan")

# Criar o gráfico de barras com cores diferentes
grafico <- barplot(dados, names.arg = categorias, xlab = "Média", ylab = "Tempo de Resposta (ms)", col = cores,ylim = c(0, max(dados) + 100))

# Adicionar números acima das barras
text(x = grafico, y = dados + 10, labels = dados, pos = 3, col = "black")
# Adicionar legenda
legend("topright", legend = categorias, fill = cores)


#GRÁFICO DE BARRAS
#get Specific Plan Data V2


dados <- c(meanProtobufGetSpecificPlanDataV2, meanJsonGetSpecificPlanDataV2)
categorias <- c("Protocol Buffers", "JSON")

# Cores para as barras
cores <- c("red", "cyan")

# Criar o gráfico de barras com cores diferentes
grafico <- barplot(dados, names.arg = categorias, xlab = "Média", ylab = "Tempo de Resposta (ms)", col = cores,ylim = c(0, max(dados) + 2000))

# Adicionar números acima das barras
text(x = grafico, y = dados + 10, labels = dados, pos = 3, col = "black")
# Adicionar legenda
legend("topright", legend = categorias, fill = cores)


#GRÁFICO DE BARRAS
#POST Create Plan Data 


dados <- c(meanProtobufPostCreatePlanData, meanJsonPostCreatePlanData)
categorias <- c("Protocol Buffers", "JSON")

# Cores para as barras
cores <- c("red", "cyan")

# Criar o gráfico de barras com cores diferentes
grafico <- barplot(dados, names.arg = categorias, xlab = "Média", ylab = "Tempo de Resposta (ms)", col = cores,ylim = c(0, max(dados) + 100))

# Adicionar números acima das barras
text(x = grafico, y = dados + 10, labels = dados, pos = 3, col = "black")
# Adicionar legenda
legend("topright", legend = categorias, fill = cores)


#GRÁFICO DE BARRAS
#POST Create user Data 


dados <- c(meanProtobufPostCreateSubscriberData, meanJsonPostCreateSubscriberData)
categorias <- c("Protocol Buffers", "JSON")

# Cores para as barras
cores <- c("red", "cyan")

# Criar o gráfico de barras com cores diferentes
grafico <- barplot(dados, names.arg = categorias, xlab = "Média", ylab = "Tempo de Resposta (ms)", col = cores,ylim = c(0, max(dados) + 2000))

# Adicionar números acima das barras
text(x = grafico, y = dados + 10, labels = dados, pos = 3, col = "black")
# Adicionar legenda
legend("topright", legend = categorias, fill = cores)



#GRÁFICO DE BARRAS
#bytes


dados <- c(meanBytesProtobufGetSpecificPlanData, meanBytesJsonGetSpecificPlanData)
categorias <- c("Protocol Buffers", "JSON")

# Cores para as barras
cores <- c("red", "cyan")

# Criar o gráfico de barras com cores diferentes
grafico <- barplot(dados, names.arg = categorias, xlab = "Média", ylab = "Tempo de Resposta (ms)", col = cores,ylim = c(0, max(dados) + 2000))

# Adicionar números acima das barras
text(x = grafico, y = dados + 10, labels = dados, pos = 3, col = "black")
# Adicionar legenda
legend("topright", legend = categorias, fill = cores)

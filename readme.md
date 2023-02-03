# 程序运行指南

## data文件夹

  该文件夹与我在HDFS中构建的文件夹具有相同的架构，如果没有对"fs.defaultFS"值进行改动，那么可以按照该目录构建与我相同的HDFS文件架构。

## CloseContact文件夹

  该文件夹内含一个IntelliJ IDEA JAVA项目，并有着Maven架构。如果发现pom.xml出现问题，请重新加载Maven项目并等待相关的jar包下载完成。

  程序中，接受一个参数，作为本地文件inf.csv的输入，默认是在项目的父文件夹中。

## STest文件夹

  该文件夹内含一个IntelliJ IDEA Scala项目，并有着Maven架构。其中，main.scala程序的22行接受一个inf.csv路径的输入。

## MapRed.png

  该文件为电脑绘图软件制作的，MapReduce程序的流程图。

## test.ipynb

  该文件为Python文件，建议通过安装Visual Studio Code的Python插件包打开，或者使用Jupyter Notebook 相关命令行功能。该文件为Spark MLlib KMeans的实现。

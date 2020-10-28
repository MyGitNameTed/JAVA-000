# 第二周：JVM进阶/NIO技术

## JVM核心技术（三）：调优分析

### 演示GC日志生成与解读

- 编译
```
- 本机OK
javac GCLogAnalysis.java
- 本机错误
javac -encoding UTF-8 GCLogAnalysis.java
```

#### 执行

##### 日志显示
- 显示1
```
java -XX:+PrintGCDetails GCLogAnalysis
```

- 显示2
```
java -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
```

##### 内存调整

- 调整:128m
```
java -Xmx128m -Xloggc:gc.demo128-1.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails GCLogAnalysis 异常：OOM 总GC：44次 GC次数：10次 Full GC次数：34次
```

- 调整:512m
```
java -Xmx512m -Xloggc:gc.demo512-1.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails GCLogAnalysis
```

- 调整:1024m
```
java -Xmx1024m -Xloggc:gc.demo1024-1.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails GCLogAnalysis
```

- 调整:2048m
```
java -Xmx2048m -Xloggc:gc.demo2048-1.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails GCLogAnalysis

java -Xmx2g -Xloggc:gc.demo2048-1.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails GCLogAnalysis
```

- 调整:4096m
```
java -Xmx4g -Xloggc:gc.demo4096-1.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails GCLogAnalysis
```

#### 解读与分析

Young GC OR (Minor GC 小型GC)

Full GC  OR (Major GC 大型GC)

清屏操作：cls
##### 串行GC
```
- 128m 
试验三次 均发生 OOM
java -XX:+UseSerialGC -Xms128m -Xmx128m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.serial128-1.log GCLogAnalysis

- 512m
java -XX:+UseSerialGC -Xms512m -Xmx512m -Xloggc:gc.serial512-1.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

java -XX:+UseSerialGC -Xms512m -Xmx512m -Xloggc:gc.serial512-2.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

- 2g
java -XX:+UseSerialGC -Xms2g -Xmx2g -Xloggc:gc.serial2g-1.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
```

- 总结
Minor GC
```
GC时 清空新生代和 当前使用存活区，复制到空闲存活区
```

- Major GC
```
GC时 堆内存无下降 濒临OOM
```


##### 并行GC
```
- 512m
java -XX:+UseParallelGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.parallel512-1.log GCLogAnalysis

- 2g
java -XX:+UseParallelGC -Xms2g -Xmx2g -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.parallel2g-1.log GCLogAnalysis

java -XX:+UseParallelGC -Xms2g -Xmx2g -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.parallel2g-2.log GCLogAnalysis

java -XX:+UseParallelGC -Xms5m -Xmx128m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.parallel2g-3.log GCLogAnalysis
```

- 总结
Young GC
```
GC时 清空新生代和当前使用存活区，复制到空闲存活区，老年代上升
```

- Full GC
```
GC时 年轻代归零，老年代下降，堆使用几乎与老年代大小一致
```

##### CMS GC
```
- 512m
java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.concMarkSweep512-1.log GCLogAnalysis

- 4g
java -XX:+UseConcMarkSweepGC -Xms4g -Xmx4g -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.concMarkSweep4g-1.log GCLogAnalysis

```

- 总结
Young GC
```
GC时 ParNew下降，老年代上升
```

- Full GC
```
GC时 年轻代下降，老年代下降，堆使用下降
```

##### G1 GC
```
- 512m
java -XX:+UseG1GC -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.g1512-1.log GCLogAnalysis

java -XX:+UseG1GC -Xms512m -Xmx512m -XX:+PrintGC -XX:+PrintGCDateStamps -Xloggc:gc.g1512-2.log GCLogAnalysis

- 4G
java -XX:+UseG1GC -Xms4g -Xmx4g -XX:+PrintGC -XX:+PrintGCDateStamps -Xloggc:gc.g14g-1.log GCLogAnalysis
```

- 总结
Young GC
```
GC时 堆使用大小下降
```

- Full GC
```
GC时 过程复杂 年轻代和老年代一起清理 堆使用大小下降
```


## NIO模型于Netty入门
# 不同 GC 和堆内存的总结

- `串行GC` Serial GC，适合单核CPU、小内存、简单桌面应用，适用场景已很少
- `‍并行GC` ParNew、Parallel Scavenge、Parallel Old，JDK8默认GC，适合高吞吐量的后台任务型应用
- `CMS` Concurrent Mark-Sweep，用于老年代；将ParallelGC的步骤细分，兼顾了GC和业务线程的并行运行
- `G1` 进一步优化CMS，分块处理，可以控制GC时间（默认200ms），JDK9 默认 GC
- `ZGC` 低延迟（10ms内）、大内存，JDK11 开始加入，到15正式开启
- `Epsilon` 实验性的GC，不回收任何内存，供性能分析使用
- `Shenandosh` 类似ZGC
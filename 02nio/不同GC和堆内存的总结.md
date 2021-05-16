# 不同 GC 和堆内存的总结

- `串行GC` Serial GC
- `‍并行GC` ParNew、Parallel Scavenge、Parallel Old
- `CMS` Concurrent Mark-Sweep，用于老年代
- `G1` 进一步优化CMS，分块处理，可以控制GC时间（默认200ms）
- `ZGC` 低延迟（10ms内）、大内存 
- `Epsilon` 实验性的GC，不回收任何内存，供性能分析使用
- `Shenandosh` 类似ZGC
package com.gnol.springboot.client.dtos.monit;

import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

import com.gnol.springboot.client.config.GnolConstant;

/**
 * @Title: NodeStatusUtil
 * @Package: com.gnol.springboot.client.dtos.monit
 * @author: 吴佳隆
 * @date: 2020年7月22日 下午4:52:21
 * @Description: 节点状态工具类
 */
public class NodeStatusUtil {

    /**
     * @Title: getLocalNodeStatus
     * @author: 吴佳隆
     * @data: 2020年7月22日 下午4:55:24
     * @Description: 获取本地节点状态
     * @return NodeStatus
     */
    public static NodeStatus getLocalNodeStatus() {
        NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setId("localhost");
        nodeStatus.setName("localhost");

        nodeStatus.setIp(GnolConstant.LOCAL_IP);
        nodeStatus.setOsName(System.getProperty("os.name"));
        nodeStatus.setOsVersion(System.getProperty("os.version"));
        nodeStatus.setJavaVersion(System.getProperty("java.version"));
        nodeStatus.setJavaHome(System.getProperty("java.home"));
        nodeStatus.setUserName(System.getProperty("user.name"));
        nodeStatus.setUserHome(System.getProperty("user.home"));
        nodeStatus.setUserDir(System.getProperty("user.dir"));
        nodeStatus.setUserTimezone(System.getProperty("user.timezone"));
        nodeStatus.setUserLanguage(System.getProperty("user.language"));
        nodeStatus.setUserCountry(System.getProperty("user.country"));
        nodeStatus.setTimestamp(System.currentTimeMillis());
        nodeStatus.setFileEncoding(System.getProperty("file.encoding"));

        // 内存信息
        MemoryMXBean memoryMBean = ManagementFactory.getMemoryMXBean();
        nodeStatus.setHeapUsage(memoryMBean.getHeapMemoryUsage());
        nodeStatus.setNonHeapUsage(memoryMBean.getNonHeapMemoryUsage());

        // 通过Runtime 获取 jvm 内存信息
        nodeStatus.setTotalMemory(Runtime.getRuntime().totalMemory() / 1024);
        nodeStatus.setFreeMemory(Runtime.getRuntime().freeMemory() / 1024);
        nodeStatus.setMaxMemory(Runtime.getRuntime().maxMemory() / 1024);

        // 内存池信息
        List<MemoryPoolMXBean> mpMBeanList = ManagementFactory.getMemoryPoolMXBeans();
        List<NodeStatus.MemoryPoolInfo> list = new ArrayList<NodeStatus.MemoryPoolInfo>();
        for (MemoryPoolMXBean mpMBean : mpMBeanList) {
            NodeStatus.MemoryPoolInfo memoryPoolInfo = nodeStatus.new MemoryPoolInfo();//创建内部类对象
            memoryPoolInfo.setName(mpMBean.getName());
            memoryPoolInfo.setUsage(mpMBean.getUsage());
            if (mpMBean.isUsageThresholdSupported()) { // 判断此内存池是否支持阀值
                memoryPoolInfo.setUsageThreshold(mpMBean.getUsageThreshold());
                memoryPoolInfo.setUsageThresholdExceeded(mpMBean.isUsageThresholdExceeded());
                memoryPoolInfo.setUsageThresholdCount(mpMBean.getUsageThresholdCount());
            }
            list.add(memoryPoolInfo);
        }
        nodeStatus.setMemoryPoolInfoList(list);

        // jvm信息
        RuntimeMXBean runtimeMBean = ManagementFactory.getRuntimeMXBean();
        nodeStatus.setJvmName(runtimeMBean.getVmName());
        nodeStatus.setJvmVersion(runtimeMBean.getVmVersion());

        // 编译器
        CompilationMXBean compilMBean = ManagementFactory.getCompilationMXBean();
        nodeStatus.setCompilationName(compilMBean.getName());
        if (compilMBean.isCompilationTimeMonitoringSupported()) {
            nodeStatus.setTotalCompilationTime(compilMBean.getTotalCompilationTime());
        }

        // 线程情况
        ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
        nodeStatus.setCurrentThreadCount(threadMBean.getThreadCount());
        nodeStatus.setPeakThreadCount(threadMBean.getPeakThreadCount());
        nodeStatus.setDaemonThreadCount(threadMBean.getDaemonThreadCount());
        nodeStatus.setTotalThreadCount(threadMBean.getTotalStartedThreadCount());
        // 是否支持 cpu 时间占用度量(当前线程)
        if (threadMBean.isCurrentThreadCpuTimeSupported() && threadMBean.isThreadCpuTimeEnabled()) {
            nodeStatus.setCurrentThreadCpuTime(threadMBean.getCurrentThreadCpuTime() / 1000000);
            /*// 查询所有的线程 cpu 占用时间
            if (threadMBean.isThreadCpuTimeEnabled() && threadMBean.isThreadCpuTimeSupported()) { // 是否支持 cpu 时间占用度量(所有线程)
                long[] threadIds = threadMBean.getAllThreadIds();
                if (threadIds != null && threadIds.length > 0) {
                    for (long threadId : threadIds) {
                        System.out.println(
                                "线程ID: " + threadId + " , 线程名称: " + threadMBean.getThreadInfo(threadId).getThreadName()
                                        + " , 占用CPU时间(ns): " + threadMBean.getThreadCpuTime(threadId));
                    }
                }
            }*/
        }

        // gc
        List<GarbageCollectorMXBean> gcMBeanList = ManagementFactory.getGarbageCollectorMXBeans();
        List<NodeStatus.GCInfo> gcInfoList = new ArrayList<NodeStatus.GCInfo>();
        for (GarbageCollectorMXBean gcMBean : gcMBeanList) {
            NodeStatus.GCInfo gcInfo = nodeStatus.new GCInfo();
            gcInfo.setName(gcMBean.getName());
            gcInfo.setCount(gcMBean.getCollectionCount());
            gcInfo.setTime(gcMBean.getCollectionTime());
            gcInfoList.add(gcInfo);
        }
        nodeStatus.setGcInfoList(gcInfoList);
        return nodeStatus;
    }

}
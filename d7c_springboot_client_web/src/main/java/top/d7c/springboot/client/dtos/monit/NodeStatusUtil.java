package top.d7c.springboot.client.dtos.monit;

import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import top.d7c.plugins.tools.date.DateUtil;
import top.d7c.springboot.client.config.D7cConstant;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.software.os.OSFileStore;
import oshi.util.Util;

/**
 * @Title: NodeStatusUtil
 * @Package: top.d7c.springboot.client.dtos.monit
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

        nodeStatus.setIp(D7cConstant.LOCAL_IP);
        nodeStatus.setOsName(System.getProperty("os.name"));
        nodeStatus.setOsVersion(System.getProperty("os.version"));
        nodeStatus.setOsArch(System.getProperty("os.arch"));
        nodeStatus.setJavaVersion(System.getProperty("java.version"));
        nodeStatus.setJavaHome(System.getProperty("java.home"));
        nodeStatus.setUserName(System.getProperty("user.name"));
        nodeStatus.setUserHome(System.getProperty("user.home"));
        nodeStatus.setUserDir(System.getProperty("user.dir"));
        nodeStatus.setUserTimezone(System.getProperty("user.timezone"));
        nodeStatus.setUserLanguage(System.getProperty("user.language"));
        nodeStatus.setUserCountry(System.getProperty("user.country"));
        nodeStatus.setTimestamp(String.valueOf(System.currentTimeMillis()));
        nodeStatus.setFileEncoding(System.getProperty("file.encoding"));

        // 内存信息
        MemoryMXBean memoryMBean = ManagementFactory.getMemoryMXBean();
        nodeStatus.setHeapUsage(memoryMBean.getHeapMemoryUsage());
        nodeStatus.setNonHeapUsage(memoryMBean.getNonHeapMemoryUsage());

        // 内存池信息
        List<MemoryPoolMXBean> mpMBeanList = ManagementFactory.getMemoryPoolMXBeans();
        List<NodeStatus.MemoryPoolInfo> list = new ArrayList<NodeStatus.MemoryPoolInfo>();
        for (MemoryPoolMXBean mpMBean : mpMBeanList) {
            NodeStatus.MemoryPoolInfo memoryPoolInfo = nodeStatus.new MemoryPoolInfo(); // 创建内部类对象
            memoryPoolInfo.setName(mpMBean.getName());
            memoryPoolInfo.setUsage(mpMBean.getUsage());
            if (mpMBean.isUsageThresholdSupported()) { // 判断此内存池是否支持阀值
                memoryPoolInfo.setUsageThreshold(mpMBean.getUsageThreshold());
                memoryPoolInfo.setUsageThresholdExceeded(mpMBean.isUsageThresholdExceeded());
                memoryPoolInfo.setUsageThresholdCount(mpMBean.getUsageThresholdCount());
            }
            list.add(memoryPoolInfo);
        }
        nodeStatus.setMemoryPoolInfos(list);

        // 通过 Runtime 获取 jvm 内存信息
        nodeStatus.setTotalMemory(Runtime.getRuntime().totalMemory() / 1024);
        nodeStatus.setFreeMemory(Runtime.getRuntime().freeMemory() / 1024);
        nodeStatus.setMaxMemory(Runtime.getRuntime().maxMemory() / 1024);

        // jvm 信息
        RuntimeMXBean runtimeMBean = ManagementFactory.getRuntimeMXBean();
        nodeStatus.setJvmName(runtimeMBean.getVmName());
        nodeStatus.setJvmVersion(runtimeMBean.getVmVersion());
        nodeStatus.setJvmStartTime(DateUtil.getDateSecond(new Date(runtimeMBean.getStartTime())));

        // 编译器
        CompilationMXBean compilMBean = ManagementFactory.getCompilationMXBean();
        nodeStatus.setCompilationName(compilMBean.getName());
        if (compilMBean.isCompilationTimeMonitoringSupported()) {
            nodeStatus.setTotalCompilationTime(compilMBean.getTotalCompilationTime());
        }

        // 线程情况
        ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
        nodeStatus.setCurrentThreadCount(threadMBean.getThreadCount());
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
        nodeStatus.setPeakThreadCount(threadMBean.getPeakThreadCount());
        nodeStatus.setDaemonThreadCount(threadMBean.getDaemonThreadCount());
        nodeStatus.setTotalThreadCount(threadMBean.getTotalStartedThreadCount());

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
        nodeStatus.setGcInfos(gcInfoList);

        SystemInfo systemInfo = new SystemInfo();
        // cpu 信息
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        NodeStatus.CpuInfo cpuInfo = nodeStatus.new CpuInfo();
        cpuInfo.setCpuNum(processor.getLogicalProcessorCount());
        cpuInfo.setTotal(totalCpu);
        cpuInfo.setSys(cSys);
        cpuInfo.setUsed(user);
        cpuInfo.setWait(iowait);
        cpuInfo.setFree(idle);

        // 系统磁盘信息
        List<NodeStatus.SysFileInfo> sysFileInfos = new LinkedList<NodeStatus.SysFileInfo>();
        List<OSFileStore> osFileStores = systemInfo.getOperatingSystem().getFileSystem().getFileStores();
        for (OSFileStore osFileStore : osFileStores) {
            long free = osFileStore.getUsableSpace();
            long total = osFileStore.getTotalSpace();
            long used = total - free;
            NodeStatus.SysFileInfo sysFile = nodeStatus.new SysFileInfo();
            sysFile.setDirName(osFileStore.getMount());
            sysFile.setSysTypeName(osFileStore.getType());
            sysFile.setTypeName(osFileStore.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            if (total == 0) {
                sysFile.setUsage(0);
            } else {
                sysFile.setUsage(used / total);
            }
            sysFileInfos.add(sysFile);
        }
        nodeStatus.setSysFileInfos(sysFileInfos);
        return nodeStatus;
    }

    /**
     * @Title: convertFileSize
     * @author: 吴佳隆
     * @data: 2020年7月23日 上午11:15:53
     * @Description: 字节转换
     * @param size      字节大小
     * @return String   转换后的值
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

}
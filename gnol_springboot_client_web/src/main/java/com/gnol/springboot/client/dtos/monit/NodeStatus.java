package com.gnol.springboot.client.dtos.monit;

import java.lang.management.MemoryUsage;
import java.util.List;

/**
 * @Title: NodeStatus
 * @Package: com.gnol.springboot.client.dtos.monit
 * @author: 吴佳隆
 * @date: 2020年7月22日 下午4:40:36
 * @Description: 服务器节点状况实体类
 */
public class NodeStatus {
    /**
     * 编号
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    // --- 环境信息
    /**
     * ip
     */
    private String ip;
    /**
     * 操作系统
     */
    private String osName;
    /**
     * 操作系统版本
     */
    private String osVersion;
    /**
     * 系统架构
     */
    private String osArch;
    /**
     * java 版本
     */
    private String javaVersion;
    /**
     * java 安装目录
     */
    private String javaHome;
    /**
     * 用户
     */
    private String userName;
    /**
     * 用户主目录
     */
    private String userHome;
    /**
     * 用户工作目录
     */
    private String userDir;
    /**
     * 用户所属时区
     */
    private String userTimezone;
    /**
     * 用户语言
     */
    private String userLanguage;
    /**
     * 用户国家
     */
    private String userCountry;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 文件编码方式
     */
    private String fileEncoding;
    // --- 内存信息 MemoryPool
    /**
     * 堆内存使用情况
     */
    private MemoryUsage heapUsage;
    /**
     * 非堆内存使用情况
     */
    private MemoryUsage nonHeapUsage;
    /**
     * 内存池信息
     */
    private List<MemoryPoolInfo> memoryPoolInfos;
    /**
     * 内存总量(kb)
     */
    private long totalMemory;
    /**
     * 空闲内存(kb)
     */
    private long freeMemory;
    /**
     * 最大内存(kb)
     */
    private long maxMemory;
    // --- JVM
    /**
     * jvm
     */
    private String jvmName;
    /**
     * jvm 版本
     */
    private String jvmVersion;
    /**
     * jvm 启动时间
     */
    private String jvmStartTime;
    /**
     * 编译器名称
     */
    private String compilationName;
    /**
     * 累计编译时长(ms)
     */
    private long totalCompilationTime;
    // --- 线程
    /**
     * 当前线程数
     */
    private int currentThreadCount;
    /**
     * 当前线程占用cpu时间(ms)
     */
    private long currentThreadCpuTime;
    /**
     * 峰值线程数
     */
    private int peakThreadCount;
    /**
     * 守护线程数
     */
    private int daemonThreadCount;
    /**
     * 自虚拟机启动以来启动的线程总数
     */
    private long totalThreadCount;
    /**
     * 垃圾收集信息
     */
    private List<GCInfo> gcInfos;
    /**
     * 系统磁盘信息
     */
    private List<SysFileInfo> sysFileInfos;

    /**
     * 内存池信息
     */
    public class MemoryPoolInfo {
        private String name; // 内存池名称
        private MemoryUsage usage; // 内存池使用情况
        private long usageThreshold; // 内存池阀值
        private boolean usageThresholdExceeded; // 是否达到或超过阀值
        private long usageThresholdCount; // 超出阀值的次数

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public MemoryUsage getUsage() {
            return usage;
        }

        public void setUsage(MemoryUsage usage) {
            this.usage = usage;
        }

        public long getUsageThreshold() {
            return usageThreshold;
        }

        public void setUsageThreshold(long usageThreshold) {
            this.usageThreshold = usageThreshold;
        }

        public boolean isUsageThresholdExceeded() {
            return usageThresholdExceeded;
        }

        public void setUsageThresholdExceeded(boolean usageThresholdExceeded) {
            this.usageThresholdExceeded = usageThresholdExceeded;
        }

        public long getUsageThresholdCount() {
            return usageThresholdCount;
        }

        public void setUsageThresholdCount(long usageThresholdCount) {
            this.usageThresholdCount = usageThresholdCount;
        }

        @Override
        public String toString() {
            return "MemoryPoolInfo{name='" + name + "\', usage='" + usage + "\', usageThreshold=" + usageThreshold
                    + ", usageThresholdExceeded=" + usageThresholdExceeded + ", usageThresholdCount="
                    + usageThresholdCount + '}';
        }

    }

    /**
     * 垃圾收集信息
     */
    public class GCInfo {
        private String name; // 垃圾收集器名称
        private long count; // gc 次数
        private long time; // gc 耗时(ms)

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "GCInfo{name='" + name + "\', count=" + count + ", time=" + time + '}';
        }

    }

    /**
     * 系统磁盘信息
     */
    public class SysFileInfo {
        /**
         * 盘符路径
         */
        private String dirName;
        /**
         * 盘符类型
         */
        private String sysTypeName;
        /**
         * 文件类型
         */
        private String typeName;
        /**
         * 总大小
         */
        private String total;
        /**
         * 剩余大小
         */
        private String free;
        /**
         * 已经使用量
         */
        private String used;
        /**
         * 资源的使用率
         */
        private double usage;

        public String getDirName() {
            return dirName;
        }

        public void setDirName(String dirName) {
            this.dirName = dirName;
        }

        public String getSysTypeName() {
            return sysTypeName;
        }

        public void setSysTypeName(String sysTypeName) {
            this.sysTypeName = sysTypeName;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getFree() {
            return free;
        }

        public void setFree(String free) {
            this.free = free;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public double getUsage() {
            return usage;
        }

        public void setUsage(double usage) {
            this.usage = usage;
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getJavaHome() {
        return javaHome;
    }

    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHome() {
        return userHome;
    }

    public void setUserHome(String userHome) {
        this.userHome = userHome;
    }

    public String getUserDir() {
        return userDir;
    }

    public void setUserDir(String userDir) {
        this.userDir = userDir;
    }

    public String getUserTimezone() {
        return userTimezone;
    }

    public void setUserTimezone(String userTimezone) {
        this.userTimezone = userTimezone;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    public MemoryUsage getHeapUsage() {
        return heapUsage;
    }

    public void setHeapUsage(MemoryUsage heapUsage) {
        this.heapUsage = heapUsage;
    }

    public MemoryUsage getNonHeapUsage() {
        return nonHeapUsage;
    }

    public void setNonHeapUsage(MemoryUsage nonHeapUsage) {
        this.nonHeapUsage = nonHeapUsage;
    }

    public List<MemoryPoolInfo> getMemoryPoolInfos() {
        return memoryPoolInfos;
    }

    public void setMemoryPoolInfos(List<MemoryPoolInfo> memoryPoolInfos) {
        this.memoryPoolInfos = memoryPoolInfos;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }

    public String getJvmName() {
        return jvmName;
    }

    public void setJvmName(String jvmName) {
        this.jvmName = jvmName;
    }

    public String getJvmVersion() {
        return jvmVersion;
    }

    public void setJvmVersion(String jvmVersion) {
        this.jvmVersion = jvmVersion;
    }

    public String getJvmStartTime() {
        return jvmStartTime;
    }

    public void setJvmStartTime(String jvmStartTime) {
        this.jvmStartTime = jvmStartTime;
    }

    public String getCompilationName() {
        return compilationName;
    }

    public void setCompilationName(String compilationName) {
        this.compilationName = compilationName;
    }

    public long getTotalCompilationTime() {
        return totalCompilationTime;
    }

    public void setTotalCompilationTime(long totalCompilationTime) {
        this.totalCompilationTime = totalCompilationTime;
    }

    public int getCurrentThreadCount() {
        return currentThreadCount;
    }

    public void setCurrentThreadCount(int currentThreadCount) {
        this.currentThreadCount = currentThreadCount;
    }

    public long getCurrentThreadCpuTime() {
        return currentThreadCpuTime;
    }

    public void setCurrentThreadCpuTime(long currentThreadCpuTime) {
        this.currentThreadCpuTime = currentThreadCpuTime;
    }

    public int getPeakThreadCount() {
        return peakThreadCount;
    }

    public void setPeakThreadCount(int peakThreadCount) {
        this.peakThreadCount = peakThreadCount;
    }

    public int getDaemonThreadCount() {
        return daemonThreadCount;
    }

    public void setDaemonThreadCount(int daemonThreadCount) {
        this.daemonThreadCount = daemonThreadCount;
    }

    public long getTotalThreadCount() {
        return totalThreadCount;
    }

    public void setTotalThreadCount(long totalThreadCount) {
        this.totalThreadCount = totalThreadCount;
    }

    public List<GCInfo> getGcInfos() {
        return gcInfos;
    }

    public void setGcInfos(List<GCInfo> gcInfos) {
        this.gcInfos = gcInfos;
    }

    public List<SysFileInfo> getSysFileInfos() {
        return sysFileInfos;
    }

    public void setSysFileInfos(List<SysFileInfo> sysFileInfos) {
        this.sysFileInfos = sysFileInfos;
    }

    @Override
    public String toString() {
        return "NodeStatus{id='" + id + "\', name='" + name + "\', ip='" + ip + "\', osName='" + osName
                + "\', osVersion='" + osVersion + "\', osArch='" + osArch + "\', javaVersion='" + javaVersion
                + "\', javaHome='" + javaHome + "\', userName='" + userName + "\', userHome='" + userHome
                + "\', userDir='" + userDir + "\', userTimezone='" + userTimezone + "\', userLanguage='" + userLanguage
                + "\', userCountry='" + userCountry + "\', timestamp='" + timestamp + "\', fileEncoding='"
                + fileEncoding + "\', heapUsage='" + heapUsage + "\', nonHeapUsage='" + nonHeapUsage
                + "\', memoryPoolInfos=" + memoryPoolInfos + ", totalMemory=" + totalMemory + ", freeMemory="
                + freeMemory + ", maxMemory=" + maxMemory + ", jvmName='" + jvmName + "\', jvmVersion='" + jvmVersion
                + "\', compilationName='" + compilationName + "\', totalCompilationTime=" + totalCompilationTime
                + ", currentThreadCount=" + currentThreadCount + ", currentThreadCpuTime=" + currentThreadCpuTime
                + ", peakThreadCount=" + peakThreadCount + ", daemonThreadCount=" + daemonThreadCount
                + ", totalThreadCount=" + totalThreadCount + ", gcInfos=" + gcInfos + ", sysFileInfos=" + sysFileInfos
                + '}';
    }

}
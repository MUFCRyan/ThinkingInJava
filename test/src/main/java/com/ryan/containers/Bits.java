package com.ryan.containers;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page520 Chapter 17.13 Java 1.0/1.1 容器
 * Book Page522 Chapter 17.13.1 BitSet：适用于高效（针对空间，时间上比本地数据慢些）的存储大量的“开关”信息，最小容量是 long：64位；相比于
 * EnumSet 后者是更好的选择，因EnumSet 允许按照名字而非数字位的位置进行操作 --> 故可以减少错误，还可防止因疏忽而添加了新的标志位 —— 能引发严重的、难以发现的缺陷；
 * BitSet 的使用场景只包括：只有在运行时才知道需要多少个标识；对标识命名不合理；需要 BitSet 的某种特殊操作（可查看 BitSet 和 EnumSet 的文档）
 */

public class Bits {
}

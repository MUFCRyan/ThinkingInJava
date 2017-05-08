package com.ryan.containers;

import com.ryan.util.Util;

import static java.lang.System.exit;

/**
 * Created by MUFCRyan on 2017/5/8.
 * Book Page507 Chapter 17.10.3 微基准测试的危险
 * 不能做太多假设、需要将测试窄化 --> 使得其尽可能的只在目标事项上花费精力；必须仔细确保测试运行足够长的时间 --> 以产生有意义的数据；
 * 要考虑到某些 Java Spot 技术只有在程序运行了一段时间之后才会踢爆问题
 * 根据计算机和使用的 JVM 不同其所产生的结果也不一样 --> 故必须亲自运行这些测试 --> 以验证得到的结果与本书是否相同 —— 应将其看作是一种容器类型与另一种之间的性能比较
 *
 * 剖析器可以更好的分析性能，Java 的剖析器详见 http://MinView.net/Books/BetterJava 处的补充材料
 */

/**
 * 程序运行命令行键入：java RandomBounds lower/java RandomBounds upper
 * 这两种情况下需要手动终止程序，实际已经证明 0.0 是包含在 Math.random 中的，其范围即为 [0,1)
 * 其误导所在：0~1 之间大约262个不同的双精度小数产生的任何一个值的可能性也许都会超过计算机甚至人本身的生命周期，故这类测试需要仔细考虑和设计
 */
public class RandomBounds {
    static void usage(){
        Util.print("Usage: ");
        Util.print("\tRandomBounds lower");
        Util.print("\tRandomBounds upper");
        exit(1);
    }

    public static void main(String[] args){
        if (args.length != 1)
            usage();
        if (args[0].equals("lower")){
            while (Math.random() != 0.0){
                // Keep trying
            }
            Util.println("Produced 0.0!");
        } else if (args[0].equals("upper")){
            while (Math.random() != 1.0){
                // Keep Trying
            }
            Util.println("Produced 1.0!");
        } else
            usage();
    }
}

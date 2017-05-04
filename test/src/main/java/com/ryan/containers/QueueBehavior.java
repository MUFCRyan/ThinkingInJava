package com.ryan.containers;

import com.ryan.generic.generator.Generator;
import com.ryan.util.Util;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by MUFCRyan on 2017/5/4.
 * Book Page480 Chapter 17.7 队列
 * 除了并发应用，Queue 在 Java SE5 中仅有的两个实现是 LinkedList 和 PriorityQueue，其差异在于排序行为，无关性能
 *
 * Book Page481 Chapter 17.7.1 优先级队列
 */

public class QueueBehavior {
    private static int count = 10;
    static <T> void test(Queue<T> queue, Generator<T> generator){
        for (int i = 0; i < count; i++) {
            queue.offer(generator.next());
        }
        while (queue.peek() != null){
            Util.print(queue.remove() + " ");
        }
        Util.println();
    }

    static class Gen implements Generator<String> {
        String[] string = ("one two three four five six seven eight nine ten").split(" ");
        int i;
        @Override
        public String next() {
            return string[i++];
        }
    }

    public static void main(String[] args){
        test(new LinkedList<String>(), new Gen());
        test(new PriorityQueue<String>(), new Gen());
        test(new ArrayBlockingQueue<String>(count), new Gen());
        test(new ConcurrentLinkedDeque<String>(), new Gen());
        test(new LinkedBlockingDeque<String>(), new Gen());
        test(new PriorityBlockingQueue<String>(), new Gen());
    }
}


class ToDoList extends PriorityQueue<ToDoList.ToDoItem>{
    static class ToDoItem implements Comparable<ToDoItem>{
        private char primary;
        private int secondary;
        private String item;
        public ToDoItem(String item, char primary, int secondary){
            this.item = item;
            this.primary = primary;
            this.secondary = secondary;
        }
        @Override
        public int compareTo(ToDoItem toDoItem) {
            if (primary > toDoItem.primary)
                return +1;
            if (primary == toDoItem.primary){
                if (secondary > toDoItem.secondary)
                    return +1;
                if (secondary == toDoItem.secondary)
                    return 0;
            }
            return -1;
        }

        @Override
        public String toString() {
            return Character.toString(primary) + secondary + ": " + item;
        }
    }

    public void add(String item, char primary, int secondary){
        add(new ToDoItem(item, primary, secondary));
    }

    public static void main(String[] args){
        ToDoList list = new ToDoList();
        list.add("Empty Trash", 'C', 4);
        list.add("Feed Dog", 'A', 2);
        list.add("Feed Bird", 'B', 7);
        list.add("Mow Lawn", 'C', 3);
        list.add("Water Lawn", 'A', 1);
        list.add("Feed Cat", 'B', 1);
        while (!list.isEmpty()){
            Util.println(list.remove());
        }
    }
}
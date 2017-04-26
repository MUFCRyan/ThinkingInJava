package com.ryan.generic.problem;

/**
 * Created by MUFCRyan on 2017/4/26.
 * Book Page401 Chapter 15.11.2 实现参数化接口：
 * 一个类不能实现同一个泛型接口的两种变体，但可以实现不带泛型化参数接口的两种变体
 */

interface Payable<T>{

}

class Employee implements Payable/*<Employee>*/{

}

/**
 * 无法工作：因为在 Hourly 中已经实现了 Payable 接口，在擦除后会将 Payable<Employee> 和 Payable<Hourly> 简化为相同的接口，所以 Hourly
 * 类不能再次实现该接口；但如果去掉二者的泛型化参数就可以既继承又实现了
 */
public class Hourly extends Employee /*implements Payable<Hourly>*/{}

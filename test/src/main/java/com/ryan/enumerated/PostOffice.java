package com.ryan.enumerated;

import com.ryan.util.Util;

import java.util.Iterator;

/**
 * !!IMPORTANT
 * Created by MUFCRyan on 2017/5/12.
 * Book Page606 Chapter 19.10.1 使用 enum 职责链
 * 职责链设计模式：以多种不同的方式来解决一个问题，然后将它们链接在一起；当一个请求到来时，其遍历该链 --> 直至链中的某个解决方案能处理该请求
 */

/**
 * 职责链由 enum MailHandler 实现，而 enum 定义的次序决定了各个解决策略在应用时的次序；对每一封邮件都要按此顺序尝试每个解决策略，直至其中一个能够成功地处理该邮件；
 *      若所有策略都失败，则该邮件将被判定为一封死信
 */
class Mail{
    // The No's lower the probability of random selection
    enum GeneralDelivery {YES, NO1, NO2, NO3, NO4, NO5}
    enum Scannability{UNSCANNABLE, YES1, YES2, YEs3, YES4}
    enum Readability{ILLEGIBLE, YES1, YES2, YES3, YES4}
    enum Address{INCORRECT, OK1, OK2, OK3, OK4, OK5, OK6}
    enum ReturnAddress{MISSING, OK1, OK2, OK3, OK4, OK5}
    GeneralDelivery mGeneralDelivery;
    Scannability mScannability;
    Readability mReadability;
    Address mAddress;
    ReturnAddress mReturnAddress;
    static long counter = 0;
    long id = counter++;

    @Override
    public String toString() {
        return "Mail " + id;
    }
    public String details(){
        return toString()
                + ", General Delivery: " + mGeneralDelivery
                + ", Address Scannability: " + mScannability
                + ", Address Readability: " + mReadability
                + ", Address: " + mAddress
                + ", Return address: " + mReturnAddress;
    }

    public static Mail randomMail(){
        Mail mail = new Mail();
        mail.mGeneralDelivery = Enums.random(GeneralDelivery.class);
        mail.mScannability = Enums.random(Scannability.class);
        mail.mReadability = Enums.random(Readability.class);
        mail.mAddress = Enums.random(Address.class);
        mail.mReturnAddress = Enums.random(ReturnAddress.class);
        return mail;
    }

    public static Iterable<Mail> generator(int count){
        return new Iterable<Mail>() {
            int n = count;
            @Override
            public Iterator<Mail> iterator() {
                return new Iterator<Mail>() {
                    @Override
                    public boolean hasNext() {
                        return n-- >0;
                    }

                    @Override
                    public Mail next() {
                        return randomMail();
                    }

                    @Override
                    public void remove() {
                        // Not implemented
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}

public abstract class PostOffice {
    enum MailHandler {
        GENERAL_DELIVERY {
            @Override
            boolean handle(Mail mail) {
                switch(mail.mGeneralDelivery){
                    case YES:
                        Util.println("Using general delivery for " + mail);
                        return true;
                }
                return false;
            }
        },
        MACHINE_SCAN {
            @Override
            boolean handle(Mail mail) {
                switch(mail.mScannability){
                    case UNSCANNABLE:
                        return false;
                }
                switch (mail.mAddress){
                    case INCORRECT:
                        return false;
                }
                Util.println("Delivery " + mail + " automatically");
                return true;
            }
        },
        VISUAL_INSPECTION {
            @Override
            boolean handle(Mail mail) {
                switch(mail.mReadability){
                    case ILLEGIBLE:
                        return false;
                }
                switch (mail.mAddress){
                    case INCORRECT:
                        return false;
                }
                Util.println("Delivery " + mail + " normally");
                return true;
            }
        },
        RETURN_TO_SENDER{
            @Override
            boolean handle(Mail mail) {
                switch(mail.mReturnAddress){
                    case MISSING:
                        return false;
                }
                Util.println("Returning " + mail + " to sender");
                return true;
            }
        };
        abstract boolean handle(Mail mail);
    }

    static void handle(Mail mail){
        for (MailHandler handler : MailHandler.values()) {
            if (handler.handle(mail))
                return;
        }
        Util.println(mail + " is a dead letter!");
    }

    public static void main(String[] args){
        for (Mail mail : Mail.generator(10)) {
            Util.println("details: " + mail.details());
            handle(mail);
            Util.println("*************");
        }
    }

}

package org.itmo;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        var spy = new SpyMessenger();
//        spy.sendMessage("1", "2", "a", "pass1");
//        Thread.sleep(2000L);
//        spy.sendMessage("1", "2", "b", "pass2");
//        Thread.sleep(2000L);
//        spy.sendMessage("1", "2", "c", "pass3");
//        var message = spy.readMessage("2", "pass3");
//        System.out.println("RESULT: " + message);
//        var message1 = spy.readMessage("2", "pass3");
//        System.out.println("RESULT1: " + message1);
//        Thread.sleep(2000L);
        var spy = new SpyMessenger();
        spy.sendMessage("1", "2", "a", "pass1");
        var message = spy.readMessage("2", "pass1");
        System.out.println("RESULT: " + message);

//        Thread.sleep(2000L);
//        spy.sendMessage("1", "2", "b", "pass2");
//        Thread.sleep(2000L);
//        spy.sendMessage("1", "2", "c", "pass3");
//        var message = spy.readMessage("2", "pass3");
//        System.out.println("RESULT: " + message);
//        var message1 = spy.readMessage("2", "pass3");
//        System.out.println("RESULT1: " + message1);
//        Thread.sleep(2000L);

        Thread.sleep(10_000L);
    }
}

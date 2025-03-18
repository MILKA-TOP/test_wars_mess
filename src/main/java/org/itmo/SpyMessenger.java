package org.itmo;

import java.time.LocalTime;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

class SpyMessenger {

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    private ConcurrentHashMap<String, Queue<Message>> table = new ConcurrentHashMap<>();

    public SpyMessenger() {
        // удаляется по чтению
        // по таймеру
        // по 5 сообщений
    }

    void sendMessage(String sender, String receiver, String message, String passcode) {
        if (sender == null) throw new NullPointerException();
        if (receiver == null) throw new NullPointerException();
        if (message == null) throw new NullPointerException();
        var senderMessages = table.getOrDefault(receiver, new ConcurrentLinkedDeque<>());
        System.out.println("Messages for (1)" + receiver + ": " + senderMessages.size());

        var newmessage = new Message(sender, message, passcode);
        senderMessages.add(newmessage);
        if (senderMessages.size() > 5) {
            senderMessages.poll();
        }
        table.put(receiver, senderMessages);
        System.out.println("Messages for (2)" + receiver + ": " + senderMessages.size());

        Runnable beeper = () -> {
            System.out.println("1: " + LocalTime.now());
            System.out.println("Start remove:" + message);
            var messages = table.getOrDefault(receiver, new ConcurrentLinkedDeque<>());
            System.out.println("Size: " + messages.size());
            try {
                messages.remove(newmessage);
                table.put(receiver, messages);
                System.out.println("Success remove:" + message);
            } catch (Exception e) {
                System.out.println("Error remove:" + message);
//                throw new RuntimeException(e);
            }
            System.out.println("beep");
        };
        System.out.println("2: " + LocalTime.now());
        scheduler.schedule(beeper, 1500, MILLISECONDS);
    }

    String readMessage(String user, String passcode) {
        if (user == null) throw new NullPointerException();
        var messages = table.getOrDefault(user, new ConcurrentLinkedDeque<>());
        System.out.println("Read messages: " + messages.size());
        var message = messages.stream().filter(message1 -> message1.passcode.equals(passcode)).findFirst();
        System.out.println("Mesage: " + message);
        if (message.isEmpty()) {
            return null;
        }
        else {
            var presentMessage = message.get();
            var updatedMessages = table.getOrDefault(passcode, new ConcurrentLinkedDeque<>());
            if (presentMessage.passcode.equals(passcode)) {
                try {
                    updatedMessages.remove(presentMessage);
                    table.put(user, updatedMessages);
                    System.out.println("Success remove(read): " + presentMessage.message);
                } catch (Exception e) {
                    System.out.println("Error remove(read): " + presentMessage.message);
                }

                return presentMessage.message;
            }

        }
        return null;
    }

    private record Message(String sender, String message, String passcode) {
    }
}
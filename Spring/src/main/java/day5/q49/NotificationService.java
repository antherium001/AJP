package day5.q49;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private DataProcessor dataProcessor;

    public void send() {
        System.out.println("NotificationService sending: " + dataProcessor.process());
    }
}

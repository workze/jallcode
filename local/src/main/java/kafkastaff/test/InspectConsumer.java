package kafkastaff.test;

import kafkastaff.KafkaSimpleConsumer;
import kafkastaff.MessageHandler;
import ormstaff.test.Person;

@KafkaSimpleConsumer(topic = "inspect", group = "inspect")
public class InspectConsumer implements MessageHandler<Person> {

    @Override
    public void handleMessage(Person object) {

    }
}

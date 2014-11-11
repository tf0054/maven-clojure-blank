package am.ik.blank;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.UUID;
//import java.lang.InterruptedException;

public class KafkaReadingInputStream extends InputStream
{
    private StringReader reader;

    private long count;
    private long readCount;
    private final ConsumerConnector consumer;
    private final String topic;

    private Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams;
    private List<KafkaStream<byte[], byte[]>> streams;
/*
static
{
    Logger rootLogger = Logger.getRootLogger();
    rootLogger.setLevel(Level.INFO);
    rootLogger.addAppender(new ConsoleAppender(
               new PatternLayout("%-6r [%p] %c - %m%n")));
}
*/
    public KafkaReadingInputStream(StringReader reader)
    {
        super();
        this.reader = reader;

        // https://github.com/bkimminich/apache-kafka-book-examples/blob/master/src/test/kafka/consumer/SimpleHLConsumer.java
        Properties props = new Properties();
        props.put("zookeeper.connect", "internal-vagrant.genn.ai:2181");
        props.put("group.id", "samoa-kafka");
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");

        this.topic = "samoa-test";

        consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));

        Map<String, Integer> topicCount = new HashMap<>();
        topicCount.put(topic, 1);

        consumerStreams = consumer.createMessageStreams(topicCount);
        streams = consumerStreams.get(topic);
        System.out.println("The num of streams: "+streams.size()); // 1

        readCount = 0;
    }

    public KafkaReadingInputStream(String value)
    {
        this(new StringReader(value));
    }

    public int read() throws IOException
    {
        int tmpInt = reader.read();

        if(tmpInt == -1){

            String strLine = "";
            ConsumerIterator<byte[], byte[]> it = streams.get(0).iterator();

            if (it.hasNext()) { // read() would be called many times.
                strLine = new String(it.next().message());
                reader = new StringReader(strLine);
                System.out.println("Message from Single Topic: " + strLine + " ("+count+"/"+readCount+")");
                count++;
            }else{
                System.out.println("There is no Message");
            }
    /*
            for (final KafkaStream stream : streams) {
                ConsumerIterator<byte[], byte[]> it = stream.iterator();
                if (it.hasNext()) {
                    strLine = new String(it.next().message());
                    reader = new StringReader(strLine);
                    System.out.println("Message from Single Topic: " + strLine + " ("+count+")");
                    count++;
                }else{
                    System.out.println("There is no Message");
                }
            }
    */
            tmpInt = reader.read();
        }
        readCount++;
        System.out.print(tmpInt+",");
        return tmpInt;
    }
}

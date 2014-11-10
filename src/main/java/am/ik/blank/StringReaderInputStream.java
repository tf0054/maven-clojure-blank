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

public class StringReaderInputStream extends InputStream
{
    private String tmpStr;
    private StringReader reader;

    private long count;
    private final ConsumerConnector consumer;
    private final String topic;

    private Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams;
    private List<KafkaStream<byte[], byte[]>> streams;
    
    public StringReaderInputStream(StringReader reader)
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
	List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
    }
    
    public StringReaderInputStream(String value)
    {
        this(new StringReader(value));
    }

    public int read() throws IOException
    {
	int tmpInt = reader.read();

	if(tmpInt == -1){

            String strLine = "";
            streams = consumerStreams.get(topic);
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

	    tmpInt = reader.read();
	}
	return tmpInt;
    }
}

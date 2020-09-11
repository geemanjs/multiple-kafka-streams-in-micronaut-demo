package blog.geeman;

import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.logging.Logger;
@Factory
public class PrintSumOfEvenStream {

    public static final String INPUT = "sum-of-even-numbers-topic";
    
    public static final Logger logger = Logger.getLogger(PrintSumOfEvenStream.class.getName());

    public static final String STREAM_NAME = "print-sum-of-even-numbers";

    @Singleton
    @Named(STREAM_NAME + "-stream")
    KStream<Integer, Integer> printSumOfOddNumbersStream(
            @Named(STREAM_NAME) ConfiguredStreamBuilder builder // Note the @Named here is super important
    ) {
        final KStream<Integer, Integer> stream = builder.stream(INPUT);
        stream.peek((key, value) -> {
            logger.info(value.toString());
        });

        return stream;
    }
}

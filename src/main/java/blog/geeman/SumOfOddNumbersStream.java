package blog.geeman;

import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Properties;

@Factory
public class SumOfOddNumbersStream {
    public static final String INPUT = "numbers-topic";
    public static final String OUTPUT = "sum-of-odd-numbers-topic";

    public static final String STREAM_NAME = "sum-of-odd-numbers";

    @Singleton
    @Named(STREAM_NAME + "-stream")
    KStream<Integer, Integer> sumOfOddNumbersStream (
       @Named("default") ConfiguredStreamBuilder builder // The `default` builder is always created
    ) {
        final KStream<Integer, Integer> stream = builder.stream(INPUT);

        final KTable<Integer, Integer> sumOfOddNumbers = stream
                //  Odds only
                .filter((k, v) -> v % 2 != 0)
                // We want to compute the total sum across ALL numbers, so we must re-key all records to the same key.
                .selectKey((k, v) -> 1)
                .groupByKey()
                // Add the numbers to compute the sum.
                .reduce((v1, v2) -> v1 + v2);

        sumOfOddNumbers.toStream().to(OUTPUT);

        return stream;
    }


}

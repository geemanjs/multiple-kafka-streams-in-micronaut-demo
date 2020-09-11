package blog.geeman;

import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import javax.inject.Named;
import javax.inject.Singleton;

@Factory
public class SumOfEvenNumbersStream {
    public static final String INPUT = "numbers-topic";
    public static final String OUTPUT = "sum-of-even-numbers-topic";

    public static final String STREAM_NAME = "sum-of-even-numbers";

    @Singleton
    @Named(STREAM_NAME + "-stream")
    KStream<Integer, Integer> sumOfOddNumbersStream(
            @Named(STREAM_NAME) ConfiguredStreamBuilder builder // Note the @Named here is super important
    ) {
        final KStream<Integer, Integer> stream = builder.stream(INPUT);

        final KTable<Integer, Integer> sumOfOddNumbers = stream
                //  Evens only
                .filter((k, v) -> v % 2 == 0)
                // We want to compute the total sum across ALL numbers, so we must re-key all records to the same key.
                .selectKey((k, v) -> 1)
                .groupByKey()
                // Add the numbers to compute the sum.
                .reduce((v1, v2) -> v1 + v2);

        sumOfOddNumbers.toStream().to(OUTPUT);

        return stream;
    }


}

## Running multiple streams in a micronaut app
I recently ran into a few problems running two streams in a single micronaut app. This is a repo to 
demonstrate a working version.

[Accompanying blog post](https://geeman.blog/running-multiple-kafka-streams-in-micronaut)

## Best way to run this
```
# Start up kafka 
git clone git@github.com:confluentinc/cp-all-in-one.git
git checkout 5.5.1-post
cd cp-all-in-one/cp-all-in-one
docker-compose up -d

# Start the service
git clone git@github.com:geemanjs/multiple-kafka-streams-in-micronaut-demo.git
./gradlew run

# Start some load
git clone git@github.com:confluentinc/kafka-streams-examples.git
cd kafka-streams-examples
mvn -DskipTests=true clean package 
java -cp target/kafka-streams-examples-5.5.0-standalone.jar io.confluent.examples.streams.SumLambdaExampleDriver
```
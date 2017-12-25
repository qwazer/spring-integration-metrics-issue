### What this?

Sample project that show bug in Spring Integration Metrics feature 
in case of using several named service activators in single chain.


## Flow

Produce String Message every 100ms and put to `fixedDelayChannel`.
`fixedDelayChannel` consumed by chain.
Chain consist of 2 Service Activators: fastService that works about 10ms and slow that works 1000ms.

 ```xml
    <int:inbound-channel-adapter id="fixedDelayProducer"
                                 expression="'string'"
                                 channel="fixedDelayChannel">
        <int:poller fixed-delay="100"/>
    </int:inbound-channel-adapter>


    <int:channel id="fixedDelayChannel"/>

    <int:chain id="chain" input-channel="fixedDelayChannel" output-channel="nullChannel">
        <int:service-activator id="fastService" expression="@stringService.handle(payload, 10)"/>
        <int:service-activator id="slowService" expression="@stringService.handle(payload, 1000)"/>
    </int:chain>

```

## Run and show metrics via http

```mvn clean spring-boot:run```

```curl http://localhost:8080/metrics```


### Actual results:

```json
 "integration.handler.chain$child.fastService.duration.mean": 1010.4844085667869,
  "integration.handler.chain$child.fastService.duration.max": 1012.227317,
  "integration.handler.chain$child.fastService.duration.min": 1010.274173,
  "integration.handler.chain$child.fastService.duration.stdev": 0.07865591181653758,
  "integration.handler.chain$child.fastService.duration.count": 652,
  "integration.handler.chain$child.fastService.activeCount": 1,
  "integration.handler.chain.duration.mean": 1010.490483280632,
  "integration.handler.chain.duration.max": 1012.273909,
  "integration.handler.chain.duration.min": 1010.277313,
  "integration.handler.chain.duration.stdev": 0.07956706859168364,
  "integration.handler.chain.duration.count": 652,
  "integration.handler.chain.activeCount": 1,
  "integration.handler.chain$child.slowService.duration.mean": 1000.237444340432,
  "integration.handler.chain$child.slowService.duration.max": 1001.537873,
  "integration.handler.chain$child.slowService.duration.min": 1000.142979,
  "integration.handler.chain$child.slowService.duration.stdev": 0.05269903416036114,
  "integration.handler.chain$child.slowService.duration.count": 652,
  "integration.handler.chain$child.slowService.activeCount": 1,
```

### Expected results:

  * `fastService` should be `fast` -> It should have mean duration less than 11 ms.
  * `fastService.duration.mean` less than `slowService.duration.mean`

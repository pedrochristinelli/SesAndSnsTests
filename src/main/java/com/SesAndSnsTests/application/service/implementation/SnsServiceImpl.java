package com.SesAndSnsTests.application.service.implementation;

import com.SesAndSnsTests.application.service.SnsService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnsServiceImpl implements SnsService {
    @Value("${aws.credentials.access_key}")
    private String accessKey;

    @Value("${aws.credentials.secret_access_key}")
    private String secretAccessKey;

    @Override
    public void sendSmsMessage(String message, String phone) {
        AmazonSNS amazonSNS = createSnsClient();

        amazonSNS.publish(new PublishRequest().withMessage(message).withPhoneNumber(phone));
    }

    @Override
    public void sendSmsTopicMessage(String message, String topic) {
        AmazonSNS amazonSNS = createSnsClient();

        amazonSNS.publish(topic, message);
    }

    @Override
    public void createSmsTopic(String topicName, String[] endpoints){
        AmazonSNS amazonSNS = createSnsClient();

        final CreateTopicRequest createTopicRequest = new CreateTopicRequest(topicName);
        final CreateTopicResult createTopicResult = amazonSNS.createTopic(createTopicRequest);

        String topicArn = createTopicResult.getTopicArn();
        for (String endpoint: endpoints) {
            final SubscribeRequest subscribeRequest = new SubscribeRequest(topicArn, "SMS", endpoint);
            amazonSNS.subscribe(subscribeRequest);
        }
    }

    @Override
    public void addNumberToTopic(String number, String topic) {
        AmazonSNS amazonSNS = createSnsClient();

        final SubscribeRequest subscribeRequest = new SubscribeRequest(topic, "SMS", number);
        amazonSNS.subscribe(subscribeRequest);
    }

    @Override
    public void removeNumberFromTopic(String number, String topic) {
        AmazonSNS amazonSNS = createSnsClient();

        List<Subscription> subscriptionList = amazonSNS.listSubscriptionsByTopic(topic).getSubscriptions();
        for (Subscription subscription: subscriptionList) {
            if (subscription.getEndpoint().equals(number)){
                amazonSNS.unsubscribe(subscription.getSubscriptionArn());
            }
        }
    }

    private AmazonSNS createSnsClient(){
        //Used for authenticating to AWS
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretAccessKey);

        //Create SNS Client
        return AmazonSNSClient
                .builder()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }
}

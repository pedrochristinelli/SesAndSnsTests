package com.SesAndSnsTests.application.service.implementation;

import com.SesAndSnsTests.application.service.SnsService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SnsServiceImpl implements SnsService {
    @Value("${aws.credentials.access_key}")
    private String accessKey;

    @Value("${aws.credentials.secret_access_key}")
    private String secretAccessKey;

    @Override
    public void sendSmsMessage(String message, String phone) {
        //Used for authenticating to AWS
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretAccessKey);

        //Create SNS Client
        AmazonSNS snsClient = AmazonSNSClient
                .builder()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        String SMSMessage = message;
        String mobile = phone; //Enter your mobile number here

        snsClient.publish(new PublishRequest().withMessage(SMSMessage).withPhoneNumber(mobile));
    }
}

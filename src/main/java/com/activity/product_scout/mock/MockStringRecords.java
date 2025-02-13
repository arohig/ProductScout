package com.activity.product_scout.mock;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// @Repository
public class MockStringRecords {
    private List<String> records;

    public MockStringRecords(){
        this.records = populateRepo();
    }

    private List<String> populateRepo(){
        List<String> recordRepo = new ArrayList<>();

        /* TEST DATA
        String str1 = "A few rain showers and flurries ending this afternoon then cloudy \n "
                        + "with 40 percent chance of flurries. \n"
                        + "Risk of freezing rain near noon. Fog patches. \n"
                        + "High plus 1. UV index 1 or low.";

        String str2 = "The term idempotent is used in mathematics to \n"
                        + "describe a function that can be applied multiple times without changing the \n"
                        + "result beyond the initial result. In computing, idempotent is used to describe \n"
                        + "an operation that will produce the same results if executed once or multiple\n"
                        + "times. \n";

        String str3 = "Apache Camel is an open source framework that provides rule-based routing \n"
                        + "and mediation engine. Apache Camel essentially provides an implementation \n"
                        + "of various EIPs. It makes integration easier by providing connectivity \n"
                        + "to a very large variety of transports and APIs.";

        String str4 = "Sunny. Wind up to 15 km/h. \n"
                        + "High minus 13. Wind chill minus 30 in the morning \n"
                        + "and minus 19 in the afternoon. Risk of frostbite. \n"
                        + "UV index 2 or low.";

        recordRepo.add(str1);
        recordRepo.add(str2);
        recordRepo.add(str3);
        recordRepo.add(str4);
         */
        return recordRepo;
    }

    public String add(String str){
        this.records.add(str);
        return str;
    }

    /* Count the number of received messages */
    public int numMessages(){
        return this.records.size();
    }
}

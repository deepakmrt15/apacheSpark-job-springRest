package com.cit.kyc.cache.message;


import com.botw.schemas.entity.party.v1.Party;
import com.botw.schemas.entity.party.v1.PartyPartyRelType;
import com.botw.schemas.publish.party.v1.PartyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamSource;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by sharmd01 on 12/26/2017.
 */
public class MdmMsgReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MdmMsgReceiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "queueName", containerFactory = "jmsListenerContainerFactory")
    public void receive(String message) {
        Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
       // LOGGER.info("Mdm Sent:", message);
        PartyEvent partyEvent = (PartyEvent) unMarshaller.unmarshal(new StreamSource(message));
        partyEvent.getEventType();
        Party party = (Party) unMarshaller.unmarshal(new StreamSource(message));
        List<PartyPartyRelType> relPartyList= party.getRelatedParties().getItems();
        int i =0;
        for ( PartyPartyRelType relParty:relPartyList
             ) {
                 System.out.println("RelatedPartyId "+relParty.getRelatedPartyId());
          }


        latch.countDown();
    }
}

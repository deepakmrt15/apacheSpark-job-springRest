package com.cit.kyc.cache.message;


import com.botw.schemas.entity.party.v1.Party;
import com.botw.schemas.publish.party.v1.PartyEvent;
import com.ibm.mq.jms.MQQueueConnectionFactory;

import com.ibm.msg.client.wmq.WMQConstants;
import com.ibm.msg.client.wmq.compat.jms.internal.JMSC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;


import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.jms.Destination;
import java.io.StringReader;

/**
 * Created by sharmd01 on 12/22/2017.
 */
@Configuration
@EnableJms
public class MdmMqConfigurer {

    @Value("tcp://localhost:61616")
    /** This method configures the connection to the MDM queue
     **/
   // @Autowired
   // JmsTemplate jmsTemplate;
   // @Bean
  //  @Primary
        public MQQueueConnectionFactory mdmMQCnxnFactory() {
        MQQueueConnectionFactory mdmMQConnectionFactory = new MQQueueConnectionFactory();
        try{
//       mdmMQConnectionFactory.createConnection("","");
        mdmMQConnectionFactory.setHostName("localhost");
      //  mdmMQConnectionFactory.setClientID("clientId");
        mdmMQConnectionFactory.setPort(1414);
            mdmMQConnectionFactory.setQueueManager("local");
            mdmMQConnectionFactory.setChannel("lCH");
        mdmMQConnectionFactory.setSSLCertStores("");
       mdmMQConnectionFactory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);

    } catch (Exception e) {
        e.printStackTrace();
    }
        return mdmMQConnectionFactory;
    }



 //@Bean
    public DefaultMessageListenerContainer jmsListenerContainerFactory() {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(mdmMQCnxnFactory());
     listenerContainer.setDestinationName("");


        return listenerContainer;
    }




}


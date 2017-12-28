package com.cit.kyc.cache.message;


import com.botw.schemas.entity.party.v1.Party;
import com.botw.schemas.publish.party.v1.PartyEvent;
import com.ibm.mq.jms.MQQueueConnectionFactory;

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
    @Autowired
    JmsTemplate jmsTemplate;
    @Bean
    @Primary
        public MQQueueConnectionFactory mdmMQCnxnFactory() {
        MQQueueConnectionFactory mdmMQConnectionFactory = new MQQueueConnectionFactory();
        try{
        mdmMQConnectionFactory.createConnection("","");
        mdmMQConnectionFactory.setHostName("");
        mdmMQConnectionFactory.setClientID("clientId");
        mdmMQConnectionFactory.setPort(7077);
        mdmMQConnectionFactory.setSSLCertStores("");
        mdmMQConnectionFactory.setTransportType(1);
    } catch (Exception e) {
        e.printStackTrace();
    }
        return mdmMQConnectionFactory;
    }



    @Bean
    public DefaultMessageListenerContainer jmsListenerContainerFactory() {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(mdmMQCnxnFactory());
        listenerContainer.setMessageConverter(messageConverter());

        return listenerContainer;
    }


    @Bean
    MessageConverter messageConverter() {
        MarshallingMessageConverter converter = new MarshallingMessageConverter();
        converter.setMarshaller( marshaller());
        converter.setUnmarshaller(unMarshaller());

        // set this converter on the implicit Spring JMS template
       // jmsTemplate.setMessageConverter(converter);
        return converter;
    }

    @Bean
    Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        //(new Class[]{twitter.model.Statuses.class});
       marshaller.setClassesToBeBound(new Class[]{PartyEvent.class, Party.class} );
        return marshaller;

    }

    @Bean
    Jaxb2Marshaller unMarshaller() {
        Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
       // unMarshaller.unmarshal(new StringReader("string"));

        return unMarshaller;

    }

}


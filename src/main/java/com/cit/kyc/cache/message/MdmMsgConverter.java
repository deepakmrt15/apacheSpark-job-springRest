package com.cit.kyc.cache.message;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;



/**
 * Created by sharmd01 on 12/26/2017.
 */
public class MdmMsgConverter {

    @Bean
    MessageConverter messageConverter() {
        MarshallingMessageConverter converter = new MarshallingMessageConverter();
        converter.setMarshaller( marshaller());
        converter.setUnmarshaller(unMarshaller());
        // set this converter on the implicit Spring JMS template
    //    jmsTemplate.messageConverter = converter
       return converter;
    }

    @Bean
    Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        return marshaller;

    }

    @Bean
    Jaxb2Marshaller unMarshaller() {
        Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
        return unMarshaller;

    }
}

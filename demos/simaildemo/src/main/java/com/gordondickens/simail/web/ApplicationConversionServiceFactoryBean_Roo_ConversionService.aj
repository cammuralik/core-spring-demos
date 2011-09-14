// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.gordondickens.simail.web;

import com.gordondickens.simail.entity.Recipient;
import java.lang.String;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new RecipientConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
    static class com.gordondickens.simail.web.ApplicationConversionServiceFactoryBean.RecipientConverter implements Converter<Recipient, String> {
        public String convert(Recipient recipient) {
            return new StringBuilder().append(recipient.getRecipientEmail()).append(" ").append(recipient.getSubject()).append(" ").append(recipient.getMessageBody()).toString();
        }
        
    }
    
}

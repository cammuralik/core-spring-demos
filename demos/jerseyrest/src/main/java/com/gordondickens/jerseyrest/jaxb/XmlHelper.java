package com.gordondickens.jerseyrest.jaxb;

import org.eclipse.persistence.exceptions.XMLMarshalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gordon
 *         Date: 2012-08-03
 */
public class XmlHelper {
    private static final Logger logger = LoggerFactory.getLogger(XmlHelper.class);
    private static String ECLIPSELINK_OXM_KEY = "eclipselink-oxm.xml";
    private Map<String, Object> properties;
    private Jaxb2Marshaller marshaller;

    /**
     * Unmarshal a given source
     */
    public Object load(Source source) throws XmlMappingException, IOException {
        return marshaller.unmarshal(source);
    }

    /**
     * Marshal a given Object to a Result
     */
    public void save(Object obj, Result result) throws XmlMappingException, IOException {
        marshaller.marshal(obj, result);
    }

    public void marshalToStandardOut(Object obj) throws XMLMarshalException {
        StringWriter sw = new StringWriter();
        Result result = new StreamResult(System.out);
        marshaller.marshal(obj, result);
    }

    /**
     * This method is used by Spring to inject the eclipselink-oxm.xml metadata
     * file to be applied to the model.
     */
    public void setMetadataFile(Resource metadataFile) throws IOException {
        properties = new HashMap<String, Object>();
        properties.put(ECLIPSELINK_OXM_KEY, new StreamSource(metadataFile.getInputStream()));
    }

    /**
     * This method is used by Spring to inject an instance of Jaxb2Marshaller
     */
    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
        this.marshaller.setJaxbContextProperties(properties);
    }
}

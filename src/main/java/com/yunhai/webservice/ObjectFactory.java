
package com.yunhai.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.yunhai.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SaysssssssssResponse_QNAME = new QName("http://www.baidu.com", "saysssssssssResponse");
    private final static QName _Saysssssssss_QNAME = new QName("http://www.baidu.com", "saysssssssss");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.yunhai.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SaysssssssssResponse }
     * 
     */
    public SaysssssssssResponse createSaysssssssssResponse() {
        return new SaysssssssssResponse();
    }

    /**
     * Create an instance of {@link Saysssssssss }
     * 
     */
    public Saysssssssss createSaysssssssss() {
        return new Saysssssssss();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaysssssssssResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.baidu.com", name = "saysssssssssResponse")
    public JAXBElement<SaysssssssssResponse> createSaysssssssssResponse(SaysssssssssResponse value) {
        return new JAXBElement<SaysssssssssResponse>(_SaysssssssssResponse_QNAME, SaysssssssssResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Saysssssssss }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.baidu.com", name = "saysssssssss")
    public JAXBElement<Saysssssssss> createSaysssssssss(Saysssssssss value) {
        return new JAXBElement<Saysssssssss>(_Saysssssssss_QNAME, Saysssssssss.class, null, value);
    }

}

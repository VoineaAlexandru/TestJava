package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("D:\\TestFiles");
        try (Stream<Path> subPaths = Files.walk(path, 1)) {

            List<String> subPathsList = subPaths.filter(Files::isRegularFile)
                    .map(Objects::toString)
                    .collect(Collectors.toList());


            for (String subPath : subPathsList) {

                String FileName = subPath;

                ArrayList<Orders> List = new ArrayList<Orders>();
                char[] fileNameArray = FileName.toCharArray();
                int OrderNumber = 0;
                // String OrderNumber="";
                for (int i = 0; i < fileNameArray.length; i++) {
                    if (i == (fileNameArray.length - 6)) {

                        OrderNumber = Integer.parseInt(String.valueOf(fileNameArray[i])) * 10 + Integer.parseInt(String.valueOf(fileNameArray[i + 1]));
                        // System.out.println(fileNameArray[i]);
                        // System.out.println(fileNameArray[i+1]);

                        //  OrderNumber= ""+fileNameArray[i]+fileNameArray[i+1];
                        // System.out.println("Order Numbe is: "+ OrderNumber);
                    }

                }
                // System.out.println("Order Numbe is: " + OrderNumber);
                //---------------------------------------------------------------------


                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                try {
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document docs = builder.parse(FileName);
                    NodeList orderList = docs.getElementsByTagName("order");
                    for (int i = 0; i < orderList.getLength(); i++) {

                        Node o = orderList.item(i);

                        if (o.getNodeType() == Node.ELEMENT_NODE) {
                            Element order = (Element) o;
                            String id = order.getAttribute("ID");

                            //System.out.println(order.getTagName()+": "+id);
                            NodeList productList = order.getChildNodes();
                            for (int j = 0; j < productList.getLength(); j++) {
                                Orders comanda = new Orders();
                                comanda.setOrderId(id);
                                Node p = productList.item(j);


                                if (p.getNodeType() == Node.ELEMENT_NODE) {

                                    Element product = (Element) p;

                                    NodeList detailsList = product.getChildNodes();
                                    for (int a = 0; a < detailsList.getLength(); a++) {
                                        Node d = detailsList.item(a);
                                        // Node currencyAttrib = d.getNamedItem(CURRENCY);
                                        if (d.getNodeType() == Node.ELEMENT_NODE) {
                                            Element details = (Element) d;
                                            String currency = details.getAttribute("currency");
                                            if (details.getTagName() == "price") {
                                                comanda.setCurrency(currency);
                                            }


                                            if (details.getTagName() == "description") {
                                                comanda.setDescription(details.getTextContent());
                                            } else if (details.getTagName() == "gtin") {
                                                comanda.setGtin(details.getTextContent());
                                            } else if (details.getTagName() == "price") {
                                                comanda.setPrice(details.getTextContent());
                                            } else if (details.getTagName() == "supplier") {
                                                comanda.setSupplier(details.getTextContent());
                                            }
                                            // if(details.getTagName()=="price"){
                                            //     System.out.println("       "+details.getTagName() +" : currency: "+currency +":" + details.getTextContent());
                                            //     System.out.println("currency:"+currency);
                                            // }else {
                                            //     System.out.println("       " + details.getTagName() + ":" + details.getTextContent()); }


                                        }
                                    }
                                    List.add(comanda);

                                }

                            }

                        }


                    }

                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                HashSet<String> suppliers = new HashSet<String>();
                for (Orders o : List) {
                    suppliers.add(o.getSupplier());

                }

                for (String s : suppliers) {
                    try {
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder;
                        dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.newDocument();
                        // add elements to Document
                        Element rootElement = doc.createElement("products");
                        // append root element to document
                        doc.appendChild(rootElement);
                    // System.out.println("The supplier for this order is: "+s+OrderNumber);
                    for (Orders o : List) {

                            if (o.getSupplier().equals(s)) {
                                //  System.out.println(o);
                                String Description = o.getDescription();
                                String GTIN = o.getGtin();
                                String Price = o.getPrice();
                                String Currency = o.getCurrency();
                                String OrderID = o.getOrderId();
                                String Supplier = o.getSupplier();
//----------------------------------------------------------------------------------------------------------------------------------

                                try {


                                    // append first child element to root element
                                    rootElement.appendChild(createUserElement(doc, Description, GTIN, Price, Currency, OrderID));


                                    // for output to file, console
                                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                    Transformer transformer = transformerFactory.newTransformer();
                                    // for pretty print
                                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                                    DOMSource source = new DOMSource(doc);

                                    // write to console or file
                                    StreamResult console = new StreamResult(System.out);
                                    StreamResult file = new StreamResult(new File("D:\\ResultTestFiles", Supplier + OrderNumber + ".xml"));

                                    // write data
                                    transformer.transform(source, console);
                                    transformer.transform(source, file);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                //-----------------------------------------------------------------------------------------------------------------------------------
                            }
                        }

                }catch (Exception e) {
                        e.printStackTrace();

            }
            }
        }

    }
    }


    private static Node createUserElement(Document doc, String description, String gtin, String price, String currency,
                                          String orderId) {
        Element user = doc.createElement("product");


        // create firstName element
        user.appendChild(createUserElements(doc, user, "description", description));

        // create lastName element
        user.appendChild(createUserElements(doc, user, "gtin", gtin));

        // create age element
        user.appendChild(createUserElements(doc, user, "price", price, currency));
       // user.setAttribute("currency", currency);

        // create gender element
        user.appendChild(createUserElements(doc, user, "orderid", orderId));

        return user;
    }
    private static Node createUserElements(Document doc, Element element, String name, String value,String currency) {
        Element node = doc.createElement(name);
        node.setAttribute("currency", currency);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    private static Node createUserElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);

        node.appendChild(doc.createTextNode(value));
        return node;
    }
}


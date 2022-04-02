package com.example.repository.xml;

import com.example.entity.user.Account;
import com.example.entity.user.Role;
import com.example.entity.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class XmlRepositoryImpl implements XmlRepository {

    @Value("${user.data.file}")
    private String filePath;

    @Override
    public void log(User user) {
        try {
            Document document = getDocument(filePath);
            Element root = document.getDocumentElement();

            Element newUser = document.createElement("user");

            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(user.getId().toString()));

            Element login = document.createElement("username");
            login.appendChild(document.createTextNode(user.getUsername()));

            Element password = document.createElement("password");
            password.appendChild(document.createTextNode(user.getPassword()));

            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(user.getEmail()));

            Element roles = document.createElement("roles");
            Element role = document.createElement("role");
            List<Role> userRoles = user.getRoles();
            for (Role r : userRoles) {
                role.appendChild(document.createTextNode(r.getName()));
                roles.appendChild(role);
            }

            newUser.appendChild(login);
            newUser.appendChild(password);
            newUser.appendChild(roles);

            root.appendChild(newUser);
            document.getDocumentElement().normalize();

            saveFile(document, filePath);
        } catch (TransformerException | IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account findUserByUsername(String username) throws IOException, ParserConfigurationException, SAXException {

        Document document = getDocument(filePath);
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("user");

        for (int itr = 0; itr < nodeList.getLength(); itr++) {

            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) node;
                if(eElement.getElementsByTagName("username").item(0).getTextContent().equals(username)){

                    Account account = new Account();
                    account.setUsername(username);
                    account.setId(Long.parseLong(eElement.getElementsByTagName("id").item(0).getTextContent()));
                    account.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());
                    account.setEmail(eElement.getElementsByTagName("email").item(0).getTextContent());

                    List<Role> roles = new ArrayList<>();
                    NodeList roleList = eElement.getElementsByTagName("role");
                    for(int j = 0; j < roleList.getLength(); j++){
                        Element roleElement = (Element)nodeList.item(j);
                        Role role = new Role();
                        role.setName(roleElement.getElementsByTagName("role").item(j).getTextContent());
                        roles.add(role);
                    }

                    account.setRoles(roles);
                    return account;
                }
            }
        }

        return null;
    }

    private Document getDocument(String fileName) throws IOException, SAXException, ParserConfigurationException {
        File usersFile = ResourceUtils.getFile(fileName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(usersFile);
    }

    private void saveFile(Document document, String fileName) throws TransformerException, FileNotFoundException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(ResourceUtils.getFile(fileName));

        transformer.transform(source, result);
    }
}

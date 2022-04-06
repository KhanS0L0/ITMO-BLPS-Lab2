package com.example.repository.xml;

import com.example.entity.user.Account;
import com.example.entity.user.Role;
import com.example.entity.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class XmlRepositoryImpl implements XmlRepository {

//    @Value("${user.data.file}")
    private String filePath = "/Users/hansultan/IdeaProjects/ITMO/ITMO-BLPS-Lab2/src/main/resources/users.xml";

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

            newUser.appendChild(id);
            newUser.appendChild(login);
            newUser.appendChild(password);
            newUser.appendChild(email);
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
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        String[] params = checkUsername(username, document, xPath);

        if(params != null){
            Account account = new Account();
            account.setUsername(username);
            account.setId(findId(params));
            account.setPassword(findPassword(params));
            account.setEmail(findEmail(params));
            account.setRoles(findRoles(params));
            return account;
        }

        return null;
    }

    private String[] checkUsername(String username, Document document, XPath xPath){
        try {
            XPathExpression xPathExpression = xPath.compile("/users/user[username='" + username + "']");
            String params = (String) xPathExpression.evaluate(document, XPathConstants.STRING);
            if(params != null && !params.isEmpty()) return params.trim().split("\\s+");
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Long findId(String[] userParams){
        return Long.parseLong(userParams[0]);
    }

    private String findPassword(String[] userParams){
        return userParams[2];
    }

    private String findEmail(String[] userParams){
        return userParams[3];
    }

    private List<Role> findRoles(String[] userParams){
        List<Role> roles = new ArrayList<>();
        if(userParams.length > 5){
            for(int i = 4; i < userParams.length; i++){
                Role role = new Role();
                role.setName(userParams[i]);
                roles.add(role);
            }
            return roles;
        }else{
            Role role = new Role();
            role.setName(userParams[4]);
            roles.add(role);
            return roles;
        }
    }

    private Document getDocument(String fileName) throws IOException, SAXException, ParserConfigurationException {
        File usersFile = ResourceUtils.getFile(fileName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
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

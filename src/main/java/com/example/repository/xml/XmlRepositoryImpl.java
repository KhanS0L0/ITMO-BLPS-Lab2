package com.example.repository.xml;

import com.example.entity.user.Account;
import com.example.entity.user.Role;
import com.example.entity.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class XmlRepositoryImpl implements XmlRepository {

    @Value("${user.data.file}")
    private String filePath;

    @Override
    public void writeToXml(User user) {
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

        List<String> params = checkUsername(username, document, xPath);
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

    private List<String> checkUsername(String username, Document document, XPath xPath){
        try {
            XPathExpression xPathExpression = xPath.compile("/users/user[username='" + username + "']");
            NodeList user = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
            NodeList userParams = user.item(0).getChildNodes();

            List<String> params = new ArrayList<>();
            for(int i = 0; i < userParams.getLength(); i++){
                if(!userParams.item(i).getNodeName().equals("#text")){
                    if(userParams.item(i).getNodeName().equals("roles")){
                        NodeList roles = userParams.item(i).getChildNodes();
                        for(int j = 0; j < roles.getLength(); j++){
                            params.add(roles.item(j).getTextContent());
                        }
                    }else {
                        params.add(userParams.item(i).getTextContent());
                    }
                }
            }
            return params;
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Long findId(List<String> userParams){
        return Long.parseLong(userParams.get(0));
    }

    private String findPassword(List<String> userParams){
        return userParams.get(2);
    }

    private String findEmail(List<String> userParams){
        return userParams.get(3);
    }

    private List<Role> findRoles(List<String> userParams){
        List<Role> roles = new ArrayList<>();
        for(int i = 4; i < userParams.size(); i++){
                Role role = new Role();
                role.setName(userParams.get(i));
                roles.add(role);
        }
        return roles;
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

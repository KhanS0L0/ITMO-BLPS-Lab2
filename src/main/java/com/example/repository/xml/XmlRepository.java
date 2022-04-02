package com.example.repository.xml;

import com.example.entity.user.Account;
import com.example.entity.user.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface XmlRepository {

    void log(User user);

    Account findUserByUsername(String username) throws IOException, ParserConfigurationException, SAXException;
}

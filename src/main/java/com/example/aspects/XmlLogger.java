package com.example.aspects;

import com.example.annotations.annotation.LogXML;
import com.example.entity.user.User;
import com.example.repository.logger.LoggerRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class XmlLogger {

    private final LoggerRepository loggerRepository;

    @Value("${user.data.file}")
    private String filePath;

    @Autowired
    public XmlLogger(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    @Pointcut("@annotation(logXML)")
    public void log(LogXML logXML) {}

    @AfterReturning(pointcut = "log(logXML)", returning = "retVal")
    public void afterReturning(Object retVal, LogXML logXML) {
        loggerRepository.log(filePath, (User) retVal);
    }
}

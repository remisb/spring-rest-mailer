package lt.bauzys.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "mailer")
public class MailSenderConfig {

    @Value("${mailer.host}")
    private String mailServerHost;

    @Value("${mailer.port}")
    private Integer mailServerPort;

    @Value("${mailer.username}")
    private String mailServerUsername;

    @Value("${mailer.password}")
    private String mailServerPassword;

    @Value("${mailer.from}")
    private String mailerFrom;

    @Value("${mailer.properties.mail.smtp.auth}")
    private String mailServerAuth;

    @Value("${mailer.properties.mail.smtp.starttls.enable}")
    private String mailServerStartTls;

    @Value("${mailer.transport.protocol}")
    private String mailerTransportProtocol;

    @Value("${mailer.debug}")
    private Boolean mailerDebug;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailServerHost);
        mailSender.setPort(mailServerPort);
        mailSender.setUsername(mailServerUsername);
        mailSender.setPassword(mailServerPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailerTransportProtocol);
        props.put("mail.smtp.auth", mailServerAuth);
        props.put("mail.smtp.starttls.enable", mailServerStartTls);
        props.put("mail.debug", mailerDebug);

        return mailSender;
    }

    public String getMailerFrom() {
        return mailerFrom;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public Integer getMailServerPort() {
        return mailServerPort;
    }

    public String getMailServerUsername() {
        return mailServerUsername;
    }

    public String getMailServerPassword() {
        return mailServerPassword;
    }

    public String getMailServerAuth() {
        return mailServerAuth;
    }

    public String getMailServerStartTls() {
        return mailServerStartTls;
    }

    public String getMailerTransportProtocol() {
        return mailerTransportProtocol;
    }

    public Boolean getMailerDebug() {
        return mailerDebug;
    }
}

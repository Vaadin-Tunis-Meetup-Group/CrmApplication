package com.crm.managedbean.util;

import com.crm.model.Client;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

public class JsfUtil {

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static String getRequestUrl() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String url = req.getRequestURL().toString();
        return url;
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        FacesContext.getCurrentInstance().validationFailed(); // Invalidate JSF page if we raise an error message

    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static Throwable getRootCause(Throwable cause) {
        if (cause != null) {
            Throwable source = cause.getCause();
            if (source != null) {
                return getRootCause(source);
            } else {
                return cause;
            }
        }
        return null;
    }

    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public static boolean isDummySelectItem(UIComponent component, String value) {
        for (UIComponent children : component.getChildren()) {
            if (children instanceof UISelectItem) {
                UISelectItem item = (UISelectItem) children;
                if (item.getItemValue() == null && item.getItemLabel().equals(value)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    public static String getComponentMessages(String clientComponent, String defaultMessage) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent component = UIComponent.getCurrentComponent(fc).findComponent(clientComponent);
        if (component instanceof UIInput) {
            UIInput inputComponent = (UIInput) component;
            if (inputComponent.isValid()) {
                return defaultMessage;
            } else {
                Iterator<FacesMessage> iter = fc.getMessages(inputComponent.getClientId());
                if (iter.hasNext()) {
                    return iter.next().getDetail();
                }
            }
        }
        return "";
    }

    public static void sendMaill(String to, String subject, Client cl) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.ssl.enable", true);

        Authenticator authenticator = null;

        props.put("mail.smtp.auth", true);
        authenticator = new Authenticator() {
            private PasswordAuthentication pa = new PasswordAuthentication("amineshaarawy@gmail.com", "amineshaarawy92");

            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return pa;
            }
        };

        Session session = Session.getInstance(props, authenticator);
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("amineshaarawy@gmail.com"));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());
            //message.setText(body);

            Multipart multipart = new MimeMultipart("alternative");
            MimeBodyPart textPart = new MimeBodyPart();
            String textContent = "Hi, Nice to meet you!";
            textPart.setText(textContent);
            MimeBodyPart htmlPart = new MimeBodyPart();
            StringBuilder sb = new StringBuilder();
            sb.append("Bonjour, ").append(cl).append("<br/>");
            sb.append("<b><p>Votre Compte est désomrmais active ! Vous pouvez dés maintenant effectuer des commandes ou demander devis</p></b>");
            sb.append("<br/>");
            sb.append("<b>Date d'activation : </b>").append(sdf.format(new Date())).append("<br/>");
            sb.append("<b>Login : </b>").append(cl.getLogin()).append("<br/>");
            sb.append("<b>Mot de passe : </b>").append(cl.getPassword()).append("<br/>");
            sb.append("Cordialement </br> Service Commercial <br/> MobileShop");
            htmlPart.setContent(sb.toString(), "text/html");
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }

    public static void sendMaillDevis(String to, DevisData devis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.ssl.enable", true);

        Authenticator authenticator = null;

        props.put("mail.smtp.auth", true);
        authenticator = new Authenticator() {
            private PasswordAuthentication pa = new PasswordAuthentication("amineshaarawy@gmail.com", "amineshaarawy92");

            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return pa;
            }
        };

        Session session = Session.getInstance(props, authenticator);
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("amineshaarawy@gmail.com"));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject("Liste de Devis");
            message.setSentDate(new Date());
            //message.setText(body);

            Multipart multipart = new MimeMultipart("alternative");

            MimeBodyPart htmlPart = new MimeBodyPart();
            StringBuilder sb = new StringBuilder();
           
            sb.append("Bonjour, ").append(devis.getClient()).append("<br/>");
            sb.append("<b><p> Voici la liste des Devis demandée</p></b>");
            sb.append("<br/>");
            sb.append("<center><table border=\"1\" class=\"tg\">\n"
                    + "  <tr>\n"
                    + "    <th style=\"font-weight:bold;background-color:#34cdf9;text-align:center\">Réference</th>\n"
                    + "    <th style=\"font-weight:bold;background-color:#34cdf9;text-align:center\">Produit</th>\n"
                    + "    <th style=\"font-weight:bold;background-color:#34cdf9;text-align:center\">Prix unitaire</th>\n"
                    + "    <th style=\"font-weight:bold;background-color:#34cdf9;text-align:center\">Quantité</th>\n"
                    + "    <th style=\"font-weight:bold;background-color:#34cdf9;text-align:center\">Prix Totale</th>\n"
                    + "  </tr>");

            for (Devis d : devis.getDevis()) {
                sb.append("<tr>\n"
                        + "    <td style=\"font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;\">" + d.getRef() + "</td>\n"
                        + "    <td style=\"font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;\">" + d.getProduit() + "</td>\n"
                        + "    <td style=\"font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;\">" + d.getPu() + "</td>\n"
                        + "    <td style=\"font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;\">" + d.getQte() + "</td>\n"
                        + "    <td style=\"font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;\">" + d.getPrix() + "</td>\n"
                        + "  </tr>");
            }

            sb.append("</table></center><br/>");
            sb.append("<b>Prix Totale = "+devis.getPrixt()+" </b><br/>");
             sb.append("<b>Commentaire = "+devis.getCommentaire()+" </b><br/>");
            sb.append("<br/>");
            sb.append("Cordialement <br/> Service Commercial - CRM Mobile Shop");
            htmlPart.setContent(sb.toString(), "text/html");
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crm.managedbean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.ejb.Init;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Haithem
 */
@Named
@ApplicationScoped
public class ChatController {

    private StringBuilder messages;

    @PostConstruct
    public void init() {
        messages = new StringBuilder();
        messages.append("<center> <b> -------- Chat --------- </b> </center> <br/>");
    }

    public StringBuilder getMessages() {
        return messages;
    }

    public void setMessages(StringBuilder messages) {
        this.messages = messages;
    }

    public void addMsg(String user, String msg) {

        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String d = sdf.format(new Date());
        messages.append("<b>").append(d).append(" - ");
        messages.append("<p style=\"color:red;display : inline;\" >").append(user).append(" </p> : </b>");
        messages.append(msg);
        messages.append("<br/>");

    }

}

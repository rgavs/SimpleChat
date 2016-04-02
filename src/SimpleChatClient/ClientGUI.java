/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * Modified by Shouheng Wu to accommodate password feature, and to
 * provide a default guest user that can be used to create new accounts
 * February 28, 2016
 */


package SimpleChatClient;

/* TextDemo.java requires no other files. */

import client.ChatClient1;
import common.ChatIF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class ClientGUI extends JPanel implements ActionListener, ChatIF {
    private JTextField textField;
    private JTextArea textArea;
    private final static String newline = "\n";

    /**
     * The default port to connect on.
     */
    private final static int DEFAULT_PORT = 5555;

    /**
     * The instance of the client created by this ClientGUI.
     */

    private ChatClient1 client;

    /**
     * The default(guest) user account and password.
     */

    private final static String DEFAULT_ID = "guest";
    private final static String DEFAULT_PASSWORD = "123";

    private ClientGUI(String host, int port, String id, String pwd) {
        super(new GridBagLayout());

        textField = new JTextField(20);
        textField.addActionListener(this);

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);

        try {
            client = new ChatClient1(host, port, this, id, pwd);
        } catch (Exception e) {
            display("Error: Can't setup connection!\n Terminating client.");
            System.exit(1);
        }
        display("connected to " + host + "-" + port);
    }

    public void actionPerformed(ActionEvent evt) {
        String message = textField.getText();
        client.handleMessageFromClientUI(message);
        textField.setText("");
    }

    public void display(String message) {
        textArea.append("> " + message + newline);
        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI(String host, int port, String id, String pwd) {
        //Create and set up the window.
        JFrame frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new ClientGUI(host, port, id, pwd));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        final String host = "localhost";
        int port;  //The port number
        String idIn, pwdIn;
        final String id, pwd;
        try {
            idIn = args[0];
            pwdIn = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Logging in with default id and password");//changed by Shouheng Wu
            idIn = DEFAULT_ID;
            pwdIn = DEFAULT_PASSWORD;
        }

        id = idIn;
        pwd = pwdIn;

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(host, DEFAULT_PORT, id, pwd);
            }
        });
    }
}


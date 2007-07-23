/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Mirth.
 *
 * The Initial Developer of the Original Code is
 * WebReach, Inc.
 * Portions created by the Initial Developer are Copyright (C) 2006
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *   Gerald Bortis <geraldb@webreachinc.com>
 *
 * ***** END LICENSE BLOCK ***** */

package com.webreach.mirth.connectors.tcp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;

import com.webreach.mirth.client.ui.PlatformUI;
import com.webreach.mirth.client.ui.UIConstants;
import com.webreach.mirth.client.ui.components.MirthFieldConstraints;
import com.webreach.mirth.client.ui.editors.transformer.TransformerPane;
import com.webreach.mirth.connectors.ConnectorClass;
import com.webreach.mirth.model.Channel;
import com.webreach.mirth.model.Connector;
import com.webreach.mirth.model.Step;

/**
 * A form that extends from ConnectorClass. All methods implemented are
 * described in ConnectorClass.
 */
public class TCPListener extends ConnectorClass
{
    /** Creates new form TCPListener */

    public TCPListener()
    {
        this.parent = PlatformUI.MIRTH_FRAME;
        name = TCPListenerProperties.name;
        initComponents();
        listenerIPAddressField.setDocument(new MirthFieldConstraints(3, false, false, true));
        listenerIPAddressField1.setDocument(new MirthFieldConstraints(3, false, false, true));
        listenerIPAddressField2.setDocument(new MirthFieldConstraints(3, false, false, true));
        listenerPortField.setDocument(new MirthFieldConstraints(5, false, false, true));
        receiveTimeoutField.setDocument(new MirthFieldConstraints(0, false, false, true));
        bufferSizeField.setDocument(new MirthFieldConstraints(0, false, false, true));
        // ast:encoding activation
        parent.setupCharsetEncodingForChannel(charsetEncodingCombobox);
    }

    public Properties getProperties()
    {
        Properties properties = new Properties();
        properties.put(TCPListenerProperties.DATATYPE, name);
        String listenerIPAddress = listenerIPAddressField.getText() + "." + listenerIPAddressField1.getText() + "." + listenerIPAddressField2.getText() + "." + listenerIPAddressField3.getText();
        properties.put(TCPListenerProperties.TCP_ADDRESS, listenerIPAddress);
        properties.put(TCPListenerProperties.TCP_PORT, listenerPortField.getText());
        properties.put(TCPListenerProperties.TCP_RECEIVE_TIMEOUT, receiveTimeoutField.getText());
        properties.put(TCPListenerProperties.TCP_BUFFER_SIZE, bufferSizeField.getText());

        properties.put(TCPListenerProperties.TCP_RESPONSE_VALUE, (String)responseFromTransformer.getSelectedItem());

        // ast:encoding
        properties.put(TCPListenerProperties.CONNECTOR_CHARSET_ENCODING, parent.getSelectedEncodingForChannel(charsetEncodingCombobox));

        if (ackOnNewConnectionYes.isSelected())
            properties.put(TCPListenerProperties.TCP_ACK_NEW_CONNECTION, UIConstants.YES_OPTION);
        else
            properties.put(TCPListenerProperties.TCP_ACK_NEW_CONNECTION, UIConstants.NO_OPTION);

        String ackIPAddress = ackIPAddressField.getText() + "." + ackIPAddressField1.getText() + "." + ackIPAddressField2.getText() + "." + ackIPAddressField3.getText();
        properties.put(TCPListenerProperties.TCP_ACK_NEW_CONNECTION_IP, ackIPAddress);
        properties.put(TCPListenerProperties.TCP_ACK_NEW_CONNECTION_PORT, ackPortField.getText());
        return properties;
    }

    public void setProperties(Properties props)
    {
        resetInvalidProperties();
        
        String listenerIPAddress = (String) props.get(TCPListenerProperties.TCP_ADDRESS);
        StringTokenizer IP = new StringTokenizer(listenerIPAddress, ".");
        if (IP.hasMoreTokens())
            listenerIPAddressField.setText(IP.nextToken());
        else
            listenerIPAddressField.setText("");
        if (IP.hasMoreTokens())
            listenerIPAddressField1.setText(IP.nextToken());
        else
            listenerIPAddressField1.setText("");
        if (IP.hasMoreTokens())
            listenerIPAddressField2.setText(IP.nextToken());
        else
            listenerIPAddressField2.setText("");
        if (IP.hasMoreTokens())
            listenerIPAddressField3.setText(IP.nextToken());
        else
            listenerIPAddressField3.setText("");

        listenerPortField.setText((String) props.get(TCPListenerProperties.TCP_PORT));
        receiveTimeoutField.setText((String) props.get(TCPListenerProperties.TCP_RECEIVE_TIMEOUT));
        bufferSizeField.setText((String) props.get(TCPListenerProperties.TCP_BUFFER_SIZE));

        boolean visible = parent.channelEditTasks.getContentPane().getComponent(0).isVisible();
        
        updateResponseDropDown();
        properties.put(TCPListenerProperties.TCP_RESPONSE_VALUE, (String)responseFromTransformer.getSelectedItem());

        parent.sePreviousSelectedEncodingForChannel(charsetEncodingCombobox, (String) props.get(TCPListenerProperties.CONNECTOR_CHARSET_ENCODING));

        if (((String) props.get(TCPListenerProperties.TCP_ACK_NEW_CONNECTION)).equalsIgnoreCase(UIConstants.YES_OPTION))
        {
            ackOnNewConnectionYesActionPerformed(null);
            ackOnNewConnectionYes.setSelected(true);
        }
        else
        {
            ackOnNewConnectionNoActionPerformed(null);
            ackOnNewConnectionNo.setSelected(true);
        }

        String ackIPAddress = (String) props.get(TCPListenerProperties.TCP_ACK_NEW_CONNECTION_IP);
        StringTokenizer ackIP = new StringTokenizer(ackIPAddress, ".");
        if (ackIP.hasMoreTokens())
            ackIPAddressField.setText(ackIP.nextToken());
        else
            ackIPAddressField.setText("");
        if (ackIP.hasMoreTokens())
            ackIPAddressField1.setText(ackIP.nextToken());
        else
            ackIPAddressField1.setText("");
        if (ackIP.hasMoreTokens())
            ackIPAddressField2.setText(ackIP.nextToken());
        else
            ackIPAddressField2.setText("");
        if (ackIP.hasMoreTokens())
            ackIPAddressField3.setText(ackIP.nextToken());
        else
            ackIPAddressField3.setText("");

        ackPortField.setText((String) props.get(TCPListenerProperties.TCP_ACK_NEW_CONNECTION_PORT));

        parent.channelEditTasks.getContentPane().getComponent(0).setVisible(visible);
    }

    public Properties getDefaults()
    {
        return new TCPListenerProperties().getDefaults();
    }

    public boolean checkProperties(Properties props)
    {
        resetInvalidProperties();
        boolean valid = true;
        
        if (((String) props.get(TCPListenerProperties.TCP_ADDRESS)).length() <= 3)
        {
            valid = false;
            listenerIPAddressField.setBackground(UIConstants.INVALID_COLOR);
            listenerIPAddressField1.setBackground(UIConstants.INVALID_COLOR);
            listenerIPAddressField2.setBackground(UIConstants.INVALID_COLOR);
            listenerIPAddressField3.setBackground(UIConstants.INVALID_COLOR);            
        }
        if (((String) props.get(TCPListenerProperties.TCP_PORT)).length() == 0)
        {
            valid = false;
            listenerPortField.setBackground(UIConstants.INVALID_COLOR);
        }
        if (((String) props.get(TCPListenerProperties.TCP_RECEIVE_TIMEOUT)).length() == 0)
        {
            valid = false;
            receiveTimeoutField.setBackground(UIConstants.INVALID_COLOR);
        }
        if (((String) props.get(TCPListenerProperties.TCP_BUFFER_SIZE)).length() == 0)
        {
            valid = false;
            bufferSizeField.setBackground(UIConstants.INVALID_COLOR);
        }
        if (((String) props.get(TCPListenerProperties.TCP_ACK_NEW_CONNECTION)).equals(UIConstants.YES_OPTION))
        {
            if (((String) props.get(TCPListenerProperties.TCP_ACK_NEW_CONNECTION_IP)).length() <= 3)
            {
                valid = false;
                ackIPAddressField.setBackground(UIConstants.INVALID_COLOR);
                ackIPAddressField1.setBackground(UIConstants.INVALID_COLOR);
                ackIPAddressField2.setBackground(UIConstants.INVALID_COLOR);
                ackIPAddressField3.setBackground(UIConstants.INVALID_COLOR);
            }
            if (((String) props.get(TCPListenerProperties.TCP_ACK_NEW_CONNECTION_PORT)).length() == 0)
            {
                valid = false;
                ackPortField.setBackground(UIConstants.INVALID_COLOR);
            }
        }
        
        return valid;
    }
    
    private void resetInvalidProperties()
    {
        listenerIPAddressField.setBackground(null);
        listenerIPAddressField1.setBackground(null);
        listenerIPAddressField2.setBackground(null);
        listenerIPAddressField3.setBackground(null);
        listenerPortField.setBackground(null);
        receiveTimeoutField.setBackground(null);
        bufferSizeField.setBackground(null);
        ackIPAddressField.setBackground(null);
        ackIPAddressField1.setBackground(null);
        ackIPAddressField2.setBackground(null);
        ackIPAddressField3.setBackground(null);
        ackPortField.setBackground(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        keepConnectionOpenGroup = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bufferSizeField = new com.webreach.mirth.client.ui.components.MirthTextField();
        receiveTimeoutField = new com.webreach.mirth.client.ui.components.MirthTextField();
        listenerPortField = new com.webreach.mirth.client.ui.components.MirthTextField();
        listenerIPAddressField3 = new com.webreach.mirth.client.ui.components.MirthTextField();
        jLabel25 = new javax.swing.JLabel();
        listenerIPAddressField2 = new com.webreach.mirth.client.ui.components.MirthTextField();
        jLabel26 = new javax.swing.JLabel();
        listenerIPAddressField1 = new com.webreach.mirth.client.ui.components.MirthTextField();
        jLabel9 = new javax.swing.JLabel();
        listenerIPAddressField = new com.webreach.mirth.client.ui.components.MirthTextField();
        charsetEncodingCombobox = new com.webreach.mirth.client.ui.components.MirthComboBox();
        jLabel39 = new javax.swing.JLabel();
        ackOnNewConnectionLabel = new javax.swing.JLabel();
        ackOnNewConnectionYes = new com.webreach.mirth.client.ui.components.MirthRadioButton();
        ackOnNewConnectionNo = new com.webreach.mirth.client.ui.components.MirthRadioButton();
        ackIPLabel = new javax.swing.JLabel();
        ackPortLabel = new javax.swing.JLabel();
        ackIPAddressField1 = new com.webreach.mirth.client.ui.components.MirthTextField();
        ackPortField = new com.webreach.mirth.client.ui.components.MirthTextField();
        ipDot1 = new javax.swing.JLabel();
        ackIPAddressField3 = new com.webreach.mirth.client.ui.components.MirthTextField();
        ipDot2 = new javax.swing.JLabel();
        ackIPAddressField = new com.webreach.mirth.client.ui.components.MirthTextField();
        ackIPAddressField2 = new com.webreach.mirth.client.ui.components.MirthTextField();
        ipDot = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        responseFromTransformer = new com.webreach.mirth.client.ui.components.MirthComboBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jLabel1.setText("Listener IP Address:");

        jLabel2.setText("Listener Port:");

        jLabel3.setText("Receive Timeout (ms):");

        jLabel4.setText("Buffer Size (bytes):");

        jLabel25.setText(".");

        jLabel26.setText(".");

        jLabel9.setText(".");

        charsetEncodingCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "default", "utf-8", "iso-8859-1", "utf-16 (le)", "utf-16 (be)", "utf-16 (bom)", "us-ascii" }));

        jLabel39.setText("Encoding:");

        ackOnNewConnectionLabel.setText("Response on New Connection:");

        ackOnNewConnectionYes.setBackground(new java.awt.Color(255, 255, 255));
        ackOnNewConnectionYes.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttonGroup4.add(ackOnNewConnectionYes);
        ackOnNewConnectionYes.setText("Yes");
        ackOnNewConnectionYes.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ackOnNewConnectionYes.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ackOnNewConnectionYesActionPerformed(evt);
            }
        });

        ackOnNewConnectionNo.setBackground(new java.awt.Color(255, 255, 255));
        ackOnNewConnectionNo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttonGroup4.add(ackOnNewConnectionNo);
        ackOnNewConnectionNo.setText("No");
        ackOnNewConnectionNo.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ackOnNewConnectionNo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ackOnNewConnectionNoActionPerformed(evt);
            }
        });

        ackIPLabel.setText("Response IP Address:");

        ackPortLabel.setText("Response Port:");

        ipDot1.setText(".");

        ipDot2.setText(".");

        ipDot.setText(".");

        jLabel6.setText("Response from Transformer:");

        responseFromTransformer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        responseFromTransformer.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                responseFromTransformerActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(10, 10, 10)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel1)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel2)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel3)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel4)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel39)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel6)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, ackOnNewConnectionLabel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, ackIPLabel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, ackPortLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(listenerIPAddressField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel9)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(listenerIPAddressField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel26)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(listenerIPAddressField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel25)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(listenerIPAddressField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(listenerPortField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(receiveTimeoutField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(bufferSizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(charsetEncodingCombobox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(responseFromTransformer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(ackIPAddressField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ipDot)
                        .add(4, 4, 4)
                        .add(ackIPAddressField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(4, 4, 4)
                        .add(ipDot1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ackIPAddressField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ipDot2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ackIPAddressField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(ackPortField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(ackOnNewConnectionYes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(ackOnNewConnectionNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(listenerIPAddressField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel1))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(listenerIPAddressField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(listenerIPAddressField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(listenerIPAddressField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(jLabel26)
                        .add(jLabel25)
                        .add(jLabel9)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(listenerPortField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(receiveTimeoutField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(bufferSizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel39)
                    .add(charsetEncodingCombobox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(responseFromTransformer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(ackOnNewConnectionLabel)
                    .add(ackOnNewConnectionYes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ackOnNewConnectionNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(ackIPLabel)
                    .add(ackIPAddressField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ipDot)
                    .add(ackIPAddressField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ipDot1)
                    .add(ackIPAddressField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ackIPAddressField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ipDot2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(ackPortLabel)
                    .add(ackPortField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void responseFromTransformerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_responseFromTransformerActionPerformed
    {//GEN-HEADEREND:event_responseFromTransformerActionPerformed
        if (responseFromTransformer.getSelectedIndex() != 0 && !parent.channelEditPanel.synchronousCheckBox.isSelected())
        {
            parent.alertInformation("The synchronize source connector setting has been enabled since it is required to use this feature.");
            parent.channelEditPanel.synchronousCheckBox.setSelected(true);
        }
    }//GEN-LAST:event_responseFromTransformerActionPerformed
    
    public void updateResponseDropDown()
    {
        boolean visible = parent.channelEditTasks.getContentPane().getComponent(0).isVisible();
        
        String selectedItem = (String) responseFromTransformer.getSelectedItem();
        
        Channel channel = parent.channelEditPanel.currentChannel;
        
        ArrayList<String> variables = new ArrayList<String>();
        
        variables.add("None");
        
        List<Step> stepsToCheck = new ArrayList<Step>();
        stepsToCheck.addAll(channel.getSourceConnector().getTransformer().getSteps());      
        
        for(Connector connector : channel.getDestinationConnectors())
        {
            variables.add(connector.getName());
            stepsToCheck.addAll(connector.getTransformer().getSteps());
        }       
               
        int i = 0;
        for (Iterator it = stepsToCheck.iterator(); it.hasNext();)
        {
            Step step = (Step) it.next();
            Map data;
            data = (Map) step.getData();
            
            if (step.getType().equalsIgnoreCase(TransformerPane.JAVASCRIPT_TYPE))
            {
                Pattern pattern = Pattern.compile(RESULT_PATTERN);
                Matcher matcher = pattern.matcher(step.getScript());
                while (matcher.find())
                {
                    String key = matcher.group(1);
                    variables.add(key);
                }
            }
            else if (step.getType().equalsIgnoreCase(TransformerPane.MAPPER_TYPE))
            {
                if(data.containsKey(UIConstants.IS_GLOBAL))
                {
                    if (((String) data.get(UIConstants.IS_GLOBAL)).equalsIgnoreCase(UIConstants.IS_GLOBAL_RESPONSE))
                        variables.add((String)data.get("Variable"));
                }
            }
        }
        
        responseFromTransformer.setModel(new DefaultComboBoxModel(variables.toArray()));
        
        if(variables.contains(selectedItem))
            responseFromTransformer.setSelectedItem(selectedItem);
        else
            responseFromTransformer.setSelectedIndex(0);
                
        parent.channelEditTasks.getContentPane().getComponent(0).setVisible(visible);
    }
        
    private void ackOnNewConnectionNoActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_ackOnNewConnectionNoActionPerformed
    {// GEN-HEADEREND:event_ackOnNewConnectionNoActionPerformed
        ackIPAddressField.setEnabled(false);
        ackIPAddressField1.setEnabled(false);
        ackIPAddressField2.setEnabled(false);
        ackIPAddressField3.setEnabled(false);
        ackPortField.setEnabled(false);

        ipDot.setEnabled(false);
        ipDot1.setEnabled(false);
        ipDot2.setEnabled(false);
        ackIPLabel.setEnabled(false);
        ackPortLabel.setEnabled(false);
    }// GEN-LAST:event_ackOnNewConnectionNoActionPerformed

    private void ackOnNewConnectionYesActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_ackOnNewConnectionYesActionPerformed
    {// GEN-HEADEREND:event_ackOnNewConnectionYesActionPerformed
        ackIPAddressField.setEnabled(true);
        ackIPAddressField1.setEnabled(true);
        ackIPAddressField2.setEnabled(true);
        ackIPAddressField3.setEnabled(true);
        ackPortField.setEnabled(true);

        ipDot.setEnabled(true);
        ipDot1.setEnabled(true);
        ipDot2.setEnabled(true);
        ackIPLabel.setEnabled(true);
        ackPortLabel.setEnabled(true);
    }// GEN-LAST:event_ackOnNewConnectionYesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.webreach.mirth.client.ui.components.MirthTextField ackIPAddressField;
    private com.webreach.mirth.client.ui.components.MirthTextField ackIPAddressField1;
    private com.webreach.mirth.client.ui.components.MirthTextField ackIPAddressField2;
    private com.webreach.mirth.client.ui.components.MirthTextField ackIPAddressField3;
    private javax.swing.JLabel ackIPLabel;
    private javax.swing.JLabel ackOnNewConnectionLabel;
    private com.webreach.mirth.client.ui.components.MirthRadioButton ackOnNewConnectionNo;
    private com.webreach.mirth.client.ui.components.MirthRadioButton ackOnNewConnectionYes;
    private com.webreach.mirth.client.ui.components.MirthTextField ackPortField;
    private javax.swing.JLabel ackPortLabel;
    private com.webreach.mirth.client.ui.components.MirthTextField bufferSizeField;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private com.webreach.mirth.client.ui.components.MirthComboBox charsetEncodingCombobox;
    private javax.swing.JLabel ipDot;
    private javax.swing.JLabel ipDot1;
    private javax.swing.JLabel ipDot2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.ButtonGroup keepConnectionOpenGroup;
    private com.webreach.mirth.client.ui.components.MirthTextField listenerIPAddressField;
    private com.webreach.mirth.client.ui.components.MirthTextField listenerIPAddressField1;
    private com.webreach.mirth.client.ui.components.MirthTextField listenerIPAddressField2;
    private com.webreach.mirth.client.ui.components.MirthTextField listenerIPAddressField3;
    private com.webreach.mirth.client.ui.components.MirthTextField listenerPortField;
    private com.webreach.mirth.client.ui.components.MirthTextField receiveTimeoutField;
    private com.webreach.mirth.client.ui.components.MirthComboBox responseFromTransformer;
    // End of variables declaration//GEN-END:variables

}

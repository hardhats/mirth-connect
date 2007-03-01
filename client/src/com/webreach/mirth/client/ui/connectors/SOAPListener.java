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


package com.webreach.mirth.client.ui.connectors;

import java.util.Properties;

import com.webreach.mirth.client.ui.Frame;
import com.webreach.mirth.client.ui.PlatformUI;
import com.webreach.mirth.client.ui.UIConstants;

/**
 * A form that extends from ConnectorClass.  All methods implemented
 * are described in ConnectorClass.
 */
public class SOAPListener extends ConnectorClass
{
    /**
     * Creates new form SOAPListener
     */
    private final String DATATYPE = "DataType";
    private final String SOAP_HOST = "host";
    private final String SOAP_LISTENER_ADDRESS = "listenerAddress";
    private final String SOAP_SERVICE_NAME = "serviceName";
    private final String SOAP_PORT = "port";
    private final String SOAP_CONTENT_TYPE = "Content-Type";
    private final String SOAP_RESPONSE_FROM_TRANSFORMER = "responseFromTransformer";

    public SOAPListener()
    {
        name = "SOAP Listener";
        initComponents();
        wsdlURL.setEditable(false);
        method.setEditable(false);
        makeResponseStep();
    }

    public Properties getProperties()
    {
        Properties properties = new Properties();
        properties.put(DATATYPE, name);
        properties.put(SOAP_LISTENER_ADDRESS, listenerAddress.getText());
        properties.put(SOAP_PORT, port.getText());
        properties.put(SOAP_SERVICE_NAME, serviceName.getText());
        properties.put(SOAP_HOST, buildHost());
        properties.put(SOAP_CONTENT_TYPE, "text/xml");
        if(responseFromTransformerYes.isSelected())
            properties.put(SOAP_RESPONSE_FROM_TRANSFORMER, UIConstants.YES_OPTION);
        else
            properties.put(SOAP_RESPONSE_FROM_TRANSFORMER, UIConstants.NO_OPTION);    
        return properties;
    }

    public void setProperties(Properties props)
    {
        listenerAddress.setText((String)props.get(SOAP_LISTENER_ADDRESS));
        port.setText((String)props.getProperty(SOAP_PORT));
        serviceName.setText((String)props.getProperty(SOAP_SERVICE_NAME));
        if(((String)props.get(SOAP_RESPONSE_FROM_TRANSFORMER)).equals(UIConstants.YES_OPTION))
            responseFromTransformerYes.setSelected(true);
        else
            responseFromTransformerNo.setSelected(true);
        updateWSDL();
    }
    
    public Properties getDefaults()
    {
        Properties properties = new Properties();
        properties.put(DATATYPE, name);
        properties.put(SOAP_HOST, "axis:http://localhost:8081/services");
        properties.put(SOAP_LISTENER_ADDRESS, "localhost");
        properties.put(SOAP_PORT, "8081");
        properties.put(SOAP_SERVICE_NAME, "Mirth");
        properties.put(SOAP_CONTENT_TYPE, "text/xml");
        properties.put(SOAP_RESPONSE_FROM_TRANSFORMER, UIConstants.NO_OPTION);  
        return properties;
    }
    
    public String buildHost()
    {
        return "axis:http://" + listenerAddress.getText() + ":" + port.getText() + "/services";
    }
    
    public void updateWSDL()
    {
        wsdlURL.setText("http://" + listenerAddress.getText() + ":" + port.getText() + "/services/" + serviceName.getText() + "?wsdl");
        
    }
    
    public boolean checkProperties(Properties props) 
    {
        if (((String) props.get(SOAP_LISTENER_ADDRESS)).length() > 0
                        && ((String) props.get(SOAP_PORT)).length() > 0
                        && ((String) props.get(SOAP_SERVICE_NAME)).length() > 0)
                return true;
        return false;
    }

    
    /**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        buttonGroup1 = new javax.swing.ButtonGroup();
        URL = new javax.swing.JLabel();
        serviceName = new com.webreach.mirth.client.ui.components.MirthTextField();
        port = new com.webreach.mirth.client.ui.components.MirthTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        URL1 = new javax.swing.JLabel();
        listenerAddress = new com.webreach.mirth.client.ui.components.MirthTextField();
        jLabel3 = new javax.swing.JLabel();
        method = new javax.swing.JTextField();
        wsdlURL = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        responseFromTransformerYes = new com.webreach.mirth.client.ui.components.MirthRadioButton();
        responseFromTransformerNo = new com.webreach.mirth.client.ui.components.MirthRadioButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        URL.setText("Service Name:");

        serviceName.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                serviceNameKeyReleased(evt);
            }
        });

        port.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                portKeyReleased(evt);
            }
        });

        jLabel1.setText("Port:");

        jLabel2.setText("Method:");

        URL1.setText("WSDL URL:");

        listenerAddress.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                listenerAddressKeyReleased(evt);
            }
        });

        jLabel3.setText("Listener Address:");

        method.setText("String acceptMessage(String message)");

        jLabel4.setText("Response from Transformer:");

        responseFromTransformerYes.setBackground(new java.awt.Color(255, 255, 255));
        responseFromTransformerYes.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttonGroup1.add(responseFromTransformerYes);
        responseFromTransformerYes.setText("Yes");
        responseFromTransformerYes.setMargin(new java.awt.Insets(0, 0, 0, 0));
        responseFromTransformerYes.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                responseFromTransformerYesActionPerformed(evt);
            }
        });

        responseFromTransformerNo.setBackground(new java.awt.Color(255, 255, 255));
        responseFromTransformerNo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        buttonGroup1.add(responseFromTransformerNo);
        responseFromTransformerNo.setText("No");
        responseFromTransformerNo.setMargin(new java.awt.Insets(0, 0, 0, 0));
        responseFromTransformerNo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                responseFromTransformerNoActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel4)
                    .add(jLabel2)
                    .add(URL)
                    .add(jLabel1)
                    .add(jLabel3)
                    .add(URL1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(serviceName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(port, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(listenerAddress, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(wsdlURL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 400, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(method, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(responseFromTransformerYes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(responseFromTransformerNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(listenerAddress, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(port, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(URL)
                    .add(serviceName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(URL1)
                    .add(wsdlURL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(method, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(responseFromTransformerYes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(responseFromTransformerNo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void responseFromTransformerYesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_responseFromTransformerYesActionPerformed
    {//GEN-HEADEREND:event_responseFromTransformerYesActionPerformed
        setResponseStep();
    }//GEN-LAST:event_responseFromTransformerYesActionPerformed

    private void responseFromTransformerNoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_responseFromTransformerNoActionPerformed
    {//GEN-HEADEREND:event_responseFromTransformerNoActionPerformed
        removeResponseStep();
    }//GEN-LAST:event_responseFromTransformerNoActionPerformed

    private void serviceNameKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_serviceNameKeyReleased
    {//GEN-HEADEREND:event_serviceNameKeyReleased
        updateWSDL();
    }//GEN-LAST:event_serviceNameKeyReleased

    private void portKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_portKeyReleased
    {//GEN-HEADEREND:event_portKeyReleased
        updateWSDL();
    }//GEN-LAST:event_portKeyReleased

    private void listenerAddressKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_listenerAddressKeyReleased
    {//GEN-HEADEREND:event_listenerAddressKeyReleased
        updateWSDL();
    }//GEN-LAST:event_listenerAddressKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel URL;
    private javax.swing.JLabel URL1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private com.webreach.mirth.client.ui.components.MirthTextField listenerAddress;
    private javax.swing.JTextField method;
    private com.webreach.mirth.client.ui.components.MirthTextField port;
    private com.webreach.mirth.client.ui.components.MirthRadioButton responseFromTransformerNo;
    private com.webreach.mirth.client.ui.components.MirthRadioButton responseFromTransformerYes;
    private com.webreach.mirth.client.ui.components.MirthTextField serviceName;
    private javax.swing.JTextField wsdlURL;
    // End of variables declaration//GEN-END:variables

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiny;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 *
 * @author yehiaa
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextArea text;
    @FXML
    private Button scanbtn;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if("Scan".equals(scanbtn.getText())){
            Scanner scan = new Scanner(text.getText());
            scan.analyze();
            text.setText(scan.toString());
            scanbtn.setText("Re-enter");
        }else{
            scanbtn.setText("Scan");
            text.setText("");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

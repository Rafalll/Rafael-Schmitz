/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.utfpr.controller;

import br.edu.utfpr.dao.ClienteDao;
import br.edu.utfpr.model.Cidade;
import br.edu.utfpr.model.Cliente;
import br.edu.utfpr.model.Produto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rafa
 */
public class FXMLClienteListaController implements Initializable {

    @FXML
    private TableView<Cliente> tableData;

    @FXML
    private TableColumn<Cliente, Long> columnId;
    @FXML
    private TableColumn<Cliente, String> columnNome;

    @FXML
    private TableColumn<Cliente, String> columnCpf;
    

    @FXML
    private TableColumn<Cliente, String> columnTelC;

    @FXML
    private TableColumn<Cliente, String> columnEmailC;
    

    @FXML
    private TableColumn<Cliente, String> columnEndE;

    @FXML
    private TableColumn<Cliente, String> columnBaiE;

    @FXML
    private TableColumn<Cliente, Cidade> columnCidE;

    private ClienteDao clienteDao;
    private ObservableList<Cliente> list
            = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.clienteDao = new ClienteDao();

        setColumnProperties();

        loadData();
    }

    private void loadData() {
        list.clear();
        list.addAll(clienteDao.getAll());

        tableData.setItems(list);

    }


    private void setColumnProperties() {
        columnId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        columnNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );
        columnCpf.setCellValueFactory(
                new PropertyValueFactory<>("cpf")
        );
        
        
        columnTelC.setCellValueFactory(
                new PropertyValueFactory<>("telefone")
        );
        
        columnEmailC.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );
        
        columnEndE.setCellValueFactory(
                new PropertyValueFactory<>("endereco")
        );
        
        columnBaiE.setCellValueFactory(
                new PropertyValueFactory<>("bairro")
        );
        
        
        columnCidE.setCellValueFactory(
                new PropertyValueFactory<>("cidade")
        );

    }

    private void openForm(
            Cliente cliente, 
            ActionEvent event) {
        try {
            // Carregar o arquivo fxml e cria um
            //novo stage para a janela Modal
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                this.getClass()
                    .getResource("/fxml/FXMLClienteCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            
            //Criando o stage para o modal
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Clientes");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(
                    ((Node) event.getSource())
                            .getScene().getWindow());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            
            FXMLClienteCadastroController controller = 
                    loader.getController();
            controller.setCliente(cliente);
            controller.setDialogStage(dialogStage);
            // Exibe a janela Modal e espera até o usuário
            //fechar
            dialogStage.showAndWait();
            
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Ocorreu um erro ao abrir "
                    + "a janela de cadastro!");
            alert.setContentText("Por favor, tente realizar "
                    + "a operação novamente!");
            alert.showAndWait();
        }
        loadData();
    }
    
    @FXML
    private void edit(ActionEvent event) {
        Cliente cliente = 
                tableData.getSelectionModel()
                    .getSelectedItem();
        this.openForm(cliente, event);
    }
    
    @FXML
    private void newRecord(ActionEvent event) {
        this.openForm(new Cliente(), event);
    }
    
    @FXML
    private void delete(ActionEvent event) {
        if (tableData.getSelectionModel()
                .getSelectedIndex() >=0) {
            try {
                Cliente cliente =  tableData
                        .getSelectionModel().getSelectedItem();
                clienteDao.delete(cliente.getId());
                tableData.getItems().remove(
                        tableData.getSelectionModel()
                                    .getSelectedIndex());
                        
                       
                
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Ocorreu um erro "
                    + " ao remover o registro!");
            alert.setContentText("Por favor, tente realizar "
                    + "a operação novamente!");
            alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhum registro "
                    + "selecionado");
            alert.setContentText("Por favor, "
                    + "selecione um registro "
                    + "na tabela!");
            alert.showAndWait();
        }
    }
}

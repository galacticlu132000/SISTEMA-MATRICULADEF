package interfazfx;

import clases.Estudiante;
import control.GestorEstudiantes;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuAdministradorControlador {

    @FXML private TableView<Estudiante> tablaEstudiantes;
    @FXML private TableColumn<Estudiante, String> colID, colNombre, colCorreo;

    private GestorEstudiantes gestor;

    public void setGestor(GestorEstudiantes gestor) {
        this.gestor = gestor;
        cargarEstudiantes();
    }

    @FXML
    private void initialize() {
        colID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdentificacionPersonal()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreCompleto()));
        colCorreo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCorreoElectronico()));
    }

    private void cargarEstudiantes() {
        tablaEstudiantes.setItems(FXCollections.observableArrayList(gestor.listarEstudiantes()));
    }

    @FXML
    private void abrirRegistro() {
        MainApp.cambiarEscena("RegistroEstudiante.fxml", 600, 500);
    }

    @FXML
    private void abrirModificacion() {
        Estudiante seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            // lógica de modificación
        }
    }

    @FXML
    private void eliminarEstudiante() {
        Estudiante seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            gestor.eliminarEstudiante(seleccionado.getIdentificacionPersonal());
            tablaEstudiantes.getItems().remove(seleccionado);
        }
    }

    @FXML
    private void verDetalles() {
        Estudiante seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfazfx/DetallesEstudiante.fxml"));
                Parent root = loader.load();

                DetallesEstudianteControlador controlador = loader.getController();
                controlador.inicializarConEstudiante(seleccionado);

                Stage ventana = new Stage();
                ventana.setTitle("Detalles del Estudiante");
                ventana.setScene(new Scene(root));
                ventana.initModality(Modality.APPLICATION_MODAL);
                ventana.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Sin selección");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor selecciona un estudiante para ver sus detalles.");
            alerta.showAndWait();
        }
    }}



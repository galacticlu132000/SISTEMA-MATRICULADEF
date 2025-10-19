package interfazfx;

import clases.Estudiante;
import control.GestorEstudiantes;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuAdministradorControlador {

    @FXML private TableView<Estudiante> tablaEstudiantes;
    @FXML private TableColumn<Estudiante, String> colID, colNombre, colCorreo;

    private final GestorEstudiantes gestor = GestorEstudiantes.getInstancia();

    @FXML
    private void initialize() {
        colID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIdentificacionPersonal()));
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreCompleto()));
        colCorreo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCorreoElectronico()));
        cargarEstudiantes();
    }

    private void cargarEstudiantes() {
        tablaEstudiantes.setItems(FXCollections.observableArrayList(gestor.listarEstudiantes()));
    }

    @FXML
    private void abrirRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfazfx/CRUDEstudiantes/RegistroEstudiante.fxml"));
            Parent root = loader.load();

            Stage ventana = new Stage();
            ventana.setTitle("Registrar Estudiante");
            ventana.setScene(new Scene(root));
            ventana.initModality(Modality.APPLICATION_MODAL);
            ventana.showAndWait();

            cargarEstudiantes(); // refrescar tabla después de registrar
        } catch (Exception e) {
            mostrarError("No se pudo abrir la ventana de registro.");
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirModificacion() {
        Estudiante seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfazfx/CRUDEstudiantes/ModificarEstudiante.fxml"));
                Parent root = loader.load();

                ModificarEstudianteControlador controlador = loader.getController();
                controlador.inicializarConEstudiante(seleccionado);

                Stage ventana = new Stage();
                ventana.setTitle("Modificar Estudiante");
                ventana.setScene(new Scene(root));
                ventana.initModality(Modality.APPLICATION_MODAL);
                ventana.showAndWait();

                cargarEstudiantes(); // refrescar tabla después de modificar
            } catch (Exception e) {
                mostrarError("No se pudo abrir la ventana de modificación.");
                e.printStackTrace();
            }
        } else {
            mostrarAdvertencia("Por favor selecciona un estudiante para modificar.");
        }
    }

    @FXML
    private void eliminarEstudiante() {
        Estudiante seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            gestor.eliminarEstudiante(seleccionado.getIdentificacionPersonal());
            tablaEstudiantes.getItems().remove(seleccionado);
        } else {
            mostrarAdvertencia("Por favor selecciona un estudiante para eliminar.");
        }
    }

    @FXML
    private void verDetalles() {
        Estudiante seleccionado = tablaEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfazfx/CRUDEstudiantes/DetallesEstudiante.fxml"));
                Parent root = loader.load();

                DetallesEstudianteControlador controlador = loader.getController();
                controlador.inicializarConEstudiante(seleccionado);

                Stage ventana = new Stage();
                ventana.setTitle("Detalles del Estudiante");
                ventana.setScene(new Scene(root));
                ventana.initModality(Modality.APPLICATION_MODAL);
                ventana.showAndWait();
            } catch (Exception e) {
                mostrarError("No se pudo abrir la ventana de detalles.");
                e.printStackTrace();
            }
        } else {
            mostrarAdvertencia("Por favor selecciona un estudiante para ver sus detalles.");
        }
    }

    private void mostrarAdvertencia(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

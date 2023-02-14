package com.example.gui_first;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.gui_first.Domain.ADTs.*;
import com.example.gui_first.Domain.Stmt.*;
import com.example.gui_first.Domain.Values.*;
import com.example.gui_first.Domain.Expressions.*;
import com.example.gui_first.Domain.Types.*;
import com.example.gui_first.Domain.PrgState;
import com.example.gui_first.Repository.*;
import com.example.gui_first.Controller.*;
import com.example.gui_first.MyException.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProgramExecCtrl {
    private Ctrl controller;

    @FXML
    private TextField myNumberOfProgramStatesTextField;

    @FXML
    private TableView<Pair<Integer, Value>> myHeapTableView;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> valueColumn;

    @FXML
    private ListView<String> myOutputListView;

    @FXML
    private ListView<String> myFileTableListView;

    @FXML
    private ListView<Integer> myProgramStateIdentifiersListView;

    @FXML
    private TableView<Pair<String, Value>> mySymbolTableView;

    @FXML
    private TableColumn<Pair<String, Value>, String> variableNameColumn;

    @FXML
    private TableColumn<Pair<String, Value>, String> variableValueColumn;

    @FXML
    private ListView<String> myExecutionStackListView;

    @FXML
    private Button runOneStepButton;

    /////////////////
    @FXML
    private TableView<Pair<Integer, Integer>> myTable;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> myIndex;

    @FXML
    private TableColumn<Pair<Integer, Integer>, String> myValue;



    public void setController(Ctrl controller) {
        this.controller = controller;
        populate();
    }
    @FXML
    public void initialize() {
        myProgramStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));//second
        variableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        variableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
        myIndex.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        myValue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));//second
    }

    @FXML
    private void setIndex(MouseEvent actionEvent){
        int id=myProgramStateIdentifiersListView.getSelectionModel().getSelectedIndex();
        //System.out.println(id);

        populateExecutionStackListView();
        populateSymbolTableView();
    }

    private PrgState getCurrentProgramState() {
        if (controller.getPrgStates().size() == 0)
            return null;
        else {
            int currentId = myProgramStateIdentifiersListView.getSelectionModel().getSelectedIndex();
            if (currentId == -1)
                return controller.getPrgStates().get(0);
            else
                return controller.getPrgStates().get(currentId);
        }
    }
    private void populate() {
        populateHeapTableView();
        populateOutputListView();
        populateFileTableListView();
        populateProgramStateIdentifiersListView();
        populateSymbolTableView();
        populateExecutionStackListView();
        populateLatchTableView();
    }


    private void populateLatchTableView() {
        PrgState programState = getCurrentProgramState();
        ILatchTable table = Objects.requireNonNull(programState).getLatchTable();
        ArrayList<Pair<Integer, Integer>> tableEntries = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry: table.getContent().entrySet()) {
            tableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        myTable.setItems(FXCollections.observableArrayList(tableEntries));
    }

    private void populateNumberOfProgramStatesTextField() {
        List<PrgState> programStates = controller.getPrgStates();
        myNumberOfProgramStatesTextField.setText(String.valueOf(programStates.size()));
    }
    private void populateExecutionStackListView() {
        PrgState programState = getCurrentProgramState();
        List<String> executionStackToString = new ArrayList<>();
        if (programState != null)
            executionStackToString.add(programState.getStk().toString());
        myExecutionStackListView.setItems(FXCollections.observableList(executionStackToString));
    }

    private void populateSymbolTableView() {
        PrgState programState = getCurrentProgramState();
        MyIDictionary<String, Value> symbolTable = Objects.requireNonNull(programState).getSymTable();
        ArrayList<Pair<String, Value>> symbolTableEntries = new ArrayList<>();
        for (Map.Entry<String, Value> entry: symbolTable.getContent().entrySet()) {
            symbolTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        mySymbolTableView.setItems(FXCollections.observableArrayList(symbolTableEntries));
    }

    private void populateProgramStateIdentifiersListView() {
        List<PrgState> programStates = controller.getPrgStates();
        List<Integer> idList = programStates.stream().map(PrgState::getMyid).collect(Collectors.toList());
        myProgramStateIdentifiersListView.setItems(FXCollections.observableList(idList));
        populateNumberOfProgramStatesTextField();
    }

    private void populateFileTableListView() {
        PrgState programState = getCurrentProgramState();
        List<String> files = new ArrayList<>(Objects.requireNonNull(programState).getFileTable().getContent().keySet());
        myFileTableListView.setItems(FXCollections.observableList(files));
    }

    private void populateOutputListView() {
        PrgState programState = getCurrentProgramState();
        List<String> output = new ArrayList<>();
        List<Value> outputList = Objects.requireNonNull(programState).getList().getList();
        int index;
        for (index = 0; index < outputList.size(); index++){
            output.add(outputList.get(index).toString());
        }
        myOutputListView.setItems(FXCollections.observableArrayList(output));
    }

    private void populateHeapTableView() {
        PrgState programState = getCurrentProgramState();
        MyIHeap heap = Objects.requireNonNull(programState).getHeap();
        ArrayList<Pair<Integer, Value>> heapEntries = new ArrayList<>();
        for(Map.Entry<Integer, Value> entry: heap.getContent().entrySet()) {
            heapEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        myHeapTableView.setItems(FXCollections.observableArrayList(heapEntries));
    }

    @FXML
    private void runOneStep(MouseEvent mouseEvent) {
        if (controller != null) {
            try {
                List<PrgState> programStates = Objects.requireNonNull(controller.getPrgStates());
                if (programStates.size() > 0) {
                    controller.oneStep();
                    populate();
                    programStates = controller.removeCompletedPrg(controller.getPrgStates());
                    controller.setPrgStates(programStates);
                    populateProgramStateIdentifiersListView();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("An error has occured!");
                    alert.setContentText("There is nothing left to execute!");
                    alert.showAndWait();
                }
            } catch (InterruptedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Execution error!");
                alert.setHeaderText("An execution error has occured!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } catch (ExprException e) {
                throw new RuntimeException(e);
            } catch (MyException e) {
                throw new RuntimeException(e);
            } catch (StmtException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error has occured!");
            alert.setContentText("No program selected!");
            alert.showAndWait();
        }
    }


}

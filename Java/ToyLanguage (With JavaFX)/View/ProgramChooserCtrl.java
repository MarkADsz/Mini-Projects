package com.example.gui_first;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.util.ArrayList;
import java.util.List;

public class ProgramChooserCtrl {
    @FXML
    private ListView<IStmt> myListView;

    @FXML
    private Button displayButton;

    private ProgramExecCtrl programExecutorController;

    public void setProgramExecutorController(ProgramExecCtrl programExecutorController) {
        this.programExecutorController = programExecutorController;
    }

    @FXML
    public void initialize() {
        myListView.setItems(getAllStatements());
        myListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(MouseEvent actionEvent) {
        IStmt selectedStatement = myListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = myListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typecheck(new MyDictionary<>());
                PrgState programState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(),new LatchTable() ,selectedStatement);
                IRepo repository = new Repo(programState,"C:\\UBB_FMI\\MAP\\projects - sablonBUN - examen1 - bun\\final-gui\\gui_first\\src\\main\\java\\myfile.txt" );//"log" + (id + 1) + ".txt"
                Ctrl controller = new Ctrl(repository);
                programExecutorController.setController(controller);
//            } catch (MyException | IOException exception ) {
           } catch (MyException exception ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }
    private ObservableList<IStmt> getAllStatements() {
        List<IStmt> allStatements = new ArrayList<>();

        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),//a
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),new
                                ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)),3),1)),
                                new CompStmt(new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExp(new
                                        IntValue(1)),1)), new PrintStmt(new VarExp("b"))))));
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),//Bool
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        IStmt ex4=new CompStmt(new VarDeclStmt("varf",new StringType()),new CompStmt(
                new AssignStmt("varf",new ValueExp(new StringValue("C:\\UBB_FMI\\MAP\\projects\\final-gui\\gui_first\\src\\main\\java\\test.in"))),new CompStmt(
                new OpenRFile(new VarExp("varf")),new CompStmt(new VarDeclStmt("varc",new IntType()),new CompStmt(
                new ReadFile(new VarExp("varf"),"varc"),new CompStmt(new PrintStmt(new VarExp("varc")),new CompStmt(
                new ReadFile(new VarExp("varf"),"varc"),new CompStmt(
                new PrintStmt(new VarExp("varc")),new CloseRFile(new VarExp("varf"))))))))));
        IStmt ex5=new CompStmt(new VarDeclStmt("v",new RefType( new IntType())),
                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt( new VarDeclStmt("a",new RefType( new RefType(new IntType()))),
                                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                        new CompStmt(new NewStmt("a",new VarExp("v")),
                                                new PrintStmt(new ReadHeap(new ReadHeap(new VarExp("a")))))))));
        IStmt ex6= new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeap("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new ReadHeap(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeap(new VarExp("a")))))))));

        IStmt ex7= new CompStmt(new VarDeclStmt("v", new IntType()),
                                                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                                                                new CompStmt(new WhileStmt(new RelationalExpression(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                                                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)),2)))),
                                                                        new PrintStmt(new VarExp("v")))));

        IStmt ex8= new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),new WriteHeap("a", new ValueExp(new IntValue(30))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeap(new VarExp("a")))))))));

//        IStmt pushable=new CompStmt(new AssignStmt("v", exp1),new WhileStmt(new RelationalExpression("<",new VarExp("v"),exp2),new CompStmt( myStatement,new AssignStmt("v",exp3))))
        //IStmt ex9=new CompStmt( new VarDeclStmt("v", new IntType()),new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),new WhileStmt(new RelationalExpression("<",new VarExp("v"),new ValueExp(new IntValue(5))),new CompStmt( new PrintStmt(new VarExp("v")),new AssignStmt("v",new ArithExp(new VarExp("v"),new ValueExp(new IntValue(1)),1))))));
       // IStmt ex9= new CompStmt(new VarDeclStmt("v",new IntType()), new ForStmt(new PrintStmt(new VarExp("v")),new ValueExp(new IntValue(0)), new ValueExp(new IntValue(5)),new ArithExp(new VarExp("v"),new ValueExp(new IntValue(1)),1)));
//        IStmt ex9=new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
//                new CompStmt( new WhileStmt(new RelationalExpression("<",,new VarExp("v")),new PrintStmt(new VarExp("v"))),new AssignStmt("v",new ArithExp(new VarExp("v"),new ValueExp(new IntValue(1)),1))));
//        IStmt ex9=new CompStmt(new VarDeclStmt("a",new IntType()),new CompStmt( new VarDeclStmt("v", new IntType()),
//                new ForStmt( new PrintStmt(new VarExp("v")), new ValueExp( new IntValue(0)),new ValueExp(new IntValue(5)),new ArithExp(new VarExp("v"),new ValueExp(new IntValue(1)),1))));

        IStmt ex9=new CompStmt(new VarDeclStmt("v",new IntType() ),new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                new CompStmt(new NewStmt("a",new ValueExp(new IntValue(20))),
                        new CompStmt(new ForStmt(new ForkStmt(new CompStmt(new PrintStmt(new VarExp("v")),new AssignStmt("v",new ArithExp(new ReadHeap(new VarExp("a")),new ValueExp(new IntValue(2)) ,3))))
                        ,new ValueExp(new IntValue(0)),new ValueExp(new IntValue(3)),new ArithExp(new VarExp("v"),new ValueExp(new IntValue(1)),1)),
                        new PrintStmt(new ReadHeap(new VarExp("a"))))
                        )));


        IStmt ex10=new CompStmt(new VarDeclStmt("a",new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("b",new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v",new IntType()),
                        new CompStmt(new NewStmt("a",new ValueExp(new IntValue(0))),
                                new CompStmt(new NewStmt("b",new ValueExp(new IntValue(0))),
                                        new CompStmt(new WriteHeap("a",new ValueExp(new IntValue(1))),
                                                new CompStmt(new WriteHeap("b",new ValueExp(new IntValue(2))),
                                                        new CompStmt(new CondAssignStmt("v",new RelationalExpression("<",new ReadHeap(new VarExp("a")),new ReadHeap(new VarExp("b"))),
                                                                new ValueExp(new IntValue(100)),new ValueExp(new IntValue(200))),
                                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                                        new CompStmt(new CondAssignStmt("v",new RelationalExpression(">",new ArithExp(new ReadHeap(new VarExp("b")),new ValueExp(new IntValue(2)),2) ,new ReadHeap(new VarExp("a"))),
                                                                                new ValueExp(new IntValue(100)),new ValueExp(new IntValue(200))),
                                                                                new PrintStmt(new VarExp("v"))
                                                                                        ))))))))));

        IStmt ex11=new CompStmt(new VarDeclStmt("v1",new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2",new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v3",new RefType(new IntType())),
                                new CompStmt(new VarDeclStmt("cnt",new IntType()),
                                        new CompStmt(new NewStmt("v1",new ValueExp(new IntValue(2))),
                                                new CompStmt(new NewStmt("v2",new ValueExp(new IntValue(3))),
                                                        new CompStmt(new NewStmt("v3",new ValueExp(new IntValue(4))),
                                                                new CompStmt(new newLatch("cnt",new ReadHeap(new VarExp("v2"))),
                                                                        new CompStmt(new ForkStmt(new CompStmt(
                                                                                new WriteHeap("v1",new ArithExp(new ReadHeap(new VarExp("v1")),new ValueExp(new IntValue(10)),3)),
                                                                                new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v1"))),
                                                                                        new CompStmt(new countDownStmt("cnt"),new ForkStmt(new CompStmt(
                                                                                                        new WriteHeap("v2",new ArithExp(new ReadHeap(new VarExp("v2")),new ValueExp(new IntValue(10)),3)),
                                                                                                        new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v2"))),
                                                                                                                new CompStmt(new countDownStmt("cnt"),
                                                                                                                        new ForkStmt(new CompStmt(
                                                                                                                                new WriteHeap("v3",new ArithExp(new ReadHeap(new VarExp("v3")),new ValueExp(new IntValue(10)),3)),
                                                                                                                                new CompStmt(new PrintStmt(new ReadHeap(new VarExp("v3"))),
                                                                                                                                        new countDownStmt("cnt")))))))))))),
                                                                                new CompStmt(new awaitStmt("cnt"),
                                                                                        new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                                new CompStmt(new countDownStmt("cnt"),new PrintStmt(new ValueExp(new IntValue(100)))) )))))))

                                ))));
        allStatements.add(ex1);
        allStatements.add(ex2);
        allStatements.add(ex3);
        allStatements.add(ex4);
        allStatements.add(ex5);
        allStatements.add(ex6);
        allStatements.add(ex7);
        allStatements.add(ex8);
        allStatements.add(ex9);
        allStatements.add(ex10);
        allStatements.add(ex11);
        return FXCollections.observableArrayList(allStatements);
    }
}
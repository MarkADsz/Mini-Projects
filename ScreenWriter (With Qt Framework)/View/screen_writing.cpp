#include "screen_writing.h"
#include <sstream>
#include <string.h>
#include <fstream>
screen_writing::screen_writing(Service& serv,Writer& w,QAbstractListModel* model, QWidget* parent)
    : QMainWindow(parent),serv(serv),w(w),model(model)
{
    ui.setupUi(this);
    this->setWindowTitle(QString::fromStdString(w.getName()));
    this->ui.listView->setModel(this->model);
    this->populate();
    
}

void screen_writing::populate()
{
    int a = this->model->rowCount();
    for (int i = 0; i < a; i++) {
        this->model->removeRow(0);
    }
    int n;
    for (auto id : this->serv.getIdeas_serv()) {
        n = this->model->rowCount();
        this->model->insertRow(n);
        QModelIndex index = this->model->index(n);
        this->model->setData(index, QString::fromStdString(id.toStr()));
    }
}
void screen_writing::addButton() {
    
    string name, de, cr;
    int act;
    name = this->ui.lineEdit_descr->text().toStdString();
    act = this->ui.lineEdit_act->text().toInt();
    de = "proposed";
    cr = this->w.getName();
    this->serv.add_Idea_serv(name, de, cr, act);
    this->populate();

}

void screen_writing::deleteButton() {
    //we get the data selected
    QModelIndexList index = this->ui.listView->selectionModel()->selectedIndexes();
    QString text = this->model->data(index.at(0)).toString();
    string g = text.toStdString();
    
    istringstream iss(g);
    string word;
    getline(iss, word, ' ');
    this->serv.deleteI_serv(word);
    this->populate();
    
}

void screen_writing::updateButton()
{
    this->serv.savetoFIle();

}

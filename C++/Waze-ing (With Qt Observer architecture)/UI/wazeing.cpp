#include "wazeing.h"
#include <string.h>
#include <sstream>
wazeing::wazeing(Service& serv, string& name,QWidget *parent)
: QMainWindow(parent), serv(serv) , name(name)
{
    ui.setupUi(this);
    this->serv.addObs(this);
    this->setWindowTitle(QString::fromStdString(this->name));
    string cl,CL, sc,st;
    for (auto i : this->serv.getDriversServ()) {
        if (i.getName().compare(this->name) == 0)
        {
            cl = to_string(i.getLat());
            CL = to_string(i.getLong());
            sc = to_string(i.getScore());
            st = i.getStatus();
            break;
        }
    }
    this->ui.label_currentLoc->setText(QString::fromStdString(cl));
    this->ui.label_currentLoc_2->setText(QString::fromStdString(CL));
    this->ui.label_Score->setText(QString::fromStdString(sc));
    this->ui.label_Status->setText(QString::fromStdString(st));
    this->ui.horizontalSlider->setValue(10);
    this->ui.label_radius->setText(QString::fromStdString(to_string(10)));
    this->populate();string stat = this->ui.label_Status->text().toStdString();
    if (stat.compare("baby") == 0)
        this->setStyleSheet("background-color: red;");
    else if (stat.compare("knight") == 0)
        this->setStyleSheet("background-color: green");
    else if (stat.compare("grown-up") == 0)
        this->setStyleSheet("background-color: blue");
    this->ui.listWidget->setStyleSheet("background-color: white");
}

void wazeing::populate()
{
    int r = this->ui.label_radius->text().toInt();
    int l = this->ui.label_currentLoc->text().toInt();
    int L = this->ui.label_currentLoc_2->text().toInt();
    

    vector<Report> gr;
    int dist,xa, xb, yb, ya;
    for (auto i : this->serv.getReportsServ()) {
        //distance=sqr((xb-xa)^2+(yb-ya)^2))
        xa = l;
        ya = L;
        xb = i.getLat();
        yb = i.getLong();
        dist = sqrt(((xb - xa) * (xb - xa)) + ((yb - ya) * (yb - ya)));
        if (dist <= r)
            gr.push_back(i);
    }
    this->ui.listWidget->clear();
    int count = 0;
    for (auto i : gr)
    {
       
        this->ui.listWidget->addItem(QString::fromStdString(i.toStr()));
        

        count++;
    }
    
}

void wazeing::update()
{
    this->populate();
}

void wazeing::north()
{   
    int old = this->ui.label_currentLoc_2->text().toInt();
    this->ui.label_currentLoc_2->setText(QString::fromStdString(to_string(old++)));
}

void wazeing::east()
{
    int old = this->ui.label_currentLoc->text().toInt();
    this->ui.label_currentLoc->setText(QString::fromStdString(to_string(old++)));
}

void wazeing::south()
{
    int old = this->ui.label_currentLoc_2->text().toInt();
    this->ui.label_currentLoc_2->setText(QString::fromStdString(to_string(old--)));
}

void wazeing::vest()
{
    int old = this->ui.label_currentLoc->text().toInt();
    this->ui.label_currentLoc->setText(QString::fromStdString(to_string(old--)));
}

void wazeing::slider() {
    int r = this->ui.horizontalSlider->value();
    this->ui.label_radius->setText(QString::fromStdString(to_string(r)));
    this->populate();
}
void wazeing::validate() {
    string name = this->windowTitle().toStdString();

    QModelIndexList ii = this->ui.listWidget->selectionModel()->selectedIndexes();
    int rr = ii.at(0).row();

    string text = this->ui.listWidget->item(rr)->text().toStdString();
    string fromtextname = "none";
    istringstream iss(text);

    if (text.find("not validated") != string::npos) {
        getline(iss, fromtextname);
        getline(iss, fromtextname);
        
    }

}


void wazeing::handle() {
   
    istringstream iss(this->ui.listWidget->currentItem()->text().toStdString());
    string word;
    getline(iss, word, ' ');
    getline(iss, word, ' ');
    this->serv.deleteReportServ(word);
    this->serv.notify();
   // this->ui.listWidget->addItem(QString::fromStdString(this->ui.listWidget->currentItem()->text().toStdString()));
}
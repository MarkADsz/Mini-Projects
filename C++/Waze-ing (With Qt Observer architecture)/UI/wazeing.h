#pragma once
#include "Service.h"
#include <QtWidgets/QMainWindow>
#include "ui_wazeing.h"
#include "Observer.h"
class wazeing : public QMainWindow, public Observer
{
    Q_OBJECT

public:
    wazeing(Service& serv,string& name,QWidget *parent = Q_NULLPTR);
    void populate();
    void update()override;
private:
    Ui::wazeingClass ui;
    Service& serv;
    string name;
public slots:
    void slider();
    void north();
    void east();
    void south();
    void vest();
    void validate();
    void handle();

};

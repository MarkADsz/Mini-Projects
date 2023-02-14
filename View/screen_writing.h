#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_screen_writing.h"
#include "Service.h"
#include "QAbstractListModel"
#include "QStringListModel"
class screen_writing : public QMainWindow
{
    Q_OBJECT

public:
    screen_writing(Service& serv,Writer& w, QAbstractListModel* model, QWidget *parent = Q_NULLPTR);
    void populate();
private:
    Ui::screen_writingClass ui;
    Service& serv;
    Writer& w;
    QAbstractListModel* model;
public slots:
    void addButton();
    void deleteButton();
    void updateButton();

};

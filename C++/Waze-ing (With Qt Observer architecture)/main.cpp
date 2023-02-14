#include "wazeing.h"
#include <QtWidgets/QApplication>
#include "Service.h"
int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    
    Repo r;
    Service s(r);
    vector<string> names;
    int j = 0;
    for (auto i : s.getDriversServ()) {
        names.push_back(i.getName());
        j++;
    }
    for (int i = 0; i < j; i++) {
        wazeing* w = new wazeing{ s,names[i] };
        w->setWindowTitle(QString::fromStdString(names[i]));
        w->show();
    }
    //wazeing w(s,n);
    //w.show();
    return a.exec();
}

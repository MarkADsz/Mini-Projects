#include "screen_writing.h"
#include <QtWidgets/QApplication>
#include "qapplication.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Repo r;
    Service s(r);
    auto* model=new QStringListModel;
    for (auto i : s.getWriters_serv()) {
        screen_writing* w = new screen_writing{ s,i,model };
        w->show();
    }

    
   
    return a.exec();
}

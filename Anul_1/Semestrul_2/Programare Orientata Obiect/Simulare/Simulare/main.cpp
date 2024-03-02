#include <QtCore/QCoreApplication>
#include <QtWidgets>

#include "Service.h"
#include "GUI.h"

int main(int argc, char *argv[])
{
    testServive();

    QApplication a(argc, argv);

    Repo repo;
    Service srv{ repo };
    srv.readFile();
    GUI gui{ srv };

    gui.reloadList(srv.getAll());
    gui.buildUI();
    gui.connect();
    gui.show();
   


    return a.exec();
}

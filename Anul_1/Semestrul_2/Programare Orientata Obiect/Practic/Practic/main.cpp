#include <QtWidgets>
#include"GUI.h"

int main(int argc, char *argv[])
{
    testService();

    QApplication a(argc, argv);

    Repo rep{ "text.txt" };
    Service srv{ rep };
    srv.loadFromFile();

    GUI gui{ srv };
    gui.buildUI();
    gui.connects();
    gui.reloadList();

    gui.show();

    return a.exec();
}

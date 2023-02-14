#pragma once
using namespace std;
#include <vector>
class Observer {
public:
	virtual void update() = 0;
};

class Subject {
private:
	vector<Observer*> observers;
public:

	void addObs(Observer* obs);
	void delObs(Observer* obs);
	void notify();
};
#include "Observer.h"

void Subject::addObs(Observer* obs)
{
	this->observers.push_back(obs);
}

void Subject::delObs(Observer* obs)
{
	auto it = find(this->observers.begin(), this->observers.end(), obs);
	this->observers.erase(it);
}

void Subject::notify()
{
	for (auto& o : this->observers) {
		o->update();
	}
}

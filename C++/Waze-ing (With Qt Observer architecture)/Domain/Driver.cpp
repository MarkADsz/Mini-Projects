#include "Driver.h"

Driver::Driver()
{
}

Driver::Driver(string& name, string& status, pair<int, int> location, int score)
{
	this->name = name;
	this->status = status;
	this->currentLocation = location;
	this->score = score;
}

int Driver::getLat()
{
	return this->currentLocation.first;
}

int Driver::getLong()
{
	return this->currentLocation.second;
}

int Driver::getScore()
{
	return this->score;
}

string Driver::getName()
{
	return this->name;
}

string Driver::getStatus()
{
	return this->status;
}

string Driver::toStr()
{
	return "Driver " + this->name + " being a " + this->status + ", having location: lat-> " + to_string(this->currentLocation.first) + " and long-> " + to_string(this->currentLocation.second) + " has score " + to_string(this->score);
}

#include "Report.h"

Report::Report()
{
}

Report::Report(string& description, string& rep, pair<int, int> location,int valid)
{
	this->description = description;
	this->reporter = rep;
	this->location = location;
	this->validated = valid;
}

string Report::getDescr()
{
	return this->description;
}

string Report::getRep()
{
	return this->reporter;
}

int Report::getLat()
{
	return this->location.first;
}

int Report::getLong()
{
	return this->location.second;
}

int Report::getValid() {
	return this->validated;
}

string Report::toStr()
{	
	if(this->validated==1)
	return "Report " + this->description + " reported by " + this->reporter + " at location: lat-> " + to_string(this->location.first) + "and long-> " + to_string(this->location.second)+" is validated";
	else
	return "Report " + this->description + " reported by " + this->reporter + " at location: lat-> " + to_string(this->location.first) + "and long-> " + to_string(this->location.second)+" is not validated";
}

#include "Ideas.h"

Idea::Idea()
{
}

Idea::Idea(string& desc, string& stat, string& cr, int act)
{
	this->description = desc;
	this->status = stat;
	this->creator = cr;
	this->act = act;
}

string Idea::getDes()
{
	return this->description;
}

string Idea::getStat()
{
	return this->status;
}

string Idea::getCreat()
{
	return this->creator;
}

int Idea::getAct()
{
	return this->act;
}

string Idea::toStr()
{
	
	return this->description + " being " + this->status + " created by " + this->creator + " on act " + to_string(this->act);
}

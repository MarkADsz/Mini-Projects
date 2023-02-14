#include "Writers.h"

Writer::Writer()
{
}

Writer::Writer(string& name, string& expertise)
{
	this->name = name;
	this->expertise = expertise;
}

string Writer::getName()
{
	return this->name;
}

string Writer::getEpertise()
{
	return this->expertise;
}

string Writer::toStr()
{
	return this->name + " " + this->expertise;
}

#include "Repo.h"

Repo::Repo()
{
}

vector<Idea> Repo::getIdeas()
{
	return this->ideas;
}

vector<Writer> Repo::getWriters()
{
	return this->writers;
}

void Repo::add_Idea(Idea& id)
{
	this->ideas.push_back(id);
}

void Repo::add_Writer(Writer& w)
{
	this->writers.push_back(w);
}

void Repo::dellI(int c)
{
	this->ideas.erase(this->ideas.begin() + c);
}

#pragma once
#include "Ideas.h"
#include "Writers.h"

#include <vector>
class Repo {
private:
	vector<Idea> ideas;
	vector<Writer> writers;
public:
	Repo();
	vector<Idea> getIdeas();
	vector<Writer> getWriters();
	void add_Idea(Idea& id);
	void add_Writer(Writer& w);
	void dellI(int c);
};

#pragma once
#include "Repo.h"
class Service {

private:
	Repo& r;
public:
	Service(Repo& r);
	vector<Idea> getIdeas_serv();
	vector<Writer> getWriters_serv();
	void add_Idea_serv(string& desc, string& stat, string& cr, int act);
	void add_W_serv(string& name, string& desc);
	void ReadW();
	void ReadI();
	void deleteI_serv(string& name);
	void savetoFIle();
};

#include "Service.h"
#include <fstream>


Service::Service(Repo& r):r(r)
{
	this->ReadI();
	this->ReadW();
}

vector<Idea> Service::getIdeas_serv()
{
	return this->r.getIdeas();
}

vector<Writer> Service::getWriters_serv()
{
	return this->r.getWriters();
}

void Service::add_Idea_serv(string& desc, string& stat, string& cr, int act)
{
	Idea in{ desc,stat,cr,act };
	this->r.add_Idea(in);
}

void Service::add_W_serv(string& name, string& desc)
{
	Writer wn{ name,desc };
	this->r.add_Writer(wn);
}

void Service::ReadW()
{
	ifstream f;
	f.open("writers.txt");
	string n, name, de;
	getline(f, n);
	int N = stoi(n);
	for (int i = 0; i < N; i++) {
		getline(f, name);
		getline(f, de);
		this->add_W_serv(name,de);

	}
	f.close();

}

void Service::ReadI()
{
	ifstream f;
	f.open("ideas.txt");
	string n, name, de,cr,act;
	getline(f, n);
	int N = stoi(n);
	for (int i = 0; i < N; i++) {
		getline(f, name);
		getline(f, de);
		getline(f, cr);
		getline(f, act);
		this->add_Idea_serv(name,de,cr,stoi(act));

	}
	f.close();
}

void Service::deleteI_serv(string& name)
{
	int c=0;
	for (auto i : this->r.getIdeas())
	{
		if (i.getDes().compare(name) == 0)
		{
			this->r.dellI(c);
			break;
		}
		c++;
	}
}

void Service::savetoFIle()
{
	ofstream oof;
	oof.open("ideas.txt");
	oof << this->r.getIdeas().size()<<endl;
	for (auto i : this->r.getIdeas()) {
		oof << i.getDes() << endl;
		oof << i.getStat() << endl;
		oof << i.getCreat() << endl;
		oof << i.getAct() << endl;

	}
	oof.close();
}

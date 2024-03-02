#pragma once
#include<string>

using std::string;

class Device
{
	int an, pret;
	string model, tip, culoare, serie;

public:
	Device(string serie, string model, string tip, string culoare, int an, int pret):
		serie{serie}, model{model}, tip{tip}, culoare{culoare}, an{an}, pret{pret}{}

	Device(const Device &d) : serie{ d.serie }, model{ d.model }, tip{ d.tip }, culoare{ d.culoare }, an{ d.an }, pret{ d.pret } {}

	// returneaza an
	int getAn()
	{
		return an;
	}

	// returneaza pret
	int getPret()
	{
		return pret;
	}

	// returneaza serie
	string getSerie()
	{
		return serie;
	}

	// returneaza model
	string getModel()
	{
		return model;
	}

	// returneaza tip
	string getTip()
	{
		return tip;
	}

	// returneaza culoare
	string getCuloare()
	{
		return culoare;
	}
};


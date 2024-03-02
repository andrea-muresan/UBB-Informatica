#pragma once
#include <vector>
#include "Device.h"

using std::vector;
class Repo
{
	vector<Device> all;

public:
	Repo() = default;
	Repo(const Repo& repo) = delete;

	vector<Device> getAll();

	void readFile();
};


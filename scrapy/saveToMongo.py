# -*- coding: utf-8 -*-
import csv

def saveAsCSV(data, filename):
    with open(filename, 'a') as f:
        f_csv = csv.writer(f)
        # f_csv.writerows(data)
        f_csv.writerows(data)


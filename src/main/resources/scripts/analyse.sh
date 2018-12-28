#!/bin/bash

#1 path to analysis folder, 2 environment name
cd $1
source activate snakemake
conda activate $2
snakemake

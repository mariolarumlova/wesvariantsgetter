#!/bin/bash

#1 path to analysis folder, 2 environment name, 3 path to genome, 4 genome.index, 5 genome.ext
source activate snakemake
conda activate $2

#1 path to genome, 2 genome.index, 3 genome.ext
mkdir $3
cd $3
cd ..
#indexing reference genome in BWA
bwa index $4$5
#add also for Bowtie2
#bowtie2-build IDX.EXT bowtie2/IDX
bowtie2-build $4$5 $4

cd $1
snakemake

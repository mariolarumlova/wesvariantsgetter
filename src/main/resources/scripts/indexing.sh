#!/bin/bash

cd $1/genomes/

#indexing reference genome in BWA
bwa index IDX.EXT

#add also for Bowtie2
#bowtie2-build IDX.EXT bowtie2/IDX
bowtie2-build IDX.EXT IDX

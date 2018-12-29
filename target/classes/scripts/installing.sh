#!/bin/bash

#1 path to miniconda, 2 environment name, 3 path to resources, 4 path to programs

#cd $1
wget https://repo.continuum.io/miniconda/Miniconda3-latest-Linux-x86_64.sh -O $1miniconda.sh
bash $1miniconda.sh -b -p $1miniconda
export PATH="$1miniconda/bin:$PATH"
hash -r
conda config --set always_yes yes --set changeps1 no
conda update -q conda
# Useful for debugging any issues with conda
conda info -a
conda config --add channels defaults
conda config --add channels conda-forge
conda config --add channels bioconda
conda create -q -n snakemake snakemake>=5.1.2 python=3.6
#conda create -q -n $2
conda env create --name $2 --file $3environment.yaml
source activate $2

mkdir $4
cd $4

wget https://sourceforge.net/projects/snvsniffer/files/latest/download/SNVSniffer-v2.0.4_bin_x86_64.tar.gz
tar xvzf SNVSniffer-v2.0.4_bin_x86_64.tar.gz
#gunzip SNVSniffer-v2.0.4_bin_x86_64.tar.gz

wget http://147.8.193.36:8080/hkubioinfo/FaSD-somatic/FaSD-somatic.zip
unzip FaSD-somatic.zip

#installing Miniconda3
#wget https://repo.continuum.io/miniconda/Miniconda3-latest-Linux-x86_64.sh
#bash Miniconda-3-latest-Linux-x86_64.sh
# delete the installer after successful run
#rm Miniconda3-latest-Linux-x86_64.sh
#echo 'export PATH="/home/$USER$/miniconda3/bin:$PATH"' >> ~/.bashrc
#echo 'export PATH="/home/$USER$/miniconda3/bin:$PATH"' >> ~/.zshrc
#echo ". /home/$USER$/miniconda3/etc/profile.d/conda.sh" >> ~/.bashrc

#installing Snakemake
#conda install -c bioconda -c conda-forge snakemake

#conda update conda
#conda env create --name ngs --file $1/environment.yaml
# activate the environment
#conda activate ngs